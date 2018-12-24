const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    list: []
  },
  onLoad: function(options) {
    this.WxValidate = app.WxValidate({
      name: {
        required: true,
        minlength: 2,
        maxlength: 20,
      },
      phone: {
        required: true,
        tel: true,
      }
    }, {
      name: {
        required: '请输入客户名称',
      },
      phone: {
        required: '请填写您的手机号',
      }
    })
  },
  onReady: function() {

  },
  onShow: function() {},
  toSelect: function() {
    wx.navigateTo({
      url: '/pages/building/select/index',
    })
  },
  delete: function(e) {
    debugger
    var id = e.currentTarget.dataset.id
    let item;
    for (let i = 0; i < this.data.list.length; i++) {
      if (this.data.list[i].id == id) {
        item = this.data.list[i];
        break;
      }
    }
    this.data.list.splice(this.data.list.indexOf(item), 1);
    this.setData({
      list: this.data.list
    })
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
    if (this.data.list.length == 0) {
      wx.showToast({
        title: `请选择楼盘`,
        icon: 'none',
        duration: 2000
      })
      return false
    }
    wx.showModal({
      content: '恭喜您，报备成功',
      showCancel: false,
      confirmText: "立即登录",
      success(res) {
       
      }
    })
  }
})