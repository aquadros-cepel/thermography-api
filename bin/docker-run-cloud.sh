#!/bin/bash
set -e

echo "======================================"
echo "  JHipster Docker Cloud Runner"
echo "======================================"

COMPOSE_FILE="docker/app-cloud.yml"

echo ""
echo "👉 Verificando se o arquivo de configuração existe"
if [ ! -f "$COMPOSE_FILE" ]; then
    echo "❌ Erro: Arquivo $COMPOSE_FILE não encontrado!"
    echo "   Certifique-se de que o deploy foi executado corretamente."
    exit 1
fi

echo ""
echo "👉 Parando containers antigos (se existirem)"
docker compose -f $COMPOSE_FILE down --remove-orphans 2>/dev/null || true

echo ""
echo "👉 Subindo ambiente Docker"
docker compose -f $COMPOSE_FILE up -d

echo ""
echo "⏳ Aguardando containers iniciarem..."
sleep 5

echo ""
echo "✅ Containers ativos:"
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

echo ""
echo "📊 Logs da aplicação:"
echo "   docker compose -f $COMPOSE_FILE logs -f app"

echo ""
echo "🔍 Verificar saúde da aplicação:"
echo "   curl http://localhost:8080/management/health"

echo ""
echo "🛑 Para parar a aplicação:"
echo "   docker compose -f $COMPOSE_FILE down"

echo ""
echo "✅ Aplicação disponível em:"
echo "   http://$(hostname -I | awk '{print $1}'):8080"
echo "   ou"
echo "   http://35.247.197.28:8080"
echo ""
