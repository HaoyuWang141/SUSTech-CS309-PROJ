# Vue Router 4

Router 专门用来处理路由相关的逻辑，包括路由的跳转、路由的拦截、路由的权限控制等等。在这里我们使用 vue-router 来实现路由的功能。

## 路由跳转
路由跳转是指从一个页面跳转到另一个页面，这里我们使用 vue-router 提供的 router-link 组件来实现路由的跳转。

```html
<router-link to="/home">Home</router-link>
```

## 基本路由
基本路由是指路由的 path 和 component 之间的映射关系，这里我们使用 vue-router 提供的 createRouter() 方法来创建路由。

学生端的所有页面都会包含一个顶栏和其他布局，我们将其抽离出来，作为一个 Layout 组件，然后在路由中引入 Layout 组件，这样我们就可以在路由中配置 Layout 组件和其他组件的映射关系。

```js
routes: [
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'page1',
        name: 'Page1',
        component: Page1
      },
      {
        path: 'page2',
        name: 'Page2',
        component: Page2
      }
    ]
  }
]
```

新加入页面时, 只需要按照示例的格式, 在 children 中添加即可.

## 路由拦截
路由拦截是指在路由跳转之前，我们可以对路由进行拦截，比如我们可以在路由跳转之前判断用户是否登录，如果没有登录则跳转到登录页面，如果已经登录则跳转到目标页面。

```js
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
    if (store.state.token) { // 通过vuex state获取当前的token是否存在
      next();
    } else {
      next({
        path: '/login',
        query: {redirect: to.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  } else {
    next();
  }
})
```

## 路由权限
路由权限是指在路由跳转之前，我们可以对路由进行权限控制，比如我们可以在路由跳转之前判断一个已登录的用户是否有权限访问该页面，如果没有权限则跳转到 403 页面。

```js
router.beforeEach((to, from, next) => {
  if (to.meta.role) { // 判断该路由是否需要权限
    if (store.state.role === to.meta.role) { // 判断用户是否有权限访问该页面
      next();
    } else {
      next({
        path: '/403',
        query: {redirect: to.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  } else {
    next();
  }
})
```