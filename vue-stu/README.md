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

### src 目录结构
    
```sh
src
├── assets: 静态资源
├── components: 公共组件
├── layout: 布局, 所有页面的公共部分, 包括头部, 背景图片, 背景颜色, 底部等
├── router: 路由
├── types: 类型定义
├── views: 页面视图
├── App.vue: 根组件
└── main.ts: 入口文件
```

仅在本页面(view)中使用的组件(component)放在本目录的 components 目录下, 而多个页面共同使用的公共组件放在 src/components 目录下.

其他具体描述见文件夹下的 README.md 文件.