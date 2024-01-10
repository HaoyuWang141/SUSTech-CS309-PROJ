# vue-stu

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```



## src 目录结构

```sh
src
├── assets: 静态资源
├── axios: 配置axios, 用于网络请求
├── components: 公共组件
├── layout: 布局, 所有页面的公共部分
├── router: 路由, 包括路由的定义和导航守卫
├── types: 类型定义
├── views: 页面视图
├── App.vue: 根组件
└── main.ts: 入口文件
```

仅在本页面(view)中使用的组件(component)放在本目录的 components 目录下, 而多个页面共同使用的公共组件放在 src/components 目录下.

其他具体描述见文件夹下的 README.md 文件.



## 前端任务

需要完成的"任务视图", 包括: 页面, 组件, 弹窗等.

每个任务视图需要完成:

+ 基本骨架
+ UI 交互逻辑
+ axios 网络请求逻辑
+ 配色及美化 
+ [可能的] localStorage 本地存储 / Pinia 存储



**任务目录**

- [x] Layout
- [x] Welcome
- [x] Login
- [ ] Homepage
  - [x] Steps
  - [ ] Profile card (refine)
  - [ ] Team card (refine)

- [x] Dormitory
- [ ] Notification (页面或弹窗)
- [ ] Profile
- [ ] 查看收藏 (弹窗)
- [ ] 聊天窗口
- [ ] 南科大地图选择宿舍区域 (页面)
- [ ] 舍友筛选 / 匹配 (页面)
