const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    phoneNum: '',
    passwd: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.WxValidate = app.WxValidate({
      phoneNum: {
        required: true,
        tel: true,
      },
      passwd: {
        required: true,
        minlength: 6,
        maxlength: 20,
      }
    }, {
      passwd: {
        required: '请输入密码',
      },
      phoneNum: {
        required: '请输入手机号',
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
    //获取用户的openId
    this.setData({
      phoneNum: params.phoneNum,
      passwd: params.passwd,
    });
    app.post("/busUser/login", this.data).then((res) => {
      console.log(res);
      debugger
      //1.存用户信息到本地存储
      wx.setStorageSync('userInfo', res.data.userInfo);
      wx.setStorageSync('token', res.data.token);
      //2.存用户信息到全局变量
      app.globalData.userInfo = res.data.userInfo;
      app.globalData.token = res.data.token;
      wx.showToast({
        title: '登录成功',
        icon: 'success',
        success: function() {
          wx.switchTab({
            url: '/pages/index/index',
          })
        }
      })
    }).catch((res) => {
      wx.showToast({
        title: res,
        icon: 'none'
      })
    });
  }
})