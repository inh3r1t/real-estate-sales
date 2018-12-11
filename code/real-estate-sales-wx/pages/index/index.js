//index.js
//获取应用实例
const app = getApp()
var mock = require('../../utils/api.js')
var http = require('../../utils/http.js')

Page({
  data: {
    recommendbuildings: [],
    imgUrls: [
      '/images/banner_02.png',
      '/images/banner_01.png',
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    isLogin: false
  },
  onLoad: function() {
    http.get("https://www.easy-mock.com/mock/5c0fa08f5324d050e6ab1ada/real-estate-sales/getBuildings#!method=get")
      .then(res => {
        this.setData({
          recommendbuildings: res.data
        })
      });
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    this.onLoad();
    wx.stopPullDownRefresh();
  },
  onShow: function() {
    this.setData({
      isLogin: app.isLogin()
    })
  },
  toMore: function(a) {
    wx.navigateTo({
      url: '/pages/building/list',
    })
  },
  toDetail: function(a) {
    var id = a.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/building/detail/detail',
    })
  }
})