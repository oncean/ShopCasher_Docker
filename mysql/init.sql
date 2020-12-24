-- 创建数据库
create database IF NOT EXISTS `shopcasher` default character set utf8 collate utf8_general_ci;


/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.17-log : Database - shopcasher
*********************************************************************
*/

USE `shopcasher`;

/*Table structure for table `t_catogery` */

DROP TABLE IF EXISTS `t_catogery`;

CREATE TABLE `t_catogery` (
  `id` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_catogery` */

insert  into `t_catogery`(`id`,`status`,`type`,`name`,`parentId`) values ('101',1,2,'休闲零食','6'),('102',1,2,'饼干蛋糕','6'),('103',1,2,'糖果/巧克力','6'),('104',1,2,'方便速食','6'),('105',1,2,'牛奶','6'),('106',1,2,'营养冲调','6'),('107',1,2,'饮料/水','6'),('108',1,2,'膨化食品','6'),('2',1,1,'粮油副食',NULL),('201',1,2,'食用油','2'),('202',1,2,'厨房调味','2'),('203',1,2,'调味酱','2'),('204',1,2,'米面/面粉','2'),('3',1,1,'个人护理',NULL),('301',1,2,'洗发护理','3'),('302',1,2,'身体护理','3'),('303',1,2,'口腔护理','3'),('304',1,2,'卫生护理','3'),('4',1,1,'家居清洁',NULL),('401',1,2,'纸品湿巾','4'),('402',1,2,'衣物清洁','4'),('403',1,2,'一次性用品','4'),('404',1,2,'家纺','4'),('405',1,2,'居家日用','4'),('406',1,2,'家庭清洁','4'),('5',1,1,'香烟酒水',NULL),('501',1,2,'白酒','5'),('502',1,2,'啤酒','5'),('503',1,2,'黄酒','5'),('504',1,2,'葡萄酒','5'),('505',1,2,'香烟','5'),('6',1,1,'食品饮料',NULL);

/*Table structure for table `t_catogery_big_tmp` */

DROP TABLE IF EXISTS `t_catogery_big_tmp`;

CREATE TABLE `t_catogery_big_tmp` (
  `id` bigint(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_catogery_big_tmp` */

insert  into `t_catogery_big_tmp`(`id`,`name`) values (1001,'一次性用品\r'),(1002,'个人清洁用品\r'),(1003,'休闲小吃\r'),(1004,'伞/雨披\r'),(1005,'保健品\r'),(1006,'内衣\r'),(1007,'冲饮\r'),(1008,'冷冻类\r'),(1009,'冷藏类\r'),(1010,'化妆品类\r'),(1011,'卫生清洁用品\r'),(1012,'卫生用品类\r'),(1013,'厨房用品类\r'),(1014,'园艺用品类\r'),(1015,'固体奶制品\r'),(1016,'婴儿用品类\r'),(1017,'季节性商品\r'),(1018,'家庭清洁用品\r'),(1019,'居室用品\r'),(1020,'干货类\r'),(1021,'床上用品\r'),(1022,'拖鞋\r'),(1023,'散装休闲类\r'),(1024,'散装南北货\r'),(1025,'早餐食品\r'),(1026,'毛巾\r'),(1027,'粮油类、主食\r'),(1028,'糖果、巧克力\r'),(1029,'纸制品类\r'),(1030,'罐头、酱制品\r'),(1031,'袜子\r'),(1032,'调味品、佐料\r'),(1033,'酒\r'),(1034,'饮品类\r'),(1035,'饼干类\r');

/*Table structure for table `t_configure` */

DROP TABLE IF EXISTS `t_configure`;

CREATE TABLE `t_configure` (
  `id` varchar(255) NOT NULL,
  `configureKey` varchar(255) DEFAULT NULL,
  `configureValue` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `configure_key` varchar(255) DEFAULT NULL,
  `configure_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_configure` */

insert  into `t_configure`(`id`,`configureKey`,`configureValue`,`description`,`configure_key`,`configure_value`) values ('1','appID','wx13f252631366e098','小程序APPID',NULL,NULL),('2','secret','4bcdaf834a1a24ad379d5c8aac440443','小程序的app secret',NULL,NULL),('3','requestUrl','https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={code}&grant_type=authorization_code','微信登陆的时候第三方请求微信API的地址',NULL,NULL);

/*Table structure for table `t_good` */

DROP TABLE IF EXISTS `t_good`;

CREATE TABLE `t_good` (
  `id` varchar(255) NOT NULL,
  `bid` double NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picUrl` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `shopId` varchar(255) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `updateUserId` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `shop_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `DTYPE` varchar(31) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_good` */

insert  into `t_good`(`id`,`bid`,`category`,`code`,`description`,`name`,`picUrl`,`price`,`shopId`,`spec`,`stock`,`updateUserId`,`createTime`,`updateTime`,`pic_url`,`shop_id`,`update_user_id`,`DTYPE`) values ('2323',123,'114','6928804011586',NULL,'手动阀手动阀犯得上发顺丰士大夫是否撒的','/uploadGoodFileDir/0c5e22bf3ccf41448c29b3a4633062ed.o6zAJs0Ia2-DX5sRVa_Pec7-PqDg.PSRzOYSFJld2e0a8d3f3e81bccf6b7d690224a09c0da.jpg',123,'Aj6Z7fJbEzUfZB7ZJbvAbMVn','包',123,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,NULL,NULL,NULL,NULL,''),('77RB73eaiqiejquaaufQJRB3',12,'103','231','123','123',NULL,12,'Z3MraiRnQf6zei6bUb22IvIn','瓶',1,'iuYNzyMRn6FvYzYFjufuYzYf','2018-07-16 22:22:23','2018-07-19 22:01:19',NULL,NULL,NULL,''),('aAriAjEbqMjiBJ32Afa2Ej22',7.8,NULL,'6928804011586','100ml','莫里思安','/uploadGoodFileDir/f8e73af3dc3a4c339179377c28378bc9.o6zAJs0Ia2-DX5sRVa_Pec7-PqDg.312f82b0609717a71a295bfed3fed5bb.jpg',10,'myiqIzvi2iuuIFJBjaVVnayy','包',0,'JzMn2uFbEJbmraaiyme6rYbe',NULL,NULL,NULL,NULL,NULL,''),('ABBFNjr2emaeFBnQRvrqENJj',20,'110','902088419825','400ml','凡士林倍护润肤露','/uploadGoodFileDir/b9e85b4f3cbd42c0b401cbed2037919f.png',23,'NnYbi2UF7zmumQnARrZzIJBb','瓶',1,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,'2018-06-16 17:34:19',NULL,NULL,NULL,''),('aIrA7nQVBNzqrUvIzeQrY3qu',20,'109','901294302822','123123','护肤瓶',NULL,23,'NnYbi2UF7zmumQnARrZzIJBb','瓶',5,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,'2018-06-03 22:14:41',NULL,NULL,NULL,''),('EjmiayMNfU3eRZny2uJVBfau',21,'108','94443383690','12','光明','/uploadGoodFileDir/5538d7c499f34286aa989adb001e7410.jpg',23,'NnYbi2UF7zmumQnARrZzIJBb','包',0,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,'2018-06-03 22:22:43',NULL,NULL,NULL,''),('IraaUjvUb2Ij3umARnbemUz2',12,'105','2312','12312','test1',NULL,123,'Z3MraiRnQf6zei6bUb22IvIn','瓶',4,'iuYNzyMRn6FvYzYFjufuYzYf','2018-07-16 22:20:09','2018-07-16 22:22:29',NULL,NULL,NULL,''),('rI36BvfqmmYj6v2qiiNFvqEv',1,'110','955558020625','11','1','/uploadGoodFileDir/bcffa52b1d9e44299429b2b5630baa99.jpg',1,'NnYbi2UF7zmumQnARrZzIJBb','条',0,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,'2018-06-03 22:22:43',NULL,NULL,NULL,''),('Zry6jyUfEbqeVf2A23viIN23n23',123,NULL,'6928804011586',NULL,'微软微软','/uploadGoodFileDir/0c5e22bf3ccf41448c29b3a4633062ed.o6zAJs0Ia2-DX5sRVa_Pec7-PqDg.PSRzOYSFJld2e0a8d3f3e81bccf6b7d690224a09c0da.jpg',123,'Aj6Z7fJbEzUfZB7ZJbvAbMVn','包',123,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,NULL,NULL,NULL,NULL,''),('Zry6jyUfEbqeVf2AviIN23n23',123,NULL,'6928804011586',NULL,'2342342342342342342','/uploadGoodFileDir/0c5e22bf3ccf41448c29b3a4633062ed.o6zAJs0Ia2-DX5sRVa_Pec7-PqDg.PSRzOYSFJld2e0a8d3f3e81bccf6b7d690224a09c0da.jpg',123,'Aj6Z7fJbEzUfZB7ZJbvAbMVn','包',123,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,NULL,NULL,NULL,NULL,''),('Zry6jyUfEbqeVf2AviINn23',123,NULL,'6928804011586',NULL,'123','/uploadGoodFileDir/0c5e22bf3ccf41448c29b3a4633062ed.o6zAJs0Ia2-DX5sRVa_Pec7-PqDg.PSRzOYSFJld2e0a8d3f3e81bccf6b7d690224a09c0da.jpg',123,'Aj6Z7fJbEzUfZB7ZJbvAbMVn','包',123,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,NULL,NULL,NULL,NULL,''),('Zry6jyUfEbqeVf2AviINnE3i',123,NULL,'6928804011586',NULL,'123','/uploadGoodFileDir/0c5e22bf3ccf41448c29b3a4633062ed.o6zAJs0Ia2-DX5sRVa_Pec7-PqDg.PSRzOYSFJld2e0a8d3f3e81bccf6b7d690224a09c0da.jpg',123,'Aj6Z7fJbEzUfZB7ZJbvAbMVn','包',123,'iuYNzyMRn6FvYzYFjufuYzYf',NULL,NULL,NULL,NULL,NULL,'');

/*Table structure for table `t_good_abandonhis` */

DROP TABLE IF EXISTS `t_good_abandonhis`;

CREATE TABLE `t_good_abandonhis` (
  `id` varchar(255) NOT NULL,
  `bid` double NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picUrl` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `shopId` varchar(255) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUserId` varchar(255) DEFAULT NULL,
  `formerId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_good_abandonhis` */

insert  into `t_good_abandonhis`(`id`,`bid`,`category`,`code`,`createTime`,`description`,`name`,`picUrl`,`price`,`shopId`,`spec`,`stock`,`updateTime`,`updateUserId`,`formerId`) values ('VRvU3yQnYfUry2QNFzq6neqa',0,NULL,'2323231','2018-07-16 22:50:44',NULL,NULL,NULL,0,'Z3MraiRnQf6zei6bUb22IvIn',NULL,0,NULL,'iuYNzyMRn6FvYzYFjufuYzYf',NULL);

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `orderId` varchar(255) NOT NULL,
  `giveChange` double NOT NULL,
  `payMethod` varchar(255) DEFAULT NULL,
  `profit` double NOT NULL,
  `receipts` double NOT NULL,
  `settlementTime` datetime DEFAULT NULL,
  `settlementUserId` varchar(255) DEFAULT NULL,
  `shopId` varchar(255) DEFAULT NULL,
  `totalNum` int(11) DEFAULT NULL,
  `totalPrice` double NOT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_order` */

insert  into `t_order`(`orderId`,`giveChange`,`payMethod`,`profit`,`receipts`,`settlementTime`,`settlementUserId`,`shopId`,`totalNum`,`totalPrice`) values ('2URnA3BVBvq2z6VB7bquqqIb',70,'1',2.2,80,'2018-03-18 12:47:53','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,10),('3a6vAjMJFR3aZFFvumrumUj2',90,'1',2.2,100,'2018-03-18 11:16:10','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,10),('7JnaM3Rji26vjMFVRvI7VN7z',290,'3',2.2,300,'2018-03-18 12:42:18','UnuqM3BBVRrujeEbameei6ry','NnYbi2UF7zmumQnARrZzIJBb',NULL,10),('B7NzYfzAZNjqNJriim7nuEFf',0,'1',2.2,10,'2018-03-18 14:43:42','UnuqM3BBVRrujeEbameei6ry','NnYbi2UF7zmumQnARrZzIJBb',NULL,10),('eqQrEfzuu2AvYriQRnm22mEr',27,'3',8,100,'2018-06-03 22:22:43','iuYNzyMRn6FvYzYFjufuYzYf','NnYbi2UF7zmumQnARrZzIJBb',7,73),('EZfiyiQ7jERvQFrYVrBBNNVj',0,'1',3,0,'2018-06-16 17:34:19','iuYNzyMRn6FvYzYFjufuYzYf','NnYbi2UF7zmumQnARrZzIJBb',1,23),('F32UjuQbYrQrBvm26na22q6v',90,'1',2.2,100,'2018-03-18 12:42:18','UnuqM3BBVRrujeEbameei6ry','NnYbi2UF7zmumQnARrZzIJBb',NULL,10),('FziEnu2YR7Z3ze6viyAjquYb',40,'1',2.2,50,'2018-03-18 14:16:58','UnuqM3BBVRrujeEbameei6ry','NnYbi2UF7zmumQnARrZzIJBb',NULL,10),('i6ZrymbaIfAfme6R7n7jAJZr',90,'2',2.2,100,'2018-03-18 12:42:07','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,10),('j6Bj6bFv6VNzRRnAbmR7Nbia',54,'1',6,100,'2018-05-15 21:22:43','iuYNzyMRn6FvYzYFjufuYzYf',NULL,2,46),('jMJBN3F3uMVrb6ZNvmmEJvEn',90,'3',2.2,100,'2018-03-18 12:42:18','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,10),('MzaMJbFny2eufEJNRzMf6jMz',0,'1',0,0,'2018-06-16 17:34:00','iuYNzyMRn6FvYzYFjufuYzYf','Z3MraiRnQf6zei6bUb22IvIn',0,0),('MziemyFBJ7NrmU3UBzeuA7Br',90,'1',2.2,100,'2018-03-18 12:42:18','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,10),('neANFvZRN3AvaAfMBvERbAJj',31,'2',9,100,'2018-05-15 21:13:49','iuYNzyMRn6FvYzYFjufuYzYf',NULL,3,69),('U7fYriVnmAVnjaQna2v6Zr6v',90,'1',2.2,100,'2018-03-18 12:42:18','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,10),('uMnyaeFf2muayq2EzyfQnYB3',18,'1',2.2,28,'2018-03-18 14:17:10','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,10),('UNvuIfAnMjIzIzqIzyeMJVZz',80,'1',4.4,100,'2018-03-18 12:42:18','JzMn2uFbEJbmraaiyme6rYbe','ueammaAfaMBjeAJfmiZ3QFZv',NULL,20),('V3mIjyyQFNNfjEN3y2JzaQri',0,'1',0,12,'2018-07-19 22:01:19','iuYNzyMRn6FvYzYFjufuYzYf','Z3MraiRnQf6zei6bUb22IvIn',1,12),('vMBfMjUJF7Jn7nQJ3uZVZNJz',0,'1',0,0,'2018-07-19 22:01:12','iuYNzyMRn6FvYzYFjufuYzYf','Z3MraiRnQf6zei6bUb22IvIn',1,12),('ZviU7fbUJJNjVBnm2umeEfm2',80,'1',4.4,100,'2018-03-18 12:42:18','UnuqM3BBVRrujeEbameei6ry','myiqIzvi2iuuIFJBjaVVnayy',NULL,20);

/*Table structure for table `t_order_item` */

DROP TABLE IF EXISTS `t_order_item`;

CREATE TABLE `t_order_item` (
  `itemId` varchar(255) NOT NULL,
  `goodId` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `orderId` varchar(255) DEFAULT NULL,
  `totalPrice` double NOT NULL,
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_order_item` */

insert  into `t_order_item`(`itemId`,`goodId`,`number`,`orderId`,`totalPrice`) values ('aA3imujeaiYzYNjMjuVVJzia','aAriAjEbqMjiBJ32Afa2Ej22',1,'MziemyFBJ7NrmU3UBzeuA7Br',10),('aANbUn6veM3uVnAjyaMRnaMv','rI36BvfqmmYj6v2qiiNFvqEv',4,'eqQrEfzuu2AvYriQRnm22mEr',0),('buqQJrNvUZbeyeaqmiyIj2qa','aAriAjEbqMjiBJ32Afa2Ej22',1,'uMnyaeFf2muayq2EzyfQnYB3',10),('ER7BfuiaMVR3rm2Q73neEJjq','ABBFNjr2emaeFBnQRvrqENJj',1,'EZfiyiQ7jERvQFrYVrBBNNVj',0),('iaaym2FN3ya2rMzMvyye2yY3','aAriAjEbqMjiBJ32Afa2Ej22',1,'B7NzYfzAZNjqNJriim7nuEFf',10),('j2yQNfeEzE3iIzaABvMZBRBz','aAriAjEbqMjiBJ32Afa2Ej22',1,'jMJBN3F3uMVrb6ZNvmmEJvEn',10),('jqEFVrb6Nz6nUBZbe2QjyINz','aAriAjEbqMjiBJ32Afa2Ej22',2,'ZviU7fbUJJNjVBnm2umeEfm2',20),('M73yYriqE3IfayUn2qiyiiMr','ABBFNjr2emaeFBnQRvrqENJj',2,'j6Bj6bFv6VNzRRnAbmR7Nbia',0),('maaqIvFvi2IbqIjaeeuuqEju','aAriAjEbqMjiBJ32Afa2Ej22',1,'i6ZrymbaIfAfme6R7n7jAJZr',10),('NNRBJbaqAVRbQFviieqQfMR3','aAriAjEbqMjiBJ32Afa2Ej22',1,'F32UjuQbYrQrBvm26na22q6v',10),('qayaequiyA3mYRvaAjBZJbum','ABBFNjr2emaeFBnQRvrqENJj',3,'neANFvZRN3AvaAfMBvERbAJj',0),('QZNfyqNZvQVnbAJriemQVfmm','aAriAjEbqMjiBJ32Afa2Ej22',2,'UNvuIfAnMjIzIzqIzyeMJVZz',20),('RrI7zq3AR7Bv2uuimm7FvEn2','aAriAjEbqMjiBJ32Afa2Ej22',1,'7JnaM3Rji26vjMFVRvI7VN7z',10),('RVfQbyuQJzmqb6FRnmNbqiqu','ABBFNjr2emaeFBnQRvrqENJj',2,'eqQrEfzuu2AvYriQRnm22mEr',0),('uQRNjuEfMvMj63yY7j3iMJbe','EjmiayMNfU3eRZny2uJVBfau',1,'eqQrEfzuu2AvYriQRnm22mEr',0),('vM3m2a3IZryaIjyYBnvUVF3m','aAriAjEbqMjiBJ32Afa2Ej22',1,'U7fYriVnmAVnjaQna2v6Zr6v',10),('yEZRBn7raUBn2UfAruYvURjm','aAriAjEbqMjiBJ32Afa2Ej22',1,'FziEnu2YR7Z3ze6viyAjquYb',10),('YfqyIjFreQ7jQj63MjNfEfE3','77RB73eaiqiejquaaufQJRB3',1,'V3mIjyyQFNNfjEN3y2JzaQri',0),('YrUN32faqQ3e6bqIFzJJJVJ3','77RB73eaiqiejquaaufQJRB3',1,'vMBfMjUJF7Jn7nQJ3uZVZNJz',0),('yU732afYrYV3iI3En2beABzi','aAriAjEbqMjiBJ32Afa2Ej22',1,'3a6vAjMJFR3aZFFvumrumUj2',10),('zUzQ7zjIZBj2mURJran2yqEb','aAriAjEbqMjiBJ32Afa2Ej22',1,'2URnA3BVBvq2z6VB7bquqqIb',10);

/*Table structure for table `t_regist_his` */

DROP TABLE IF EXISTS `t_regist_his`;

CREATE TABLE `t_regist_his` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `expiredTime` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `successTime` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_regist_his` */

insert  into `t_regist_his`(`id`,`address`,`code`,`createTime`,`expiredTime`,`password`,`status`,`successTime`,`userName`) values ('2MFJniNbEf6biAvM3m3U3eMf','1017075689@qq.com','654774','2018-06-07 23:21:52','2018-06-07 23:31:52',NULL,'2',NULL,'小辣鸡，你不会吧'),('3yauMvNvqaYvJvyEfmEryMva','913476114@qq.com','117280','2018-06-07 23:22:06','2018-06-07 23:32:06',NULL,'0',NULL,'小辣鸡，你不会吧'),('77JjQn2ABv22E3YzqaQnUjem','1017075689@qq.com','138171.13486985106','2018-06-07 23:10:50','2018-06-07 23:20:50',NULL,'2',NULL,'123'),('ba67fmvAfuueF7nmMneYbEre','1017075689@qq.com','842178','2018-06-08 22:33:50','2018-06-08 22:43:50','1','1','2018-06-08 22:55:48','wangshengtest'),('FfQjYnU36rYzfIRZZjVZJNF3','1017075689@qq.com','077437','2018-06-08 21:47:24','2018-06-08 21:57:24',NULL,'2',NULL,NULL),('nQV3U3rIz6nq2Mn2aq2yYRZz','1017075689@qq.com','386358','2018-06-08 21:53:46','2018-06-08 22:03:46',NULL,'2',NULL,NULL),('rAjqMnRFr2MjJb2MV3ZF3qyq','1017075689@qq.com','141402','2018-06-08 21:46:09','2018-06-08 21:56:09',NULL,'2',NULL,NULL),('zqYzaaRJRVjqaMbI7rRJjU73','1017075689@qq.com','231242','2018-06-08 22:17:45','2018-06-08 22:27:45',NULL,'2',NULL,NULL),('ZrAjiyquaYruvMjamq3UFrAb','1017075689@qq.com','620478','2018-06-08 22:06:16','2018-06-08 22:16:16',NULL,'2',NULL,NULL);

/*Table structure for table `t_settlement` */

DROP TABLE IF EXISTS `t_settlement`;

CREATE TABLE `t_settlement` (
  `settlementId` varchar(255) NOT NULL,
  `orderNum` int(11) DEFAULT NULL,
  `profit` double NOT NULL,
  `sales` double NOT NULL,
  `settlementDate` varchar(255) DEFAULT NULL,
  `shopId` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`settlementId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_settlement` */

insert  into `t_settlement`(`settlementId`,`orderNum`,`profit`,`sales`,`settlementDate`,`shopId`,`updateTime`) values ('2AV7Zj2Yre63NzqURzMbyEjm',0,0,0,'2018-07-26','iqIZriuyEry2367Z7bJnUBfa',NULL),('2q2eeqemyeaymymi6jyaMBFr',0,0,0,'2018-07-04','Z3MraiRnQf6zei6bUb22IvIn',NULL),('3IJRF3mqEZ7veyuEV3JFnmIj',0,0,0,'2018-07-20','iqIZriuyEry2367Z7bJnUBfa',NULL),('3iQNNvMbQZJnYJj2Mvbaieyq',0,0,0,'2018-07-07','Z3MraiRnQf6zei6bUb22IvIn',NULL),('3u6b6nUvIRbaqaqE7nNBzyai',0,0,0,'2018-07-14','Z3MraiRnQf6zei6bUb22IvIn',NULL),('3UvUFvJbUBRvbUjiQ3JBz2iu',0,0,0,'2018-07-06','NnYbi2UF7zmumQnARrZzIJBb',NULL),('3y6ZNnANrmIbfuq2Mny67rIn',0,4.4,20,'2018-03-14','myiqIzvi2iuuIFJBjaVVnayy','2018-03-14 23:38:30'),('3Y7ZZn6F77bqYz6Vbiqqa6Rz',0,0,0,'2018-06-11','iqIZriuyEry2367Z7bJnUBfa',NULL),('63MNVjIjqQbuaquIfeIFzaae',0,0,0,'2018-07-24','NnYbi2UF7zmumQnARrZzIJBb',NULL),('63UfUrBJRjQbqUfUZn32632y',0,0,0,'2018-07-16','NnYbi2UF7zmumQnARrZzIJBb',NULL),('673ErqNbQ3MbuY3aIj3iemai',0,0,0,'2018-07-06','Z3MraiRnQf6zei6bUb22IvIn',NULL),('6FJNJz2ayQR3uii6JzayUBjm',0,0,0,'2018-07-05','Z3MraiRnQf6zei6bUb22IvIn',NULL),('6NrqMjieY3QzB7VnQzAJJVr2',0,0,0,'2018-06-02','NnYbi2UF7zmumQnARrZzIJBb',NULL),('6VF7JrURfmYjQjeqQzqaABra',0,0,0,'2018-03-16','myiqIzvi2iuuIFJBjaVVnayy','2018-03-16 21:42:12'),('6vUZrm7riyaifm6fEjuUVJze',0,0,0,'2018-07-24','Z3MraiRnQf6zei6bUb22IvIn',NULL),('7fqIriYNFjAz2A3YNzN7nYFf',0,0,0,'2018-06-09','iqIZriuyEry2367Z7bJnUBfa',NULL),('7n2iMnvu63aqz6RriaZBbyei',0,0,0,'2018-07-06','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('7nea6vYfEVbuIbuIfa3INzYn',0,0,0,'2018-07-25','iqIZriuyEry2367Z7bJnUBfa',NULL),('7nyiq2AV367fzUzUnmnQraqq',0,0,0,'2018-07-18','NnYbi2UF7zmumQnARrZzIJBb',NULL),('aAbMNrMVRBbmBzYbYzjMjIre',0,0,0,'2018-07-05','iqIZriuyEry2367Z7bJnUBfa',NULL),('aaQjaeEvMvyqqUbuAzMveqii',0,0,0,'2018-05-30','NnYbi2UF7zmumQnARrZzIJBb',NULL),('aAVneqQjAzq2JNBveinIZJFb',4,8.8,40,'2018-03-15','myiqIzvi2iuuIFJBjaVVnayy','2018-03-15 22:41:23'),('AbQNBzVnq2m2IrMJfijQvmeq',0,1,2,'2018-05-27','NnYbi2UF7zmumQnARrZzIJBb','2018-05-27 12:38:58'),('AfMnai3QnqyqfqiArmqaqQ7n',0,0,0,'2018-06-09','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('aIfYFvvyyy6rmIN7JzIjy6fe',0,0,0,'2018-07-04','iqIZriuyEry2367Z7bJnUBfa',NULL),('aINZfq7vEFZnErYZFbi6Zbae',0,0,0,'2018-07-20','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('aQ7VruzYZryeV7JvMjvEbMzq',0,0,0,'2018-06-29','Z3MraiRnQf6zei6bUb22IvIn',NULL),('ARze22RRNJNzuQVjymviUbiq',0,0,0,'2018-05-15',NULL,'2018-05-15 21:22:43'),('Avq2uyb2MBVvQNvMVzBBBjui',0,4.4,20,'2018-03-14','ueammaAfaMBjeAJfmiZ3QFZv','2018-03-14 23:37:02'),('AvQvii6ZbIzuAFzQVvZnEz2y',0,0,0,'2018-06-28','NnYbi2UF7zmumQnARrZzIJBb',NULL),('aYBRjerieiQzVFJreiNzqY3e',0,0,0,'2018-05-29','NnYbi2UF7zmumQnARrZzIJBb',NULL),('baaiAvR7jEJvmaUvma3emQJb',0,0,0,'2018-03-25','Aj6Z7fJbEzUfZB7ZJbvAbMVn','2018-03-25 21:24:13'),('BbYBRfBVnu6f6JzMFnzaY3qi',0,0,0,'2018-07-12','Z3MraiRnQf6zei6bUb22IvIn',NULL),('BFrQ3232Yr2ureQf2qzuqiUv',0,0,0,'2018-07-05','NnYbi2UF7zmumQnARrZzIJBb',NULL),('BJrmmmJ7nMvyQBNjUvVVZ3yu',0,0,0,'2018-07-18','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('BnYFzymAv6JvmYN3AnNBrqae',0,0,0,'2018-06-16','iqIZriuyEry2367Z7bJnUBfa',NULL),('BrMzEfy2aauiVfERrumqyyie',0,0,0,'2018-06-08','NnYbi2UF7zmumQnARrZzIJBb',NULL),('BVvqAvB3aQRjaeYjUvua6fQv',0,0,0,'2018-07-04','NnYbi2UF7zmumQnARrZzIJBb',NULL),('byEzAvyyMZbyIzeaaur6jyQ3',0,0,0,'2018-07-15','iqIZriuyEry2367Z7bJnUBfa',NULL),('eArER3qEZFbmaaE7NnqQ7niu',0,0,0,'2018-07-16','Z3MraiRnQf6zei6bUb22IvIn',NULL),('eENBRvUrQFNzR3iaq2u6jYVv',0,0,0,'2018-07-18','Z3MraiRnQf6zei6bUb22IvIn',NULL),('ENZFzyfERzQfAFnIVfFNZVJf',0,0,0,'2018-07-16','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('eqIVbumYZBJjZNJbMbIvq6zm',1,3,23,'2018-06-16','NnYbi2UF7zmumQnARrZzIJBb','2018-06-16 17:34:19'),('ERJ7f23quQjmrq2iM3EFzui2',1,8,73,'2018-06-03','NnYbi2UF7zmumQnARrZzIJBb','2018-06-03 22:22:43'),('Ev6F3aYRJNzyfUzQRn7r22Ar',0,0,0,'2018-07-04','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('EvEb2iNFnYvyU3y6zu2ANZ3y',0,0,0,'2018-07-07','NnYbi2UF7zmumQnARrZzIJBb',NULL),('EvQNJ3AfMJ3eQ7jIZzZvEnMn',0,0,0,'2018-07-03','NnYbi2UF7zmumQnARrZzIJBb',NULL),('EvyeaqVjAVnynQBJzmaEnyui',0,0,0,'2018-06-12','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('eymy6reiyeAvVzMRJfe2UnQj',0,0,0,'2018-07-12','iqIZriuyEry2367Z7bJnUBfa',NULL),('f6FnUzqYjAF3ziE7JfzEFbMn',0,0,0,'2018-07-20','NnYbi2UF7zmumQnARrZzIJBb',NULL),('fABJ3aYV36fiJZvIBjvq6N3i',0,0,0,'2018-06-12','NnYbi2UF7zmumQnARrZzIJBb',NULL),('fAF7reveiURjm6NfymyuMbAf',0,0,0,'2018-06-11','NnYbi2UF7zmumQnARrZzIJBb',NULL),('FFBBfeMbqA7buAbMJrVn6Zni',0,0,0,'2018-06-04','iqIZriuyEry2367Z7bJnUBfa',NULL),('FfiEzyUF3Uby6zaiEfaYbeEr',0,0,0,'2018-07-06','iqIZriuyEry2367Z7bJnUBfa',NULL),('fIb2me2eaEz273IZveBbiMbi',0,0,0,'2018-07-26','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('fiu2i2aqQ77jq6fUJjY3UBva',0,0,0,'2018-06-10','Z3MraiRnQf6zei6bUb22IvIn',NULL),('fQb2InQRFbYvJfEZ3yZ3ErI3',0,0,0,'2018-05-15','NnYbi2UF7zmumQnARrZzIJBb','2018-05-15 21:12:21'),('Fvqy6fUVZbaiaqUZn2QZbyU3',0,0,0,'2018-06-11','Z3MraiRnQf6zei6bUb22IvIn',NULL),('I36ZvqBjuuuqqUBfaaEBVVZz',0,0,0,'2018-03-27','Aj6Z7fJbEzUfZB7ZJbvAbMVn','2018-03-27 19:37:14'),('I7VviaEbAFF3UNzaEjfqMRbi',0,0,0,'2018-06-06','iqIZriuyEry2367Z7bJnUBfa',NULL),('ia6fq22amuM3IZzuiie2UFri',0,0,0,'2018-06-06','NnYbi2UF7zmumQnARrZzIJBb',NULL),('iaieEvZRZnMriQBrumBJve6f',0,0,0,'2018-07-12','NnYbi2UF7zmumQnARrZzIJBb',NULL),('IBNjauURjm2a7vM3yuYRNZvm',0,0,0,'2018-06-13','NnYbi2UF7zmumQnARrZzIJBb',NULL),('IbuUVfiuYjQfziQJFnR32Eru',0,0,0,'2018-06-05','NnYbi2UF7zmumQnARrZzIJBb',NULL),('If6ryyV73Ezivy2qIvJ7nyIj',0,0,0,'2018-07-25','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('IFBbYjNny6FnaymABfqi6f6n',0,0,0,'2018-07-19','NnYbi2UF7zmumQnARrZzIJBb',NULL),('IfUfiiq2ERV3fMvyU3VZBN7j',0,0,0,'2018-07-07','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('IFv2mmE773aaMJN7N33IfM3a',0,0,0,'2018-07-17','iqIZriuyEry2367Z7bJnUBfa',NULL),('Ij2ARnu2mURz2YBBZzARVVjm',0,0,0,'2018-06-13','iqIZriuyEry2367Z7bJnUBfa',NULL),('Inemmu2a26Fj7Rniayau6bae',0,1,2,'2018-05-26','NnYbi2UF7zmumQnARrZzIJBb','2018-05-26 00:37:08'),('IniAv2IJNZnmRZbEZzfEVfEf',0,1,2,'2018-05-25','NnYbi2UF7zmumQnARrZzIJBb','2018-05-25 21:31:10'),('iUVzQfMVfyYjfuaMjeBzUnii',0,0,0,'2018-07-17','Z3MraiRnQf6zei6bUb22IvIn',NULL),('iUz2IfaYBrInEju2ueMjMjA3',0,0,0,'2018-06-06','Z3MraiRnQf6zei6bUb22IvIn',NULL),('iUZfuurAzINryQVneeviEzUn',0,0,0,'2018-06-12','Z3MraiRnQf6zei6bUb22IvIn',NULL),('IVJVFnENv2YveIbaMzeyu2Af',0,0,0,'2018-06-28','iqIZriuyEry2367Z7bJnUBfa',NULL),('iY32YnYzmiUnrEBV7rmUVna2',0,0,0,'2018-07-25','Z3MraiRnQf6zei6bUb22IvIn',NULL),('iYN3M3imYFrizy6ZFn7Jvy6r',0,0,0,'2018-02-18','NnYbi2UF7zmumQnARrZzIJBb','2018-03-18 12:19:13'),('jam2QruieMvefMbiqm7naUBr',0,0,0,'2018-06-01','NnYbi2UF7zmumQnARrZzIJBb',NULL),('JBbmi2UJF7VrqmMBNbneuq22',0,0,0,'2018-06-07','iqIZriuyEry2367Z7bJnUBfa',NULL),('JF3Mry3iuIR3RF73YfNruMfm',0,0,0,'2018-07-24','iqIZriuyEry2367Z7bJnUBfa',NULL),('JfqyYf3e2eEjYzAvEvEVfUFf',0,0,0,'2018-01-18','myiqIzvi2iuuIFJBjaVVnayy','2018-03-18 12:40:48'),('JFRJz2eaYnuaEJrUZzb22UNj',0,0,0,'2018-07-26','Z3MraiRnQf6zei6bUb22IvIn',NULL),('jINrEfimaEVraqemiuey2Afe',0,0,0,'2018-06-09','NnYbi2UF7zmumQnARrZzIJBb',NULL),('jQRnAv2i6nMfMNbiIfJJfqui',0,0,0,'2018-07-12','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('JZ73Ajuye6b2RNzEFb6ZRjia',0,0,0,'2018-06-05','Z3MraiRnQf6zei6bUb22IvIn',NULL),('JZZzymvay6VzVNvYbqbaIZjy',0,0,0,'2018-07-19','iqIZriuyEry2367Z7bJnUBfa',NULL),('MBveieyeAfi2aqQ36b6vQRB3',0,0,0,'2018-07-17','NnYbi2UF7zmumQnARrZzIJBb',NULL),('MJj6neEZRfQr6jMBNnuAZJZ3',0,0,0,'2018-06-08','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('MNBr2iiMjuymAbYrmeamiYzi',0,0,0,'2018-07-07','iqIZriuyEry2367Z7bJnUBfa',NULL),('mqYziaMnmaUf3IZjueJV3yyu',0,0,0,'2018-05-14','NnYbi2UF7zmumQnARrZzIJBb','2018-05-14 15:27:26'),('muyqY3nQB7rmaEv6Jn26vMRv',0,0,0,'2018-03-17','myiqIzvi2iuuIFJBjaVVnayy','2018-03-18 14:16:32'),('MZ7n6vnmmiuaJ736FrjuqAFn',0,0,0,'2018-06-04','Z3MraiRnQf6zei6bUb22IvIn',NULL),('n6Jvi2aAbeqqimm2uuUvueMf',0,0,0,'2018-06-08','Z3MraiRnQf6zei6bUb22IvIn',NULL),('N7fuAnmuMnUjiAreIfM7RN7f',0,1,2,'2018-05-21','NnYbi2UF7zmumQnARrZzIJBb','2018-05-21 22:16:44'),('nMJVJbYBjMFnneqaeqUB7nMr',0,0,0,'2018-06-05','iqIZriuyEry2367Z7bJnUBfa',NULL),('NNfAvm7Fni6jmYJfMnneAZJv',0,0,0,'2018-06-04','NnYbi2UF7zmumQnARrZzIJBb',NULL),('nyIJBbaiaqArfaEbiijMVjIj',0,0,0,'2018-06-08','iqIZriuyEry2367Z7bJnUBfa',NULL),('NzEz2yfmUvuu3YryqyMFjIFf',0,0,0,'2018-06-10','NnYbi2UF7zmumQnARrZzIJBb',NULL),('QBVfy2NJNjyiM32qUziQVRFz',0,0,0,'2018-06-29','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('qEJFfumYjMRnfYbaYvRjM3Mn',0,0,0,'2018-06-11','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('qM7b6bvYzIZjB7Nzmm2uaumq',0,0,0,'2018-07-05','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('qYja2mmI7VVjNvMbameI7VZb',0,0,0,'2018-06-04','undefined',NULL),('r6zU7rEzeI3ezMvYnmA77rEf',0,0,0,'2018-06-28','Z3MraiRnQf6zei6bUb22IvIn',NULL),('R7RfeeFbaMNvBvU3eeamY7ni',0,0,0,'2018-06-13','Z3MraiRnQf6zei6bUb22IvIn',NULL),('R7V3QzB32yY3NB367njArMRr',0,0,0,'2018-05-31','NnYbi2UF7zmumQnARrZzIJBb',NULL),('RbmUnq77fuMfiYFbaeRf26Nf',0,1,23.65,'2018-05-19','NnYbi2UF7zmumQnARrZzIJBb','2018-05-19 00:16:27'),('RBzaMrvaIz6naURvQfb2yem2',0,0,0,'2018-06-07','Z3MraiRnQf6zei6bUb22IvIn',NULL),('rU7zqiYJvaQruqMzQbMzuum2',0,0,0,'2018-05-17','NnYbi2UF7zmumQnARrZzIJBb','2018-05-17 19:53:32'),('ryURN3QZrAf2UBNjAzMb2Yjm',0,0,0,'2018-05-19','undefined','2018-05-19 00:15:13'),('rYVRnqfeYri2mEZFzuMby2An',0,0,0,'2018-07-15','Z3MraiRnQf6zei6bUb22IvIn',NULL),('U3MnQ3qUfYBzmqiuq2IfyaYj',0,1,1,'2018-05-28','NnYbi2UF7zmumQnARrZzIJBb',NULL),('U7buieu2y2mi73QrAvFBJjYn',0,0,0,'2018-07-25','NnYbi2UF7zmumQnARrZzIJBb',NULL),('UB7NFjzaMJ7bz2mIJzNveQfm',0,0,0,'2018-06-10','iqIZriuyEry2367Z7bJnUBfa',NULL),('UBra6nNbq67rIB3qA3ZV7Rzy',0,0,0,'2018-07-16','iqIZriuyEry2367Z7bJnUBfa',NULL),('UBRrqazuEf6zbAJb2maeyqm2',0,0,0,'2018-06-29','iqIZriuyEry2367Z7bJnUBfa',NULL),('uIzIBnmqmQZrNFRz2iURbyy2',0,0,0,'2018-07-03','Z3MraiRnQf6zei6bUb22IvIn',NULL),('vAfYRzYbuymyYfq6fuiuiYZ3',0,0,0,'2018-07-14','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('vAjeqyfA3ARj6raYBnuAJvMf',2,0,24,'2018-07-19','Z3MraiRnQf6zei6bUb22IvIn','2018-07-19 22:01:19'),('VfAFb2NfQziaJRbyqaZfAR3q',0,0,0,'2018-07-14','NnYbi2UF7zmumQnARrZzIJBb',NULL),('VFfMZ3YzYnqquyqyAvreMZ3y',0,0,0,'2018-07-26','NnYbi2UF7zmumQnARrZzIJBb',NULL),('vIJ3imNJbAjuM7juAzjmUNNb',0,0,0,'2018-07-20','Z3MraiRnQf6zei6bUb22IvIn',NULL),('vQfIRbz6vUf2yeqIRfZ7zEBj',0,0,0,'2018-07-03','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('VzYr2ubIBvMnbENvqyeuIrae',0,0,0,'2018-06-09','Z3MraiRnQf6zei6bUb22IvIn',NULL),('Yfaaaa7rAVRj7rYN7r2IVrmu',0,0,0,'2018-07-17','NJbUb2e6NJJ3BZVrMjyaiIfa',NULL),('yiiMrqBB3EnumiaeeeNjiQve',0,0,0,'2018-07-18','iqIZriuyEry2367Z7bJnUBfa',NULL),('YjIVbiNzUbIr6RzMziUVRJb2',0,0,0,'2018-07-14','iqIZriuyEry2367Z7bJnUBfa',NULL),('z2I7zmuuyIR36BbmmymUjeai',0,0,0,'2018-07-03','iqIZriuyEry2367Z7bJnUBfa',NULL),('zIvIF3zQzA3qaEJjaajmMJra',0,0,0,'2018-07-15','NnYbi2UF7zmumQnARrZzIJBb',NULL),('ZJziauFjUvAjQFB77jziEzqy',0,0,0,'2018-06-12','iqIZriuyEry2367Z7bJnUBfa',NULL),('ZnU7JfZBjaI3AJJB7rbMRNfu',0,0,0,'2018-06-29','NnYbi2UF7zmumQnARrZzIJBb',NULL),('zQnY7rj63IbqvAnMBbiyIbMf',1,0,0,'2018-06-16','Z3MraiRnQf6zei6bUb22IvIn','2018-06-16 17:34:00'),('zYJNbmEVRrAjABJvYrYvYJV3',12,28.599999999999994,130,'2018-03-18','myiqIzvi2iuuIFJBjaVVnayy','2018-03-18 14:43:42'),('ZZFNRfrAZjmyn6vqqeq2yUN3',0,0,0,'2018-06-07','NnYbi2UF7zmumQnARrZzIJBb',NULL);

/*Table structure for table `t_shop` */

DROP TABLE IF EXISTS `t_shop`;

CREATE TABLE `t_shop` (
  `shopId` varchar(255) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUserId` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `shopName` varchar(255) DEFAULT NULL,
  `shop_id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `owner_user_id` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`shopId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_shop` */

insert  into `t_shop`(`shopId`,`createTime`,`createUserId`,`location`,`shopName`,`shop_id`,`create_time`,`create_user_id`,`owner_user_id`,`shop_name`,`status`)
values ('iqIZriuyEry2367Z7bJnUBfa','2018-06-04 23:19:22','iuYNzyMRn6FvYzYFjufuYzYf','2123','dianpi2','',NULL,NULL,NULL,NULL,NULL),
('NJbUb2e6NJJ3BZVrMjyaiIfa','2018-06-08 23:24:47','fUZNzy3Ir6vqrQn6RnJJvYfa','莱娅媛','君乐超市','',NULL,NULL,NULL,NULL,NULL),
('NnYbi2UF7zmumQnARrZzIJBb','2018-05-14 15:08:13','iuYNzyMRn6FvYzYFjufuYzYf','加油站','君乐超市','',NULL,NULL,NULL,NULL,NULL),
('Z3MraiRnQf6zei6bUb22IvIn','2018-06-04 23:36:56','iuYNzyMRn6FvYzYFjufuYzYf','weqwe','qweew','',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `t_shop_user_relation` */

DROP TABLE IF EXISTS `t_shop_user_relation`;

CREATE TABLE `t_shop_user_relation` (
  `relationId` varchar(255) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `relationType` int(11) DEFAULT NULL,
  `shopId` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`relationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_shop_user_relation` */

insert  into `t_shop_user_relation`(`relationId`,`createTime`,`relationType`,`shopId`,`updateTime`,`userId`) values ('6Z7FraQ7BnqeIV7ramrUz6Rj','2018-06-08 23:24:47',0,'NJbUb2e6NJJ3BZVrMjyaiIfa','2018-06-08 23:24:47','fUZNzy3Ir6vqrQn6RnJJvYfa'),('InIbeuBZZjUzYZBFFnNbMz2i','2018-06-08 23:24:47',1,'NJbUb2e6NJJ3BZVrMjyaiIfa','2018-06-08 23:24:47','fUZNzy3Ir6vqrQn6RnJJvYfa'),('JfQVje7VbQ7fziEBrqEn2Q3q','2018-06-04 23:36:56',0,'Z3MraiRnQf6zei6bUb22IvIn','2018-06-04 23:36:56','iuYNzyMRn6FvYzYFjufuYzYf'),('qEB7n2Fj2Ev2mEnMFn22mqya','2018-06-04 23:19:22',1,'iqIZriuyEry2367Z7bJnUBfa','2018-06-04 23:19:22','iuYNzyMRn6FvYzYFjufuYzYf'),('qumANvm67fuyjieYBrjyauQn','2018-05-14 15:08:13',1,'NnYbi2UF7zmumQnARrZzIJBb','2018-05-14 15:08:13','iuYNzyMRn6FvYzYFjufuYzYf'),('qy6bMjRJJZzmNN3YJneeAV3a','2018-06-04 23:36:56',1,'Z3MraiRnQf6zei6bUb22IvIn','2018-06-04 23:36:56','iuYNzyMRn6FvYzYFjufuYzYf'),('vu2AJb2myMfeMbYr6bVvmiiu','2018-05-14 15:08:13',0,'NnYbi2UF7zmumQnARrZzIJBb','2018-05-14 15:08:13','iuYNzyMRn6FvYzYFjufuYzYf'),('zaYrauaYv6ZfVryUbaF7fIJr','2018-06-04 23:19:22',0,'iqIZriuyEry2367Z7bJnUBfa','2018-06-04 23:19:22','iuYNzyMRn6FvYzYFjufuYzYf');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `userId` varchar(255) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `createType` int(11) DEFAULT NULL,
  `openId` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_type` int(11) DEFAULT NULL,
  `open_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`userId`,`createTime`,`createType`,`openId`,`password`,`phone`,`status`,`userName`,`email`,`user_id`,`create_time`,`create_type`,`open_id`,`user_name`) values ('1','2018-03-25 21:23:50',2,'wwe','2',NULL,0,'2','1@qq.com','',NULL,NULL,NULL,NULL),('fUZNzy3Ir6vqrQn6RnJJvYfa','2018-06-08 22:55:48',0,NULL,'1',NULL,1,'wangshengtest','1017075689@qq.com','',NULL,NULL,NULL,NULL),('iuYNzyMRn6FvYzYFjufuYzYf','2018-03-25 21:23:50',1,'oTkgk0eb-UXIWyqXidQZhLldHzMY','1',NULL,0,'1',NULL,'',NULL,NULL,NULL,NULL);
