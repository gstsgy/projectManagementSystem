<template lang="html">
    <div class="usermanage">
        <Form :label-width="85" inline style="text-align:left;">
            <div>
                <!-- <Form-item label="用户角色">
                    <Select v-model="sysLanguageView.userroleid" clearable placeholder="请选择"
                            @on-change="getInfo">
                      <Option v-for="item in datatypeEnum" :value="item.code" :key="item.code">{{ item.name }}</Option>
                    </Select>
                  </Form-item>-->

                <Form-item label="建设单位" span="6" style="margin-left:35px">
                    <Input v-model="searchModel.compname" @blur.native.capture="getInfo(1)" style="width: 150px"/>
                </Form-item>
                <Form-item label="建设项目" span="6" style="margin-left:35px">
                    <Input v-model="searchModel.proname" @blur.native.capture="getInfo(1)" style="width: 150px"/>
                </Form-item>
                <Form-item label="联系人" span="6" style="margin-left:35px">
                    <Input v-model="searchModel.contact" @blur.native.capture="getInfo(1)" style="width: 150px"/>
                </Form-item>
                <Form-item label="合同编号" span="6" style="margin-left:35px">
                    <Input v-model="searchModel.contractno" @blur.native.capture="getInfo(1)" style="width: 150px"/>
                </Form-item>

                <Form-item label="登记时间" span="6" style="margin-left:35px">
                    <DatePicker type="date" v-model="searchModel.registerdateS"
                                format="yyyy-MM-dd" @on-change="getInfo(1)"
                                placeholder="请选择"></DatePicker>
                    <span>-</span>
                    <DatePicker type="date" v-model="searchModel.registerdateE"
                                format="yyyy-MM-dd" @on-change="getInfo(1)"
                                placeholder="请选择"></DatePicker>
                </Form-item>
            </div>
        </Form>
        <Row>
            <Col span="24" style="display:flex;justify-content:space-between">
                <div class="">
                    <Button type="primary" shape="circle" v-if="!deleteButtonFlag&&$store.state.controls.c32"
                            @click="deleteconfirm"
                            style="width:120px;height:28px;border-radius:14px;background-color:#fe3838;border:0 none;"
                            icon="md-trash">删除选中项
                    </Button>
                    <Button type="primary" shape="circle" disabled v-if="deleteButtonFlag&&$store.state.controls.c32"
                            style="width:120px;height:28px;border-radius:14px;background-color:white;color:#b4b4b4"
                            icon="md-trash">删除选中项
                    </Button>
                </div>
                <div class="">

                    <Button v-if="$store.state.controls.c40" type="primary" shape="circle" @click="addMes"
                            style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;">新&nbsp;&nbsp;增
                    </Button>
                </div>
            </Col>
        </Row>

        <br>
        <Table :row-class-name="rowClassName" stripe border style="color: #000000" :columns="tableColumns.card" :data="mesInfos"
               @on-selection-change="v=>this.deleteButtonFlag = v.length <= 0" @on-row-dblclick="showUpdate"
               ref="table"></Table>
        <br>
        <div class="page">
            <Page show-sizer show-elevator show-total :total="this.searchModel.total"
                  :current="this.searchModel.pageNum"
                  :page-size="this.searchModel.pageSize"
                  :page-size-opts="[10,20,30]"
                  @on-change="getInfo" @on-page-size-change="getInfo1" style="text-align:center"></Page>
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
        <Modal class="modal_cargo" v-model="uploadfileCtrl" width="640px" :styles="{top: '85px'}"
               :mask-closable="false">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">文件上传</span>
            </p>
            <Form :model="uploadfile">
                <Row>
                    <Col span="12">
                        <Form-item label="选择" >
                            <Upload  style=""
                                     :before-upload="handleUpload"
                                     action="//jsonplaceholder.typicode.com/posts/">
                                <Button icon="ios-cloud-upload-outline">选择文件</Button>
                            </Upload>
                        </Form-item>

                    </Col>
                    <Col span="12">
                        <Form-item label="文件" >
                            <Input  v-model="file.name" disabled style="width: 200px"/>
                        </Form-item>
                    </Col>
                </Row>
                <Row>

                    <Col span="12">
                        <Form-item label="描述" >
                            <Input v-model="uploadfile.fabout" style="width: 200px"/>
                        </Form-item>
                    </Col>
                </Row>


            </Form>

            <div slot="footer">
                <Button type="text" shape="circle" :loading="loadingStatus"
                        style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;" @click="upload">
                    {{ loadingStatus ? 'Uploading' : '提交' }}
                </Button>
            </div>
        </Modal>

        <Modal class="modal_cargo" v-model="insertModelCtrl" width="840px" :styles="{top: '85px'}"
               :mask-closable="false">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">新增项目</span>
            </p>
            <Form :model="insertModel" >
                <Row>
                    <Col span="6">
                        <Form-item label="建设单位" style="margin-left:35px">
                            <Input v-model="insertModel.compname" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="建设项目" style="margin-left:35px">
                            <Input v-model="insertModel.proname" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="联系人" style="margin-left:35px">
                            <Input v-model="insertModel.contact" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="合同编号" style="margin-left:35px">
                            <Input v-model="insertModel.contractno" style="width: 150px"/>
                        </Form-item>
                    </Col>


                </Row>
                <Row>
                    <Col span="6">

                        <Form-item label="登记日期" style="margin-left:35px">
                            <DatePicker type="date" v-model="insertModel.registerdate"
                                        format="yyyy-MM-dd"
                                        placeholder="请选择"></DatePicker>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="备注" style="margin-left:35px">
                            <Input v-model="insertModel.remark" style="width: 150px"/>
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

        <Modal class="modal_cargo" v-model="updateModelCtrl" width="1200px" :styles="{top: '85px'}"
               :mask-closable="false"
               @on-cancel="insert_cancel">
            <p slot="header" style="text-align:center">
                <span style="font-size:14px">更新项目</span>
            </p>
            <Form :model="updateModel">
                <Row>
                    <Col span="6">
                        <Form-item label="项目编号">
                            <Input v-model="updateModel.proid" disabled="disabled" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="建设单位">
                            <Input v-model="updateModel.compname" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="建设项目">
                            <Input v-model="updateModel.proname" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="联系人">
                            <Input v-model="updateModel.contact" style="width: 150px"/>
                        </Form-item>
                    </Col>


                </Row>
                <Row>
                    <Col span="6">
                        <Form-item label="合同编号">
                            <Input v-model="updateModel.contractno" style="width: 150px"/>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="登记日期">
                            <DatePicker type="date" v-model="updateModel.registerdate" style="width: 150px"
                                        format="yyyy-MM-dd"
                                        placeholder="请选择"></DatePicker>
                        </Form-item>
                    </Col>
                    <Col span="6">
                        <Form-item label="备注信息">
                            <Input v-model="updateModel.remark" style="width: 150px"/>
                        </Form-item>
                    </Col>

                </Row>

            </Form>

            <Row>
                <Col span="24" style="display:flex;justify-content:space-between">
                    <div class="">

                    </div>
                    <div class="">
                       <!-- <Button type="primary" shape="circle" @click="uploadfileCtrl = true"
                                style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;">附件上传
                        </Button>-->
                        <Button type="primary" shape="circle" @click="addMes1" v-if="$store.state.controls.c49"
                                style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;margin-left: 35px">新&nbsp;&nbsp;增
                        </Button>

                    </div>
                </Col>
            </Row>

            <br>
            <Table stripe border  :columns="tableColumns.card1" :data="updateModel.children"
                   @on-selection-change="v=>this.deleteButtonFlag1 = v.length <= 0"
                   ref="table1"></Table>
            <br>
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
    import expandRow from '../../components/table-expand.vue';

    export default {
        // eslint-disable-next-line vue/no-unused-components
        components: {expandRow},
        data() {
            return {
                roledata : 0,
                uploadfile: {
                    cid:null,
                    ftype: null,
                    fabout: ""
                },
                uploadfileCtrl: false,
                filetype: [],
                file: {
                    name:null
                },
                loadingStatus: false,
                datatypeEnum: null,

                searchModel: {
                    compname: null,
                    proname: null,
                    contact: null,
                    contractno: null,
                    registerdateE: null,
                    registerdateS: null,
                    pageNum: 1,
                    pageSize: 20,
                    total: 0,
                    username: sessionStorage.username
                },
                ptypeenum: [],
                subtypeenum: [],

                delete_confirm1: false,
                deleteButtonFlag1: false,

                mesInfos: [],
                insertModelCtrl: false,

                updateModelCtrl: false,
                deleteButtonFlag: true,
                insertModel: {
                    compname: "",
                    proname: "",
                    contact: "",
                    contractno: "",
                    registerdate: "",
                    remark: "",
                },
                updateModel: {
                    compname: "",
                    proname: "",
                    contact: "",
                    contractno: "",
                    registerdate: "",
                    remark: "",
                },

                inertModelSubmitCtrl: true,
                updateModelSubmitCtrl: true,
                delete_confirm: false,

                tableColumns: {
                    card: [
                        {
                            type: 'expand',
                            width: 30,
                            render: (h, params) => {
                                return h(expandRow, {
                                    props: {
                                        data1: params.row.children?params.row.children:[]
                                    }
                                })
                            }
                        },
                        {title: '选择', key: 'select', type: 'selection', width: 75, align: 'center'},
                        {
                            title: '序号', key: 'id', align: 'center', width: 75, render: (h, params) => {
                                return h("div", params.row._index + 1 + (this.searchModel.pageNum - 1) * this.searchModel.pageSize)
                            }
                        },
                        {title: '项目编号', key: 'proid', align: 'center'},
                        {title: '建设单位', key: 'compname', align: 'center',tooltip:'true'},
                        {title: '建设项目', key: 'proname', align: 'center',tooltip:'true'},
                        {title: '联系人', key: 'contact', align: 'center',tooltip:'true'},
                        {title: '合同编号', key: 'contractno', align: 'center'},
                        {title: '登记日期', key: 'registerdate', align: 'center'},
                        {title: '内容', key: 'count', align: 'center',render: (h, params) => {
                                return h("div", params.row.children.length)
                            }
                        },
                        {title: '备注', key: 'remark', align: 'center',tooltip:'true'},

                    ],
                    card1: [
                        {
                            title: '序号', key: 'no', width: '60', align: 'center', render: (h, params) => {
                                return h("div", params.row._index + 1)
                            }
                        },
                        {
                            title: '内容编号',
                            width: '140',
                            align: 'center',
                            key: 'id'
                        },
                        {
                            title: '主类型',
                            align: 'center',

                            key: 'ptypeid',
                            width: '160',
                            render: (h, params) => {
                                return h('Select', {
                                        props: {
                                            value: params.row.ptypeid,
                                            size: 'large',
                                            filterable: true,
                                            transfer: true
                                        },
                                        on: {
                                            'on-change': (event) => {
                                                this.updateModel.children[params.row._index].ptypeid = event
                                                this.$axios.get(`/ptype/subenumidbyid?infoid=${event}`).then(res => {
                                                    this.updateModel.children[params.row._index].subtypeenum = res.data;

                                                }).catch(exmsg => {
                                                    console.log(exmsg)
                                                })
                                            }
                                        },
                                    },
                                    this.ptypeenum.map(item => {
                                        return h('Option', {
                                            props: {
                                                value: item.code,
                                                label: item.name
                                            }
                                        })
                                    })
                                );
                            }
                        },
                        {
                            title: '次类型',
                            align: 'center',
                            width: '180',
                            key: 'subid',
                            render: (h, params) => {
                                return h('Select', {
                                        props: {
                                            value: params.row.subid,
                                            size: 'large',
                                            filterable: true,
                                            transfer: true
                                        },
                                        on: {
                                            'on-change': (event) => {
                                                this.updateModel.children[params.row._index].subid = event
                                            }
                                        },
                                    },
                                    this.updateModel.children[params.row._index].subtypeenum.map(item => {
                                        return h('Option', {
                                            props: {
                                                value: item.code,
                                                label: item.name
                                            }
                                        })
                                    })
                                );
                            }
                        },
                        {
                            title: '负责人',
                            align: 'center',
                            key: 'mainpeople',
                            render: (h, params) => {
                                return h('Input', {
                                    props: {
                                        value: params.row.mainpeople,
                                        size: 'small',
                                    },
                                    on: {
                                        'on-change': (event) => {

                                            this.updateModel.children[params.row._index].mainpeople = event.target.value
                                            if (event.target.value != null && event.target.value != null && this.updateModel.children[params.row._index].isarrange === 0) {
                                                this.updateModel.children[params.row._index].isarrange = 1
                                            }
                                        }
                                    },

                                })
                            }
                        },
                        {
                            title: '编制人',
                            align: 'center',
                            key: 'preparedby',
                            render: (h, params) => {
                                return h('Input', {
                                    props: {
                                        value: params.row.preparedby,
                                        size: 'small',
                                    },
                                    on: {
                                        'on-change': (event) => {
                                            this.updateModel.children[params.row._index].preparedby = event.target.value
                                            if (event.target.value != null && event.target.value != null && this.updateModel.children[params.row._index].isarrange === 0) {
                                                this.updateModel.children[params.row._index].isarrange = 1
                                            }
                                        }
                                    },

                                })
                            }
                        },
                        {
                            title: '进度',
                            align: 'center',
                            key: 'schedule',
                            render: (h, params) => {
                                return h('InputNumber', {
                                    props: {
                                        value: params.row.schedule,
                                        max: 100,
                                        min:0,
                                        step:10
                                       /* formatter: (value) => `${value}%`,
                                        parser:(value) => value.replace('%', '')*/
                                    },
                                    on: {
                                        'on-change': (value) => {

                                            this.updateModel.children[params.row._index].schedule = value
                                            if (value === 100) {
                                                this.updateModel.children[params.row._index].isfinish = 1
                                            }
                                        },
                                        "on-blur": ()=> {
                                            if(this.updateModel.children[params.row._index].schedule>100){
                                                this.updateModel.children[params.row._index].schedule = 100;
                                                this.updateModel.children[params.row._index].isfinish = 1
                                            }
                                        }
                                    },
                                })
                            }
                        },
                        {
                            title: '备注',
                            align: 'center',
                            key: 'remark',
                            render: (h, params) => {
                                return h('Input', {
                                    props: {
                                        value: params.row.remark,
                                        size: 'small'
                                    },
                                    on: {
                                        'on-change': (event) => {
                                            this.updateModel.children[params.row._index].remark = event.target.value
                                        }
                                    },

                                })
                            }
                            ,tooltip:'true'
                        },
                        {
                            title: '附件上传',
                            align: 'center',
                            key: 'delete',
                            // eslint-disable-next-line
                            render: (h, params) => {
                                return h('Button', {
                                        props: {
                                            type:"primary",
                                            icon:'ios-cloud-upload-outline',
                                            disabled: !this.$store.state.controls.c50
                                        },
                                        on: {
                                            'click': () => {
                                               // console.log(params.row.id)
                                                if(params.row.id===undefined){
                                                    iView.Message.error({
                                                        content: "请提交后再添加附件！",
                                                        duration: 2
                                                    });
                                                    return
                                                }
                                                this.uploadfile.cid = params.row.id;
                                                this.uploadfileCtrl = true
                                            }
                                        },
                                    }
                                );
                            }
                        },
                        {
                            title: '删除',
                            align: 'center',
                            key: 'delete',
                            render: (h, params) => {
                                return h('Button', {
                                        props: {
                                            type: 'error',
                                            icon: "md-trash",
                                            disabled:!this.$store.state.controls.c48
                                        },
                                        on: {
                                            'click': () => {
                                                this.$Modal.confirm({
                                                    title: '删除确认',
                                                    content: '<p>您确定删除吗？</p>',
                                                    onOk: () => {
                                                        this.updateModel.children=this.updateModel.children.filter((item,index)=>index!==params.row._index);
                                                    },
                                                    onCancel: () => {
                                                     //   this.$Message.info('Clicked cancel');
                                                    }
                                                });

                                            }
                                        },
                                    }
                                );
                            }

                        },
                    ]
                }
            }
        },


        async created() {
            await this.$axios.get(`/ptype/ptypeenum`).then(res => {
                this.ptypeenum = res.data;
            }).catch(exmsg => {
                console.log(exmsg)
            })

            await this.$axios.get(`/system/proledata?username=${sessionStorage.username}`).then(res => {
                this.roledata = res.data;
            }).catch(exmsg => {
                console.log(exmsg)
            })
            await this.getInfo(1);
        }
        ,
        methods: {
            rowClassName (row) {
                const tmp = row.children.filter(item=>item.newlyflag===0);
                if(this.roledata==1&&tmp.length>0){
                    return 'demo-table-info-row';
                }
                return '';
            },
            handleUpload(file) {
                this.file = file;
                return false
            },
            upload() {
                if (this.file.name == null) {
                    iView.Message.error({
                        content: "请选择要上传的文件！",
                        duration: 2
                    });
                    return;
                }
                if (this.file.size/(1024*1024)>100) {
                    iView.Message.error({
                        content: "上传文件不能大于100M！",
                        duration: 2
                    });
                    return;
                }

                this.loadingStatus = true;
                const formData = new FormData();
                formData.append('file', this.file);

                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    }
                };
                this.$axios.post(`/file/file?ftype=${this.uploadfile.ftype}&fabout=${this.uploadfile.fabout}&cid=${this.uploadfile.cid}`, formData, config)
                    .then((response) => {
                        if (response.data) {
                            this.file = {name:null};
                            this.loadingStatus = false;
                            this.uploadfileCtrl = false;
                            iView.Message.info({
                                content: "附件上传成功！",
                                duration: 2
                            });
                        }

                    })
            },
            deleteconfirm() {
                this.delete_confirm = true;
            },
            deleteconfirm1() {
                this.delete_confirm1 = true;
            },
            getInfo1(p) {
                this.searchModel.pageSize = p
                this.getInfo(this.searchModel.pageNum)
            },
            getInfo(v) {

                this.searchModel.pageNum = v

                if (this.searchModel.registerdateS instanceof Date) {
                    this.searchModel.registerdateS = this.format(this.searchModel.registerdateS)
                }
                if (this.searchModel.registerdateE instanceof Date) {
                    this.searchModel.registerdateE = this.format(this.searchModel.registerdateE)
                }
                // console.log(this.searchModel)

                this.$axios.get(`/pro/proinfo`, {
                    params: this.searchModel
                }).then(res => {
                    this.mesInfos = res.data.list;
                    this.searchModel.total = res.data.total;
                    this.deleteButtonFlag = true;

                }).catch(exmsg => {
                    console.log(exmsg)
                })

            }
            ,

            changePagenum(v) {
                this.pageNum = v;
                this.getMenu()
            }
            ,

            insetCheck() {
                // let bo = false;
                this.$axios.get(`/puser/checkisexistbyname?name=${this.insertModel.remark1}`).then(res => {
                    if (res.data) {
                        iView.Message.error({
                            content: "姓名已存在",
                            duration: 2
                        });
                        this.insertModel.remark1 = null;
                        //bo= true;
                    }

                }).catch(exmsg => {
                    console.log(exmsg)
                })
                this.$axios.get(`/puser/checkisexistbycode?code=${this.insertModel.username}`).then(res => {
                    if (res.data) {
                        iView.Message.error({
                            content: "账号已存在",
                            duration: 2
                        });
                        this.insertModel.username = null;
                        //   bo= true;
                    }

                }).catch(exmsg => {
                    console.log(exmsg)
                })
                //  return bo;
                //insertModel.controlID
            }
            ,


            insert() {

                if (!this.inertModelSubmitCtrl)
                    return;
                this.inertModelSubmitCtrl = false;

                if (this.insertModel.compname == null || this.insertModel.compname === "") {
                    iView.Message.error({
                        content: "建设单位不能为空！",
                        duration: 2
                    });
                    this.inertModelSubmitCtrl = true;
                    return;
                }
                if (this.insertModel.proname == null || this.insertModel.proname === "") {
                    iView.Message.error({
                        content: "建设项目不能为空！",
                        duration: 2
                    });
                    this.inertModelSubmitCtrl = true;
                    return;
                }

                if (this.insertModel.registerdate instanceof Date) {
                    this.insertModel.registerdate = this.format(this.insertModel.registerdate)
                }

                this.$axios.get(`/pro/procountno?contractno=${this.insertModel.contractno}`).then(res=>{
                    if(this.insertModel.contractno!==null&&this.insertModel.contractno!==""&&res.data>0){
                        iView.Message.error({
                            content: "合同号不可重复！",
                            duration: 2
                        });
                        this.inertModelSubmitCtrl = true;
                        return;
                    }else{
                        this.$axios.post(`/pro/proinfo`, this.insertModel).then(res => {
                            if (res.data) {
                                iView.Message.info({
                                    content: "新增成功！",
                                    duration: 2
                                });
                                this.insertModelCtrl = false;
                                this.inertModelSubmitCtrl = true;
                                // this.$refs.insert1.resetFields();
                                //this.insertModel.controlID = null;
                                this.getInfo(1)
                                this.insertModel =  {
                                    compname: "",
                                    proname: "",
                                    contact: "",
                                    contractno: "",
                                    registerdate: "",
                                    remark: "",
                                }
                            }
                        }).catch(() => {

                            this.inertModelSubmitCtrl = true;
                        })
                    }
                }).catch(ex=>console.log(ex))







            }
            ,

            insert_cancel() {
                this.insertModelCtrl = false;

            }
            ,

            addMes() {
                this.insertModelCtrl = true
            }
            ,
            addMes1() {
                const obj = {
                    infoid: this.updateModel.proid,
                    ptypeid: null,
                    subid: null,
                    isarrange: 0,
                    mainpeople: null,
                    preparedby: null,
                    newlyflag: 0,
                    isfinish: 0,
                    schedule: 0,
                    remark: null,
                    subtypeenum: []
                }
                this.updateModel.children.push(obj)
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

                this.$axios.delete(`/pro/proinfo`, {
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
                if (!this.$store.state.controls.c24) return
                this.updateModel = v;
                this.updateModelCtrl = true;
            }
            ,

            update() {
                if (!this.updateModelSubmitCtrl)
                    return;
                this.updateModelSubmitCtrl = false;
                if (this.updateModel.compname == null || this.updateModel.compname === "") {
                    iView.Message.error({
                        content: "建设单位不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }
                let tmp = this.updateModel.children.filter(item=>item.ptypeid==null);
                if (tmp.length>0) {
                    iView.Message.error({
                        content: "项目主类型不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }
                tmp = this.updateModel.children.filter(item=>item.subid==null);
                if (tmp.length>0) {
                    iView.Message.error({
                        content: "项目此类型不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }

                if (this.updateModel.proname == null || this.updateModel.proname === "") {
                    iView.Message.error({
                        content: "建设项目不能为空！",
                        duration: 2
                    });
                    this.updateModelSubmitCtrl = true;
                    return;
                }
                if (this.updateModel.registerdate instanceof Date) {
                    this.updateModel.registerdate = this.format(this.updateModel.registerdate)
                }

                this.$axios.put(`/pro/proinfo`, this.updateModel).then(res => {

                    if (res.data) {
                        this.getInfo(1)
                        iView.Message.info({
                            content: "更新成功！",
                            duration: 2
                        });
                    }
                    this.updateModelSubmitCtrl = true;
                    this.updateModelCtrl = false;
                }).catch(() => {

                })




            }
            ,

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
    .ivu-table .demo-table-info-row td{
        background-color: #ff6600 !important;
        color: #fff;
    }
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
