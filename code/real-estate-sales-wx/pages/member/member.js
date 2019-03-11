var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    isLogin: false,
    isBindWechat: false,
    username: '用户名',
    company: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.setData({
      isLogin: app.isLogin(),
      isBindWechat: wx.getStorageSync('userInfo') && wx.getStorageSync('userInfo').openId != null && wx.getStorageSync('userInfo').openId != '',
      username: wx.getStorageSync('userInfo') && wx.getStorageSync('userInfo').userName,
      company: wx.getStorageSync('userInfo') && wx.getStorageSync('userInfo').companyName || '',
      avatar: (wx.getStorageSync('userInfo') && wx.getStorageSync('wxUserInfo') && wx.getStorageSync('wxUserInfo').avatarUrl) || '/images/avatar.jpg'
    })
  },
  login: function() {
    wx.navigateTo({
      url: '/pages/login/index',
    })
  },
  logout: function() {
    wx.showModal({
      title: '提示',
      content: '确定退出登录吗？',
      success: res => {
        wx.setStorageSync('userInfo', null);
        wx.setStorageSync('wxUserInfo', null);
        wx.setStorageSync('token', null);
        app.globalData.userInfo = null;
        app.globalData.token = null;
        this.setData({
          isLogin: false
        })
      }
    })

  },
  toCompany: function() {
    wx.navigateTo({
      url: '/pages/company/profile',
    })
  },
  toHelp: function () {
    wx.navigateTo({
      url: '/pages/help/help',
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
      success: res => {
        if (res.confirm) {
          app.post("/busUser/update", {
            js_code: ''
          }).then((res) => {
            wx.showToast({
              title: '微信解除绑定成功',
              icon: 'success'
            })
            this.setData({
              isBindWechat: false,
              avatar: '/images/avatar.jpg'
            })
            wx.setStorageSync('userInfo', res.data)
            app.globalData.userInfo = res.data
          }).catch((res) => {
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
    wx.showModal({
      title: '提示',
      content: '确定绑定微信快捷登录吗？',
      success: res => {
        if (res.confirm) {
          wx.getSetting({
            success: res => {
              if (res.authSetting['scope.userInfo']) {
                wx.login({
                  success: res_login => {
                    if (res_login.code) {
                      app.post("/busUser/update", {
                        js_code: res_login.code
                      }).then((res) => {
                        wx.showToast({
                          title: '微信绑定成功',
                          icon: 'success'
                        })
                        wx.getUserInfo({
                          success(result) {
                            wx.setStorageSync('wxUserInfo', result.userInfo)
                            this.setData({
                              isBindWechat: true,
                              avatar: result.userInfo.avatarUrl || '/images/avatar.jpg'
                            })
                          },
                          fail(res) {
                            this.setData({
                              isBindWechat: false,
                              avatar: '/images/avatar.jpg'
                            })
                          }
                        })
                        wx.setStorageSync('userInfo', res.data)
                        app.globalData.userInfo = res.data

                      }).catch((res) => {
                        wx.showToast({
                          title: res,
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
      }
    })

  }
})