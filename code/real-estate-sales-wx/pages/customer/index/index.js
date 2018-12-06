// pages/customer/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showShareModal: false,
    shareData: "客户到访\n项目名称：山河印\n客户姓名：陈先生\n客户电话：‭130 *** 5158\n报备时间：2018.11.21\n到访时间：2018.11.21\n渠道公司：德信安居\n项目经理：13888888888"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

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

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  toDetail: function() {
    wx.navigateTo({
      url: '/pages/customer/detail/detail',
    })
  },
  confirm: function() {
    wx.showModal({
      title: '标题',
      content: '这是一个模态弹窗',
      success(res) {
        if (res.confirm) {
          console.log('确定')
        } else if (res.cancel) {
          console.log('取消')
        }
      }
    })
  },
  share: function() {
    this.setData({
      showShareModal: true
    })
  },
  modalCandel: function() {
    // do something
    this.setData({
      showShareModal: false
    })
  },

  /**
   *  点击确认
   */
  modalConfirm: function() {
    var data = this.data.shareData;
    wx.setClipboardData({
      data: data,
      success(res) {
        wx.getClipboardData({
          success(res) {
            console.log(res.data) // data
          }
        })
      }
    })
    this.setData({
      showShareModal: false
    })

  }
})