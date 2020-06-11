import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
import {routers} from "../router/router";
import {router} from "../router";

export default new Vuex.Store({
  state:{
    controls :{
      M_2_200421111846_insert:true,
    },

  },

  mutations: {
    setRouter(state, data) {
      data= data ?  data :  JSON.parse(localStorage.getItem('routerList') || '[]');
      if(data.length===0)return;
      const arr =[]
      let resolRouter =function (data) {
         if(!data)return;
         data.forEach(item=>{
           if (item.isLeaf==='1')
             arr.push({
             path: item.menuAction,
             component: () => import(`@/pages${item.relativePath}`),
             meta: {
               breadcrumb: [{
                 name: '首页',
                 href: '/'
               },
                 {
                   name: item.parentLabel,
                   href: null
                 },
                 {
                   name: item.menuLabel,
                   href: null
                 }
               ],
               storage: 'selectionAll',
               type: 'card'
             }
           });
           resolRouter(item.children)
         })
       };
      resolRouter(data);
      routers[1].children.push(...arr);
      router.addRoutes(routers)
    },

    setControls(state,controls){
      controls= controls ?  controls :  JSON.parse(localStorage.getItem('controls') || '[]');
     // if(controls.length===0)return;
      state.controls = controls;
    },
    

  },
  actions:
    {
      routerAdd(state, data) {
        state.commit('setRouter', data)
      },
      setControls(state,controls){
        state.commit('setControls',controls)
      },

    },
})
