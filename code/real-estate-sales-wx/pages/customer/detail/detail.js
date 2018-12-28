var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    model: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let id = options.id || 0
    app.get("http://127.0.0.1:8080/busDeal/getById/" + id).then(res => {
      console.log(res)
      this.setData({
        model: res.data
      })
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },
  makePhoneCall: function(e) {
    debugger
    let phone = e.currentTarget.dataset.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  }

})