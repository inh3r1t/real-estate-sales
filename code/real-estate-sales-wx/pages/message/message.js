const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    more: true,
    page: 1,
    list: [],
    startX: 0,
    delBtnWidth: 150 //删除按钮宽度单位（rpx）
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
  touchstart: function(e) {
    this.setData({
      index: e.currentTarget.dataset.index,
      Mstart: e.changedTouches[0].pageX
    });
  },
  touchmove: function(e) {
    //列表项数组
    let list = this.data.list;
    //手指在屏幕上移动的距离
    //移动距离 = 触摸的位置 - 移动后的触摸位置
    let move = this.data.Mstart - e.changedTouches[0].pageX;
    // 这里就使用到我们之前记录的索引index
    //比如你滑动第一个列表项index就是0，第二个列表项就是1，···
    //通过index我们就可以很准确的获得当前触发的元素，当然我们需要在事前为数组list的每一项元素添加x属性
    list[this.data.index].x = move > 0 ? -move : 0;
    this.setData({
      list: list
    });
  },
  touchend: function(e) {
    let list = this.data.list;
    let move = this.data.Mstart - e.changedTouches[0].pageX;
    list[this.data.index].x = move > 100 ? -180 : 0;
    this.setData({
      list: list
    });
  },
  deleteItem: function(e) {
    var id = e.currentTarget.dataset.id
    var index = e.currentTarget.dataset.index
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定删除该条消息吗？',
      success: res => {
        app.post("/busNotifyMsg/deleteById", {
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
  }
})