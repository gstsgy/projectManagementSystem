<template lang="html">
    <div class="usermanage">
        <Form :label-width="85" inline style="text-align:left;">
            <div>
                <Form-item label="建设单位">
                    <Input v-model="searchModel.compname" @blur.native.capture="getInfo(1)"/>
                </Form-item>
                <Form-item label="建设项目">
                    <Input v-model="searchModel.proname" @blur.native.capture="getInfo(1)"/>
                </Form-item>
                <Form-item label="结算情况">
                    <Select v-model="searchModel.isfinish" clearable placeholder="请选择"
                            @on-change="getInfo(1)">
                        <Option value="0">未结算</Option>
                        <Option value="1">已结算</Option>
                    </Select>
                </Form-item>
                <Form-item label="预付款">
                    <Select v-model="searchModel.isadvancecharge" clearable placeholder="请选择"
                            @on-change="getInfo(1)">
                        <Option value="0">未付</Option>
                        <Option value="1">已付</Option>

                    </Select>
                </Form-item>

                <Form-item label="开票情况">
                    <Select v-model="searchModel.isbilling" clearable placeholder="请选择"
                            @on-change="getInfo(1)">
                        <Option value="0">未开票</Option>
                        <Option value="1">已开票</Option>
                    </Select>
                </Form-item>
            </div>
        </Form>

        <Row>
            <Col span="3" style="display:flex;justify-content:space-between">
                <Button v-if="$store.state.controls.c26" type="primary" shape="circle" @click="exportdata"
                        style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;">导&nbsp;&nbsp;出
                </Button>
            </Col>
        </Row>
        <br>
        <Table stripe border  :columns="card" :data="infos"
               @on-row-dblclick="showUpdate"></Table>
        <br>
        <div class="page">
            <Page show-sizer show-elevator show-total :total="this.searchModel.total"
                  :current="this.searchModel.pageNum"
                  :page-size="this.searchModel.pageSize"
                  :page-size-opts="[10,20,30]"
                  @on-change="changePagenum" @on-page-size-change="changePagesize" style="text-align:center"></Page>
        </div>
        <br>
        <br>
        <br>
        <br>
        <Modal class="modal_cargo" v-model="updateModelCtrl" width="840px" :styles="{top: '85px'}"
               :mask-closable="false"
               >
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">更新财务信息</span>
            </p>
            <Form :model="updateModel">
                <Row>
                    <Col span="6">
                        <Form-item label="项目编号" style="margin-left:35px">
                            <Input v-model="updateModel.infoid" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="总金额" style="margin-left:35px">
                            <Input type="number" v-model="updateModel.sumofmoney" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="结算情况" style="margin-left:35px">

                            <Select v-model="updateModel.isfinish" >
                                <Option :value="0"  >未结算</Option>
                                <Option :value="1" >已结算</Option>
                            </Select>
                        </Form-item>
                    </Col>

                    <Col span="6">

                        <Form-item  label="预付款是否已付" style="margin-left:35px">
                            <Select :disabled="updateModel.isfinish==1" v-model="updateModel.isadvancecharge" clearable placeholder="请选择">
                                <Option :value="0" >未付</Option>
                                <Option :value="1" >已付</Option>
                            </Select>
                        </Form-item>
                    </Col>

                </Row>
                <Row>

                    <Col span="6">
                        <Form-item  label="预付款" style="margin-left:35px">
                            <Input :disabled="updateModel.isfinish==1||updateModel.isadvancecharge==0" type="number" v-model="updateModel.advancecharge" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">

                        <Form-item label="是否开票" style="margin-left:35px">
                            <Select v-model="updateModel.isbilling" clearable placeholder="请选择">
                                <Option :value="0" >未开</Option>
                                <Option :value="1" >已开</Option>
                            </Select>
                        </Form-item>
                    </Col>
                    <Col span="6">

                        <Form-item label="开票金额" style="margin-left:35px">
                            <Input type="number" :disabled="updateModel.isbilling==0" v-model="updateModel.billingnum" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">

                        <Form-item label="备注" style="margin-left:35px">
                            <Input number v-model="updateModel.remark" style="width: 150px"/>
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

    </div>
</template>

<script>
    import iView from 'iview'

    export default {
        data() {
            return {
                searchModel: {
                    proname:null,
                    isfinish: null,
                    isadvancecharge: null,
                    isbilling: null,
                    pageNum: 1,
                    pageSize: 20,
                    total: null,
                    compname:null,
                },
                updateModelCtrl:false,
                updateModel: {},
                card: [
                    {
                        title: '序号', key: 'id', align: 'center', width: 75, render: (h, params) => {
                            return h("div", params.row._index + 1 + (this.searchModel.pageNum - 1) * this.searchModel.pageSize)
                        }
                    },
                    {title: '建设单位', key: 'compname', align: 'center',tooltip:'true'},
                    {title: '建设项目', key: 'proname', align: 'center',tooltip:'true'},
                    {title: '合同编号', key: 'contractno', align: 'center'},
                    {title: '联系人', key: 'contact', align: 'center',tooltip:'true'},

                    {title: '总金额', key: 'sumofmoney', align: 'center'},
                    {title: '预付款', key: 'advancecharge', align: 'center'},
                    {title: '尾款', key: 'finalpayment', align: 'center'},
                    {title: '结算情况', key: 'finshname', align: 'center'},
                    {title: '开票情况', key: 'billName', align: 'center'},
                    {title: '备注', key: 'remark', align: 'center',tooltip:'true'},
                ],
                infos: []
            }
        },

        async created() {
            await this.init();
        },

        computed: {},

        methods: {

            init() {
                this.getInfo(1)
            },

            getInfo(v) {
                this.searchModel.pageNum = v;
                this.$axios.get(`/export/money`,{params:this.searchModel}).then(res => {
                    this.infos = res.data.list;
                    this.searchModel.total = res.data.total;
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },

            exportdata(){



                this.$axios.get(`/export/exportmoney`,{params:this.searchModel,
                    responseType: 'blob'
                }).then((response) => {
                    let blob = new Blob([response])
                    let downloadElement = document.createElement('a');
                    let href = window.URL.createObjectURL(blob); //创建下载的链接
                    downloadElement.href = href;
                    downloadElement.download =`财务数据${this.format(new Date())}.xlsx` ; //下载后文件名
                    document.body.appendChild(downloadElement);
                    downloadElement.click(); //点击下载
                    document.body.removeChild(downloadElement); //下载完成移除元素
                    window.URL.revokeObjectURL(href); //释放掉blob对象
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },
            changePagenum(v) {
                this.getInfo(v);
            },
            showUpdate(v){
                if(!this.$store.state.controls.c34)return
                this.$axios.get(`/export/money1?fid=${v.fid}`).then(res => {
                    this.updateModel = res.data;
                    this.updateModelCtrl = true;
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },
            update(){
                this.updateModel.finalpayment = this.updateModel.sumofmoney - this.updateModel.advancecharge;
                if(this.updateModel.isfinish===1){
                    this.updateModel.advancecharge = "";
                    this.updateModel.finalpayment = "";
                 }
                this.$axios.put(`/export/money`,this.updateModel).then(res => {
                    if(res.data){
                        iView.Message.info({
                            content: "更新成功",
                            duration: 2
                        });
                    }
                    this.updateModelCtrl = false;
                    this.getInfo(this.searchModel.pageNum)
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },


            changePagesize(v) {
                this.searchModel.pageSize =v;
                this.getInfo(this.searchModel.pageNum)
            },

            enumConvert(code, enums) {
                if (enums === null) return null;
                let tmp = enums.find(item => item.code === code);
                return tmp === undefined ? null : tmp.name
            },
            format(date) {
                let fmt = "yyyy-MM-dd";
                let o = {
                    "M+": date.getMonth() + 1, //月份
                    "d+": date.getDate(), //日
                    "h+": date.getHours(), //小时
                    "m+": date.getMinutes(), //分
                    "s+": date.getSeconds(), //秒
                    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                    "S": date.getMilliseconds() //毫秒
                };
                if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (let k in o)
                    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                return fmt;
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
