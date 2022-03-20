FROM graal_vm_test_env
RUN yum update && yum install glibc-devel zlib-devel gcc -y
ENV PATH=/usr/lib/graal-vm/graalvm-ce-java11-19.3.1/bin:$PATH 

RUN gu install native-image

ENV JAVA_HOME=/usr/lib/graal-vm/graalvm-ce-java11-19.3.1

CMD ["/bin/bash"]
