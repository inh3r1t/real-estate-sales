var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    more: true,
    page: 1,
    type: 0,
    list: [],
    selectData: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.getList(1)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      isLogin: app.isLogin()
    })
    this.getList(1, true)
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.getList(1, true).then(() => {
      // 处理完成后，终止下拉刷新
      wx.stopPullDownRefresh()
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.getList(this.data.page + 1)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '加载中',
      })
      return app.post("/busVisitRegister/getPage", {
        page: pageNo,
        pageSize: 10,
        extend2: this.data.type > 0 ? this.data.type : null
      }).then(res => {
        //这里既可以获取模拟的res
        // console.log(res)
        this.setData({
          list: override ? res.data.Items : this.data.list.concat(res.data.Items),
          more: res.data.Items != null && res.data.Items.length == 10,
          page: pageNo
        })
        // 隐藏加载框
        wx.hideLoading();
      }).catch(err => {
        // console.log("==> [ERROR]", err)
        // 隐藏加载框
        wx.hideLoading();
      })
    }
  },
  deleteItem: function (e) {
    var id = e.currentTarget.dataset.id
    var index = e.currentTarget.dataset.index
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定删除该条来访登记吗？',
      success: res => {
        app.post("/busVisitRegister/deleteById", {
          id: id
        }).then(res => {
          var history = that.data.list;
          history.splice(index, 1);
          that.setData({
            list: history
          });
        })
      }
    })
  
  },
  toDetail: function (e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/visitor/register/register?id=' + id,
    })
  },
  toVisitorRegister: function () {
    wx.navigateTo({
      url: '/pages/visitor/register/register',
    })
  }
})