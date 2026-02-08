#!/bin/bash

set -e

echo "=== Build rotina-service ==="
cd rotina-service
./mvnw clean package -DskipTests
cd ..

echo ""
echo "=== Build registro-service ==="
cd registro-service
./mvnw clean package -DskipTests
cd ..

echo ""
echo "=== Parar containers antigos ==="
docker-compose down 2>/dev/null || true

echo ""
echo "=== Subir infraestrutura (Jaeger + OpenTelemetry Collector) ==="
docker-compose up -d

echo ""
echo "=== Aguardando infraestrutura iniciar ==="
sleep 5

echo ""
echo "=== Iniciando rotina-service com OpenTelemetry Agent ==="
cd rotina-service
java --add-opens=java.base/java.lang=ALL-UNNAMED \
  --add-opens=java.base/java.util=ALL-UNNAMED \
  -javaagent:../opentelemetry-javaagent.jar \
  -Dotel.service.name=rotina-service \
  -Dotel.traces.exporter=otlp \
  -Dotel.exporter.otlp.endpoint=http://localhost:4318 \
  -Dotel.exporter.otlp.protocol=http/protobuf \
  -Dotel.metrics.exporter=none \
  -Dotel.logs.exporter=none \
  -jar target/rotina-service-0.0.1-SNAPSHOT.jar > ../rotina-service.log 2>&1 &
ROTINA_PID=$!
echo "rotina-service PID: $ROTINA_PID"
cd ..

echo ""
echo "=== Aguardando rotina-service iniciar ==="
sleep 20

echo ""
echo "=== Iniciando registro-service com OpenTelemetry Agent ==="
cd registro-service
java --add-opens=java.base/java.lang=ALL-UNNAMED \
  --add-opens=java.base/java.util=ALL-UNNAMED \
  -javaagent:../opentelemetry-javaagent.jar \
  -Dotel.service.name=registro-service \
  -Dotel.traces.exporter=otlp \
  -Dotel.exporter.otlp.endpoint=http://localhost:4318 \
  -Dotel.exporter.otlp.protocol=http/protobuf \
  -Dotel.metrics.exporter=none \
  -Dotel.logs.exporter=none \
  -jar target/registro-service-0.0.1-SNAPSHOT.jar > ../registro-service.log 2>&1 &
REGISTRO_PID=$!
echo "registro-service PID: $REGISTRO_PID"
cd ..

echo ""
echo "========================================="
echo "POC Service Mesh Rodando!"
echo "========================================="
echo ""
echo "Aguarde 20 segundos para tudo iniciar..."
sleep 20
echo ""
echo "Serviços:"
echo "  rotina-service:   http://localhost:8081/api/rotinas"
echo "  registro-service: http://localhost:8082/api/registros"
echo ""
echo "Observabilidade:"
echo "  Jaeger UI:        http://localhost:16686"
echo ""
echo "Logs:"
echo "  rotina-service:   tail -f rotina-service.log"
echo "  registro-service: tail -f registro-service.log"
echo ""
echo "Para parar tudo:"
echo "  ./stop.sh"
echo ""
echo "PIDs salvos em: pids.txt"
echo "$ROTINA_PID" > pids.txt
echo "$REGISTRO_PID" >> pids.txt

echo ""
echo "=== Testando endpoints ==="
curl -X POST http://localhost:8081/api/rotinas \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste Inicial","descricao":"Verificar tracing"}'

echo ""
echo ""
echo "Acesse o Jaeger: http://localhost:16686"
echo "Agora deve aparecer rotina-service e registro-service!"