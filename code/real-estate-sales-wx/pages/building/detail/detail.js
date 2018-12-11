var WxParse = require('../../../wxParse/wxParse.js');
const http = require('../../../utils/http.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    building: {}
  },
  makePhoneCall: function() {
    wx.makePhoneCall({
      phoneNumber: this.data.building.phone,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    http.get("https://www.easy-mock.com/mock/5c0fa08f5324d050e6ab1ada/real-estate-sales/getBuildingById").then(res => {
      this.setData({
        building: res.data
      })
      /** 
       * WxParse.wxParse(bindName , type, data, target,imagePadding) 
       * 1.bindName绑定的数据名(必填) 
       * 2.type可以为html或者md(必填) 
       * 3.data为传入的具体数据(必填) 
       * 4.target为Page对象,一般为this(必填) 
       * 5.imagePadding为当图片自适应是左右的单一padding(默认为0,可选) 
       */
      WxParse.wxParse('detail', 'html', this.data.building.detail, this, 20);
    })
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

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  report: function() {
    wx.navigateTo({
      url: '/pages/building/report/report',
    })
  }
})