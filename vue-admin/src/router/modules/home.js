// home.js
const Layout = () => import('@/layout/index.vue')
const Home = () => import('@/views/home/index.vue')
const Pc = () => import('@/views/pcenter/index.vue')


export default [
  {
    path: '/home',
    component: Layout,
    name: 'Dashboard',
    meta: {
      title: 'menu.dashboard',
    },
    icon: 'HomeFilled',
    children: [
      {
        path: '',
        name: 'home',
        component: Home,
        meta: {
          title: 'menu.homepage',
          affix: true,
        },
      },
    ],
  },
  {
    path: '/pcenter',
    component: Layout,
    name: 'center',
    meta: {
      title: '个人中心',
    },
    icon: 'UserFilled',
    children: [
      {
        path: '',
        name: 'home',
        component: Pc,
        meta: {
          // title: 'menu.homepage',
          affix: true,
        },
      },
    ],
  }
]
