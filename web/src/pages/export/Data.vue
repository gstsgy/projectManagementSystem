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
                <Form-item label="合同编号">
                    <Input v-model="searchModel.contractno" @blur.native.capture="getInfo(1)"/>
                </Form-item>


                <Form-item label="项目主类型">
                    <Select v-model="searchModel.ptypeid" clearable placeholder="请选择"
                            @on-change="ptypechange">
                        <Option v-for="item in ptypeenum" :value="item.code" :key="item.code">{{ item.name }}
                        </Option>
                    </Select>
                </Form-item>
                <Form-item label="项目次类型">
                    <Select v-model="searchModel.subid" clearable placeholder="请选择"
                            @on-change="getInfo(1)">
                        <Option v-for="item in subtypeenum" :value="item.code" :key="item.code">{{ item.name }}
                        </Option>
                    </Select>
                </Form-item>

                <Form-item label="完成情况">
                    <Select v-model="searchModel.isfinish" clearable placeholder="请选择"
                            @on-change="getInfo(1)">
                        <Option value="0">未完成</Option>
                        <Option value="1">已完成</Option>
                    </Select>
                </Form-item>

                <Form-item label="安排情况">
                    <Select v-model="searchModel.isarrange" clearable placeholder="请选择"
                            @on-change="getInfo(1)">
                        <Option value="0">未安排</Option>
                        <Option value="1">已安排</Option>
                    </Select>
                </Form-item>
                <Form-item label="登记日期">
                    <DatePicker type="date" v-model="searchModel.registerdateS"
                                format="yyyy-MM-dd" @on-change="getInfo(1)"
                                placeholder="请选择"></DatePicker>
                    <span>-</span>
                    <DatePicker type="date" v-model="searchModel.registerdateE"
                                format="yyyy-MM-dd" @on-change="getInfo(1)"
                                placeholder="请选择"></DatePicker>
                </Form-item>
                <Form-item label="负责人" v-if="searchModel.isarrange==1">
                    <Input v-model="searchModel.mainpeople" @blur.native.capture="getInfo(1)"/>
                </Form-item>

                <Form-item label="编制人" v-if="searchModel.isarrange==1">
                    <Input v-model="searchModel.preparedby" @blur.native.capture="getInfo(1)"/>
                </Form-item>

            </div>
        </Form>

        <Row>
            <Col span="3" style="display:flex;justify-content:space-between">
                <Button v-if="$store.state.controls.c27" type="primary" shape="circle" @click="exportdata"
                        style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;">导&nbsp;&nbsp;出
                </Button>
            </Col>
        </Row>
        <br>
        <Table stripe border  :columns="card" :data="infos"
               ref="table"></Table>
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

    </div>
</template>

<script>
    // import iView from 'iview'

    export default {
        data() {
            return {
                searchModel: {
                    compname: null,
                    proname: null,
                    registerdateS: null,
                    registerdateE: null,
                    contractno: null,
                    mainpeople: null,
                    preparedby: null,
                    isarrange: null,
                    isfinish: null,
                    pageNum: 1,
                    pageSize: 20,
                    total: null,
                    ptypeid: null,
                    subid: null,
                    username:sessionStorage.username
                },
                subtypeenum: [],
                ptypeenum: [],
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

                    {title: '主类型', key: 'ptype', align: 'center'},
                    {title: '次类型', key: 'subtype', align: 'center'},
                    {title: '负责人', key: 'mainpeople', align: 'center'},
                    {title: '编制人员', key: 'preparedby', align: 'center'},
                    {title: '完成情况', key: 'isfinishName', align: 'center'},
                    {title: '进度', key: 'schedule', align: 'center'},
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
                this.$axios.get(`/ptype/ptypeenum`).then(res => {
                    this.ptypeenum = res.data;
                }).catch(exmsg => {
                    console.log(exmsg)
                })
                this.getInfo(1)
            },

            getInfo(v) {
                if (this.searchModel.registerdateS instanceof Date) {
                    this.searchModel.registerdateS = this.format(this.searchModel.registerdateS)
                }
                if (this.searchModel.registerdateE instanceof Date) {
                    this.searchModel.registerdateE = this.format(this.searchModel.registerdateE)
                }
                this.searchModel.pageNum = v;
                this.$axios.get(`/export/data`,{params:this.searchModel}).then(res => {
                    this.infos = res.data.list;
                    this.searchModel.total = res.data.total;
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },
            ptypechange() {
                this.$axios.get(`/ptype/subenumidbyid?infoid=${this.searchModel.ptypeid}`).then(res => {
                    this.subtypeenum = res.data;
                    this.getInfo(1);
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },
            exportdata(){
                if (this.searchModel.registerdateS instanceof Date) {
                    this.searchModel.registerdateS = this.format(this.searchModel.registerdateS)
                }
                if (this.searchModel.registerdateE instanceof Date) {
                    this.searchModel.registerdateE = this.format(this.searchModel.registerdateE)
                } // this.searchModel.pageNum = v;

                this.$axios.get(`/export/exportdata`,{params:this.searchModel,
                    responseType: 'blob'
                }).then((response) => {
                    let blob = new Blob([response])
                    let downloadElement = document.createElement('a');
                    let href = window.URL.createObjectURL(blob); //创建下载的链接
                    downloadElement.href = href;
                    downloadElement.download =`项目资料${this.format(new Date())}.xlsx` ; //下载后文件名
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
