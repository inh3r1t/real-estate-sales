//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    recommendbuildings: [{
      id: 1,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水，32层挑高超大户型，依山傍水',
      commission: '1.5%+12000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      commission: '1.5%+12000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      commission: '1.5%+12000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      commission: '1.5%+12000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      commission: '1.5%+12000'

    }],
    imgUrls: [
      '/images/banner_02.png',
      '/images/banner_01.png',
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function() {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})