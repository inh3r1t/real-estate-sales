const app = getApp()
Page({
  data: {
    topList: [],
    recommendbuildings: [],
    imgUrls: [
      '/images/banner_01.png',
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    isLogin: false
  },
  onLoad: function() {
    app.post("/busRealEstate/getPage", {
      page: 1,
      pageSize: 5,
      isTopRecommend: 1
    }).then(res => {
      console.log(res.data);
      this.setData({
        topList: res.data.Items
      })
    })
    app.post("/busRealEstate/getPage", {
      page: 1,
      pageSize: 10,
      isListRecommend: 1
    }).then(res => {
      console.log(res.data);
      this.setData({
        recommendbuildings: res.data.Items
      })
    })

  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    this.onLoad();
    wx.stopPullDownRefresh();
  },
  onShow: function() {
    this.setData({
      isLogin: app.isLogin()
    })
  },
  toMore: function(a) {
    wx.switchTab({
      url: '/pages/building/list',
    })
  },
  toDetail: function(a) {
    var id = a.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/building/detail/detail?id=' + id,
    })
  }
})