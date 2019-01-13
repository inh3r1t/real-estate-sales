const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    name: "",
    phone: '',
    isReal: false,
    list: []
  },
  onLoad: function(options) {
    app.checkLogin().then(res => {
      var buildingId = options.buildingId;
      var buildingName = options.buildingName;
      var isReal = options.isReal == '1';
      if (buildingId == undefined || buildingName == undefined) {
        this.setData({
          isReal: isReal
        });
      } else {
        this.setData({
          list: [{
            id: buildingId,
            name: buildingName,
          }],
          isReal: isReal
        });
      }
      this.WxValidate = app.WxValidate({
        name: {
          required: true,
          minlength: 1,
          maxlength: 20,
        },
        phone: {
          required: true,
          minlength: 11,
          maxlength: 11,
          telfuzzy: true,
        }
      }, {
        name: {
          required: '请输入客户名称',
        },
        phone: {
          required: '请输入手机号',
          minlength: '请输入正确手机号码',
          maxlength: '请输入正确手机号码',
        }
      })

      // 自定义验证规则
      this.WxValidate.addMethod('telfuzzy', (value, param) => {
        var regex = isReal ? /^1[345789]\d{9}$/ : /^1[345789][0-9]\*{4}\d{4}$/
        return this.WxValidate.optional(value) || regex.test(value)
      }, isReal ? '请输入完整手机号码' : '请输入手机号码(中间四位为****)')
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
    app.post("/busDeal/report", {
        reportUserId: 3,
        realEstateIds: ids.toString(),
        customerName: params.name,
        customerPhone: params.phone,
        customerSex: params.sex,
        formId: e.detail.formId
      })
      .then(res => {
        // console.log(res)
        wx.showToast({
          title: `报备成功`,
          icon: 'success',
          duration: 2000,
          success: function() {
            wx.navigateBack({})
          }
        })
      }).catch(res => {
        wx.showToast({
          title: `报备失败`,
          icon: 'none',
          duration: 2000
        })
      })

  },
  handlePhone: function(e) {
    var value = e.detail.value
    if (value.length >= 4 && value.length <= 7) {
      value.substring(0, 3)
      return value.substring(0, 3) + Array(value.length - 3 + 1).join('*');
    }
  }
})