<style scoped>
    @import '../assets/styles/login.css';
</style>
<template>
    <div class="container">
        <div class='content'>
            <div class='header'>白驼山司徒子侑项目管理系统</div>
            <div class='desc'>心中有理想，脚下有力量</div>
            <Form ref="formInline" :model="formInline" :rules="ruleInline">
                <FormItem prop="user">
                    <Input type="text" v-model="formInline.user" placeholder="账号" prefix="ios-person-outline"/>
                    <!--<Icon type="ios-person-outline" slot="prepend" />-->
                    <!--<Icon type="ios-person" slot="prepend" />-->
                </FormItem>
                <FormItem prop="password">
                    <Input :type="inputType" v-model="formInline.password" placeholder="密码"
                           prefix="ios-lock-outline">
                        <Icon :type="eyeType" slot="suffix" @click="inputIcon"/>
                    </Input>
                </FormItem>
                <FormItem>
                    <div class="alink">
                        <Checkbox v-model="remberpst">记住密码</Checkbox>
                        <span style="margin-right: 0" @click="modal_show=true">修改密码</span>
                    </div>
                </FormItem>

                <FormItem>
                    <Button type="primary" @click="handleSubmit('formInline')" long>登 录</Button>
                </FormItem>
            </Form>
        </div>
        <div class='foot'> {copyright}asdasd</div>
        <Modal v-model="modal_show" title="密码修改"  @on-cancel="cancel" >
            <Form :model="updateModel" :rules="ruleInline1" ref="cpw">
                <FormItem prop="username">
                    <Input type="text" v-model="updateModel.username" placeholder="输入账号" prefix="ios-person-outline"/>
                    <!--<Icon type="ios-person-outline" slot="prepend" />-->
                    <!--<Icon type="ios-person" slot="prepend" />-->
                </FormItem>
                <FormItem prop="oldpwd">
                    <Input :type="inputType" v-model="updateModel.oldpwd" placeholder="输入旧密码"
                           prefix="ios-lock-outline">
                        <Icon :type="eyeType" slot="suffix" @click="inputIcon"/>
                    </Input>
                </FormItem>
                <FormItem prop="newpwd">
                    <Input :type="inputType" v-model="updateModel.newpwd" placeholder="输入密码"
                           prefix="ios-lock-outline">
                        <Icon :type="eyeType" slot="suffix" @click="inputIcon"/>
                    </Input>
                </FormItem>
                <FormItem prop="newpwd1">
                    <Input :type="inputType" v-model="updateModel.newpwd1" placeholder="再输入一次密码"
                           prefix="ios-lock-outline">
                        <Icon :type="eyeType" slot="suffix" @click="inputIcon"/>
                    </Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="cancel">取消</Button>
                <Button type="primary" size="large" @click="ok('cpw')">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
              //  loading: true,
                updateModel: {
                    username: "",
                    oldpwd: "",
                    newpwd: "",
                    newpwd1: ""
                },
                ruleInline1: {
                    username: [
                        {required: true, message: '请输入账号', trigger: 'blur'}
                    ],
                    oldpwd: [
                        {required: true, message: '请输入旧密码', trigger: 'blur'},
                        {
                            type: 'string',
                            min: 6,
                            message: '密码不能少于6位',
                            trigger: 'blur'
                        }
                    ],
                    newpwd: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {
                            type: 'string',
                            min: 6,
                            message: '密码不能少于6位',
                            trigger: 'blur'
                        }
                    ],
                    newpwd1: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {
                            type: 'string',
                            min: 6,
                            message: '密码不能少于6位',
                            trigger: 'blur'
                        }
                    ]
                },
                modal_show: false,
                inputType: 'password',
                eyeType: 'ios-eye',
                remberpst: false,
                formInline: {
                    user: localStorage.user,
                    password: localStorage.password
                },
                ruleInline: {
                    user: [
                        {required: true, message: '请输入账号', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {
                            type: 'string',
                            min: 6,
                            message: '密码不能少于6位',
                            trigger: 'blur'
                        }
                    ]
                }
            }
        },
        methods: {
            ok(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        if(this.updateModel.newpwd!==this.updateModel.newpwd1){
                            this.$Message.error('两次密码不一致!');
                            return
                        } if(this.updateModel.username==="admin"){
                            this.$Message.error('admin不允许被修改!');
                            return
                        }

                        this.$axios.put(`/puser/cpw?username=${this.updateModel.username}&oldpw=${this.updateModel.oldpwd}&npw=${this.updateModel.newpwd1}`)
                            .then(res => {
                                if (res.data) {
                                    this.$Message.success('修改成功!');
                                    this.modal_show = false;

                                } else {
                                    this.$Message.error('旧密码不正确!');
                                }
                            }).catch()


                    } else {
                        return;
                       // this.modal_show = true;
                       // this.loading = false;
                       // this.$Message.error('Fail!');
                    }
                })
            },
            cancel() {
                this.$refs['cpw'].resetFields();
                this.modal_show = false
            },
            inputIcon() {
                this.inputType = this.inputType === 'password' ? 'text' : 'password'
                this.eyeType = this.eyeType === 'ios-eye' ? 'ios-eye-outline' : 'ios-eye'
            },
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        if (this.remberpst) {
                            localStorage.user = this.formInline.user
                            localStorage.password = this.formInline.password
                        } else {
                            delete localStorage.user
                            delete localStorage.password
                        }


                        this.$axios.post(`/system/login?username=${this.formInline.user}&password=${this.formInline.password}`)
                            // eslint-disable-next-line no-unused-vars
                            .then(res => {
                                if (res.data !== null) {
                                    sessionStorage.username = res.data.username
                                    sessionStorage.userid = res.data.userid
                                    this.$axios.get(`/system/menus?roleid=${res.data.userroleid}`).then((res) => {
                                        this.$store.commit('setRouter', res.data);
                                        localStorage.setItem('routerList', JSON.stringify(res.data));
                                        this.$router.push(`/`)
                                    })
                                        .catch((res) => {
                                            console.log(res)
                                        });
                                    this.$axios.get(`/system/controls?roleid=${res.data.userroleid}`).then((res) => {
                                        this.$store.dispatch('setControls', res.data);
                                        localStorage.setItem('controls', JSON.stringify(res.data));

                                    })
                                        .catch((res) => {
                                            console.log(res)
                                        });
                                    this.$Message.success('登录成功!');

                                } else {
                                    this.$Message.error('账号和密码不匹配!');
                                }
                            }).catch()

                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
        }
    }
</script>

