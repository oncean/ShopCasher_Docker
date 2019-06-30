import Vue from 'vue'
import Router from 'vue-router'
import Index from '../../index/Index'
import userService from '../../common/service/userService'

const Login = resolve => require(['../../index/Login'], resolve)
const Regist = resolve => require(['../../index/Regist'], resolve)
const Home = resolve => require(['../../components/Home'], resolve)
const ShopHome = resolve => require(['../../components/ShopHome'], resolve)
const Checkout = resolve => require(['../../components/Checkout'], resolve)
const AddGood = resolve => require(['../../components/AddGood'], resolve)
const OrderManage = resolve => require(['../../components/OrderManage'], resolve)
const GoodManage = resolve => require(['../../components/GoodManage'], resolve)
const Settlement = resolve => require(['../../components/Settlement'], resolve)
const PlayVideo = resolve => require(['../../components/PlayVideo'], resolve)
Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/login',
      component: Login
    }, {
      path: '/signUp',
      component: Regist
    }, {
      path: '/',
      component: Index,
      children: [
        {
          path: '/',
          redirect: '/home'
        },
        {
          path: '/index',
          component: Home
        },
        {
          path: '/home',
          component: Home
        },
        {
          path: '/home/:shopId',
          component: Home
        },
        {
          path: '/shop/:shopId',
          component: ShopHome
        },
        {
          path: '/checkout/:shopId',
          component: Checkout
        },
        {
          path: '/addGood/:shopId',
          component: AddGood
        },
        {
          path: '/goodManage/:shopId',
          component: GoodManage
        },
        {
          path: '/orderManage/:shopId',
          component: OrderManage
        },
        {
          path: '/settlement/:shopId',
          component: Settlement
        },
        {
          path: '/playVideo',
          component: PlayVideo
        }
      ]
    },
    {
      path: '*',
      component: Index
    }
  ]
})
router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/scanClient') {
    next()
  } else if (!userService.ifLogined()) {
    next({
      path: '/login',
      query: {redirect: to.fullPath}
    })
  } else {
    next()
  }
})
export default router
