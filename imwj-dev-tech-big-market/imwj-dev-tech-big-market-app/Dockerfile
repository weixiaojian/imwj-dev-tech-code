# 基础镜像
FROM openjdk:8-jre-slim

# 作者
MAINTAINER imwj

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 端口
EXPOSE 8091
ENV PORT 8091

# 添加应用
ADD target/imwj-dev-tech-big-market-app.jar /imwj-dev-tech-big-market-app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /imwj-dev-tech-big-market-app.jar $PARAMS"]