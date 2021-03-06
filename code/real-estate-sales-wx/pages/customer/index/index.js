var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentTab: -1,
    showShareModal: false,
    shareData: "",
    isLogin: false,
    more: true,
    page: 1,
    list: [],
    count: {
      total_count: 0,
      report_count: 0,
      arrive_count: 0,
      subscribe_count: 0,
    },
    isManager: false,
    isChange: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    app.checkLogin();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var prevLogin = this.data.isLogin;
    this.setData({
      isLogin: app.isLogin()
    })
    if (this.data.isChange) {
      this.getList(1, true);
    } else if (prevLogin != this.data.isLogin) {
      this.getList(1, true);
    }
    var user = app.globalData.userInfo;
    if (user != null) {
      this.setData({
        isManager: user.busRole.type == 0
        // isManager: true
      })
    }
  },


  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    this.getList(1, true).then(res => {
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
  //点击切换
  clickTab: function(e) {
    if (this.data.currentTab === e.currentTarget.dataset.current) {
      return false;
    } else {
      this.setData({
        currentTab: e.currentTarget.dataset.current,
        list: []
      })
      this.getList(1, true);

    }
  },
  toDetail: function(e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/customer/detail/detail?id=' + id,
    })
  },
  operate: function(e) {
    const index = e.currentTarget.dataset.index
    const state = e.currentTarget.dataset.state
    const id = e.currentTarget.dataset.id
    const currentItem = this.data.list[index]
    let url = "";
    if (state == 0) {
      // 报备用户 -> 预约
      url = '/pages/customer/appointment/appointment?id=' + id + "&index=" + index
    } else if (state == 1) {
      // 预约用户 -> 到访
      url = '/pages/customer/visit/visit?id=' + id + "&index=" + index
    } else if (state == 2) {
      // 报备到访 -> 认购
      url = '/pages/customer/subscribe/subscribe?id=' + id + "&index=" + index
    } else if (state == 3) {
      // 报备认购 -> 签约
      url = '/pages/customer/sign/sign?id=' + id + "&index=" + index
    }
    wx.navigateTo({
      url: url,
    })
  },
  share: function(e) {
    let index = e.currentTarget.dataset.index;
    let item = this.data.list[index];

    this.setData({
      showShareModal: true,
      shareData: "项目名称：" + item.realEstateName +
        "<br/>客户姓名：" + item.customerName +
        "<br/>客户电话：" + item.customerPhone +
        (item.reportTime != '' && item.reportTime != undefined ? "<br/>报备时间：" + item.reportTime : "") +
        (item.arriveTime != '' && item.arriveTime != undefined ? "<br/>到访时间：" + item.arriveTime : "") +
        "<br/>渠道公司：德信安居"
    })
  },
  modalCancel: function() {
    // do something
    this.setData({
      showShareModal: false
    })
  },
  makePhoneCall: function(e) {
    let phone = e.currentTarget.dataset.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  },

  /**
   *  点击确认
   */
  modalConfirm: function() {
    var data = this.data.shareData;
    data = data.replace(/\<br\/\>/g, "\r\n")
    wx.setClipboardData({
      data: data,
      success(res) {
        wx.getClipboardData({
          success(res) {
            // console.log(res.data)
          }
        })
      }
    })
    this.setData({
      showShareModal: false
    })

  },
  getList(pageNo, override) {
    if (this.data.more || override) {
      wx.showLoading({
        title: '加载中',
      })
      var state = '';
      if (this.data.currentTab == 0) {
        state = 0;
      }
      if (this.data.currentTab == 1) {
        state = 1;
      }
      if (this.data.currentTab == 2) {
        state = 2;
      }
      if (this.data.currentTab == 3) {
        state = 3;
      }
      if (this.data.currentTab == 4) {
        state = 4;
      }
      return app.post("/busDeal/getPage", {
        page: pageNo,
        pageSize: 5,
        state: state
      }).then(res => {
        //这里既可以获取模拟的res
        // console.log(res)
        // for (let i = 0; i < res.data.list.Items.length; i++) {
        // extend1
        // if (res.data.list.Items[i].busRealEstate.extend1 != '1') {
        //   res.data.list.Items[i].customerPhone = res.data.list.Items[i].customerPhone.replace(/(\d{3})[\s\S]*(\d{4})/, '$1****$2');
        // }
        // }
        this.setData({
          list: override ? res.data.list.Items : this.data.list.concat(res.data.list.Items),
          more: res.data.list.Items != null && res.data.list.Items.length == 5,
          count: res.data.count,
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
  }
})