// pages/building/report/report.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: []
  },
  onLoad: function(options) {},
  onReady: function() {

  },
  onShow: function() {
  },
  toSelect: function() {
    wx.navigateTo({
      url: '/pages/building/select/index',
    })
  },
  delete: function(e) {
    debugger
    var id = e.currentTarget.dataset.id
    let item;
    for (let i = 0; i < this.data.list.length; i++) {
      if (this.data.list[i].id == id) {
        item = this.data.list[i];
        break;
      }
    }
    this.data.list.splice(this.data.list.indexOf(item), 1);
    this.setData({
      list: this.data.list
    })
  }
})