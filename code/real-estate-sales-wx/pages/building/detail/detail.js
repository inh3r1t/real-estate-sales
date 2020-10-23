var WxParse = require('../../../wxParse/wxParse.js');
const util = require('../../../utils/util.js')
import Card from '../../../utils/image.js';
import painter from "../../../components/painter/painter.js"
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    isLogin: false,
    isManager: false,
    isShare: false,
    showShareModal: false,
    building: {},

    articleTitle: '',
    articleShareDes: '',
    hasPower: false,
    hasAuth: false,
    bgPic: '/img/bz_bg.png',
    template: {},
    shareImage: '',
  },
  makePhoneCall: function () {
    wx.makePhoneCall({
      phoneNumber: this.data.building.manager.phoneNum,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var source = options.source;
    console.log(options);

    if (source == "share") {
      this.setData({
        isShare: true
      })
    }
    var id = options.id;
    const scene = decodeURIComponent(options.scene);
    if (scene != undefined && scene != "undefined") {
      id = scene;
      this.setData({
        isShare: true
      })
    }
    if (id != undefined && id != '') {
      app.get("/busRealEstate/getById/" + id).then(res => {
        // console.log(res);
        this.setData({
          building: res.data
        })
        /** 
         * WxParse.wxParse(bindName , type, data, target,imagePadding) 
         * 1.bindName绑定的数据名(必填) 
         * 2.type可以为html或者md(必填) 
         * 3.data为传入的具体数据(必填) 
         * 4.target为Page对象,一般为this(必填) 
         * 5.imagePadding为当图片自适应是左右的单一padding(默认为0,可选) 
         */
        if (this.data.building.detail == null) {
          this.data.building.detail = '';
        }
        WxParse.wxParse('detail', 'html', this.data.building.detail, this, 20);
      })
    } else {
      wx.showToast({
        title: '该楼盘不存在',
        icon: 'none'
      })
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      isLogin: app.isLogin()
    })
    var user = app.globalData.userInfo;
    if (user != null) {
      this.setData({
        isManager: user.busRole.type == 0
      })
    }

    wx.getSetting({
      success: (res) => {
        console.log(res.authSetting['scope.writePhotosAlbum'] === undefined)
        if (res.authSetting['scope.writePhotosAlbum'] === undefined) {
          this.setData({
            hasAuth: false
          })
        } else {
          this.setData({
            hasAuth: true
          })
          if (res.authSetting['scope.writePhotosAlbum']) {
            this.setData({
              hasPower: true
            })
          } else {
            this.setData({
              hasPower: false
            })
          }
          console.log(this.data.hasPower)
        }
      }
    })
  },
  report: function () {
    app.checkLogin().then(res => {
      wx.navigateTo({
        url: '/pages/building/report/report?buildingId=' + this.data.building.id + '&buildingName=' + this.data.building.name + '&isReal=' + this.data.building.extend1,
      })
    })
  },
  previewImage: function (e) {
    var current = e.target.dataset.src;
    wx.previewImage({
      current: current, // 当前显示图片的http链接
      urls: this.data.building.images // 需要预览的图片http链接列表
    })
  },
  share: function () {
    let userInfo = wx.getStorageSync('userInfo');
    var articleTitle = this.data.building.name;
    var contact = "经纪人：" + userInfo.userName + " - " + userInfo.phoneNum + "";
    var articleShareDes = this.data.building.summery;
    // var articleTitleUrl = util.getCurrentPageUrlWithArgs() + "&source=share";
    var articleTitleUrl1 = this.data.building.images[0];
    var articleTitleUrl2 = this.data.building.images[1];
    var articleTitleUrl3 = this.data.building.images[2];
    var articleTitleUrl4 = this.data.building.images[3];
    var articleTitleUrl5 = this.data.building.images[4];
    var articleTitleUrl6 = this.data.building.images[5];
    var miniAppImg = "/images/logo-top.png";
    var miniCdoe = "";
    var bgPic = "/images/bg.png";
    wx.showLoading({
      title: '生成分享图片中...',
    })
    app.get("/busRealEstate/gainMiniProgramCode?page=" + util.getCurrentPageUrl() + "&scene=" + this.data.building.id).then(res => {
      miniCdoe = res.data;
      console.log(res);
      this.setData({
        showShareModal: true,
        template: new Card().palette(bgPic,
          miniCdoe,
          miniAppImg,
          contact,
          articleTitle,
          articleShareDes,
          articleTitleUrl1,
          articleTitleUrl2,
          articleTitleUrl3,
          articleTitleUrl4,
          articleTitleUrl5,
          articleTitleUrl6),
      });
    })

  },
  onShareAppMessage(res) {
    let userInfo = wx.getStorageSync('userInfo');
    return {
      // title: "买房请联系 " + userInfo.phoneNum,
      title: "经纪人:" + userInfo.userName + "-" + userInfo.phoneNum + "",
      path: util.getCurrentPageUrlWithArgs() + "&source=share",
      // imageUrl: this.data.building.images[0]
    }
    // if (res.from === 'button') {
    //   this.setData({
    //     showShareModal: true
    //   });
    // } else {
    //   let userInfo = wx.getStorageSync('userInfo');
    //   return {
    //     // title: "买房请联系 " + userInfo.phoneNum,
    //     title: "经纪人： " + userInfo.userName + " - " + userInfo.phoneNum + "",
    //     path: util.getCurrentPageUrlWithArgs() + "&source=share",
    //     // imageUrl: this.data.building.images[0]
    //   }
    // }

  },
  //用户点击右上角分享朋友圈
  onShareTimeline() {
    var articleTitle = this.data.building.name;
    return {
      title: articleTitle,
      imageUrl: this.data.building.images[0]
    }
  },
  hideModal: function () {
    this.setData({
      showShareModal: false
    });
  },
  onImgOK(e) {
    wx.hideLoading()
    this.setData({
      shareImage: e.detail.path
    })
    this.eventSave();
  },
  eventSave() {
    let that = this;
    wx.authorize({
      scope: 'scope.writePhotosAlbum',
      success() {
        // 用户已经同意小程序使用录音功能，后续调用 wx.startRecord 接口不会弹窗询问
        // console.log(that.data.hasPower)
        let imgSrc = that.data.shareImage;
        wx.saveImageToPhotosAlbum({
          filePath: imgSrc,
          success: function (res) {
            wx.showToast({
              title: '图片已保存至本地相册，点击关闭',
              icon: 'none'
            })
          },
          fail: function () {
            wx.showToast({
              title: '保存失败',
              icon: 'none'
            })
          }
        })
      }
    })

  },

})