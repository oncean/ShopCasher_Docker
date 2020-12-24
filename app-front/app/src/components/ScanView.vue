<style lang="scss" scoped>
</style>
<template>
    <div>
        <quagga-scanner :onDetected="logIt" :readerSize="readerSize" :readerType="'ean_reader'"></quagga-scanner>
    </div>
</template>

<script>
    import { QuaggaScanner } from 'vue-quaggajs'
    export default {
        name: 'VueBarcodeTest',
        data () {
            return {
                readerSize: {
                    width: 500,
                    height: 480
                },
                detecteds: []
            }
        },
        components: {
            QuaggaScanner
        },
        methods: {
            logIt (data) {
                this.$message.info(JSON.stringify(data))
            }

        }
    }
</script>
