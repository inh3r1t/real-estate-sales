var app = getApp()
var util = require('../../../utils/util.js')
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    more: true,
    page: 1,
    list: [],
    prevPageList: []

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var pages = getCurrentPages(); // 获取页面栈 
    var prevPage = pages[pages.length - 2]; // 上一个页面
    this.setData({
      prevPageList: prevPage.data.list
    })
    this.getList(1);
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
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    this.getList(1, true).then(() => {
      // 处理完成后，终止下拉刷新
      wx.stopPullDownRefresh()
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    this.getList(this.data.page + 1)
  },


  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '玩命加载中',
      })
      return app.post("http://127.0.0.1:8080/busRealEstate/getPage", {
        page: pageNo,
        pageSize: 10
      }).then(res => {
        //这里既可以获取模拟的res
        console.log(res)
        var pList = this.data.prevPageList;
        var list = override ? res.data.Items : this.data.list.concat(res.data.Items);
        for (var i = 0, len = list.length; i < len; i++) {
          for (var j = 0, plen = pList.length; j < plen; j++) {
            if (list[i].id == pList[j].id) {
              list[i].checked = true;
            }
          }
        }
        this.setData({
          list: list,
          more: res.data.Items != null && res.data.Items.length == 10
        })

        console.log(this.data.list)
        this.loading = false
        // 隐藏加载框
        wx.hideLoading();
      }).catch(err => {
        console.log("==> [ERROR]", err)
        this.loading = false
        // 隐藏加载框
        wx.hideLoading();
      })
    }
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