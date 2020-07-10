# Kafka Starter Kit

O objetivo desse repositório é prover um cluster **Apache Kafka** pronto para utilização em ambiente de experimentação. 

O laboratório conta com: 
 - 3 Zookeepers;
 - 3 Kafka brokers com o agente do Prometheus ativo, expondo todas as métricas do broker;
 - 1 Schema Registry
 - 1 Java project com 2 tipos de produtores e consumidores, utilizando dois tipos de serialização diferentes (plain text e Avro); 

## Pré requisitos
 - Sistema operacional Windows/Linux/macOS;
 - Docker e Docker Compose;
 - Java 8;
 - IDE de desenvolvimento de sua preferencia (IntelliJ, VS Code);
 - Baixar os dois arquivos abaixo em um diretório etc/prometheus na raiz onde o docker-compose estiver rodando

https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.12.0/jmx_prometheus_javaagent-0.12.0.jar
https://raw.githubusercontent.com/prometheus/jmx_exporter/master/example_configs/kafka-2_0_0.yml

> **Nota:** Se estiver utilizando o macOS, sugiro subir o ambiente docker em um servidor virtual Linux (CentOS/Ubuntu)

## Subindo o cluster
Para subir o cluster do Kafka, iremos usar o docker-compose localizado no diretório raiz do repositório (docker-compose.yml).
Nesse arquivo é necessário informar o "advertise.listener" dos brokers, que é o DNS onde os brokers estarão ouvindo, entáo lembre-se de incluir o ip do host onde está rodando o docker na variável KAFKA_ADVERTISED_LISTENERS
Após isso execute o comando abaixo:
```sh
$ docker-compose up -d
```
A saída deve ser:
```sh
Starting apache-kafka-basics_zookeeper-1_1   ... done
Starting apache-kafka-basics_zookeeper-3_1   ... done
Starting apache-kafka-basics_zookeeper-2_1   ... done
Starting apache-kafka-basics_kafka-2_1       ... done
Starting apache-kafka-basics_kafka-3_1       ... done
Starting apache-kafka-basics_kafka-1_1       ... done
```

A partir desse ponto o cluster já esta pronto para uso.
Os instancias de Zookeeper em <ip>:22181,<ip>:32181 e <ip>:42181
Os brokers estão disponiveis em <ip>:19092, <ip>:29092 e <ip>:39092
As métricas para coleta podem ser encontradas em <ip>:7071, <ip>:7072 e <ip>:7073

## Subindo o Schema Registry
Para subir a instancia do Schema Registry para o cluster ativado no passo anterior, execute o comando abaixo (pode demorar um pouco, a imagem tem mais ou menos  1Gb):
```sh
$ docker run -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=localhost:19092,localhost:29092,localhost:39092 -e SCHEMA_REGISTRY_HOST_NAME=localhost --network host -d confluentinc/cp-schema-registry
```

Após isso o Schema Registry poderá ser acessado em <ip>:8081
 
A partir daqui todos os exemplos disponíveis na aplicação Java disponivel no diretorio kafka-client-sample podem ser executados

### Monitoração

Para executar o setup de monitoração com o Prometheus Server e o Grafana, o artigo abaixo do Alvaro Bacelar é excelente! Só se atente que a parte de exposição das metricas ja foi feita nesse setup, então pode pular essa parte.
https://medium.com/@alvarobacelar/monitorando-um-cluster-kafka-com-ferramentas-open-source-a4032836dc79

Bons estudos :)
