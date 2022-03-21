FROM amazonlinux:2
RUN yum update -y && yum install wget gzip tar glibc-devel zlib-devel gcc libstdc++-static -y \
    && wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-22.0.0.2/graalvm-ce-java11-linux-amd64-22.0.0.2.tar.gz \
    && tar -zxvf graalvm-ce-java11-linux-amd64-22.0.0.2.tar.gz \
    && mkdir /usr/lib/graal-vm && mv graalvm-ce-java11-22.0.0.2/ /usr/lib/graal-vm/ \
    && yum install libstdc++-static -y

ENV PATH=/usr/lib/graal-vm/graalvm-ce-java11-22.0.0.2/bin:$PATH
ENV JAVA_HOME=/usr/lib/graal-vm/graalvm-ce-java11-22.0.0.2
RUN gu install native-image \
    && rm graalvm-ce-java11-linux-amd64-22.0.0.2.tar.gz \
    && yum remove wget tar gzip -y && yum clean all --enablerepo="*"

CMD ["/bin/bash"]
