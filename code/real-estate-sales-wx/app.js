var http = require('utils/http.js')
// var WxValidate = require('utils/WxValidate.js')
import WxValidate from 'utils/WxValidate.js'
App({
  onLaunch: function() {
    this.isLogin();
  },
  globalData: {
    userInfo: null,
    token: null,
    _baseUrl: 'http://127.0.0.1:8080'
  },
  isLogin: function() {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    if (userInfo && token) {
      this.globalData.userInfo = userInfo;
      this.globalData.token = token;
      return true;
    } else {
      this.globalData.userInfo = null;
      this.globalData.token = null;
      return false;
    }
  },
  checkLogin: function() {
    if (!this.isLogin()) {
      wx.redirectTo({
        url: '/pages/login/index'
      })
    }
  },
  get: function (url, data) {
    return http.get(this.globalData._baseUrl + url, data)
  },
  post: function (url, data) {
    return http.post(this.globalData._baseUrl + url, data)
  },
  WxValidate: (rules, messages) => new WxValidate(rules, messages)

})