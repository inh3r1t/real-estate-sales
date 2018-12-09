const http = require('../../../utils/http.js')
var mock = require('../../../utils/api.js')
var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    isLogin: false,
    page: 1,
    list: []

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getList(1);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.setData({
      isLogin: app.isLogin()
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    if (!this.loading) {
      this.getList(1, true).then(() => {
        // 处理完成后，终止下拉刷新
        wx.stopPullDownRefresh()
      })
    }
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    wx.showLoading({
      title: '玩命加载中',
    })
    // this.getList(this.data.page + 1).then(res => {
    //   // 隐藏加载框
    //   wx.hideLoading();
    // })
    wx.hideLoading();
  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  getList(pageNo, override) {
    this.loading = true
    return http.get('').then(res => {
      const articles = this.data.list
      this.setData({
        page: pageNo, //当前的页号
        list: override ? articles : this.data.list.concat(articles)
      })
      console.log("==> [SUCCESS]")
    }).catch(err => {
      console.log("==> [ERROR]", err)
      mock.ajax('', res => {
        //这里既可以获取模拟的res
        console.log(res)
        this.setData({
          list: override ? res.data : this.data.list.concat(res.data)
        })
        this.loading = false
        // 隐藏加载框
        wx.hideLoading();
      });
    })
  },
  formSubmit: function(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value.selectList)
    if (e.detail.value.selectList != undefined) {
      var selectList = new Array();
      for (var i = 0; i < e.detail.value.selectList.length; i++) {
        var item = e.detail.value.selectList[i].split(',');
        var id = item[0];
        var name = item[1];
        selectList.push({
          id: id,
          name: name
        });
      }
      var pages = getCurrentPages(); // 获取页面栈 
      var prevPage = pages[pages.length - 2]; // 上一个页面
      prevPage.setData({
        list: selectList
      })
      wx.navigateBack({})
    }
  },
})