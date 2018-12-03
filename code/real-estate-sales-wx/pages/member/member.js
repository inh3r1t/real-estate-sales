var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false
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
    let userInfo = wx.getStorageSync('userInfo');
    if (!userInfo) {
      // wx.navigateTo({
      //   url: "/pages/login/index"
      // })
      this.setData({
        isLogin: false
      })
    } else {
      this.setData({
        isLogin: true
      })
    }
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
  login: function() {
    wx.navigateTo({
      url: '/pages/login/index',
    })
  },
  logout: function() {
    wx.setStorageSync('userInfo', null);
    app.globalData.userInfo = null;
    this.setData({
      isLogin: false
    })
  },
  toMessage: function() {
    wx.navigateTo({
      url: '/pages/message/message',
    })
  },
  toChangePassword: function() {
    wx.navigateTo({
      url: '/pages/password/password',
    })
  }
})