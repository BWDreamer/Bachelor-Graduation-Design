import Vue from 'vue'
import VueRouter from 'vue-router'

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect:'/home', //重定向到主页
    children:[
      {path: '/403', name: 'Auth', meta: {name: '无权限' }, component: () => import('../views/manager/Auth.vue')},
      {path:'home',name:'Home', meta: {name: '系统首页' }, component:()=>import('../views/manager/Home.vue')},
      {path:'user',name:'User', meta: {name: '用户信息' }, component:()=>import('../views/manager/User.vue')},
      {path:'person',name:'Person', meta: {name: '个人信息' }, component:()=>import('../views/manager/Person.vue')},
      {path:'password',name:'Password', meta: {name: '修改密码' }, component:()=>import('../views/manager/Password.vue')},
      {path:'category',name:'Category', meta: {name: '游戏类型' }, component:()=>import('../views/manager/Category.vue')},
      {path:'blog',name:'Blog', meta: {name: '游戏文章' }, component:()=>import('../views/manager/Blog.vue')},
      {path:'activity',name:'Activity', meta: {name: '游戏活动' }, component:()=>import('../views/manager/Activity.vue')},
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
    ]
  },
  {path: '/login', name: 'Login', meta: {name: '登录' }, component: () => import('../views/Login.vue')},
  {path: '/register', name: 'Register', meta: {name: '注册' }, component: () => import('../views/Register.vue')},
  {path: '*', name: '404', meta: {name: '无法访问' }, component: () => import('../views/404.vue')},
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next)=>{
  // to 是到达的路由信息
  // from 是来源的路由信息
  // next 是帮助我们跳转路由的函数
  let adminPaths = ['/user']
  let user = JSON.parse(localStorage.getItem('web-user') || '{}')

  if (user.role !== '管理员' && adminPaths.includes(to.path)) {
    // 如果当前登录的用户不是管理员，然后当前的到达的路径是管理员才有权限访问的路径，那这个时候我就让用户去到一个没有权限的页面，不让他访问实际的页面
    next('/403')
  } else {
    next()
  }
})

export default router
