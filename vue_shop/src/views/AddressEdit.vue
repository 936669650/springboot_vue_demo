<template>
    <van-address-edit
            :area-list="areaList"
            show-delete
            :address-info="addressInfo"
            save-button-text="修改"
            @save="onSave"
            @delete="onDelete"
    />
</template>


<script>
    import AreaList from '../api/area';
    import { Toast } from 'vant';

    export default {
        name: "AddressEdit",
        created(){
            let data = JSON.parse(this.$route.query.item);
            this.addressInfo = data
            let index = data.address.indexOf('区')
            if(index < 0) index = data.address.indexOf('县')
            this.addressInfo.addressDetail = data.address.substring(index+1)
        },
        data() {
            return {
                areaList: AreaList,
                addressInfo:null
            };
        },
        methods: {
            onSave() {
                Toast('save');
            },
            onDelete() {
                Toast('delete');
                history.go(-1)
            }
        },
    };
</script>

<style scoped>

</style>