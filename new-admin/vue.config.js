const {defineConfig} = require('@vue/cli-service')

// const webpack = require('webpack');

module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        host: 'localhost',
        port: 8899,
        proxy: {
            '/api': {
                target: 'http://114.132.51.227:8082/',
                changeOrigin: true,
                pathRewrite: {
                    '/api': ''
                },
                // rewrite: (path) => path.replace(/^\/api/, '')
            }
        },
    },
    // configureWebpack: {
    //     plugins: [
    //         new webpack.ProvidePlugin({
    //             'window.Quill': 'quill/dist/quill.js',
    //             'Quill': 'quill/dist/quill.js'
    //         }),
    //     ]
    // }
})