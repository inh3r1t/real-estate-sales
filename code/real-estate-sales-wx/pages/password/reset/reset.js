const app = getApp();
Page({
  data: {
    resetPassword: false,
    timer: "",
    disabled: false

  },
  onLoad: function(options) {
    this.WxValidate = app.WxValidate({
      phone: {
        required: true,
        tel: true
      },
      code: {
        required: true
      }
    }, {
      phone: {
        required: '请输入手机号',
        tel: '请输入正确的手机号'
      },
      code: {
        required: '请输入验证码'
      }
    })
  },
  onShow: function() {

  },
  formSubmit: function(e) {
    const params = e.detail.value
    if (!this.WxValidate.checkForm(params)) {
      const error = this.WxValidate.errorList[0]
      wx.showToast({
        title: `${error.msg} `,
        icon: 'none',
        duration: 2000
      })
      return false
    }
    this.setData({
      phone: params.phone,
      code: params.code,
      resetPassword: true
    });

    // app.post("/busUser/passwordReset", this.data).then((res) => {
    //   // console.log(res);

    //   wx.showModal({
    //     content: '密码重置',
    //     showCancel: false,
    //     confirmText: "立即登录",
    //     success(res) {
    //       if (res.confirm) {
    //         wx.redirectTo({
    //           url: '/pages/login/index',
    //         })
    //       }
    //     }
    //   })
    // }).catch((res) => {
    //   wx.showToast({
    //     title: res,
    //     icon: 'none'
    //   })
    // });

  },
  resetFormSubmit: function(e) {
    const params = e.detail.value
    this.setData({
      password: params.password
    });
    wx.showModal({
      content: '密码重置成功',
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
  },
  //验证码倒计时函数
  getCode: function(options) {
    var that = this;
    var currentTime = 60;
    that.setData({
      timer: currentTime + '秒',
      disabled: true
    })
    var interval = setInterval(function() {
      that.setData({
        timer: (currentTime - 1) + '秒'
      })
      currentTime--;
      if (currentTime <= 0) {
        clearInterval(interval)
        that.setData({
          timer: '',
          currentTime: 60,
          disabled: false
        })
      }
    }, 1000)
  }

})