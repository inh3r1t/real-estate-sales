const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    model: {},
    teamList: ["代理 ", "自销 "],
    addressList: ["琅琊", "南谯", "定远", "明光", "来安", "其他地区"],
    occupationList: ["公务员", "医生", "教师", "个体", "职工", "其他"],
    timesList: ["首访", "二访", "三访", "多次到访"],
    purposeList: ["刚需", "婚房", "改善", "投资"],
    paymentList: ["商贷", "公积金", "组合贷", "一次性"],
    productTypeList: ["高层", "洋房", "大平层", "别墅", "商铺"],
    areaList: ["100m²以内", "100m²-120m²", "120m²-140m²", "140m²-200m²", "200m²以上"],
    levelList: ["A", "B", "C"],
    intentionList: ["已成交", "未成交", "已小定", "已验资/定存"],
    propertyList: ["个人经纪人", "中介"]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.WxValidate = app.WxValidate({
      customer: {
        required: true,
        minlength: 2,
        maxlength: 10,
      }
    }, {
      customer: {
        required: '请输入姓名',
        minlength: '姓名至少2个字符',
        maxlength: '姓名不超过10个字符',
      }
    })
    var id = options.id;
    if (id != undefined && id != '') {
      app.get("/busVisitRegister/getById/" + id).then(res => {
        this.setData({
          model: res.data
        })
      })
    }
  },
  formSubmit: function (e) {
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
      id: params.id,
      realEstateId: params.realEstateId,
      realEstateName: params.realEstateName,
      customer: params.customer,
      phone: params.phone,
      team: params.team,
      adviser: params.adviser,
      address: params.address,
      occupation: params.occupation,
      times: params.times,
      purpose: params.purpose,
      payment: params.payment,
      product_type: params.product_type,
      area: params.area,
      level: params.level,
      intention: params.intention,
      nodeal: params.nodeal,
      property: params.property,
      reporter: params.reporter,
      remark: params.remark,
      createrid: params.createrid
    });
    app.post("/busVisitRegister/report", this.data).then(res => {
      wx.showToast({
        title: `来访登记成功`,
        icon: 'success',
        duration: 2000,
        success: function () {
          setTimeout(function () {
            wx.navigateBack({
              success: res => {
                let pages = getCurrentPages();
                let prevPage = pages[pages.length - 1]; // 上一个页面的对象
                // 当然, 也可以通过setData设置那个页面的其他数据
                prevPage.onLoad(); // 触发上一个页面的onLoad生命周期函数
              }
            })
          }, 1000)
        }
      })
    }).catch(res => {
      wx.showToast({
        title: `来访登记失败`,
        icon: 'none',
        duration: 2000
      })
    })


  },
  toSelect: function () {
    wx.navigateTo({
      url: '/pages/visitor/select/index',
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getTeam: function () {
    wx.showActionSheet({
      itemList: this.data.teamList,
      success: res => {
        this.setData({
          "model.team": this.data.teamList[res.tapIndex]
        })
      }
    })
  },
  getAddress: function () {
    wx.showActionSheet({
      itemList: this.data.addressList,
      success: res => {
        this.setData({
          "model.address": this.data.addressList[res.tapIndex]
        })
      }
    })
  },
  getOccupation: function () {
    wx.showActionSheet({
      itemList: this.data.occupationList,
      success: res => {
        this.setData({
          "model.occupation": this.data.occupationList[res.tapIndex]
        })
      }
    })
  },
  getTimes: function () {
    wx.showActionSheet({
      itemList: this.data.timesList,
      success: res => {
        this.setData({
          "model.times": this.data.timesList[res.tapIndex]
        })
      }
    })
  },
  getPurpose: function () {
    wx.showActionSheet({
      itemList: this.data.purposeList,
      success: res => {
        this.setData({
          "model.purpose": this.data.purposeList[res.tapIndex]
        })
      }
    })
  },
  getPayment: function () {
    wx.showActionSheet({
      itemList: this.data.paymentList,
      success: res => {
        this.setData({
          "model.payment": this.data.paymentList[res.tapIndex]
        })
      }
    })
  },
  getProductType: function () {
    wx.showActionSheet({
      itemList: this.data.productTypeList,
      success: res => {
        this.setData({
          "model.productType": this.data.productTypeList[res.tapIndex]
        })
      }
    })
  },
  getArea: function () {
    wx.showActionSheet({
      itemList: this.data.areaList,
      success: res => {
        this.setData({
          "model.area": this.data.areaList[res.tapIndex]
        })
      }
    })
  },
  getLevel: function () {
    wx.showActionSheet({
      itemList: this.data.levelList,
      success: res => {
        this.setData({
          "model.level": this.data.levelList[res.tapIndex]
        })
      }
    })
  },
  getIntention: function () {
    wx.showActionSheet({
      itemList: this.data.intentionList,
      success: res => {
        this.setData({
          "model.intention": this.data.intentionList[res.tapIndex]
        })
      }
    })
  },
  getProperty: function () {
    wx.showActionSheet({
      itemList: this.data.propertyList,
      success: res => {
        this.setData({
          "model.property": this.data.propertyList[res.tapIndex]
        })
      }
    })
  }
})