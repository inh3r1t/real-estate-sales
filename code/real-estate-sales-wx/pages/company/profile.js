var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    banner: '/images/company_01.png',
    name: '',
    chargePerson: '',
    contact: '',
    content: ''
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) { 
    app.get("/busAgentCompany/getById/1").then(res => {
      this.setData({
        name: res.data.name,
        chargePerson: res.data.chargePerson,
        contact: res.data.phone,
        content: res.data.extend1
      })
    })
  },
  makePhoneCall: function (e) {
    let phone = e.currentTarget.dataset.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  }
})