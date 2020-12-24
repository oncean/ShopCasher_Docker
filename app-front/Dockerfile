#创建node开发环境 并且进行打包
FROM node:12.13.0 as build-stage
WORKDIR /app

COPY ./startServer.js .
COPY ./entryPoint.sh /
RUN chmod +x /entryPoint.sh
ENTRYPOINT ["/entryPoint.sh"]