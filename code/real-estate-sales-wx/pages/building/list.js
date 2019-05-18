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
  toDetail: function(e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/building/detail/detail?id=' + id,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    app.get("/busRealEstate/category").then(res => {
      this.setData({
        selectData: res.data
      })
      console.log(res)
      this.getList(1);
    });
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
  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '加载中',
      })
      return app.post("/busRealEstate/getPage", {
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
  selected(e) {
    const id = e.detail.value;
    this.setData({
      type: id
    })
    this.getList(1, true)
  }
})