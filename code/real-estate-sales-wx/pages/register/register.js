const app = getApp();
var WxValidate;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userName: "卫义",
    phoneNum: "18715512332",
    passwd: "123456",
    pollCode: "123456"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.WxValidate = app.WxValidate({
      userName: {
        required: true,
        minlength: 2,
        maxlength: 10,
      },
      phoneNum: {
        required: true,
        tel: true,
      },
      passwd: {
        required: true,
        minlength: 6,
        maxlength: 20,
      },
      pollCode: {
        required: true,
        minlength: 2,
        maxlength: 100,
      }
    }, {
      userName: {
        required: '请输入姓名',
      },
      phoneNum: {
        required: '请输入手机号',
      },
      passwd: {
        required: '请输入密码',
      },
      pollCode: {
        required: '请输入注册码',
      }
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },
  formSubmit: function(e) {
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
    //获取用户的openId
    this.setData({
      userName: params.userName,
      phoneNum: params.phoneNum,
      passwd: params.passwd,
      pollCode: params.pollCode
    });
    app.post("http://127.0.0.1:8080/busUser/register", this.data).then((res) => {
      console.log(res);
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
    }).catch((res) => {
      wx.showToast({
        title: res,
        icon: 'none'
      })
    });

  }
})