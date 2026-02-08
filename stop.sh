#!/bin/bash

echo "=== Parando serviços ==="

if [ -f pids.txt ]; then
    while read pid; do
        if ps -p $pid > /dev/null 2>&1; then
            echo "Matando processo $pid"
            kill $pid
        fi
    done < pids.txt
    rm pids.txt
else
    echo "Matando todos os processos Java dos serviços..."
    pkill -f "rotina-service"
    pkill -f "registro-service"
fi

echo ""
echo "=== Parando containers Docker ==="
docker-compose down

echo ""
echo "Tudo parado!"