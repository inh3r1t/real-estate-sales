var app = getApp();
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

  },
  getUserInfo: function(e) {
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权
          wx.showLoading({
            title: '正在登录...',
            mask: true
          });
          //调登录api
          wx.login({
            success: res_login => {
              if (res_login.code) {
                //获取用户的openId
                app.post("/busUser/login", {
                  js_code: res_login.code
                }).then((res) => {
                  debugger
                  wx.setStorageSync('wxUserInfo', e.detail.userInfo);
                  //1.存用户信息到本地存储
                  wx.setStorageSync('userInfo', res.data.userInfo);
                  wx.setStorageSync('token', res.data.token);
                  //2.存用户信息到全局变量
                  app.globalData.userInfo = res.data.userInfo;
                  app.globalData.token = res.data.token;
                  wx.hideLoading();
                  wx.showToast({
                    title: '登录成功',
                    icon: 'success',
                    success: function() {
                      wx.navigateBack({
                      })
                    }
                  })
                }).catch((res) => {
                  wx.showToast({
                    title: '微信快捷登录失败',
                    icon: 'none'
                  })
                  wx.navigateTo({
                    url: '/pages/login/phone',
                  })
                });

              }
            }
          })
        }
      }
    })
  },
  register: function() {
    wx.navigateTo({
      url: '/pages/register/register',
    })
  },
  loginByPhone: function() {
    wx.navigateTo({
      url: '/pages/login/phone',
    })
  }
})