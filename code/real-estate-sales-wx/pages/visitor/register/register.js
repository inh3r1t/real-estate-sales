var dateTimePicker = require('../../../utils/dateTimePicker.js');
const app = getApp();
const as = require("../../../utils/actionSheet.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: '',
    dateTime: null,
    dateTimeArray: null,
    startYear: 2016,
    endYear: 2050,
    day: '',
    model: {},
    realEstateList: [],
    realEstateIdList: [],
    teamList: ["代理 ", "自销 "],
    addressList: ["琅琊", "南谯", "定远", "明光", "来安", "其他乡镇", "外地"],
    occupationList: ["公务员", "医生", "教师", "个体", "职工", "其他"],
    timesList: ["首访", "二访", "三访", "多次到访"],
    purposeList: ["刚需", "婚房", "改善", "投资"],
    paymentList: ["商贷", "公积金", "组合贷", "一次性"],
    productTypeList: ["高层", "洋房", "大平层", "别墅", "商铺"],
    areaList: ["100m²以内", "100m²-120m²", "120m²-140m²", "140m²-200m²", "200m²以上"],
    levelList: ["A", "B", "C"],
    intentionList: ["已认购", "未认购", "已小定", "已验资/定存"],
    propertyList: ["个人经纪人", "中介"]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.WxValidate = app.WxValidate({
      realEstateId: {
        required: true
      },
      realEstateName: {
        required: true
      },
      customer: {
        required: true,
        minlength: 2,
        maxlength: 10,
      },
      phone: {
        required: true
      },
      team: {
        required: true
      },
      adviser: {
        required: true
      },
      address: {
        required: true
      },
      occupation: {
        required: true
      },
      times: {
        required: true
      },
      purpose: {
        required: true
      },
      payment: {
        required: true
      },
      productType: {
        required: true
      },
      area: {
        required: true
      },
      level: {
        required: true
      },
      intention: {
        required: true
      },
      property: {
        required: true
      },
      reporter: {
        required: true
      }
    }, {
      realEstateId: {
        required: '楼盘是必填项'
      },
      realEstateName: {
        required: '楼盘是必填项'
      },
      customer: {
        required: '客户姓名是必填项',
        minlength: '姓名至少2个字符',
        maxlength: '姓名不超过10个字符',
      },
      phone: {
        required: '联系方式是必填项'
      },
      team: {
        required: '所属团队是必填项'
      },
      adviser: {
        required: '置业顾问是必填项'
      },
      address: {
        required: '居住区域是必填项'
      },
      occupation: {
        required: '职业是必填项'
      },
      times: {
        required: '到访次数是必填项'
      },
      purpose: {
        required: '购买用途是必填项'
      },
      payment: {
        required: '付款方式是必填项'
      },
      productType: {
        required: '意向产品是必填项'
      },
      area: {
        required: '意向面积是必填项'
      },
      level: {
        required: '意向等级是必填项'
      },
      intention: {
        required: '购买意向是必填项'
      },
      property: {
        required: '报备人属性是必填项'
      },
      reporter: {
        required: '报备人姓名是必填项'
      },
      createtime: {
        required: '创建时间是必填项'
      },
      createrid: {
        required: '创建人是必填项'
      }

    })
    var id = options.id;
    if (id != undefined && id != '') {
      app.get("/busVisitRegister/getById/" + id).then(res => {
        var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear, res.data.visittime);
        obj.dateTime[2] = parseInt((obj.defaultDay).substring(0, 2)) - 1; //day 字符串 'xx日' 转 'int'
        this.setData({
          model: res.data,
          dateTime: obj.dateTime,
          dateTimeArray: obj.dateTimeArray,
          day: obj.defaultDay
        });
      })
    } else {
      var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
      obj.dateTime[2] = parseInt((obj.defaultDay).substring(0, 2)) - 1; //day 字符串 'xx日' 转 'int'
      this.setData({
        dateTime: obj.dateTime,
        dateTimeArray: obj.dateTimeArray,
        day: obj.defaultDay
      });
    }
    // 获取驻场经理楼盘
    app.post("/busRealEstate/my", {
      page: 1,
      pageSize: 100
    }).then(res => {
      let nameList = []
      let realEstateIdList = []
      if (res.data != null && res.data.Items != null) {
        for (var i = 0; i < res.data.Items.length; i++) {
          let item = res.data.Items[i]
          nameList.push(item.name)
          realEstateIdList.push(item.id)
        }
      }
      this.setData({
        realEstateList: nameList,
        realEstateIdList: realEstateIdList
      })
    })
  },
  changeDateTimeColumn: function (e) {
    var arr = this.data.dateTime,
      dateArr = this.data.dateTimeArray;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);
    //console.log(arr);
    this.setData({
      dateTimeArray: dateArr,
      dateTime: arr,
      day: dateArr[2][arr[2]].substring(0, 3),
    });
  },
  getDate: function () {
    return this.data.dateTimeArray[0][this.data.dateTime[0]] + '-' + this.data.dateTimeArray[1][this.data.dateTime[1]] + '-' + this.data.day + ' 00:00:00';
  },
  formSubmit: function (e) {
    const params = e.detail.value
    wx.showLoading({
      title: '提交中',
    })
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
      productType: params.productType,
      area: params.area,
      level: params.level,
      intention: params.intention,
      nodeal: params.nodeal,
      property: params.property,
      reporter: params.reporter,
      remark: params.remark,
      createrid: params.createrid,
      visittime: this.getDate()
    });
    app.post("/busVisitRegister/report", this.data).then(res => {
      wx.showToast({
        title: `来访登记成功`,
        icon: 'success',
        duration: 2000,
        success: function () {
          setTimeout(function () {
            wx.navigateBack({})
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
  toActionSheet: function () {
    wx.navigateTo({
      url: '/pages/visitor/action/sheet',
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
  getRealEstateName: function () {
    as.showActionSheet({
      itemList: this.data.realEstateList,
      success: res => {
        this.setData({
          "model.realEstateName": this.data.realEstateList[res.tapIndex],
          "model.realEstateId": this.data.realEstateIdList[res.tapIndex]
          
        })
      }
    })
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
    as.showActionSheet({
      itemList: this.data.addressList,
      success: res => {
        this.setData({
          "model.address": this.data.addressList[res.tapIndex]
        })
      }
    })

    // wx.showActionSheet({
    //   itemList: this.data.addressList,
    //   success: res => {
    //     this.setData({
    //       "model.address": this.data.addressList[res.tapIndex]
    //     })
    //   }
    // })
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