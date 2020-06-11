<template lang="html">
    <div class="home">
        <Row  type="flex" justify="center" style="flex-wrap: wrap;margin-bottom: 20px">
            <router-link to="/pro/manage"  v-for="(infor, i) in inforCardData" :key="`infor-${i}`" class="card-item">
                <infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="36">
                    <count-to :end="infor.count" count-class="count-style"/>
                    <p>{{ infor.title }}</p>
                </infor-card>
            </router-link>
        </Row>
        <Row style="background:#eee;padding:60px">
            <Col span="11">
                <Card :bordered="false">
                    <p slot="title"  style="text-align: center;">本周重点工作</p>
                    <Form :model="mes1" :label-width="80">
                        <FormItem v-for="item in mes1" :label="item.date">: {{item.content}}</FormItem>

                    </Form>
                </Card>
            </Col>
            <Col span="11" offset="2">
                <Card shadow>
                    <p slot="title" style="text-align: center;">本周工作安排</p>
                    <Form :model="mes2" :label-width="80">
                        <FormItem v-for="item in mes2" :label="item.date">: {{item.content}}</FormItem>
                    </Form>
                </Card>
            </Col>
        </Row>

    </div>
</template>

<script>
    export default {
        components: {
            InforCard: () => import('../components/info-card'),
            CountTo: () => import('../components/count-to'),
        },
        data() {
            return {
                formItem: {},
                inforCardData: [
                    {title: '项目总计', icon: 'md-person-add', count: 803, color: '#2d8cf0'},
                    {title: '新增项目', icon: 'md-locate', count: 232, color: '#19be6b'},
                ],
                mes1:[],
                mes2:[]
            }
        },
       async created() {
           await this.$axios.get(`/pro/statistics?username=${sessionStorage.username}`).then(res => {
                this.inforCardData[0].count = res.data.sum
                this.inforCardData[1].count = res.data.about
            }).catch(exmsg => {
                console.log(exmsg)
            })
           await this.$axios.get(`/task`).then(res => {
                this.mes1 = res.data.filter(item=>item.type===0)
                this.mes2 = res.data.filter(item=>item.type===1)
            }).catch(exmsg => {
                console.log(exmsg)
            })

        },
    }
</script>

<style lang="less">


    .home {
        width: 100%;
        height: 100%;
        background-image: image("../assets/img/home.png") !important;
        background-size: 100% 100%;
       //
    }
    .card-item{
        width: 200px;
        height: 100px;
    }
    .card-item +  .card-item{
        margin-left: 200px;
    }

</style>
