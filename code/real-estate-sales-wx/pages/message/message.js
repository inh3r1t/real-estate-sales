const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    more: true,
    page: 1,
    list: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    app.checkLogin().then(res => {
      this.getList(1);
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
  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '加载中',
      })
      return app.post("/busNotifyMsg/getPage", {
        page: pageNo,
        pageSize: 10
      }).then(res => {
        //这里既可以获取模拟的res
        console.log(res)
        this.setData({
          list: override ? res.data.Items : this.data.list.concat(res.data.Items),
          more: res.data.Items != null && res.data.Items.length == 10,
          page: pageNo
        })
        // 隐藏加载框
        wx.hideLoading();
      }).catch(err => {
        console.log("==> [ERROR]", err)
        // 隐藏加载框
        wx.hideLoading();
      })
    }
  }
})