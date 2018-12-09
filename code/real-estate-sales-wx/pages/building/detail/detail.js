var WxParse = require('../../../wxParse/wxParse.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgUrls: [
      '/images/banner_02.png',
      '/images/banner_01.png',
      '/images/banner_02.png',
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    building: {
      id: 1,
      thumb: '/images/house1.png',
      name: '滁州万科城市中心',
      address: '滁州万科城市中心',
      summary: '32层挑高超大户型，依山傍水，32层挑高超大户型，依山傍水',
      commission: '1.5%+12000',
      manager_name: "王老五",
      manager_phone: "18715512332",
      detail: '万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心万科城市中心\n万科城市中心万科城市中心万科城市中心\n万科城市中心万科城市中心万科城市中心'
    }
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
    /** 
     * WxParse.wxParse(bindName , type, data, target,imagePadding) 
     * 1.bindName绑定的数据名(必填) 
     * 2.type可以为html或者md(必填) 
     * 3.data为传入的具体数据(必填) 
     * 4.target为Page对象,一般为this(必填) 
     * 5.imagePadding为当图片自适应是左右的单一padding(默认为0,可选) 
     */
    WxParse.wxParse('detail',  'html', this.data.building.detail, this,  20);
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