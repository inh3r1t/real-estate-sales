// pages/visitor/register/register.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: "",
    teamList: [{
      "value": "1",
      "name": "代理"
    }, {
      "value": "2",
      "name": "自销"
    }],
    addressList: [{
      "value": "1",
      "name": "琅琊"
    }, {
      "value": "2",
      "name": "南谯"
    }, {
      "value": "3",
      "name": "定远"
    }, {
      "value": "4",
      "name": "明光"
    }, {
      "value": "5",
      "name": "来安"
    }, {
      "value": "6",
      "name": "其他乡镇"
    }, {
      "value": "7",
      "name": "外地"
    }],
    occupationList: [{
      "value": "1",
      "name": "公务员"
    }, {
      "value": "2",
      "name": "医生"
    }, {
      "value": "3",
      "name": "教师"
    }, {
      "value": "4",
      "name": "个体"
    }, {
      "value": "5",
      "name": "职工"
    }, {
      "value": "6",
      "name": "其他"
    }],
    timesList: [{
      "value": "1",
      "name": "首访"
    }, {
      "value": "2",
      "name": "二访"
    }, {
      "value": "3",
      "name": "三访"
    }, {
      "value": "4",
      "name": "多次到访"
    }],
    purposeList: [{
      "value": "1",
      "name": "刚需"
    }, {
      "value": "2",
      "name": "婚房"
    }, {
      "value": "3",
      "name": "改善"
    }, {
      "value": "4",
      "name": "投资"
    }],
    paymentList: [{
      "value": "1",
      "name": "刚需"
    }, {
      "value": "2",
      "name": "婚房"
    }, {
      "value": "3",
      "name": "改善"
    }, {
      "value": "4",
      "name": "投资"
    }],
    paymentList: [{
      "value": "1",
      "name": "商贷"
    }, {
      "value": "2",
      "name": "公积金"
    }, {
      "value": "3",
      "name": "组合贷"
    }, {
      "value": "4",
      "name": "一次性"
    }],
    productTypeList: [{
      "value": "1",
      "name": "高层"
    }, {
      "value": "2",
      "name": "洋房"
    }, {
      "value": "3",
      "name": "大平层"
    }, {
      "value": "4",
      "name": "别墅"
    }, {
      "value": "5",
      "name": "商铺"
    }],
    areaList: [{
      "value": "1",
      "name": "100m²以内"
    }, {
      "value": "2",
      "name": "100m²-120m²"
    }, {
      "value": "3",
      "name": "120m²-140m²"
    }, {
      "value": "4",
      "name": "140m²-200m²"
    }, {
      "value": "5",
      "name": "200m²以上"
    }],
    levelList: [{
      "value": "1",
      "name": "A"
    }, {
      "value": "2",
      "name": "B"
    }, {
      "value": "3",
      "name": "C"
    }],
    intentionList: [{
      "value": "1",
      "name": "已成交"
    }, {
      "value": "2",
      "name": "未成交"
    }, {
      "value": "3",
      "name": "已小定"
    }, {
      "value": "4",
      "name": "已验资/定存"
    }],
    propertyList: [{
      "value": "1",
      "name": "个人经纪人"
    }, {
      "value": "1",
      "name": "中介"
    }]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  }
})