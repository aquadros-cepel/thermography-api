#!/bin/bash
set -e

echo "======================================"
echo "  JHipster Docker Local Runner"
echo "======================================"

COMPOSE_FILE="src/main/docker/app.yml"

echo ""
echo "👉 Subindo ambiente Docker"
docker compose -f $COMPOSE_FILE down -v --remove-orphans
docker compose -f $COMPOSE_FILE up

echo ""
echo "✅ Containers ativos:"
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

echo ""
echo "✅ Aplicação disponível em:"
echo "   http://localhost:8080"
