## POC - Sidecar Observabilidade

Este projeto implementa dois microsserviços simples que se comunicam via HTTP, demonstrando:
- Isolamento de domínio: Cada serviço tem seu próprio banco de dados
- Comunicação entre serviços: Via HTTP REST usando Feign Client
- Sidecar Pattern: OpenTelemetry Collector como componente de infraestrutura separado
- Observabilidade: Tracing distribuído com Jaeger

### Domínio
Sistema: Painel de Rotinas
- rotina-service: Gerencia rotinas (ex: "Estudar DDD", "Caminhar")
- registro-service: Registra execuções de rotinas em datas específicas

### Arquitetura
``` ┌─────────────────────────────────────────────┐
│         Aplicações (Local - Java)           │
│                                             │
│  ┌──────────────┐      ┌──────────────┐    │
│  │rotina-service│      │registro-srv  │    │
│  │  (porta 8081)│◄─────┤  (porta 8082)│    │
│  │  [H2: rotinadb]     │[H2: registrodb]   │
│  └──────┬───────┘      └──────┬───────┘    │
│         │                     │            │
│         │   Traces (OTLP)     │            │
│         └──────┬──────────────┘            │
└────────────────│──────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────┐
│       Containers Docker (Infraestrutura)    │
│                                             │
│  ┌──────────────────────┐                  │
│  │  otel-collector      │  ◄─ SIDECAR      │
│  │    (porta 4318)      │                  │
│  └──────────┬───────────┘                  │
│             │                               │
│             ▼                               │
│  ┌──────────────────────┐                  │
│  │      Jaeger          │                  │
│  │   (porta 16686)      │                  │
│  └──────────────────────┘                  │
└─────────────────────────────────────────────┘
```

### Instalação e Execução
 1. Clonar o Repositório
 `git clone git@github.com:antonialucianapires/service-mesh-app-rotina.git`
Em seguida, `cd service-mesh-app-rotina`.

2. Dar Permissão aos Scripts
```
chmod +x exec.sh stop.sh
```
3. Executar o Projeto
```/
exec.sh
```
O script exec.sh automaticamente:


### Testar endpoints
curl http://localhost:8081/api/rotinas
curl http://localhost:8082/api/registros