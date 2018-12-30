var http = require('utils/http.js')
// var WxValidate = require('utils/WxValidate.js')
import WxValidate from 'utils/WxValidate.js'
App({
  onLaunch: function() {
    const updateManager = wx.getUpdateManager()

    updateManager.onCheckForUpdate(function (res) {
      // 请求完新版本信息的回调
      console.log(res.hasUpdate)
      let token = wx.getStorageSync('token');
    })

    updateManager.onUpdateReady(function () {
      wx.showModal({
        title: '更新提示',
        content: '新版本已经准备好，是否重启应用？',
        success: function (res) {
          if (res.confirm) {
            // 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
            updateManager.applyUpdate()
          }
        }
      })
    })
    this.isLogin();
  },
  globalData: {
    userInfo: null,
    token: null,
    // _baseUrl: 'https://inh3rit.top'
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
      wx.navigateTo({
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