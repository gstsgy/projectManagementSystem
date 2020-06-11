<template lang="html">
    <div class="permismanage">
        <!-- :model="userInfo"表单对象 在下面绑定数据 -->

        <Row>
            <Col span="24" style="display:flex;justify-content:space-between">
                <div class="">

                    <Form ref="searchUserInfo" :label-width="75" inline style="text-align:left;">
                        <Row :gutter="56">
                            <Col span="8">
                                <Form-item label="角色" >

                                    <Select v-model="selectRole" clearable placeholder="请选择" @on-change="getAllMenuByroleId">
                                        <Option v-for="sector in roles"  :key="sector.code" :value="sector.code">{{sector.name}}</Option>
                                    </Select>
                                </Form-item>
                            </Col>
                            <Col span="8">
                            </Col>
                            <Col span="4">
                                <input type="hidden" placeholder="" style="width: 100px" />
                            </Col>
                            <Col span="4"></Col>
                        </Row>
                    </Form>
                </div>
            </Col>
        </Row>
        <!-- <div style="inline"> -->
        <Row>
            <Col span="3" ><font size="2px">编辑</font><font size="2px" color="blue"> {{selectRole.roleName}} </font><font size="2px">的权限：</font></Col>
            <Col span="5" ><input type="hidden" placeholder="" style="width: 250px" /></Col>
            <Col span="5" ><input type="hidden" placeholder="" style="width: 250px" /></Col>
            <Col span="6" ><input type="hidden" placeholder="" style="width: 250px" /></Col>
            <Col span="3" >

            </Col>
            <Col span="2" >
                <Button type="primary" shape="circle"  style="width:120px;height:28px;border-radius:14px;background-color:#5aa5ff;" @click='saveRole'>角色分配</Button>
            </Col>
        </Row>
        <!-- </div> -->
        <!-- <hr/> -->
        <!-- <br> -->
        <br/>
        <div>
            <Tree :data="menus" show-checkbox   ref="tree"></Tree>
        </div>
    </div>
</template>

<script>
    import iview from 'iview'
    export default {
        components: {

        },
        data () {
            return {
                roles : [{
                    code:null,
                    name:null
                }],
                menus : [],
                selectRole:{
                    code:null,
                    name:null
                }
            }
        },
       async created(){
         await   this.getMenus();

          await  this.getRoles();


        },


        methods:{
            getMenus () {
                this.$axios.get(`/system/menus1`)
                    .then((result) => {
                        this.menus = result.data;
                    })
                    .catch(()=>{
                    })
            },

            getRoles () {
                this.$axios.get(`/system/proleenum`)
                    .then((result) => {
                        this.roles = result.data;
                    })
                    .catch(()=>{
                    })
            },

            getAllMenuByroleId () {
                this.menus.forEach(item=>{
                    item.checked = false;
                    item.children.forEach(it=>{
                        it.checked = false;
                        it.children.forEach(i=>{
                            i.checked = false;
                        })
                    })
                })
                this.$axios.get(`/system/controls?roleid=${this.selectRole}`)
                    .then((result) => {
                        const arr = []
                        for (let item in result.data){
                            arr.push(item)
                        }
                        console.log(typeof  result.data)
                        this.menus.forEach(item=>{
                            item.children.forEach(it=>{
                                it.children.forEach(tmp=>{

                                    if(arr.find(i=>i.substr(1)==tmp.value)!==undefined)tmp.checked = true;
                                })
                            })
                        })
                        // this.menus.forEach(it=>it.checked = true)
                    })
                    .catch(()=>{

                    })



            },

            saveRole () {
                if(this.selectRole===undefined){
                    iview.Message.error({
                        content:"未选择角色",
                        duration:5
                    });
                    return;
                }

                const slectMenu = this.$refs.tree.getCheckedNodes();
                //console.log(slectMenu)
                const params = slectMenu.filter(item=>item.level===2).map(item=>item.value);
               // console.log(params)
               // return
                this.$axios.post(`/system/menurole`, {
                    roleid:this.selectRole,
                    menuid:params,
                }).then(res=>{
                    if(res.code===0){
                        iview.Message.info({
                            content:"角色分配成功",
                            duration:5
                        });
                        this.getAllMenuByroleId ()
                    }


                }).catch(()=>{

                })
            }



        }
    }

</script>

<style lang="css" scoped>
    .ivu-menu-light{
        background-color:rgb(238,247,255)
    }
    .addrole{
        display: inline-block;
        width: 150px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        background:white;
        border-radius: 15px;
        color: rgb(90,165,255);
        border: 1px solid rgb(90,165,255);
        font-size: 16px;
        margin-right: 10px;
    }
    .editinfo{
        color: #5aa5ff;
        font-size: 20px;
    }
    .ivu-table-overflowX{
        overflow-x: hidden;
    }
    .ivu-radio-wrapper{
        padding-top: 16px !important;
    }

</style>
<style lang="less">
    .permismanage{
        th .ivu-table-cell{
            font-size: 12px !important;
            font-weight: bold;
            color:#333;
        }
        .ivu-form .ivu-form-item-label{
            color:#5aa5ff !important;
            font-size: 12px !important;
            font-family:"Microsoft YaHei";
            line-height: 20px;
        }
        .ivu-input-wrapper{
            width: 160px;
            height: 20px;
        }
        .ivu-select{
            width: 160px;
            height: 20px;
        }
        .ivu-tabs-nav .ivu-tabs-tab-active{
            background-color: white !important;
            font-size: 20px;
            border-radius: 8px;
        }
        .ivu-tabs-ink-bar{
            background-color: #fff;
        }
        .ivu-tabs-nav .ivu-tabs-tab{
            font-size: 14px;
            background-color: #f3f3f3;
        }
        .ivu-tabs-nav-scroll{
            background-color: #f3f3f3;
        }
        .ivu-input{
            border:1px solid #888888;
        }
        .ivu-select-selection{
            border:1px solid #888888;
        }
        .ivu-table-overflowY {
            overflow-y: scroll;
        }
        .ivu-table-tbody{
            font-size: 12px;
            color:#999;
            font-family:"Microsoft YaHei";
            line-height: 50px;
            vertical-align: middle;
        }
        .ivu-table td{
            background-color:#fff;
        }
        .ivu-table-stripe .ivu-table-body tr:nth-child(2n) td, .ivu-table-stripe .ivu-table-fixed-body tr:nth-child(2n) td{
            background-color: #f9f9f9;
        }
        .ivu-input-icon{
            position: absolute;
            right:45px;
        }
        .ivu-input-icon-normal+.ivu-input{
            width: 120px;
        }
        text-align: left;
    }
</style>
