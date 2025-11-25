# 🚀 Guia de Deploy para Nuvem

## 📋 Pré-requisitos

1. **Docker instalado localmente** (para build da imagem)
2. **Docker instalado no servidor remoto**
3. **Acesso SSH configurado** com a chave `~/.ssh/tech.thermography`
4. **Servidor remoto**: `35.247.197.28`

---

## 🎯 Deploy Rápido (Passo a Passo)

### 1️⃣ Execute o script de deploy (na sua máquina local)

```bash
chmod +x bin/deploy-to-cloud.sh
./bin/deploy-to-cloud.sh
```

**O que este script faz:**

- ✅ Constrói a imagem Docker localmente
- ✅ Salva a imagem em arquivo `.tar`
- ✅ Copia a imagem e arquivos de configuração via SCP
- ✅ Carrega a imagem no servidor remoto
- ✅ Limpa arquivos temporários

---

### 2️⃣ Conecte-se ao servidor remoto

```bash
ssh -i ~/.ssh/tech.thermography tech_thermography@35.247.197.28
```

---

### 3️⃣ Navegue até o diretório da aplicação

```bash
cd /home/tech_thermography/thermography-api
```

---

### 4️⃣ Execute a aplicação

```bash
chmod +x docker-run-cloud.sh
./docker-run-cloud.sh
```

---

### 5️⃣ Verifique se está funcionando

```bash
# Verificar containers
docker ps

# Verificar logs
docker compose -f docker/app-cloud.yml logs -f app

# Testar health check
curl http://localhost:8080/management/health
```

---

## 🌐 Acessar a Aplicação

- **URL Externa**: http://35.247.197.28:8080
- **Swagger UI**: http://35.247.197.28:8080/swagger-ui/index.html
- **Health Check**: http://35.247.197.28:8080/management/health

---

## 🔐 Configurar Senha do PostgreSQL (Recomendado)

### No servidor remoto:

```bash
# Criar arquivo .env
cat > /home/tech_thermography/thermography-api/docker/.env << EOF
POSTGRES_PASSWORD=SuaSenhaSeguraAqui123!
EOF

# Reiniciar containers
cd /home/tech_thermography/thermography-api
docker compose -f docker/app-cloud.yml down
docker compose -f docker/app-cloud.yml up -d
```

---

## 🛠️ Comandos Úteis

### Ver logs em tempo real

```bash
docker compose -f docker/app-cloud.yml logs -f app
```

### Parar aplicação

```bash
docker compose -f docker/app-cloud.yml down
```

### Reiniciar aplicação

```bash
docker compose -f docker/app-cloud.yml restart
```

### Ver status dos containers

```bash
docker ps
```

### Acessar banco de dados

```bash
docker exec -it thermographyapi-postgresql psql -U thermographyApi -d thermographyApi
```

### Backup do banco de dados

```bash
docker exec thermographyapi-postgresql pg_dump -U thermographyApi thermographyApi > backup.sql
```

### Restaurar backup

```bash
cat backup.sql | docker exec -i thermographyapi-postgresql psql -U thermographyApi -d thermographyApi
```

---

## 🔄 Atualizar Aplicação (Re-deploy)

```bash
# Na sua máquina local
./bin/deploy-to-cloud.sh

# No servidor remoto
cd /home/tech_thermography/thermography-api
docker compose -f docker/app-cloud.yml down
docker compose -f docker/app-cloud.yml up -d
```

---

## 📊 Monitoramento

### Verificar uso de recursos

```bash
docker stats
```

### Verificar espaço em disco

```bash
df -h
docker system df
```

### Limpar recursos não utilizados

```bash
docker system prune -a
```

---

## 🔥 Firewall (Importante!)

Certifique-se de que a porta 8080 está aberta no firewall do servidor:

```bash
# Google Cloud (gcloud)
gcloud compute firewall-rules create allow-thermography \
  --allow tcp:8080 \
  --source-ranges 0.0.0.0/0 \
  --description "Allow Thermography API"

# UFW (Ubuntu)
sudo ufw allow 8080/tcp
sudo ufw reload
```

---

## 🐛 Troubleshooting

### Aplicação não inicia

```bash
# Ver logs detalhados
docker compose -f docker/app-cloud.yml logs app

# Verificar se PostgreSQL está saudável
docker compose -f docker/app-cloud.yml ps
```

### Erro de conexão com banco de dados

```bash
# Verificar se PostgreSQL está rodando
docker ps | grep postgresql

# Testar conexão
docker exec thermographyapi-postgresql pg_isready -U thermographyApi
```

### Porta 8080 já em uso

```bash
# Ver o que está usando a porta
sudo lsof -i :8080

# Ou
sudo netstat -tulpn | grep 8080
```

---

## 📁 Estrutura de Arquivos no Servidor

```
/home/tech_thermography/thermography-api/
├── docker/
│   ├── app-cloud.yml          # Configuração Docker Compose
│   ├── postgresql.yml         # Configuração PostgreSQL
│   └── .env                   # Variáveis de ambiente (criar manualmente)
├── docker-run-cloud.sh        # Script para rodar aplicação
└── thermographyapi-image.tar  # Imagem Docker (temporário)
```

---

## 🔒 Segurança (Recomendações)

1. **Altere a senha padrão do PostgreSQL**
2. **Configure HTTPS com certificado SSL**
3. **Use um proxy reverso (Nginx)**
4. **Configure backup automático do banco**
5. **Monitore logs de segurança**
6. **Mantenha o sistema atualizado**

---

## 📞 Suporte

Em caso de problemas, verifique:

1. Logs da aplicação
2. Logs do PostgreSQL
3. Conectividade de rede
4. Espaço em disco disponível
5. Memória RAM disponível
