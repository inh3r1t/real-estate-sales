var http = require('utils/http.js')
// var WxValidate = require('utils/WxValidate.js')
import WxValidate from 'utils/WxValidate.js'
App({
  onLaunch: function() {
    this.isLogin();
  },
  globalData: {
    userInfo: null,
    token: null
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
  get: (url, data) => http.get(url, data),
  post: (url, data) => http.post(url, data),
  WxValidate: (rules, messages) => new WxValidate(rules, messages)

})