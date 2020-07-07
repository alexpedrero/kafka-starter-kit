# Kafka Starter Kit

O objetivo desse repositório é prover um cluster **Apache Kafka** pronto para utilização em ambiente de experimentação. 

O laboratório conta com: 
 - 3 Zookeepers;
 - 3 Kafka brokers;
 - 1 Schema Registry
 - Stack de monitoração com Prometheus e Grafana
 - 1 Java project com 2 tipos de produtores e consumidores, utilizando dois tipos de serialização diferentes (plain text e Avro); 

## Pré requisitos
 - Sistema operacional Windows/Linux/macOS;
 - Docker e Docker Compose;
 - Java 8;
 - IDE de desenvolvimento de sua preferencia (IntelliJ, VS Code);
 - 

> **Nota:** Se estiver utilizando o macOS, sugiro subir o ambiente docker em um servidor virtual Linux (CentOS/Ubuntu)

## Subindo o cluster
Para subir o cluster do Kafka, iremos usar o docker-compose localizado no diretório raiz do repositório (docker-compose.yml).
 

https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.13.0/jmx_prometheus_javaagent-0.13.0.jar