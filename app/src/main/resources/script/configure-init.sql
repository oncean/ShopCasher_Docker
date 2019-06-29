
/* 微信登陆的时候第三方请求微信API-小程序APPDID */
INSERT IGNORE INTO t_configure VALUES("1","appID","wx13f252631366e098","小程序APPID");

/* 微信登陆的时候第三方请求微信API-小程序的 app secret */
INSERT IGNORE INTO t_configure VALUES("2","secret","4bcdaf834a1a24ad379d5c8aac440443","小程序的app secret");

/* 微信登陆的时候第三方请求微信API的地址 */
INSERT IGNORE INTO t_configure VALUES("3","requestUrl","https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={code}&grant_type=authorization_code","微信登陆的时候第三方请求微信API的地址");
