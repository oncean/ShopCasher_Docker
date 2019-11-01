/**
 * 启动node静态服务器
 */
var url = require('url');
var http = require('http');
var fs = require('fs');  //涉及文件读取
var path = require('path');  //涉及路径处理
var mime = {
  "css": "text/css",
  "gif": "image/gif",
  "html": "text/html",
  "ico": "image/x-icon",
  "jpeg": "image/jpeg",
  "jpg": "image/jpeg",
  "js": "text/javascript",
  "json": "application/json",
  "pdf": "application/pdf",
  "png": "image/png",
  "svg": "image/svg+xml",
  "swf": "application/x-shockwave-flash",
  "tiff": "image/tiff",
  "txt": "text/plain",
  "wav": "audio/x-wav",
  "wma": "audio/x-ms-wma",
  "wmv": "video/x-ms-wmv",
  "xml": "text/xml"
}; //引入mime类型
var config = {
  Welcome: {//用户请求了一个目录路径，而且没有带上/。那么我们为其添加上/index.html，再重新做解析
    file: "index.html"
  },
  Compress: {//对于图片一类的文件，不需要进行gzip压缩，只压缩三种文件
    match: /css|js|html/ig
  },
  Expires: { //指定后缀文件和过期日期
    fileMatch: /^(gif|png|jpg|js|css)$/ig,
    maxAge: 60 * 60
  }
};
var zlib = require("zlib");  //使用gzip压缩，引入原生模块zlib

var server = http.createServer(function (request, response) {
  //获得请求url并得到最终的路径
  var pathname = url.parse(request.url).pathname;

  ///结尾的请求，自动添加上”index.html
  if (pathname.slice(-1) === "/") {
    pathname = pathname + config.Welcome.file;
  }

  //使用normalize方法来处理掉不正常的/
  var realPath = path.join("/app/dist", path.normalize(pathname.replace(/\.\./g, "")));

  var pathHandle = function (realPath) {  	//使用fs.stat处理路径
    fs.stat(realPath, function (err, stats) {
      if (err) {
        response.writeHead(404, "Not Found", { 'Content-Type': 'text/plain' });
        response.write("This request URL " + pathname + " was not found on this server.");
        response.end();
      } else { //如果请求的路径没有以/结尾，需要做判断，看路径是目录还是文件
        if (stats.isDirectory()) {
          //如果是目录则添加上/和index.html
          realPath = path.join(realPath, "/", config.Welcome.file);
          pathHandle(realPath);
        } else {
          //获得文件扩展名，通过slice方法来剔除掉”.”，没有后缀名的文件认为是unknown
          var ext = path.extname(realPath);
          ext = ext ? ext.slice(1) : 'unknown';
          var contentType = mime[ext] || "text/plain";
          response.setHeader("Content-Type", contentType);

          //设置缓存支持和控制，提高性能，减少IO操作
          var lastModified = stats.mtime.toUTCString();
          var ifModifiedSince = "If-Modified-Since".toLowerCase();
          response.setHeader("Last-Modified", lastModified);

          if (ext.match(config.Expires.fileMatch)) {
            var expires = new Date();
            expires.setTime(expires.getTime() + config.Expires.maxAge * 1000);
            response.setHeader("Expires", expires.toUTCString());
            response.setHeader("Cache-Control", "max-age=" + config.Expires.maxAge);
          }

          //判断请求头部的是否存在ifModifiedSince，并判断lastModified是否等于ifModifiedSince
          //如果相等则说明文件没有修改，返回304
          if (request.headers[ifModifiedSince] && lastModified == request.headers[ifModifiedSince]) {
            response.writeHead(304, "Not Modified");
            response.end();
          } else {
            //如果修改过则读取文件
            //为了防止大文件，也为了满足zlib模块的调用模式，将读取文件改为流的形式进行读取。
            //对于支持压缩的文件格式以及浏览器端接受gzip或deflate压缩，我们调用压缩。
            //若不，则管道方式转发给response。
            var raw = fs.createReadStream(realPath);
            var acceptEncoding = request.headers['accept-encoding'] || "";
            var matched = ext.match(config.Compress.match);
            if (matched && acceptEncoding.match(/\bgzip\b/)) {
              response.writeHead(200, "Ok", { 'Content-Encoding': 'gzip' });
              raw.pipe(zlib.createGzip()).pipe(response);
            } else if (matched && acceptEncoding.match(/\bdeflate\b/)) {
              response.writeHead(200, "Ok", { 'Content-Encoding': 'deflate' });
              raw.pipe(zlib.createDeflate()).pipe(response);
            } else {
              response.writeHead(200, "Ok");
              raw.pipe(response);
            }
          }
        }
      }
    });
  };

  pathHandle(realPath);  //路径处理函数
});

server.listen(80);
