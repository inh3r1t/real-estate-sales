//app.js
App({
  onLaunch: function() {
    let userInfo = wx.getStorageSync('userInfo');
    if (userInfo) {
      this.globalData.userInfo = userInfo
    } else {
      this.globalData.userInfo = null
    }
  },
  globalData: {
    userInfo: null
  },
  isLogin: function() {
    let userInfo = wx.getStorageSync('userInfo');
    if (userInfo) {
      this.globalData.userInfo = userInfo;
      return true;
    } else {
      this.globalData.userInfo = null;
      return false;
    }
  }

})