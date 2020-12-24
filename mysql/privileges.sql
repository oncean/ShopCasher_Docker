use mysql;
select host, user from user;
-- 因为mysql版本是5.7，因此新建用户为如下命令：
create user IF NOT EXISTS root identified by 'root';
-- 将docker_mysql数据库的权限授权给创建的docker用户，密码为123456：
grant all on *.* to root@'%' identified by 'root' with grant option;
-- 这一条命令一定要有：
flush privileges;