#创建node开发环境 并且进行打包
FROM node:10.10.0-alpine as build-stage
WORKDIR /app


COPY ./entryPoint.sh /
RUN chmod +x /entryPoint.sh
ENTRYPOINT ["/entryPoint.sh"]