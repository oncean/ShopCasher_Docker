#!/bin/sh

cd /app
npm install -g cnpm --registry=https://registry.npm.taobao.org
cnpm install
cnpm run build
echo "build success"

node startServer.js
echo "start node server success"