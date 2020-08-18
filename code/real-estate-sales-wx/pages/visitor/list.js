var app = getApp()
const as = require("../../utils/actionSheet.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showSearch: false,
    showDatePicker: false,
    startDate: "",
    endDate: "",
    keyword: "",
    isLogin: false,
    more: true,
    page: 1,
    type: 0,
    list: [],
    selectData: [],
    realEstateId: 0,
    realEstateName: "",
    realEstateList: [],
    realEstateIdList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.getList(1)
    // 获取驻场经理楼盘
    app.post("/busRealEstate/my", {
      page: 1,
      pageSize: 100
    }).then(res => {
      let nameList = ["全部"]
      let realEstateIdList = [0]
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      isLogin: app.isLogin()
    })
    this.getList(1, true)
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
    this.getList(1, true).then(() => {
      // 处理完成后，终止下拉刷新
      wx.stopPullDownRefresh()
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.getList(this.data.page + 1)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '加载中',
      })
      return app.post("/busVisitRegister/getPage", {
        page: pageNo,
        pageSize: 10,
        keyword: this.data.keyword,
        realEstateId: this.data.realEstateId,
        startDate: this.data.startDate,
        endDate: this.data.endDate
      }).then(res => {
        //这里既可以获取模拟的res
        // console.log(res)
        this.setData({
          list: override ? res.data.Items : this.data.list.concat(res.data.Items),
          more: res.data.Items != null && res.data.Items.length == 10,
          page: pageNo
        })
        // 隐藏加载框
        wx.hideLoading();
      }).catch(err => {
        // console.log("==> [ERROR]", err)
        // 隐藏加载框
        wx.hideLoading();
      })
    }
  },
  deleteItem: function (e) {
    var id = e.currentTarget.dataset.id
    var index = e.currentTarget.dataset.index
    var customer = e.currentTarget.dataset.customer
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定删除' + customer + '该条来访记录吗？',
      success: res => {
        if (res.confirm) {
          app.post("/busVisitRegister/deleteById", {
            id: id
          }).then(res => {
            var history = that.data.list;
            history.splice(index, 1);
            that.setData({
              list: history
            });
            wx.showToast({
              title: '删除成功',
              icon: 'success'
            })
          })
        }
      }
    })

  },
  toDetail: function (e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/visitor/register/register?id=' + id,
    })
  },
  toVisitorRegister: function () {
    wx.navigateTo({
      url: '/pages/visitor/register/register',
    })
  },
  keywordInput: function (e) {
    this.setData({
      keyword: e.detail.value
    })
  },
  showInput: function (e) {
    this.setData({
      showSearch: true
    })
  },
  search: function (e) {
    setTimeout(() => {
      this.getList(1, true)
    }, 200)
  },
  hideInput: function (e) {
    this.setData({
      realEstateId: 0,
      realEstateName: "",
      showSearch: false,
      showDatePicker: false,
      startDate: "",
      endDate: "",
      keyword: ""
    })
    this.search()
  },
  resetForm: function (e) {
    this.setData({
      realEstateId: 0,
      realEstateName: "",
      startDate: "",
      endDate: "",
      keyword: ""
    })
  },
  // 时间段选择  
  bindStartDateChange(e) {
    this.setData({
      startDate: e.detail.value,
    })
  },
  bindEndDateChange(e) {
    this.setData({
      endDate: e.detail.value,
    })
  },
  showDatePicker(e) {
    let d = this.data.showDatePicker
    this.setData({
      showDatePicker: !d
    })
    if (d) {
      this.setData({
        startDate: "",
        endDate: ""
      })
    }
  },
  getRealEstateName: function () {
    if (this.data.realEstateList == null || this.data.realEstateList.length == 0) {
      wx.showToast({
        title: '没有可选择的楼盘',
        icon: 'none'
      })
    } else {
      as.showActionSheet({
        itemList: this.data.realEstateList,
        success: res => {
          this.setData({
            realEstateName: this.data.realEstateList[res.tapIndex],
            realEstateId: this.data.realEstateIdList[res.tapIndex]
          })
        }
      })
    }
  }
})