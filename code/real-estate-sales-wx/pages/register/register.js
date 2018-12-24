const app = getApp();
var WxValidate;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: "",
    phone: "",
    password: "",
    code: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.WxValidate = app.WxValidate({
      name: {
        required: true,
        minlength: 2,
        maxlength: 10,
      },
      phone: {
        required: true,
        telfuzzy: true,
      },
      password: {
        required: true,
        minlength: 6,
        maxlength: 20,
      },
      code: {
        required: true,
        minlength: 2,
        maxlength: 100,
      }
    }, {
      name: {
        required: '请填写您的姓名',
      },
      phone: {
        required: '请填写您的手机号',
      },
      password: {
        required: '请输入密码',
      },
      code: {
        required: '请输入注册码',
      }
    })
    // 自定义验证规则
    this.WxValidate.addMethod('telfuzzy', (value, param) => {
      return this.WxValidate.optional(value) || /^1[345789][0-9]\*{4}\d{4}$/.test(value)
    }, '请输入手机号码，中间4位隐藏')
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
      // `${error.param} : ${error.msg} `
      wx.showToast({
        title: `${error.msg} `,
        icon: 'none',
        duration: 2000
      })
      return false
    }
    wx.showModal({
      content: '恭喜您，注册成功',
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
  handlePhone: function(e) {
    const value = e.detail.value
    if (value.length === 11) {
      debugger
      return value.replace(/(\d{3})[\s\S]{4}(\d{4})/, '$1****$2');
    } else if (value.length > 11) {
      debugger
      return value.substring(0, 11).replace(/(\d{3})[\s\S]{4}(\d{4})/, '$1****$2');
    }
  }
})