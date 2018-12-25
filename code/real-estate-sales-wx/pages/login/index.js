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
          //开启loading框
          wx.showLoading({
            title: '正在登录...',
            mask: true
          });
          console.log(e.detail.userInfo)
          //调登录api
          wx.login({
            success: res_login => {
              if (res_login.code) {
                //获取用户的openId
                app.post("http://127.0.0.1:8080/busUser/login", {
                  js_code: res_login.code
                }).then((res) => {
                  console.log(res);
                  //1.存用户信息到本地存储
                  wx.setStorageSync('userInfo', e.detail.userInfo)
                  //2.存用户信息到全局变量
                  var app = getApp();
                  app.globalData.userInfo = e.detail.userInfo
                  //隐藏loading框
                  wx.hideLoading();
                }).catch((res) => {
                  console.log(res);
                  wx.setStorageSync('userInfo', e.detail.userInfo)
                  var app = getApp();
                  app.globalData.userInfo = e.detail.userInfo
                  wx.showToast({
                    title: '登录失败',
                    icon: 'none'
                  })
                  wx.navigateBack();
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