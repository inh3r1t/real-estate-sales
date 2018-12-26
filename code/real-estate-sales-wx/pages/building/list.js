var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    more: true,
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
    this.getList(1, true).then(() => {
      // 处理完成后，终止下拉刷新
      wx.stopPullDownRefresh()
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    this.getList(this.data.page + 1)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '玩命加载中',
      })
      return app.post("http://127.0.0.1:8080/busRealEstate/getPage", {
        page: pageNo,
        pageSize: 10
      }).then(res => {
        //这里既可以获取模拟的res
        console.log(res)
        this.setData({
          list: override ? res.data.Items : this.data.list.concat(res.data.Items),
          more: res.data.Items != null && res.data.Items.length == 10
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
    // return app.get('https://www.easy-mock.com/mock/5c0fa08f5324d050e6ab1ada/real-estate-sales/getBuildings#!method=get').then(res => {
    //   //这里既可以获取模拟的res
    //   console.log(res)
    //   this.setData({
    //     list: override ? res.data : this.data.list.concat(res.data)
    //   })
    //   console.log(this.data.list)
    //   this.loading = false
    //   // 隐藏加载框
    //   wx.hideLoading();
    // }).catch(err => {
    //   console.log("==> [ERROR]", err)
    //   this.loading = false
    //   // 隐藏加载框
    //   wx.hideLoading();
    // })
  }
})