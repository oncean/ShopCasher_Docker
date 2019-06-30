const env = process.env.NODE_ENV;
let target = '';
// 默认是本地环境
if(env==='production'){  // 生产环境
    target = "http://35.220.161.215:8080/ShopCasher";
}else if(env==='development'){ // 测试环境
    target = "http://35.220.161.215:8080/ShopCasher";
}else{  // 本地环境
    target = "http://35.220.161.215:8080/ShopCasher";
}
module.exports = {
    assetsDir: 'static',

    css: {
        loaderOptions: {
            sass: {
                data: '@import "@/assets/scss/base.scss";'
            }
        }
    },

    productionSourceMap: false,

    devServer: {
        https: true,
        disableHostCheck: true,
        proxy: {
            '/ShopCasher': {
                target: target,
                secure: false,
                changeOrigin: true,
                pathRewrite: {
                    "^/ShopCasher": "/"
                }
            }
        }
    },

    baseUrl: '/ShopCasher/',
    outputDir: undefined,
    runtimeCompiler: undefined,
    parallel: undefined
}
