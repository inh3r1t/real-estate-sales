var app = getApp()
var util = require('../../../utils/util.js')
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    more: true,
    isReal: false,
    page: 1,
    list: [],
    prevPageList: []

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var isReal = options.isReal == 'true';
    var pages = getCurrentPages(); // 获取页面栈 
    var prevPage = pages[pages.length - 2]; // 上一个页面
    this.setData({
      prevPageList: prevPage.data.list,
      isReal: isReal
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
        title: '加载中',
      })
      return app.post("/busRealEstate/getPage", {
        page: pageNo,
        pageSize: 10
      }).then(res => {
        //这里既可以获取模拟的res
        // console.log(res)
        var pList = this.data.prevPageList;
        // var cPageList = res.data.Items;
        // for (var i = 0, len = cPageList.length; i < len; i++) {
        //   for (var j = 0, plen = pList.length; j < plen; j++) {
        //     if (cPageList[i].id == pList[j].id) {
        //       cPageList[i].checked = true;
        //     }
        //   }
        // }
        // var list = override ? cPageList : this.data.list.concat(cPageList);

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
          more: res.data.Items != null && res.data.Items.length == 10,
          page: pageNo
        })

        // console.log(this.data.list)
        // 隐藏加载框
        wx.hideLoading();
      }).catch(err => {
        // console.log("==> [ERROR]", err)
        // 隐藏加载框
        wx.hideLoading();
      })
    }
  },
  formSubmit: function(e) {
    // console.log('form发生了submit事件，携带数据为：', e.detail.value.selectList)
    if (e.detail.value.selectList != undefined) {
      var selectList = new Array();
      var pList = this.data.prevPageList;
      var isReal = false;
      for (var i = 0; i < e.detail.value.selectList.length; i++) {
        var item = e.detail.value.selectList[i].split(',');
        var id = item[0];
        var name = item[1];
        var real = item[2] == '1';
        var exist = false;
        for (var j = 0, plen = pList.length; j < plen; j++) {
          if (id == pList[j].id) {
            exist = true;
          }
        }
        if (real) {
          isReal = true;
        }
        if (!exist) {
          selectList.push({
            id: id,
            name: name
          });
        }
      }
      var pages = getCurrentPages(); // 获取页面栈 
      var prevPage = pages[pages.length - 2]; // 上一个页面
      prevPage.setData({
        list: this.data.prevPageList.concat(selectList),
        isReal: isReal
      })
      wx.navigateBack({})
    }
  },
  checkType: function(e) {
    console.log(e)
    if (!this.data.isReal && e.currentTarget.dataset.real == '1') {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '该楼盘需要输入完整号码',
        success: res => {
          if (res.confirm) {
            // var pages = getCurrentPages(); // 获取页面栈 
            // var prevPage = pages[pages.length - 2]; // 上一个页面
            // prevPage.setData({
            //   isReal: true
            // })
          }
        }
      })
    }

  }
})