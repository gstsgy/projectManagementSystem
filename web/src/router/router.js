// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [

]
export const routers = [
    ...appRouter,
    {
        path: '/login',
        name: 'Login',
        component:()=> import('@/pages/Login')
    },
    {
        path: '/',
        name: 'Layout',
        component: () => import('@/pages/Layout'),
        redirect: '/home',
        children: [{
            path: '/home',
            component: () => import('@/pages/Home'),
            meta: {
                breadcrumb: [{
                    name: '首页',
                    href: '/home'
                }],
                storage: 'selectionAll',
                type: 'card'
            }
        }
        ],
    }
]
