import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router) // 代表vue使用 vue-router插件

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'), // 路由对应页面
      meta: {title: 'CloudSchool后台', icon: 'dashboard'} // title 是界面左侧栏目的名字， icon 是栏目图标
    }]
  },

  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/list', // redirect,如果直接访问/teacher 会默认跳到/teacher/list
    name: 'Teacher',
    meta: { title: '讲师管理', icon: 'el-icon-user' },
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/teacher/list'),
        meta: { title: '讲师列表', icon: 'el-icon-document' }
      },
      {
        path: 'save',
        name: 'Save',
        component: () => import('@/views/teacher/save'),
        meta: { title: '添加讲师', icon: 'el-icon-circle-plus' }

      },
      {
        path: 'edit/:id', // :id 相当于占位符，是路由参数，可以通过this.$route.params.id取到
        name: '讲师编辑',
        /**
         * 这里component可以用save的组件，即两个路由都渲染同一个组件，这就是组件重用
         * 组件重用产生的问题：组件的生命周期钩子(created)不会再被调用，使得组件的一些数据无法根据路由的改变而得到更新
         * 如：若这里重用组件save，那么在回显之后，点击save路由，得到的页面仍有回显值
         * 解决路由切换问题(经典的vue问题)的办法：1.用vue的监听来解决，监听就是说路由产生变化，监听就会知道；在created()中增加监听对象watch:{},
         * 然后在save的created()中清空回显属性就会生效
         * 2.也可以不用，直接再复制一份，我选择不用
         *
         */
        component: () => import('@/views/teacher/edit'),
        meta: {title: '讲师编辑', noCache: true}, // noCache
        hidden: true // hidden:true，则不会在侧边栏显示，这是框架Layout的参数
      }

    ]
  },

  {
    path: '/subject',
    component: Layout,
    redirect: '/subject/list',
    name: '课程分类管理',
    meta: {title: '课程分类管理', icon: 'example'},
    children: [
      {
        path: 'list',
        name: '课程分类列表',
        component: () => import('@/views/subject/list'),
        meta: {title: '课程分类列表', icon: 'table'}
      },
      {
        path: 'save',
        name: '添加课程分类',
        component: () => import('@/views/subject/save'),
        meta: {title: '添加课程分类', icon: 'tree'}
      }
    ]
  },

  {
    path: '/course',
    component: Layout,
    redirect: '/course/list',
    name: '课程管理',
    meta: {title: '课程管理', icon: 'example'},
    children: [
      {
        path: 'list',
        name: '课程列表',
        component: () => import('@/views/course/list'),
        meta: {title: '课程列表', icon: 'table'}
      },
      {
        path: 'info',
        name: '添加课程',
        component: () => import('@/views/course/info'),
        meta: {title: '添加课程', icon: 'tree'}
      },
      {
        path: 'info/:id',
        name: 'EduCourseInfoEdit',
        component: () => import('@/views/course/info'),
        meta: {title: '编辑课程基本信息', noCache: true},
        hidden: true
      },
      {
        path: 'chapter/:id',
        name: 'EduCourseChapterEdit',
        component: () => import('@/views/course/chapter'),
        meta: {title: '编辑课程大纲', noCache: true},
        hidden: true
      },
      {
        path: 'publish/:id',
        name: 'EduCoursePublishEdit',
        component: () => import('@/views/course/publish'),
        meta: {title: '发布课程', noCache: true},
        hidden: true
      }
    ]
  },

  // {
  //   path: '/sta',
  //   component: Layout,
  //   redirect: '/sta/create',
  //   name: '统计分析',
  //   meta: { title: '统计分析', icon: 'example' },
  //   children: [
  //     {
  //       path: 'create',
  //       name: '生成数据',
  //       component: () => import('@/views/sta/create'),
  //       meta: { title: '生成数据', icon: 'table' }
  //     },
  //     {
  //       path: 'show',
  //       name: '图表显示',
  //       component: () => import('@/views/sta/show'),
  //       meta: { title: '图表显示', icon: 'tree' }
  //     }
  //   ]
  // },
  //
  // {
  //   path: '/acl',
  //   component: Layout,
  //   redirect: '/acl/user/list',
  //   name: '权限管理',
  //   meta: { title: '权限管理', icon: 'chart' },
  //   children: [
  //     {
  //       path: 'user/list',
  //       name: '用户管理',
  //       component: () => import('@/views/acl/user/list'),
  //       meta: { title: '用户管理' }
  //     },
  //     {
  //       path: 'role/list',
  //       name: '角色管理',
  //       component: () => import('@/views/acl/role/list'),
  //       meta: { title: '角色管理' }
  //     },
  //     {
  //       path: 'role/form',
  //       name: '角色添加',
  //       component: () => import('@/views/acl/role/form'),
  //       meta: { title: '角色添加' },
  //       hidden: true
  //     },
  //     {
  //       path: 'role/update/:id',
  //       name: '角色修改',
  //       component: () => import('@/views/acl/role/form'),
  //       meta: { title: '角色修改' },
  //       hidden: true
  //     },
  //     {
  //       path: 'role/distribution/:id',
  //       name: '角色权限',
  //       component: () => import('@/views/acl/role/roleForm'),
  //       meta: { title: '角色权限' },
  //       hidden: true
  //     },
  //     {
  //       path: 'menu/list',
  //       name: '菜单管理',
  //       component: () => import('@/views/acl/menu/list'),
  //       meta: { title: '菜单管理' }
  //     },
  //     {
  //       path: 'user/add',
  //       name: '用户添加',
  //       component: () => import('@/views/acl/user/form'),
  //       meta: { title: '用户添加' },
  //       hidden: true
  //     },
  //     {
  //       path: 'user/update/:id',
  //       name: '用户修改',
  //       component: () => import('@/views/acl/user/form'),
  //       meta: { title: '用户修改' },
  //       hidden: true
  //     },
  //     {
  //       path: 'user/role/:id',
  //       name: '用户角色',
  //       component: () => import('@/views/acl/user/roleForm'),
  //       meta: { title: '用户角色' },
  //       hidden: true
  //     }
  //   ]
  // },

  {
    path: '/test',
    component: Layout,
    name: 'Test',
    redirect: '/test/index',
    /*
      如果加meta，Dashboard上会显示：Dashboard/测试element-ui组件/某个element-ui组件
      如果不加，Dashboard上会显示：Dashboard/某个element-ui组件  因为，我们只想显示唯一一个children，这样更合理
    */
    // meta: { title: '测试element-ui组件', icon: 'el-icon-s-test' },
    children: [
      {
        path: 'index',
        name: 'Test',
        component: () => import('@/views/test/index'),
        meta: { title: '测试某个element组件', icon: 'el-icon-eleme' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
