#!/bin/bash
set -e

echo "======================================"
echo "  Deploy para Máquina na Nuvem"
echo "======================================"

# Configurações
REMOTE_HOST="34.95.132.119"
REMOTE_USER="tech.thermography"
SSH_KEY="~/.ssh/tech.thermography"
REMOTE_DIR="/home/tech.thermography/thermography-api"
SSH_OPTS="-i $SSH_KEY -o IdentitiesOnly=yes"

echo ""
echo "📦 Passo 1: Construindo imagem Docker localmente"
./bin/docker-build.sh

echo ""
echo "💾 Passo 2: Salvando imagem Docker em arquivo tar"
docker save thermographyapi:latest -o thermographyapi-image.tar
echo "✅ Imagem salva: thermographyapi-image.tar ($(du -h thermographyapi-image.tar | cut -f1))"

echo ""
echo "📤 Passo 3: Verificando/criando diretório remoto"
#mkdir -p é idempotente - não gera erro se o diretório já existe
ssh $SSH_OPTS $REMOTE_USER@$REMOTE_HOST "mkdir -p $REMOTE_DIR/docker"

echo ""
echo "📤 Passo 4: Copiando arquivos para servidor remoto"
echo "   → Copiando imagem Docker..."
scp $SSH_OPTS thermographyapi-image.tar $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/

echo "   → Copiando arquivos de configuração Docker..."
scp $SSH_OPTS src/main/docker/app-cloud.yml $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/docker/
scp $SSH_OPTS src/main/docker/postgresql.yml $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/docker/

echo "   → Copiando script de execução..."
scp $SSH_OPTS bin/docker-run-cloud.sh $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/

echo ""
echo "🐳 Passo 5: Carregando imagem Docker no servidor remoto"
ssh $SSH_OPTS $REMOTE_USER@$REMOTE_HOST "cd $REMOTE_DIR && docker load -i thermographyapi-image.tar"

echo ""
echo "🧹 Passo 6: Limpando arquivo tar local"
rm thermographyapi-image.tar

echo ""
echo "✅ Deploy concluído com sucesso!"
echo ""
echo "📋 Próximos passos:"
echo "   1. Conecte-se ao servidor:"
echo "      ssh -i $SSH_KEY $REMOTE_USER@$REMOTE_HOST"
echo ""
echo "   2. Navegue até o diretório:"
echo "      cd $REMOTE_DIR"
echo ""
echo "   3. Execute a aplicação:"
echo "      ./docker-run-cloud.sh"
echo ""
echo "   4. Acesse a aplicação em:"
echo "      http://$REMOTE_HOST:8080"
echo ""
