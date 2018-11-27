// pages/building/list.js
const util = require('../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    page: 1,
    list: [{
      id: 1,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水，32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }, {
      id: 2,
      thumb: '/images/house1.png',
      housename: '滁州万科城市中心',
      companyname: '万科',
      houseaddress: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水',
      houseprice: '10000'

    }]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    console.log("121111")
    if (!this.loading) {
      this.getList(1, true).then(() => {
        // 处理完成后，终止下拉刷新
        wx.stopPullDownRefresh()
      })
    }
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    wx.showLoading({
      title: '玩命加载中',
    })
    this.getList(this.data.page + 1).then(res => {
      // 隐藏加载框
      wx.hideLoading();
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  getList(pageNo, override) {
    this.loading = true
    return util.get().then(res => {
      const articles = this.data.list
      this.setData({
        page: pageNo, //当前的页号
        list: override ? articles : this.data.list.concat(articles)
      })
      console.log("==> [SUCCESS]")
    }).catch(err => {
      console.log("==> [ERROR]", err)
    }).then(() => {
      this.loading = false
      const articles = this.data.list
      this.setData({
        page: pageNo, //当前的页号
        list: override ? articles : this.data.list.concat(articles)
      })
      // 隐藏加载框
      wx.hideLoading();

    })
  }
})