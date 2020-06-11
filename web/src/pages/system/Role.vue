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

                    <Button :type="deleteButtonFlag?'default':'error'" :disabled="deleteButtonFlag" shape="circle" v-if="$store.state.controls.c29" @click="deleteconfirm"

                            icon="md-trash">删除选中项
                    </Button>
                </div>
                <div class="" v-if="$store.state.controls.c37">

                    <Button type="primary" shape="circle" @click="addMes"
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
        <Modal class="modal_cargo" v-model="updateModelCtrl" width="800px" :styles="{top: '85px'}" :mask-closable="false"
               @on-cancel="updateModelCtrl = false">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">修改角色</span>
            </p>
            <Form :model="updateModel">
                <Row>
                    <Col span="6">

                        <Form-item label="角色编号" style="margin-left:35px">
                            <Input v-model="updateModel.roleid" disabled style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">

                        <Form-item label="角色名称" style="margin-left:35px">
                            <Input v-model="updateModel.rolename" @blur.native.capture="insetCheck" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">

                        <Form-item label="角色数据权限" style="margin-left:35px">

                            <Select v-model="updateModel.remark1" clearable placeholder="请选择">
                                <Option value="0" selected="selected"> 全部 </Option>
                                <Option value="1">相关</Option>
                            </Select>
                        </Form-item>
                    </Col>

                </Row>
            </Form>

            <div slot="footer">
                <Button type="primary" shape="circle"
                        style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;" @click="update">提交
                </Button>
            </div>
        </Modal>
        <Modal class="modal_cargo" v-model="insertModelCtrl" width="800px" :styles="{top: '85px'}" :mask-closable="false"
               @on-cancel="insert_cancel">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">新增角色</span>
            </p>
            <Form :model="insertModel">
                <Row>

                    <Col span="6">

                        <Form-item label="角色名称" style="margin-left:35px">
                            <Input v-model="insertModel.rolename" @blur.native.capture="insetCheck" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">

                        <Form-item label="角色数据权限" style="margin-left:35px">

                            <Select v-model="insertModel.remark1" clearable placeholder="请选择">
                                <Option value="0" selected="selected"> 全部 </Option>
                                <Option value="1">相关</Option>
                            </Select>
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
    </div>
</template>

<script>
    import iView from 'iview'

    export default {
        data() {
            return {
              //  datatypeEnum: null,

               /* sysLanguageView: {
                    useremail:null,
                    remark1:null,
                    userroleid:null
                },*/




                mesInfos: [],
                insertModelCtrl: false,



                insertModel: {
                    rolename: "",
                    remark1:"0"

                },
                updateModel:{
                    roleid: "",
                    rolename: "",
                    remark1:"0"
                },
                inertModelSubmitCtrl: true,
                updateModelSubmitCtrl: true,
                updateModelCtrl: false,
                delete_confirm: false,
                deleteButtonFlag: true,
                tableColumns: {
                    card: [
                        {title: '选择', key: 'select', type: 'selection', width: 50, align: 'center'},
                        {
                            title: '序号', key: 'id', align: 'center', width: 75, render: (h, params) => {
                                return h("div", +params.row._index + 1)
                            }
                        },
                        {title: 'id', key: 'roleid', align: 'center'},
                        {title: '名称', key: 'rolename', align: 'center'},
                        {title: '数据权限', key: 'remark1', align: 'center',render:(h,p)=>{
                                return h("div", p.row.remark1==="0"?"全部":"相关")
                            }
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
            deleteconfirm(){

                this.delete_confirm=true;
            },

            getInfo() {
                this.$axios.get(`/system/proles`).then(res => {
                    this.mesInfos = res.data;
                    this.deleteButtonFlag = true;

                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },



             insetCheck() {
             const tmp=this.mesInfos.find(item=>item.rolename===this.insertModel.rolename)
               if(tmp!==undefined){
                   iView.Message.error({
                       content: "角色已存在！",
                       duration: 2
                   });
                   this.insertModel.rolename = "";
                 //  return;
               }
              //  return bo;
                //insertModel.controlID
            },


            insert() {

                if (!this.inertModelSubmitCtrl)
                    return;
                this.inertModelSubmitCtrl = false;

                if (this.insertModel.rolename == null || this.insertModel.rolename === "") {
                    iView.Message.error({
                        content: "名称不能为空！",
                        duration: 2
                    });
                    this.inertModelSubmitCtrl = true;
                    return;
                }



                //this.insertModel.envalue = this.insertModel.CNvalue;

                this.$axios.post(`/system/prole`, this.insertModel).then(res => {

                    if (res.data) {
                        iView.Message.info({
                            content: "新增成功！",
                            duration: 2
                        });
                        this.insertModelCtrl = false;
                        this.inertModelSubmitCtrl = true;
                        //this.insertModel.controlID = null;
                        this.getInfo(1)
                        this.insertModel =  {
                            rolename: "",
                            remark1:"0"

                        }
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


            enumConvert(code, enums) {
                if (enums === null) return null;
                let tmp = enums.find(item => item.code === code);
                return tmp === undefined ? null : tmp.name
            },

            deleteSelection() {
                const list = this.$refs.table.getSelection();
                if (list.lenth > 0) {
                    iView.Message.error({
                        content: "未选择数据！",
                        duration: 2
                    });
                    this.delete_confirm = false;
                    return;
                }
                this.$axios.delete(`/system/prole`,{
                    data: list
                } ).then(res => {
                    if (res.data) {
                        iView.Message.info({
                            content: "删除成功！",
                            duration: 2
                        });
                        this.getInfo(1);
                        this.delete_confirm = false;
                    }
                }).catch(() => {
                    this.delete_confirm = false;
                })
            },

            showUpdate(v) {
                if(!this.$store.state.controls.c44)return
                this.updateModel = v;
                this.updateModelCtrl = true;
            },

            update() {
                if (!this.updateModelSubmitCtrl)
                    return;
                this.updateModelSubmitCtrl = false;
                if (this.updateModel.rolename == null || this.updateModel.rolename === "") {
                    iView.Message.error({
                        content: "名称不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }
                if (this.updateModel.remark1 == null || this.updateModel.remark1 === "") {
                    iView.Message.error({
                        content: "权限不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }



                this.$axios.put(`/system/prole`, this.updateModel).then(res => {

                    if (res.data) {
                        iView.Message.info({
                            content: "更新成功！",
                            duration: 2
                        });
                        this.updateModelSubmitCtrl = true;
                        this.updateModelCtrl = false;
                        this.getInfo()
                        // this.insertModel.controlID = null;

                    }else{
                        iView.Message.info({
                            content: "更新失败！",
                            duration: 2
                        });
                        this.updateModelSubmitCtrl = true;
                        this.updateModelCtrl = false;
                    }
                }).catch(() => {
                    /*iView.Message.error({
                      content: exsmes,
                      duration: 2
                    });*/
                    this.updateModelSubmitCtrl = true;


                })

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
