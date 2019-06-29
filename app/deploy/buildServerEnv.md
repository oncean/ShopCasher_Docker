# 安装数据库服务
    更新apt数据源：apt-get update
## 1、安装数据库
##### 1、安装服务端 `sudo apt-get install mysql-server`   安装过程中可能需要设置密码。
##### 2、安装客户端 `sudo apt-get install mysql-client`  

## 2、初始化数据库
##### 1、登录MySQL `sudo mysql -u root -p`
    如果登录不上查找密码：打开一个文件 sudo vim /etc/mysql/debian.cnf
##### 2、修改root密码
    set password for root@localhost = password('root'); 
##### 3、修改数据库权限，支持远程访问
    1、修改文件 sudo vim /etc/mysql/mariadb.conf.d/50-server.cnf
    2、bind-address = 127.0.0.1 该句表示只允许本机访问mysql
    3、执行sql:GRANT ALL PRIVILEGES ON *.* TO '用户名'@'%'IDENTIFIED BY '密码' WITH GRANT OPTION;
    4、重启mysql  sudo /etc/init.d/mysql restart
#### 4、执行初始化脚本
#### 5、ubuntu/kylin4.02sp1 mariadb 进入数据库必须使用sudo的问题
    sudo vim /etc/mysql/my.cnf
    
    增加 [ mysqld ]
    
    skip-grant-tables #忽略密码授权
# 安装JDK
    sudo apt-get install default-jre
# 安装redis
#### 1、安装
    sudo apt-get install redis-server
#### 2、启动redis
    sudo redis-server /etc/redis/redis.conf
#### 3、查看是否启动成功
    运行命令 redis-cli
#### 4、redis重设密码
    sudo vim  /etc/redis/redis.conf
    
    #requirepass foobared去掉注释，foobared改为自己的密码
  
    requirepass 123456
    
    然后保存，重启服务
    sudo redis-server /etc/redis/redis.conf
# 启动服务
#### 1、上传jar包
#### 2、运行jar包
    java -jar xxx.jar