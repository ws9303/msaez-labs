# ddd-petstore

## GitPod 접속
https://gitpod.io/#https://github.com/msa-school/msaez-labs

- 접속 후 "Terminal > Terminal열기"

## 유틸리티 설치(httpie)

- httpie (curl / POSTMAN 대용)
```
pip install httpie
```

## Kafka 의 접속
### Docker Compose 이용 (도커 있을 때 강추)
- Kafka 의 실행 (Docker Compose)
```
cd kafka
docker-compose up -d     # docker-compose 가 모든 kafka 관련 리소스를 받고 실행할 때까지 기다림
```
- Kafka 정상 실행 확인
```
$ docker-compose logs kafka | grep -i started    

kafka-kafka-1  | [2022-04-21 22:07:03,262] INFO [KafkaServer id=1] started (kafka.server.KafkaServer)
```
- Kafka consumer 접속
```
docker-compose exec -it kafka /bin/bash   # kafka docker container 내부 shell 로 진입

[appuser@e23fbf89f899 bin]$ cd/bin
[appuser@e23fbf89f899 bin]$ ./kafka-console-consumer --bootstrap-server localhost:9092 --topic petstore
```
- Docker Compose 사용시 기존 소스코드 상의 application.yml 변경 (9092 -> 29092)

```
  cloud:
    stream:
      kafka:
        binder:
          brokers: localhost:29092
```

### 로컬 설치 (비추)
- Kafka Download
```
wget https://dlcdn.apache.org/kafka/3.1.0/kafka_2.13-3.1.0.tgz
tar -xf kafka_2.13-3.1.0.tgz
```

- Run Kafka
```
cd kafka_2.13-3.1.0/
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
```

- Kafka Event 컨슈밍하기 (별도 터미널)
```
cd kafka_2.13-3.1.0/
bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic petstore
```


# Kubernetes 에 배포

# Docker 배포 관련

각 프로젝트 내에는 Dockerfile 이 포함되어있습니다. 이것을 빌드하기 위해서는 우선 maven 빌드로 jar 를 만들어준 후, jar 를 Dockerfile 로 다시 빌드해줍니다:

```
cd pet-store
mvn package -B
docker build -t pet:v1 .
docker run pet:v1
```


## kafka 설치하기

### Helm 

Helm(패키지 인스톨러) 설치
- Helm 3.x 설치(권장)
```bash
curl https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 > get_helm.sh
chmod 700 get_helm.sh
./get_helm.sh
```

### Kafka 를 helm 으로 설치
```bash
helm repo update
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install my-kafka bitnami/kafka
```
