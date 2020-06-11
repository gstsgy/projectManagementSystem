import Vue from 'vue'
import Router from 'vue-router'
import {routers} from './router.js'

Vue.use(Router);

const RouterConfig = {
    routes: routers
};
export const router = new Router(RouterConfig);
router.beforeEach((to, from, next) => {
    let name = sessionStorage.username;
    if(!name&&to.path!="/login"){
        next('/login')
    }else{
        let boo = false;
        let search = function (routers) {
            if (!routers) return;
            if (routers.find(item => item.path === to.path || item.menuAction === to.path)) return boo = true;
            routers.forEach(item => search(item.children))
        };
        search(routers);

        if (!boo) {
            let data = localStorage.getItem('routerList');
            if (data) search(JSON.parse(data));
        }
        if(boo){
            next()
        }else{
            next('/home')
        }
    }

});
