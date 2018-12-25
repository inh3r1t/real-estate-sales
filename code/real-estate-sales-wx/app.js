var http = require('utils/http.js')
// var WxValidate = require('utils/WxValidate.js')
import WxValidate from 'utils/WxValidate.js'
App({
  onLaunch: function() {
    let userInfo = wx.getStorageSync('userInfo');
    if (userInfo) {
      this.globalData.userInfo = userInfo
    } else {
      this.globalData.userInfo = null
    }
  },
  globalData: {
    userInfo: null
  },
  isLogin: function() {
    let userInfo = wx.getStorageSync('userInfo');
    if (userInfo) {
      this.globalData.userInfo = userInfo;
      return true;
    } else {
      this.globalData.userInfo = null;
      return false;
    }
  },
  get: (url, data) => http.get(url, data),
  post: (url, data) => http.post(url, data),
  WxValidate: (rules, messages) => new WxValidate(rules, messages)

})