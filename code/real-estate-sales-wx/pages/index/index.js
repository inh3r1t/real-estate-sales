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
      commission: '1.5%'

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
      commission: '12000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      commission: '1%'

    }],
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