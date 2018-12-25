const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.WxValidate = app.WxValidate({
      phone: {
        required: true,
        tel: true,
      },
      password: {
        required: true,
        minlength: 6,
        maxlength: 20,
      }
    }, {
      password: {
        required: '请输入密码',
      },
      phone: {
        required: '请填写您的手机号',
      }
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },
  formSubmit: function(e) {
    //提交错误描述
    const params = e.detail.value
    if (!this.WxValidate.checkForm(params)) {
      const error = this.WxValidate.errorList[0]
      wx.showToast({
        title: `${error.msg}`,
        icon: 'none',
        duration: 2000
      })
      return false
    }
    wx.showModal({
      content: '恭喜您，登录成功',
      showCancel: false,
      confirmText: "立即登录",
      success(res) {
        if (res.confirm) {
          wx.redirectTo({
            url: '/pages/login/index',
          })
        }
      }
    })
  }
})