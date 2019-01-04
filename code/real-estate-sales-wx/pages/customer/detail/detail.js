var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    model: {},
    arriveCertifyPhotoPaths: [],
    subscribePhotoPaths: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let id = options.id || 0
    app.get("/busDeal/getById/" + id).then(res => {
      console.log(res)
      if (res.data.customerPhone != '' && res.data.customerPhone != undefined) {
        res.data.customerPhone = res.data.customerPhone.replace(/(\d{3})[\s\S]*(\d{4})/, '$1****$2');
      }
      var arriveCertifyPhotoPathList = []
      if (res.data.arriveCertifyPhotoPath != null) {
        if (res.data.arriveCertifyPhotoPath.indexOf(',') > -1) {
          arriveCertifyPhotoPathList = res.data.rriveCertifyPhotoPath.split(',');
        } else {
          arriveCertifyPhotoPathList.push(res.data.arriveCertifyPhotoPath)
        }
      }
      var subscribePhotoPathsList = []
      if (res.data.subscribePhotoPahts != null) {
        if (res.data.subscribePhotoPahts.indexOf(',') > -1) {
          subscribePhotoPathsList = res.data.subscribePhotoPahts.split(',');
        } else {
          subscribePhotoPathsList.push(res.data.subscribePhotoPahts)
        }
      }
      this.setData({
        model: res.data,
        arriveCertifyPhotoPaths: arriveCertifyPhotoPathList,
        subscribePhotoPaths: subscribePhotoPathsList
      })
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },
  makePhoneCall: function(e) {
    let phone = e.currentTarget.dataset.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  }

})