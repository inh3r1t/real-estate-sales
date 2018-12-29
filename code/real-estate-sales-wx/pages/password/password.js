const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {

  },
  onLoad: function(options) {
    this.WxValidate = app.WxValidate({
      // oldpasswd: {
      //   required: true,
      //   minlength: 6,
      //   maxlength: 20,
      // },
      passwd: {
        required: true,
        minlength: 6,
        maxlength: 20,
      },
      repasswd: {
        required: true,
        equalTo: 'passwd'
      }
    }, {
      // oldpasswd: {
      //   required: '请输入原密码',
      // },
      passwd: {
        required: '请输入新密码',
      },
      repasswd: {
        equalTo: '输入值必须和新密码相同'
      }
    })
  },
  onShow: function() {
    app.checkLogin();
  },
  formSubmit: function(e) {
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
    wx.showLoading({
      title: '加载中',
    })
    app.post("/busUser/update", {
      passwd: params.passwd
    }).then((res) => {
      wx.showToast({
        title: '密码修改成功',
        icon: 'success'
      })
      wx.setStorageSync('userInfo', null);
      wx.setStorageSync('token', null);
      app.globalData.userInfo = null;
      app.globalData.token = null;
      wx.redirectTo({
        url: '/pages/login/index',
      })

      wx.setStorageSync('userInfo', res.data)
      app.globalData.userInfo = res.data
      wx.hideLoading()
    }).catch((res) => {
      wx.showToast({
        title: '密码修改失败',
        icon: 'none'
      })
    });
  }
})