<style lang="scss" scoped>
    .upload-action {
        position: relative;
        min-height: 20px;
        display: inline-block;
        width: 31px;
        height: 27px;
        i {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }

        input[type='file'] {
            position: absolute;
            top: 0;
            left: 0;
            cursor: pointer;
            opacity: 0;
            z-index: 10;
            width: 100%;
            height: 100%;
        }
    }
</style>
<template>
    <div class="upload-action">
        <input type="file" accept="image/*;capture=camera" @change="pic($event)">
        <i class="el-icon-picture-outline" style="font-size: 30px;" @click="pic"></i>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                connectCode: '',
                visible: false
            }
        },
        mounted() {
        },
        methods: {
            pic(event) {
                let input = event.target
                if (input.files && input.files.length) {
                    this.decode(URL.createObjectURL(input.files[0]));
                }
            }
        }
    }
</script>
