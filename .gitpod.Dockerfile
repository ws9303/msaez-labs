FROM gitpod/workspace-full

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 17.0.3-ms && \
    sdk default java 17.0.3-ms"

RUN bash -c "/workspace/msaez-labs/init.sh"

WORKDIR /workspace/msaez-labs/kafka

RUN docker-compose up -d