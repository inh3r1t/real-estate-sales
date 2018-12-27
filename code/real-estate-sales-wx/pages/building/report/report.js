const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    list: []
  },
  onLoad: function(options) {
    var buildingId = options.buildingId;
    var buildingName = options.buildingName;
    this.setData({
      list: [{
        id: buildingId,
        name: buildingName,
      }]
    });
    this.WxValidate = app.WxValidate({
      name: {
        required: true,
        minlength: 2,
        maxlength: 20,
      },
      phone: {
        required: true,
        telfuzzy: true,
      }
    }, {
      name: {
        required: '请输入客户名称',
      },
      phone: {
        required: '请输入手机号',
      }
    })

    // 自定义验证规则
    this.WxValidate.addMethod('telfuzzy', (value, param) => {
      return this.WxValidate.optional(value) || /^1[345789][0-9]\*{4}\d{4}$/.test(value)
    }, '请输入手机号码，中间4位隐藏')
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
    var ids = []
    // 报备提交
    for (var j = 0, len = this.data.list.length; j < len; j++) {
      ids.push(this.data.list[j].id);
    }
    app.post("http://127.0.0.1:8080/busDeal/report", {
        realEstateIds: ids.toString(),
        customerName: params.name,
        customerPhone: params.phone,
        customerSex: params.sex
      })
      .then(res => {
        console.log(res)

        wx.showToast({
          title: `恭喜您，报备成功`,
          icon: 'success',
          duration: 2000
        })
      })

  },
  handlePhone: function(e) {
    const value = e.detail.value
    if (value.length === 11) {
      return value.replace(/(\d{3})[\s\S]{4}(\d{4})/, '$1****$2');
    } else if (value.length > 11) {
      return value.substring(0, 11).replace(/(\d{3})[\s\S]{4}(\d{4})/, '$1****$2');
    }
  }
})