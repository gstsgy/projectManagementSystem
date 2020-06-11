<template lang="html">
    <div class="usermanage">
        <Form :label-width="85" inline style="text-align:left;">
            <div>
                <Form-item label="建设项目">
                    <Input v-model="searchModel.proname" @blur.native.capture="getInfo(1)"/>
                </Form-item>
                <Form-item label="合同编号">
                    <Input v-model="searchModel.contractno" @blur.native.capture="getInfo(1)"/>
                </Form-item>
                <Form-item label="文件名称">
                    <Input v-model="searchModel.mname" @blur.native.capture="getInfo(1)"/>
                </Form-item>


                <Form-item label="项目主类型">
                    <Select v-model="searchModel.ftype" clearable placeholder="请选择"
                            @on-change="getInfo(1)">
                        <Option v-for="item in ptypeenum" :value="item.code" :key="item.code">{{ item.name }}
                        </Option>
                    </Select>
                </Form-item>

            </div>
        </Form>

        <Row>
            <Button :type="deleteButtonFlag?'default':'error'" :disabled="deleteButtonFlag" shape="circle" v-if="$store.state.controls.c33" @click="delete_confirm=true"

                    icon="md-trash">删除选中项
            </Button>
        </Row>
        <br>
        <Table stripe border  :columns="card" :data="infos"
               @on-selection-change="v=>this.deleteButtonFlag = v.length <= 0" @on-row-dblclick="showUpdate"
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
                <span style="font-size:14px">修改文件</span>
            </p>
            <Form :model="updateModel">
                <Row>
                    <Col span="6">

                        <Form-item label="文件编号" style="margin-left:35px">
                            <Input v-model="updateModel.mid" disabled style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">

                        <Form-item label="文件名称" style="margin-left:35px">
                            <Input v-model="updateModel.mname"  style="width: 150px"/>
                        </Form-item>
                    </Col>
                   <!-- <Col span="6">

                        <Form-item label="文件类型" style="margin-left:35px">
                            <Select v-model="updateModel.ftype" clearable placeholder="请选择">
                                <Option v-for="item in ptypeenum" :value="item.code" :key="item.code">{{ item.name }}</Option>
                            </Select>
                        </Form-item>
                    </Col>-->
                    <Col span="6">

                        <Form-item label="文件描述" style="margin-left:35px">
                            <Input v-model="updateModel.fabout"  style="width: 150px"/>
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
    // import iView from 'iview'

    import iView from "iview";

    export default {
        data() {
            return {
                updateModelSubmitCtrl: true,
                updateModelCtrl: false,
                updateModel:{
                    mid:"",
                    mname:"",
                    ftype:"",
                    fabout:"",
                },
                delete_confirm: false,
                deleteButtonFlag : true,
                searchModel: {
                    proname: null,
                    contractno: null,
                    mname: null,
                    ftype: null,
                    pageNum: 1,
                    pageSize: 20,
                    total: null,
                },
                subtypeenum: [],
                ptypeenum: [],
                card: [
                    {title: '选择', key: 'select', type: 'selection', width: 50, align: 'center'},
                    {
                        title: '序号', key: 'id', align: 'center', width: 75, render: (h, params) => {
                            return h("div", params.row._index + 1 + (this.searchModel.pageNum - 1) * this.searchModel.pageSize)
                        }
                    },
                    {title: '建设单位', key: 'remark', align: 'center',tooltip:'true'},
                    {title: '建设项目', key: 'path', align: 'center',tooltip:'true'},
                    {title: '次类型', key: 'remark1', align: 'center',tooltip:'true',render:(h,p)=>{
                            return h("div",  this.enumConvert(p.row.remark1,this.subtypeenum))
                        }},
                    {title: '文件名称', key: 'mname', align: 'center',render:(h,p)=>{
                            return h("div", {
                                style:{
                                    color:"blue",
                                    cursor:"pointer"
                                },
                                on:{
                                    click:()=>{
                                        console.log(p.row)
                                        this.exportdata(p.row)
                                    }
                                }

                            },p.row.mname)
                    }
                    },
                   /* {title: '文件类型', key: 'ftype', align: 'center',render:(h,p) => {
                        return h("div", this.enumConvert(p.row.ftype,this.ptypeenum))
                     }},*/

                    {title: '文件大小', key: 'fsize', align: 'center'},
                    {title: '描述', key: 'fabout', align: 'center'},
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
                    this.$axios.get(`/ptype/subenum`).then(res => {
                        this.subtypeenum = res.data;
                        this.getInfo(1)
                    }).catch(exmsg => {
                        console.log(exmsg)
                    })



                }).catch(exmsg => {
                    console.log(exmsg)
                })



            },

            getInfo(v) {
                this.searchModel.pageNum = v;
                this.$axios.get(`/file/files`,{params:this.searchModel}).then(res => {
                    this.infos = res.data.list;
                    this.searchModel.total = res.data.total;
                }).catch(exmsg => {
                    console.log(exmsg)
                })
            },
            exportdata(v){


                this.$axios.get(`/file/file`,{params:v,
                    responseType: 'blob'
                }).then((response) => {
                    let blob = new Blob([response])
                    let downloadElement = document.createElement('a');
                    let href = window.URL.createObjectURL(blob); //创建下载的链接
                    downloadElement.href = href;
                    downloadElement.download =v.mname ; //下载后文件名
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
            showUpdate(v) {
                if(!this.$store.state.controls.c25)return
                this.updateModel = v;
                this.updateModelCtrl = true;
            },
            update() {
                if (!this.updateModelSubmitCtrl)
                    return;
                this.updateModelSubmitCtrl = false;
                if (this.updateModel.mname == null || this.updateModel.mname === "") {
                    iView.Message.error({
                        content: "名称不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }
                if (this.updateModel.ftype == null || this.updateModel.ftype === "") {
                    iView.Message.error({
                        content: "类型不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }



                this.$axios.put(`/file/file`, this.updateModel).then(res => {

                    if (res.data) {
                        iView.Message.info({
                            content: "更新成功！",
                            duration: 2
                        });
                        this.updateModelSubmitCtrl = true;
                        this.updateModelCtrl = false;
                        this.getInfo(1)
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
                    this.updateModelSubmitCtrl = true;
                })

            },
            changePagesize(v) {
                this.searchModel.pageSize =v;
                this.getInfo(this.searchModel.pageNum)
            },
            deleteSelection(){
                const list = this.$refs.table.getSelection();
                if (list.lenth > 0) {
                    iView.Message.error({
                        content: "未选择数据！",
                        duration: 2
                    });
                    this.delete_confirm = false;
                    return;
                }
                this.$axios.delete(`/file/file`,{
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
            enumConvert(code, enums) {
                if (enums === null) return null;
                let tmp = enums.find(item => item.code == code);
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
