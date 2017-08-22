FROM registry.docker.elo7aws.com.br/java8

MAINTAINER Mateus Molinaro Motta <mateus.motta@elo7.com>

ENV METRICS /usr/local/metrics

WORKDIR $METRICS

ADD . $METRICS

ENTRYPOINT ["java", "-jar", "/usr/local/metrics/target/metrics.jar"]
