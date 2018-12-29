var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    isLogin: false,
    isBindWechat: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    app.checkLogin();
    this.setData({
      isLogin: app.isLogin(),
      isBindWechat: wx.getStorageSync('userInfo').openId != null && wx.getStorageSync('userInfo').openId != ''
    })
  },
  login: function() {
    wx.navigateTo({
      url: '/pages/login/index',
    })
  },
  logout: function() {
    wx.setStorageSync('userInfo', null);
    wx.setStorageSync('token', null);
    app.globalData.userInfo = null;
    app.globalData.token = null;
    this.setData({
      isLogin: false
    })
  },
  toMessage: function() {
    wx.navigateTo({
      url: '/pages/message/message',
    })
  },
  toChangePassword: function() {
    wx.navigateTo({
      url: '/pages/password/password',
    })
  },
  toReliveWechat: function() {
    wx.showModal({
      title: '提示',
      content: '确定解除微信快捷登录吗？',
      success(res) {
        if (res.confirm) {
          app.post("http://127.0.0.1:8080/busUser/update", {
            js_code: ''
          }).then((res) => {
            wx.showToast({
              title: '微信解除绑定成功',
              icon: 'success'
            })
            this.setData({
              isBindWechat: false
            })
            wx.setStorageSync('userInfo', res.data)
            app.globalData.userInfo = res.data
          }).catch((res) => {
            debugger
            wx.showToast({
              title: '微信解除绑定失败',
              icon: 'none'
            })
          });
        }
      }
    })
  },
  toBindWechat: function() {
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          wx.login({
            success: res_login => {
              if (res_login.code) {
                app.post("http://127.0.0.1:8080/busUser/update", {
                  js_code: res_login.code
                }).then((res) => {
                  wx.showToast({
                    title: '微信绑定成功',
                    icon: 'success'
                  })
                  wx.setStorageSync('userInfo', res.data)
                  app.globalData.userInfo = res.data
                }).catch((res) => {
                  wx.showToast({
                    title: '微信绑定失败',
                    icon: 'none'
                  })
                });

              }
            }
          })
        }
      }
    })
  }
})