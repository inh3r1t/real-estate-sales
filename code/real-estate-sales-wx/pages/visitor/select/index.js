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
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getList(1);
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      isLogin: app.isLogin()
    })
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


  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '加载中',
      })
      return app.post("/busRealEstate/getPage", {
        page: pageNo,
        pageSize: 10
      }).then(res => {
        var list = override ? res.data.Items : this.data.list.concat(res.data.Items);
        this.setData({
          list: list,
          more: res.data.Items != null && res.data.Items.length == 10,
          page: pageNo
        })

        // console.log(this.data.list)
        // 隐藏加载框
        wx.hideLoading();
      }).catch(err => {
        // console.log("==> [ERROR]", err)
        // 隐藏加载框
        wx.hideLoading();
      })
    }
  },
  select: function (e) {
    let realEstateId = e.currentTarget.dataset.id
    let realEstateName = e.currentTarget.dataset.name
    var pages = getCurrentPages(); // 获取页面栈 
    var prevPage = pages[pages.length - 2]; // 上一个页面
    prevPage.setData({
      realEstateId: realEstateId,
      realEstateName: realEstateName
    })
    wx.navigateBack({})
  }
})