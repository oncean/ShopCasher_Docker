
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
    outputDir: undefined,
    runtimeCompiler: undefined,
    parallel: undefined
}
