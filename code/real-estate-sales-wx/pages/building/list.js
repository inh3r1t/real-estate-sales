var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    isLogin: false,
    page: 1,
    list: []

  },
  toDetail: function(a) {
    var id = a.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/building/detail/detail',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getList(1);
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
    this.setData({
      isLogin: app.isLogin()
    })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
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
    return app.get('https://www.easy-mock.com/mock/5c0fa08f5324d050e6ab1ada/real-estate-sales/getBuildings#!method=get').then(res => {
      //这里既可以获取模拟的res
      console.log(res)
      this.setData({
        list: override ? res.data : this.data.list.concat(res.data)
      })
      console.log(this.data.list)
      this.loading = false
      // 隐藏加载框
      wx.hideLoading();
    }).catch(err => {
      console.log("==> [ERROR]", err)
      this.loading = false
      // 隐藏加载框
      wx.hideLoading();
    })
  }
})