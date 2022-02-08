import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../components/Home'
import BlogDetail from '../components/blog/BlogDetail'
import BlogEdit from '../components/blog/BlogEdit'
import LibraryIndex from '../components/library/LibraryIndex'
import Release from '../components/blog/Release'
import Commit from '../components/blog/Commit'
Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: { name: 'Blogs' }
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    // home页面并不需要被访问
    redirect: '/blogs',
    children: [
      {
        path: '/blogs',
        name: 'Blogs',
        // 懒加载
        component: () => import('../components/blog/Blogs.vue')
      },
      {
        path: '/blog/add', // 注意放在 path: '/blog/:blogId'之前
        name: 'BlogAdd',
        meta: {
          requireAuth: true   //添加路由权限
        },
        component: BlogEdit //每次路由之前（router.beforeEach）判断token的状态，觉得是否需要跳转到登录页面
      },
      //点击 进入对应文章界面
      {
        path: '/blog/:blogId',
        name: 'BlogDetail',
        component: BlogDetail
      },
      
      //改：进入对应文章界面 并对文章进行编辑
      {
        path: '/blog/:blogId/edit',
        name: 'BlogEdit',
        meta: {
          requireAuth: true   //添加路由权限
        },
        component: BlogEdit
      },
      {
        path: '/library',
        name: 'Library',
        component: LibraryIndex,
        // meta: {
        //   requireAuth: true
        // }
      },
      {
        path: '/Release',
        name: 'Release',
        component: Release,
      },
      {
        path: '/Commit',
        name: 'Commit',
        component: Commit,
      }

    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  // {
  //   path: '/blogs',
  //   name: 'Blogs',
  //   // 懒加载
  //   component: () => import('../views/Blogs.vue')
  // },
  // {
  //   path: '/blog/add', // 注意放在 path: '/blog/:blogId'之前
  //   name: 'BlogAdd',
  //   meta: {
  //     requireAuth: true
  //   },
  //   component: BlogEdit
  // },
  // {
  //   path: '/blog/:blogId',
  //   name: 'BlogDetail',
  //   component: BlogDetail
  // },
  // {
  //   path: '/blog/:blogId/edit',
  //   name: 'BlogEdit',
  //   meta: {
  //     requireAuth: true
  //   },
  //   component: BlogEdit
  // }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
