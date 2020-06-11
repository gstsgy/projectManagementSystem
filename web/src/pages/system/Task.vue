<template lang="html">
    <div class="usermanage">
        <!-- <Form :label-width="85" inline style="text-align:left;">
             <div>
                <Form-item label="用户角色">
                   <Select v-model="sysLanguageView.userroleid" clearable placeholder="请选择"
                           @on-change="getInfo">
                     <Option v-for="item in datatypeEnum" :value="item.code" :key="item.code">{{ item.name }}</Option>
                   </Select>
                 </Form-item>

                 <Form-item label="姓名" style="margin-left:35px">
                     <Input v-model="sysLanguageView.remark1" @blur.native.capture="getInfo" style="width: 150px"/>
                 </Form-item>

                 <Form-item label="邮箱" style="margin-left:35px">
                     <Input v-model="sysLanguageView.useremail" @blur.native.capture="getInfo" style="width: 150px"/>
                 </Form-item>

             </div>
         </Form>-->
        <Row>
            <Col span="24" style="display:flex;justify-content:space-between">
                <div class="">

                    <Button type="primary" shape="circle" @click="addMes"
                            style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;">新&nbsp;&nbsp;增
                    </Button>
                </div>
            </Col>
        </Row>

        <br>
        <Table stripe border  :columns="tableColumns.card" :data="mesInfos"
               @on-row-dblclick="showUpdate"
               ref="table"></Table>
        <br>
        <Modal class="modal_cargo" v-model="insertModelCtrl" width="800px" :styles="{top: '85px'}"
               :mask-closable="false"
               @on-cancel="insert_cancel">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">{{title}}</span>
            </p>
            <Form :model="insertModel">
                <Row>
                    <Col span="6">
                        <Form-item label="任务类型" style="margin-left:35px">
                            <Select v-model.lazy="insertModel.type" clearable placeholder="请选择">
                                <Option :value="0">重点任务</Option>
                                <Option :value="1">普通任务</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="18">
                        <Form-item label="任务内容" style="margin-left:35px">
                            <Input v-model.lazy="insertModel.content"
                                   style="width: 500px"/>
                        </Form-item>
                    </Col>


                </Row>
                <Row>
                    <Col span="6">
                        <Form-item label="任务展示" style="margin-left:35px">
                            <br>
                            <i-switch v-model.lazy="insertModel.show" size="large">
                                <span slot="open">On</span>
                                <span slot="close">Off</span>
                            </i-switch>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="显示顺序" style="margin-left:35px">
                            <Input v-model.lazy="insertModel.seq" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="执行时间" style="margin-left:35px">
                            <Select v-model.lazy="insertModel.date" clearable placeholder="请选择">
                                <Option value="周一">周一</Option>
                                <Option value="周二">周二</Option>
                                <Option value="周三">周三</Option>
                                <Option value="周四">周四</Option>
                                <Option value="周五">周五</Option>
                                <Option value="周六">周六</Option>
                                <Option value="周日">周日</Option>
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
                title:"",
                mesInfos: [],
                insertModelCtrl: false,
                insertModel: {
                },


                inertModelSubmitCtrl: true,


                tableColumns: {
                    card: [
                        {
                            title: '序号', key: 'id', align: 'center', width: 75, render: (h, params) => {
                                return h("div", +params.row._index + 1)
                            }
                        },
                        {
                            title: '类型', key: 'type', align: 'center', render: (h, params) => {
                                return h("div", +params.row.type === 0 ? "重点工作" : "普通工作")
                            }
                        },
                        {title: '工作内容', key: 'content', width: 520, align: 'center',tooltip:'true'},
                        {
                            title: '是否展示', key: 'show', align: 'center', render: (h, params) => {
                                return h("div", +params.row.show === 0 ? "否" : "是")
                            }
                        },
                        {title: '排序', key: 'seq', align: 'center'},
                        {title: '执行日期', key: 'date', align: 'center'},
                        {
                            title: '删除', key: 'delet', align: 'center', render: (h, params) => {
                                return h("Button", {
                                        props: {
                                            type: 'error',
                                            icon: "md-trash"
                                        },
                                        on: {
                                            'click': () => {
                                                this.delete(params.row)
                                                //  this.updateModel.children = this.updateModel.children.filter((item, index) => index !== params.row._index);
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    ],
                }
            }
        },

        async created() {
            await this.init();
        },

        computed: {},

        methods: {
            deleteconfirm() {

                this.delete_confirm = true;
            },
            init() {
                this.getInfo();
            },

            getInfo() {
                this.$axios.get(`/tasks`, {
                    params: this.sysLanguageView
                }).then(res => {
                    this.mesInfos = res.data;
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },

            insert() {

                if (!this.inertModelSubmitCtrl)
                    return;
                this.inertModelSubmitCtrl = false;

                if (this.insertModel.type == null || this.insertModel.type === "") {
                    iView.Message.error({
                        content: "类型不能为空！",
                        duration: 2
                    });
                    this.inertModelSubmitCtrl = true;
                    return;
                }
                if (this.insertModel.content == null || this.insertModel.content === "") {
                    iView.Message.error({
                        content: "内容不能为空！",
                        duration: 2
                    });
                    this.inertModelSubmitCtrl = true;
                    return;
                }
                if(this.insertModel.seq ===null||this.insertModel.seq ===""){
                    this.insertModel.seq = 0;
                }
                if(this.insertModel.date ===null||this.insertModel.date ===""){
                    this.insertModel.date = "周一";
                }
                this.insertModel.show = this.insertModel.show?1:0;
               if(this.title==="新增任务") {
                   this.$axios.post(`/task`, this.insertModel).then(res => {

                       if (res.data) {
                           iView.Message.info({
                               content: "新增成功！",
                               duration: 2
                           });
                           this.insertModelCtrl = false;
                           this.inertModelSubmitCtrl = true;
                           this.getInfo(1)
                       }
                   }).catch(() => {

                       this.inertModelSubmitCtrl = true;
                   })
               }else{
                   this.$axios.put(`/task`, this.insertModel).then(res => {

                       if (res.data) {
                           iView.Message.info({
                               content: "更新成功！",
                               duration: 2
                           });
                           this.insertModelCtrl = false;
                           this.inertModelSubmitCtrl = true;
                           this.getInfo(1)
                       }
                   }).catch(() => {
                       this.inertModelSubmitCtrl = true;
                   })
               }
            },

            insert_cancel() {
                this.insertModelCtrl = false;
            },

            addMes() {
                this.title = "新增任务"
                this.insertModelCtrl = true
                this.insertModel = {
                    type: null,
                    content: null,
                    show: true,
                    seq: null,
                    date: null
                }
            },

            showUpdate(v) {
                this.title = "更新任务"
                this.insertModelCtrl = true
                this.insertModel =v
                this.insertModel.show = this.insertModel.show===1?true:false;
            },
            delete(v) {
                this.$axios.delete(`/task`, {data:v}).then(res => {

                    if (res.data) {
                        iView.Message.info({
                            content: "删除成功！",
                            duration: 2
                        });

                        this.getInfo(1)
                    }
                }).catch(() => {

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
