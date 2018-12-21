const http = require('../../../utils/http.js')
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
    http.get("https://www.easy-mock.com/mock/5c0fa08f5324d050e6ab1ada/real-estate-sales/customer/detail").then(res => {
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