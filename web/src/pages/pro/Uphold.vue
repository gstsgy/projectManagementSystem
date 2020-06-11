<template lang="html">
    <div class="usermanage">
        <Form :label-width="85" inline style="text-align:left;">
            <div>
                <!-- <Form-item label="姓名" style="margin-left:35px">
                     <Input v-model="sysLanguageView.remark1" @blur.native.capture="getInfo" style="width: 150px"/>
                 </Form-item>-->
            </div>
        </Form>
        <Row>
            <Col span="24" style="display:flex;justify-content:space-between">
                <div class="">
                    <Button :type="deleteButtonFlag?'default':'error'" :disabled="deleteButtonFlag" shape="circle"
                            v-if="$store.state.controls.c31" @click="deleteconfirm"

                            icon="md-trash">删除选中项
                    </Button>
                </div>
                <div class="">

                    <Button v-if="$store.state.controls.c39" type="primary" shape="circle" @click="addMes"
                            style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;">新&nbsp;&nbsp;增
                    </Button>
                </div>
            </Col>
        </Row>

        <br>
        <Table stripe border  :columns="tableColumns.card" :data="mesInfos"
               @on-selection-change="v=>this.deleteButtonFlag = v.length <= 0" @on-row-dblclick="showUpdate"
               ref="table"></Table>
        <br>
        <!--<div class="page">
            <Page show-sizer show-elevator show-total :total="this.total" :current="this.pageNum"
                  :page-size="this.pageSize"
                  :page-size-opts="[10,20,30]"
                  @on-change="changePagenum" @on-page-size-change="changePagesize" style="text-align:center"></Page>
        </div>-->


        <Modal v-model="delete_confirm" width="360" style="font-size:14px">
            <p slot="header" style="color:#f60;text-align:center">
                <Icon type="ios-information-circle"></Icon>
                <span>删除确认</span>
            </p>
            <div style="text-align:center">
                <span>确定要删除吗？</span>
            </div>
            <div slot="footer" style="display:flex;justify-content:center;">
                <Button type="error" style="width:120px;" shape="circle" @click="deleteSelection">确定删除</Button>
            </div>
        </Modal>

        <Modal class="modal_cargo" v-model="insertModelCtrl" width="800px" :styles="{top: '85px'}"
               :mask-closable="false"
               @on-cancel="insert_cancel">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">新增类型</span>
            </p>
            <Form :model="insertModel">
                <Row>

                    <Col span="6">

                        <Form-item label="类型名称" style="margin-left:35px">
                            <Input v-model="insertModel.name" @blur.native.capture="insetCheck" style="width: 150px"/>
                        </Form-item>
                    </Col>


                </Row>
            </Form>

            <div slot="footer">
                <Button type="primary" shape="circle"
                        style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;" @click="insert">提交
                </Button>
            </div>
        </Modal>
        <Modal class="modal_cargo" v-model="updateModelCtrl" width="800px" :styles="{top: '85px'}"
               :mask-closable="false"
               @on-cancel="updateModelCtrl=false">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">次类型查看</span>
            </p>


            <Row>
                <Col span="24" style="display:flex;justify-content:space-between">
                    <div class="">
                        <Button type="primary" shape="circle" v-if="!deleteButtonFlag1" @click="deleteconfirm1"
                                style="width:120px;height:28px;border-radius:14px;background-color:#fe3838;border:0 none;"
                                icon="md-trash">删除选中项
                        </Button>
                        <Button type="primary" shape="circle" disabled v-if="deleteButtonFlag1"
                                style="width:120px;height:28px;border-radius:14px;background-color:white;color:#b4b4b4"
                                icon="md-trash">删除选中项
                        </Button>
                    </div>
                    <div class="">

                        <Button type="primary" shape="circle" @click="addMes1"
                                style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;">新&nbsp;&nbsp;增
                        </Button>
                    </div>
                </Col>
            </Row>

            <br>
            <Table stripe border  :columns="tableColumns.card1" :data="mesInfos1"
                   @on-selection-change="v=>this.deleteButtonFlag1 = v.length <= 0"
                   ref="table1"></Table>
            <br>
            <div slot="footer">
                <!-- <Button type="primary" shape="circle"
                         style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;" @click="update1">密码重置
                 </Button>-->
                <Button type="primary" shape="circle"
                        style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;" @click="update">提交
                </Button>
            </div>
        </Modal>

    </div>
</template>

<script>
    import iView from 'iview'

    export default {
        data() {
            return {
                selectType: null,
                mesInfos: [],
                mesInfos1: [],
                insertModelCtrl: false,

                updateModelCtrl: false,
                deleteButtonFlag: true,

                insertModel: {
                    name: null
                },

                inertModelSubmitCtrl: true,
                updateModelSubmitCtrl: true,
                delete_confirm: false,
                delete_confirm1: false,
                deleteButtonFlag1: false,
                tableColumns: {
                    card: [
                        {title: '选择', key: 'select', type: 'selection', width: 50, align: 'center'},
                        {
                            title: '序号', key: 'no', align: 'center', width: 75, render: (h, params) => {
                                return h("div", +params.row._index + 1)
                            }
                        },
                        {title: '编号', key: 'id', align: 'center'},
                        {title: '名称', key: 'name', align: 'center'}


                    ],
                    card1: [
                        {title: '选择', key: 'select', type: 'selection', width: 50, align: 'center'},
                        {
                            title: '序号', key: 'no', align: 'center', width: 75, render: (h, params) => {
                                return h("div", params.row._index + 1)
                            }
                        },
                        {
                            title: '主类型', key: 'tid', align: 'center', width: 75, render: (h, params) => {
                                return h("div", this.mesInfos.find(item => item.id === params.row.tid).name)
                            }
                        },
                        {title: '编号', key: 'subid', align: 'center'},
                        {
                            title: '名称', key: 'name', align: 'center', render: (h, params) => {
                                if (params.row.subid === null) {
                                    return h('Input', {
                                        props: {
                                            value: params.row.name,
                                            size: 'small',
                                        },
                                        on: {
                                            'on-change': (event) => {
                                                // console.log(event.target.value)
                                                const tmp = this.mesInfos1.find(item => item.name === event.target.value)
                                                if (tmp === undefined) {
                                                    this.mesInfos1[params.row._index].name = event.target.value
                                                } else {
                                                    iView.Message.error({
                                                        content: "次类型已存在！",
                                                        duration: 2
                                                    });
                                                }
                                            }
                                        },
                                    })
                                } else {
                                    return h('div', params.row.name);
                                }
                            }
                            /*,render: (h, params) => {

                                if (params.row.subid===null) {
                                    params.row.$isEdit=true;
                                    return h('input', {

                                        domProps: {
                                            value: params.row.name
                                        },
                                        on: {
                                            change:  (event) =>{
                                               const tmp =this.mesInfos1.find(item=>item.name===event.target.value)
                                                if(tmp===undefined){
                                                    this.mesInfos1[params.row._index].name = event.target.value
                                                }else{
                                                    iView.Message.error({
                                                        content: "次类型已存在！",
                                                        duration: 2
                                                    });
                                                }
                                            }
                                        }
                                    });
                                } else  {

                                    return h('div', params.row.name);
                                }
                            }*/

                        }


                    ],
                }
            }
        },

        async created() {
            await this.getInfo();
        },

        computed: {},

        methods: {
            deleteconfirm() {

                this.delete_confirm = true;
            },
            deleteconfirm1() {

                // console.log(this.$refs.table.getSelection())
                const list = this.$refs.table1.getSelection().filter(item => item.subid != null);


                if (list.lenth > 0) {
                    iView.Message.error({
                        content: "未选择数据！",
                        duration: 2
                    });
                    this.delete_confirm = false;
                    return;
                }

                this.$axios.delete(`/ptype/subtype`, {
                    data: list
                }).then(res => {
                    if (res.data) {
                        iView.Message.info({
                            content: "删除成功！",
                            duration: 2
                        });

                        this.$axios.get(`/ptype/subtypebyid?id=${this.selectType.id}`).then(res => {
                            this.mesInfos1 = res.data;
                            // console.log(this.mesInfos)
                            this.deleteButtonFlag1 = true;

                        }).catch(exmsg => {
                            console.log(exmsg)
                        })
                        //  this.delete_confirm = false;

                    }
                }).catch(() => {
                    /* iView.Message.error({
                       content: exmsg,
                       duration: 2
                     });*/
                    // this.delete_confirm = false;
                })
            },

            getInfo() {
                this.$axios.get(`/ptype/ptypes`).then(res => {
                    this.mesInfos = res.data;
                    //  console.log(this.mesInfos)
                    this.deleteButtonFlag = true;

                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },


            insetCheck() {
                const tmp = this.mesInfos.find(item => item.name === this.insertModel.name)
                if (tmp !== undefined) {
                    iView.Message.error({
                        content: "类型已存在！",
                        duration: 2
                    });
                    this.insertModel.name = "";
                    //  return;
                }
                //  return bo;
                //insertModel.controlID
            },


            insert() {

                if (!this.inertModelSubmitCtrl)
                    return;
                this.inertModelSubmitCtrl = false;

                if (this.insertModel.name == null || this.insertModel.name === "") {
                    iView.Message.error({
                        content: "名称不能为空！",
                        duration: 2
                    });
                    this.inertModelSubmitCtrl = true;
                    return;
                }


                //this.insertModel.envalue = this.insertModel.CNvalue;

                this.$axios.post(`/ptype/ptype`, this.insertModel).then(res => {

                    if (res.data) {
                        iView.Message.info({
                            content: "新增成功！",
                            duration: 2
                        });
                        this.insertModelCtrl = false;
                        this.inertModelSubmitCtrl = true;
                        this.insertModel = {
                            name: null
                        }
                        this.getInfo(1)
                    }
                }).catch(() => {

                    this.inertModelSubmitCtrl = true;
                })
            },

            insert_cancel() {
                this.insertModelCtrl = false;

            },

            addMes() {
                this.insertModelCtrl = true
            },
            addMes1() {
                this.mesInfos1.push(
                    {
                        subid: null,
                        tid: this.selectType.id,
                        name: null,
                    }
                )
            },


            enumConvert(code, enums) {
                if (enums === null) return null;
                let tmp = enums.find(item => item.code === code);
                return tmp === undefined ? null : tmp.name
            },

            deleteSelection() {
                // console.log(this.$refs.table.getSelection())
                const list = this.$refs.table.getSelection();


                if (list.lenth > 0) {
                    iView.Message.error({
                        content: "未选择数据！",
                        duration: 2
                    });
                    this.delete_confirm = false;
                    return;
                }

                this.$axios.delete(`/ptype/ptype`, {
                    data: list
                }).then(res => {
                    if (res.data) {
                        iView.Message.info({
                            content: "删除成功！",
                            duration: 2
                        });

                        this.getInfo(1);
                        this.delete_confirm = false;

                    }
                }).catch(() => {
                    /* iView.Message.error({
                       content: exmsg,
                       duration: 2
                     });*/
                    this.delete_confirm = false;
                })
            },

            showUpdate(v) {
                if (!this.$store.state.controls.c23) return
                this.selectType = v;
                this.$axios.get(`/ptype/subtypebyid?id=${v.id}`).then(res => {
                    this.mesInfos1 = res.data;
                    // console.log(this.mesInfos)
                    this.deleteButtonFlag1 = true;

                }).catch(exmsg => {
                    console.log(exmsg)
                })
                this.updateModelCtrl = true;
            },

            update() {
                if (!this.updateModelSubmitCtrl)
                    return;
                this.updateModelSubmitCtrl = false;
                const list = this.mesInfos1.filter(item => item.subid === null);
                const tmp = this.mesInfos1.filter(item => item.name === null);

                if (tmp.length > 0) {
                    iView.Message.error({
                        content: "名称不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }
                /*list.forEach(item=>
                    this.mesInfos1.find(it=>it.name)
                )*/
                //  console.log(list)
                /* if (this.updateModel.username == null || this.insertModel.username === "") {
                     iView.Message.error({
                         content: "账号不能为空！",
                         duration: 2
                     });
                     this.updateModelSubmitCtrl = true;
                     return;
                 }
                 if (this.updateModel.remark1 == null || this.insertModel.remark1 === "") {
                     iView.Message.error({
                         content: "姓名不能为空！",
                         duration: 2
                     });
                     this.updateModelSubmitCtrl = true;
                     return;
                 }*/


                list.forEach(item => {
                    this.$axios.post(`/ptype/subtype`, item).then(() => {
                    }).catch(() => {
                    })
                })
                iView.Message.info({
                    content: "新增成功！",
                    duration: 2
                });
                this.updateModelSubmitCtrl = true;
                this.updateModelCtrl = false;
            }

        }
    }

</script>

<style lang="css">
    .ivu-table-overflowX {
        overflow-x: hidden;
    }
</style>

<style lang="less">
    .rolemodalstyle {

        color: #5aa5ff !important;
        font-size: 12px !important;
        font-family: "Microsoft YaHei";
        line-height: 20px !important;

        .ivu-input-number {
            width: 160px !important;
            height: 32px !important;
            border: 1px solid #888888;
        }

        .ivu-input {
            border: 1px solid #888888;
        }

    }

    .usermanage {
        th .ivu-table-cell {
            font-size: 12px !important;
            font-weight: bold;
            color: #333;
        }

        .ivu-table-tbody {
            line-height: 16px !important;
        }

        .ivu-form .ivu-form-item-label {
            color: #5aa5ff !important;
            font-size: 12px !important;
            font-family: "Microsoft YaHei";
            line-height: 20px;
        }

        .ivu-input-wrapper {
            width: 160px;
            height: 20px;
        }

        .ivu-select {
            width: 160px;
            height: 20px;
        }

        .ivu-tabs-nav .ivu-tabs-tab-active {
            background-color: white !important;
            font-size: 20px;
            border-radius: 8px;
        }

        .ivu-tabs-ink-bar {
            background-color: #fff;
        }

        .ivu-tabs-nav .ivu-tabs-tab {
            font-size: 14px;
            background-color: #f3f3f3;
        }

        .ivu-tabs-nav-scroll {
            background-color: #f3f3f3;
        }

        .ivu-input {
            border: 1px solid #888888;
        }

        .ivu-select-selection {
            border: 1px solid #888888;
        }

        .ivu-table-overflowY {
            overflow-y: scroll;
        }

        .ivu-table-tbody {
            font-size: 12px;
            color: #999;
            font-family: "Microsoft YaHei";
            vertical-align: middle;
        }

        .ivu-table td {
            background-color: #fff;
            height: 40px;
        }

        .ivu-table-stripe .ivu-table-body tr:nth-child(2n) td, .ivu-table-stripe .ivu-table-fixed-body tr:nth-child(2n) td {
            background-color: #f9f9f9;
            height: 40px;
        }

        .ivu-input-icon {
            position: absolute;
            right: 45px;
        }

        .ivu-input-icon-normal + .ivu-input {
            width: 120px;
        }

        .page {
            .ivu-select-selection {
                bottom: 6px
            }
        }
    }
</style>
