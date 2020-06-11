<template lang="html">
  <div class="layout">
    <Layout>
      <Header>
        <div class="header-item">
          <div class="column-div"><img src="../assets/img/logo.png" style="height: 50px"></div>
          <p class="column-div" style="font-size: 24px;font-weight:300;">您好，欢迎使用南京信佣项目管理系统</p>
          <div class="column-div" style="flex-direction: row">欢迎 {{getName}}<div class="icon" @click="logOut"></div></div>
        </div>
      </Header>
      <Layout>
        <Sider hide-trigger :style="{background: '#fff' }">
          <Menu :theme="menuColor" @on-select='Onselect'  width="auto">
            <Submenu v-for="(item,index) in menuList" :name="index+1" :key="index+1">
                    <span slot="title">
                      <Icon :type="item.menuView" />
                      <i :class="getCommons()"></i>
                        {{item.menuLabel}}
                    </span>
              <MenuItem v-for="(it,index) in item.children" :name="it.menuAction" :key="index">{{it.menuLabel}}</MenuItem>
            </Submenu>
          </Menu>
        </Sider>
        <Layout :style="{padding: '0 24px 24px'}">
          <Breadcrumb :style="{margin: '24px 0'}">
            <Breadcrumb-item v-for="(v, k) in paths" :key='k' :to="v.href">{{v.name}}</Breadcrumb-item>
          </Breadcrumb>
          <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
            <router-view/>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  </div>

</template>

<script>


  export default {
    data() {
      return {
        allMenu: [],
        menuColor: 'light',
        menuList: [],
        pathList: [],
      }
    },
     created() {

      this.menuList =JSON.parse(localStorage.getItem("routerList"))

    },
    computed: {
      getName() {
        return sessionStorage.username
      },
      paths() {
        return this.$route.meta.breadcrumb
      },
      //渲染数据
      text() {

        return {
          id: 1,
          val: 2
        }
      },
    },


    methods: {
      getCommons(a) {
        return `${a} ${a}_blue`;
      },

      Onselect(name) {
        this.$router.push(name)
      },
      logOut() {
                  location.href = location.origin + '/pms/#/login'
                  delete sessionStorage.username
                  delete sessionStorage.userid
                  localStorage.removeItem('routerList');
                  localStorage.removeItem('controls');
      }
    }
  }
</script>

<style lang="less" scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
    height: 100vh;
  }
  .layout-logo{
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
  }
  .layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
  }
  .ivu-layout{
    height: 100%;
  }
  .header-item{
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }
  .header-item > div{
    height: 100%;
  }
  .ivu-layout-header{
    line-height: initial;
  }
  .column-div{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    color: #f0f2f5;
  }
  .icon {
margin-left: 8px;
    display: inline-block;
    width: 26px;
    height: 26px;
    background: url('../assets/img/switch_closebtn.png') no-repeat center center;
    cursor: pointer;
    background-size: 100% 100%;
  }
  .icon:hover {
    background: url('../assets/img/switch_openbtn.png') no-repeat center center;
    background-size: 100% 100%;
  }
</style>
