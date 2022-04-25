FROM amazonlinux:2
ENV PATH=/usr/lib/graal-vm/graalvm-ce-java11-22.0.0.2/bin:$PATH
ENV JAVA_HOME=/usr/lib/graal-vm/graalvm-ce-java11-22.0.0.2
RUN yum update -y && yum install wget gzip tar glibc-devel zlib-devel gcc libstdc++-static -y \
    && wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-22.0.0.2/graalvm-ce-java11-linux-amd64-22.0.0.2.tar.gz \
    && tar -zxvf graalvm-ce-java11-linux-amd64-22.0.0.2.tar.gz \
    && mkdir /usr/lib/graal-vm && mv graalvm-ce-java11-22.0.0.2/ /usr/lib/graal-vm/ \
    && yum install libstdc++-static -y \
    && gu install native-image \
    && rm graalvm-ce-java11-linux-amd64-22.0.0.2.tar.gz \
    && yum remove wget -y && yum clean all --enablerepo="*" \
    && yum -y install https://repo.ius.io/ius-release-el7.rpm https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm \
    && yum -y install libsecret pcre2 \
    && yum -y install git --enablerepo=ius --disablerepo=base,epel,extras,updates

CMD ["/bin/bash"]
