# 🚀 Resumo: Scripts de Deploy para Nuvem

## ✅ Arquivos Criados

### 1. **`bin/deploy-to-cloud.sh`** (Script Principal de Deploy)

**Função**: Automatiza todo o processo de deploy da máquina local para o servidor na nuvem

**O que faz:**

- ✅ Constrói a imagem Docker localmente (`./bin/docker-build.sh`)
- ✅ Salva a imagem em arquivo `.tar` (para transferência)
- ✅ Cria diretório remoto via SSH
- ✅ Copia arquivos via SCP:
  - Imagem Docker (`.tar`)
  - Configuração Docker Compose (`app-cloud.yml`)
  - Configuração PostgreSQL (`postgresql.yml`)
  - Script de execução (`docker-run-cloud.sh`)
- ✅ Carrega a imagem Docker no servidor remoto
- ✅ Limpa arquivos temporários locais

**Uso:**

```bash
./bin/deploy-to-cloud.sh
```

---

### 2. **`bin/docker-run-cloud.sh`** (Script para Rodar no Servidor)

**Função**: Executa a aplicação no servidor remoto

**O que faz:**

- ✅ Verifica se arquivos de configuração existem
- ✅ Para containers antigos (se existirem)
- ✅ Sobe os containers (app + PostgreSQL)
- ✅ Mostra status dos containers
- ✅ Exibe instruções de uso

**Uso (no servidor remoto):**

```bash
cd /home/tech_thermography/thermography-api
./docker-run-cloud.sh
```

---

### 3. **`src/main/docker/app-cloud.yml`** (Configuração Docker Compose para Nuvem)

**Função**: Define a infraestrutura Docker otimizada para produção

**Características:**

- ✅ **Aplicação Java**:

  - Memória: 1GB max, 512MB min
  - Profile: `prod`
  - Porta: 8080 (exposta externamente)
  - Health check configurado
  - Restart automático: `unless-stopped`
  - Kafka desabilitado

- ✅ **PostgreSQL**:

  - Versão: 17.4
  - Volume nomeado para persistência (`postgres-data`)
  - Senha configurável via variável de ambiente
  - Health check configurado
  - Restart automático: `unless-stopped`

- ✅ **Rede isolada**: `thermography-network`
- ✅ **Volume persistente**: `postgres-data` (dados não são perdidos ao reiniciar)

---

### 4. **`bin/README-DEPLOY.md`** (Documentação Completa)

**Função**: Guia completo de deploy e operação

**Conteúdo:**

- ✅ Pré-requisitos
- ✅ Passo a passo do deploy
- ✅ Configuração de segurança (senha PostgreSQL)
- ✅ Comandos úteis (logs, restart, backup, etc.)
- ✅ Monitoramento e troubleshooting
- ✅ Configuração de firewall
- ✅ Recomendações de segurança

---

## 🎯 Como Usar (Passo a Passo Simplificado)

### Na sua máquina local:

```bash
# 1. Execute o deploy
./bin/deploy-to-cloud.sh
```

### No servidor remoto (35.247.197.28):

```bash
# 2. Conecte-se via SSH
ssh -i ~/.ssh/tech.thermography tech_thermography@35.247.197.28

# 3. Navegue até o diretório
cd /home/tech_thermography/thermography-api

# 4. Execute a aplicação
./docker-run-cloud.sh

# 5. Verifique se está funcionando
curl http://localhost:8080/management/health
```

### Acesse a aplicação:

- **URL**: http://35.247.197.28:8080
- **Swagger**: http://35.247.197.28:8080/swagger-ui/index.html

---

## 🔐 Configuração de Segurança (Importante!)

### Alterar senha do PostgreSQL:

No servidor remoto, crie um arquivo `.env`:

```bash
cd /home/tech_thermography/thermography-api/docker
cat > .env << EOF
POSTGRES_PASSWORD=SuaSenhaSeguraAqui123!
EOF

# Reinicie os containers
cd ..
docker compose -f docker/app-cloud.yml down
docker compose -f docker/app-cloud.yml up -d
```

---

## 📊 Diferenças: Local vs Nuvem

| Aspecto               | Local (`app.yml`)               | Nuvem (`app-cloud.yml`)          |
| --------------------- | ------------------------------- | -------------------------------- |
| **Porta**             | `127.0.0.1:8080` (apenas local) | `8080:8080` (exposta)            |
| **Memória**           | 512MB max                       | 1GB max                          |
| **Volume PostgreSQL** | `~/volumes/...` (bind mount)    | `postgres-data` (volume nomeado) |
| **Restart**           | Não configurado                 | `unless-stopped`                 |
| **Kafka**             | Incluído (comentado)            | Removido                         |
| **Rede**              | Default                         | `thermography-network` (isolada) |
| **Senha PostgreSQL**  | `trust` (sem senha)             | Variável de ambiente             |

---

## 🛠️ Comandos Úteis no Servidor

```bash
# Ver logs em tempo real
docker compose -f docker/app-cloud.yml logs -f app

# Parar aplicação
docker compose -f docker/app-cloud.yml down

# Reiniciar aplicação
docker compose -f docker/app-cloud.yml restart

# Ver status
docker ps

# Backup do banco
docker exec thermographyapi-postgresql pg_dump -U thermographyApi thermographyApi > backup.sql

# Acessar banco de dados
docker exec -it thermographyapi-postgresql psql -U thermographyApi -d thermographyApi
```

---

## 🔥 Configurar Firewall (Google Cloud)

```bash
gcloud compute firewall-rules create allow-thermography \
  --allow tcp:8080 \
  --source-ranges 0.0.0.0/0 \
  --description "Allow Thermography API"
```

---

## 📁 Estrutura de Arquivos

### Local (sua máquina):

```
bin/
├── deploy-to-cloud.sh       ← Script de deploy
├── docker-run-cloud.sh      ← Script para servidor
├── docker-build.sh          ← Build da imagem
└── README-DEPLOY.md         ← Documentação

src/main/docker/
├── app-cloud.yml            ← Config para nuvem
└── postgresql.yml           ← Config PostgreSQL
```

### Remoto (servidor na nuvem):

```
/home/tech_thermography/thermography-api/
├── docker/
│   ├── app-cloud.yml
│   ├── postgresql.yml
│   └── .env (criar manualmente)
└── docker-run-cloud.sh
```

---

## ✅ Checklist de Deploy

- [ ] Docker instalado localmente
- [ ] Docker instalado no servidor remoto
- [ ] Chave SSH configurada (`~/.ssh/tech.thermography`)
- [ ] Executar `./bin/deploy-to-cloud.sh`
- [ ] Conectar ao servidor via SSH
- [ ] Executar `./docker-run-cloud.sh`
- [ ] Configurar senha do PostgreSQL (`.env`)
- [ ] Configurar firewall (porta 8080)
- [ ] Testar acesso: http://35.247.197.28:8080
- [ ] Verificar health check
- [ ] Configurar backup automático (opcional)

---

## 🎓 Explicação Técnica

### Por que usar volume nomeado em vez de bind mount?

**Bind Mount** (`~/volumes/...`):

- ❌ Depende do caminho do host
- ❌ Problemas de permissão
- ❌ Não portável entre sistemas

**Volume Nomeado** (`postgres-data`):

- ✅ Gerenciado pelo Docker
- ✅ Sem problemas de permissão
- ✅ Portável e independente do host
- ✅ Melhor performance
- ✅ Recomendado para produção

### Por que usar rede isolada?

- ✅ Segurança: containers isolados da rede host
- ✅ Comunicação interna: app ↔ PostgreSQL via nome do serviço
- ✅ Controle: apenas portas necessárias expostas

---

## 📞 Suporte

Para mais detalhes, consulte: `bin/README-DEPLOY.md`
