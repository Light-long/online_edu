'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  // 这个是‘ “ ”’，单引号+双引号
  // 将请求IP和端口改为nginx的9001
  BASE_API: '"http://localhost:9001"',
})
