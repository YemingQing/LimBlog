FROM java:8

MAINTAINER limerenxey <邮箱地址>

ADD blog_api.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]
