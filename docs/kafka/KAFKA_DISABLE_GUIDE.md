# Guia: Como Desabilitar Temporariamente o Kafka

## Opção 1: Desabilitar via Configuração (RECOMENDADO)

### Método A: Adicionar configuração no application-dev.yml (APLICADO) ✓

Adicionadas as seguintes linhas no arquivo `src/main/resources/config/application-dev.yml`:

```yaml
spring:
  autoconfigure:
    exclude:
      - org.springframework.cloud.stream.config.BindingServiceConfiguration
  cloud:
    stream:
      enabled: false
```

**Nota:** A classe `KafkaBinderConfiguration` não deve ser incluída na exclusão pois não é uma auto-configuração válida.

### Método B: Usar variável de ambiente

Execute a aplicação com:

```bash
java -jar target/*.jar --spring.cloud.stream.kafka.binder.brokers=PLAINTEXT://localhost:9092 --spring.autoconfigure.exclude=org.springframework.cloud.stream.config.BindingServiceConfiguration
```

Ou adicione no seu script de execução:

```bash
export SPRING_AUTOCONFIGURE_EXCLUDE=org.springframework.cloud.stream.config.BindingServiceConfiguration,org.springframework.cloud.stream.binder.kafka.config.KafkaBinderConfiguration
./mvnw spring-boot:run
```

## Opção 2: Comentar a Configuração no application.yml

Edite `src/main/resources/config/application.yml` e comente as linhas do Kafka:

```yaml
spring:
  # cloud:
  #   function:
  #     definition: kafkaConsumer;kafkaProducer
  #   stream:
  #     kafka:
  #       binder:
  #         replicationFactor: 1
  #         auto-create-topics: true
  #         brokers: localhost:9092
  #     bindings:
  #       binding-out-0:
  #         content-type: text/plain
  #         group: thermography-api
  #       kafkaConsumer-in-0:
  #         destination: sse-topic
  #         content-type: text/plain
  #         group: thermography-api
  #       kafkaProducer-out-0:
  #         content-type: text/plain
  #         group: thermography-api
```

## Opção 3: Remover do Docker Compose

Se você estiver usando Docker Compose, edite `src/main/docker/services.yml` e comente/remova o serviço Kafka.

## Opção 4: Desabilitar Spring Cloud Stream Completamente

Adicione no `application-dev.yml`:

```yaml
spring:
  cloud:
    stream:
      enabled: false
```

## Opção 5: Profile Específico Sem Kafka

Crie um novo profile `no-kafka` em `application-no-kafka.yml`:

```yaml
spring:
  autoconfigure:
    exclude:
      - org.springframework.cloud.stream.config.BindingServiceConfiguration
      - org.springframework.cloud.stream.binder.kafka.config.KafkaBinderConfiguration
  cloud:
    stream:
      enabled: false
```

Execute com:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,no-kafka
```

## Verificação

Após aplicar qualquer uma das opções acima, verifique os logs da aplicação. Você NÃO deve ver mensagens como:

- "Started Kafka consumer"
- "Kafka binder health check"
- "Connecting to Kafka broker"

## Solução Implementada ✓

A solução final implementada combina várias abordagens:

### 1. Configuração no application-dev.yml

```yaml
kafka:
  enabled: false

spring:
  autoconfigure:
    exclude:
      - org.springframework.cloud.stream.config.BindingServiceConfiguration
  cloud:
    stream:
      enabled: false
```

### 2. Configuração no application.yml

Toda a configuração do Kafka foi comentada (cloud.function, cloud.stream, bindings)

### 3. Docker Compose (services.yml)

Serviço Kafka comentado, mantendo apenas PostgreSQL

### 4. Classes Java com @ConditionalOnProperty

Adicionado `@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)` nas seguintes classes:

- `KafkaConsumer.java`
- `KafkaProducer.java`
- `ThermographyApiKafkaResource.java`

Isso garante que essas classes só sejam carregadas quando `kafka.enabled=true`.

## Reativar o Kafka

Para reativar o Kafka:

1. **application-dev.yml**: Altere `kafka.enabled: false` para `kafka.enabled: true`
2. **application.yml**: Descomente as configurações do Kafka
3. **services.yml**: Descomente o serviço Kafka
4. As classes Java já estão preparadas e serão carregadas automaticamente quando `kafka.enabled=true`

## Verificação

Após aplicar as mudanças, a aplicação deve iniciar sem erros relacionados ao Kafka:

- ✅ Sem "BindingService bean not found"
- ✅ Sem tentativas de conexão ao Kafka
- ✅ Endpoints Kafka não disponíveis (/api/thermography-api-kafka/\*)
