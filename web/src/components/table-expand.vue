<template>
    <Table :columns="columns1" :data="data1"></Table>
</template>

<script>
    export default {
        name: "table-expand",
        props: {
            data1: Array
        },
        data () {
            return {
                columns1: [
                    {
                        title: '序号', key: 'id', align: 'center', render: (h, params) => {
                            return h("div", params.row._index + 1 )
                        }
                    },
                    {
                        title: '项目内容编号',
                        align: 'center',
                        key: 'id'
                    },
                    {
                        title: '主类型',
                        align: 'center',
                        key: 'ptypeid',
                        render: (h, params) => {
                            return h("div", this.enumConvert(params.row.ptypeid,this.ptypeenum) )
                        }

                    },
                    {
                        title: '次类型',
                        align: 'center',
                        key: 'subid',
                        render: (h, params) => {
                            return h("div", this.enumConvert(params.row.subid,this.subtypeenum) )
                        }
                    },
                    {
                        title: '是否安排',
                        align: 'center',
                        key: 'isarrange',
                        render: (h, params) => {
                            return h("div", params.row.isarrange===0?"未安排":"已安排" )
                        }
                    },
                    {
                        title: '负责人',
                        align: 'center',
                        key: 'mainpeople'
                    },
                    {
                        title: '编制人',
                        align: 'center',
                        key: 'preparedby'
                    },
                    {
                        title: '是否已查看',
                        align: 'center',
                        key: 'newlyflag',
                        render: (h, params) => {
                            return h("div", params.row.newlyflag===0?"未查看":"已查看" )
                        }
                    }
                    ,
                    {
                        title: '是否完成',
                        align: 'center',
                        key: 'isfinish',
                        render: (h, params) => {
                            return h("div", params.row.isfinish===0?"未完成":"已完成" )
                        }
                    }
                    ,
                    {
                        title: '进度',
                        align: 'center',
                        key: 'schedule',
                        render: (h, params) => {
                            return h("div", params.row.schedule + "%" )
                        }
                    }
                    ,
                    {
                        title: '备注',
                        align: 'center',
                        key: 'remark'
                        ,tooltip:'true'},
                ],
                ptypeenum:[],
                subtypeenum:[]
            }
        },
        async created() {
            await this.$axios.get(`/ptype/ptypeenum`).then(res => {
                this.ptypeenum = res.data;


            }).catch(exmsg => {
                console.log(exmsg)
            })

            await this.$axios.get(`/ptype/subenum`).then(res => {
                this.subtypeenum = res.data;

            }).catch(exmsg => {
                console.log(exmsg)
            })

            this.$axios.get(`/system/proledata?username=${sessionStorage.username}`).then(res => {
                const tmp = this.data1.filter(item=>item.newlyflag===0);
                if(res.data==1&&tmp.length>0){
                    this.$axios.put(`/pro/read`,tmp).then(() => {
                      //  this.subtypeenum = res.data;

                    }).catch(exmsg => {
                        console.log(exmsg)
                    })
                }

            }).catch(exmsg => {
                console.log(exmsg)
            })





        },

        methods:{
            enumConvert(code, enums) {
                if (enums === null) return null;
                let tmp = enums.find(item => item.code === code);
                return tmp === undefined ? null : tmp.name
            },
        }
    }
</script>

<style scoped>
    .expand-row{
        margin-bottom: 16px;
    }
</style>