const app = getApp();
Page({
  data: {
    resetPassword: false,
    timer: "",
    disabled: false,
    phone: ""

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
    app.get("/busUser/verifyCode/" + this.data.phone + '/' + params.code).then((res) => {
      console.log(res);
      this.setData({
        resetPassword: true
      })
    }).catch((res) => {
      wx.showToast({
        title: res,
        icon: 'none'
      })
    });

  },
  resetFormSubmit: function(e) {
    const params = e.detail.value
    if (/^[0-9A-Za-z]{6,12}$/.test(params.password) == false) {
      wx.showToast({
        title: '请输入6-12位数字和字母的组合',
        icon: 'none'
      })
      return false;
    }
    app.get("/busUser/reset/" + this.data.phone + "/" + params.password).then((res) => {
      console.log(res);
      wx.showModal({
        content: '密码重置成功',
        showCancel: false,
        confirmText: "重新登录",
        success(res) {
          if (res.confirm) {
            wx.redirectTo({
              url: '/pages/login/index',
            })
          }
        }
      })
    }).catch((res) => {
      wx.showToast({
        title: res,
        icon: 'none'
      })
    });

  },
  //验证码倒计时函数
  getCode: function(e) {
    const params = {
      phone: this.data.phone,
      code: '1'
    }
    if (!this.WxValidate.checkForm(params)) {
      const error = this.WxValidate.errorList[0]
      wx.showToast({
        title: `${error.msg} `,
        icon: 'none',
        duration: 2000
      })
      return false
    }
    app.get("/busUser/sendMessage/" + this.data.phone).then((res) => {
      console.log(res);
      wx.showToast({
        title: res.msg,
        icon: 'none',
        duration: 2000
      })
    }).catch((res) => {
      wx.showToast({
        title: res,
        icon: 'none'
      })
    });
    var that = this;
    var currentTime = 6;
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
          currentTime: 6,
          disabled: false
        })
      }
    }, 1000)
  },
  bindinput: function(e) {
    this.setData({
      phone: e.detail.value
    })
  }
})