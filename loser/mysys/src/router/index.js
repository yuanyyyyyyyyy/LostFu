import Vue from 'vue'
import VueRouter from 'vue-router'

import login from '../views/common/login.vue'
import UserHome from '../views/common/UserHome.vue'
import allList from '../views/common/allList.vue'
import searchMe from '../views/user/searchMe.vue'
import info from '../views/admin/info.vue'
import log from '../views/super/log.vue'
import wantAd from '../views/super/wantAd.vue'
import adminInfo from '../views/super/adminInfo.vue'
import chat from '@/views/chat/chat.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: login
  }, {
    path: '/login',
    name: 'login',
    component: login
  },
  {
    path: '/chat',
    name: 'chat',
    component: chat
  },
  {
    path: '/UserHome',
    name: 'UserHome',
    component: UserHome,
    children: [
      {
        path: '/allList',
        name: 'allList',
        component: allList,
      },
      {
        path: '/searchMe',
        name: 'searchMe',
        component: searchMe,
      }
      ,
      {
        path: '/info',
        name: 'info',
        component: info,
      }
      ,
      {
        path: '/log',
        name: 'log',
        component: log,
      }

      ,
      {
        path: '/wantAd',
        name: 'wantAd',
        component: wantAd,
      }
      ,
      {
        path: '/adminInfo',
        name: 'adminInfo',
        component: adminInfo,
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
