var dateTimePicker = require('../../../utils/dateTimePicker.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: '2018-10-01',
    time: '12:00',
    dateTime: null,
    dateTimeArray: null,
    startYear: 2016,
    endYear: 2050,
    day: '',
    id: 0,
    index: 0,
    images: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let id = options.id != undefined && options.id != '' ? parseInt(options.id) : 0
    let index = options.index != undefined && options.index != '' ? parseInt(options.index) : 0
    var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    var time = dateTimePicker.getHourMinu();
    obj.dateTime[2] = parseInt((obj.defaultDay).substring(0, 2)) - 1; //day 字符串 'xx日' 转 'int'
    this.setData({
      dateTime: obj.dateTime,
      dateTimeArray: obj.dateTimeArray,
      day: obj.defaultDay,
      time: time,
      id: id,
      index: index
    });
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    app.checkLogin();
  },
  bindTimeChange: function(e) {
    this.setData({
      time: e.detail.value
    })
  },
  changeDateTimeColumn(e) {
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
  changeDateTimeColumn(e) {
    var arr = this.data.dateTime,
      dateArr = this.data.dateTimeArray;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);
    // console.log(arr);
    this.setData({
      dateTimeArray: dateArr,
      dateTime: arr,
      day: dateArr[2][arr[2]].substring(0, 3),
    });
  },
  selectImage: function() {
    wx.chooseImage({
      success: res => {
        const tempFilePaths = res.tempFilePaths
        //启动上传等待中...  
        wx.showToast({
          title: '正在上传...',
          icon: 'loading',
          mask: true,
          duration: 10000
        })
        var uploadImgCount = 0;
        for (var i = 0, h = tempFilePaths.length; i < h; i++) {
          wx.uploadFile({
            url: app.globalData._baseUrl + '/uploadFromWechat',
            filePath: tempFilePaths[i],
            name: 'file',
            header: {
              "Content-Type": "multipart/form-data"
            },
            success: res => {
              uploadImgCount++;
              // console.log(res)
              if (res.data) {
                this.data.images.push(JSON.parse(res.data).data);
                this.setData({
                  images: this.data.images
                });
              }
              //如果是最后一张,则隐藏等待中  
              if (uploadImgCount == tempFilePaths.length) {
                wx.hideToast();
              }
            },
            fail: function(res) {
              wx.hideToast();
              wx.showModal({
                title: '错误提示',
                content: '上传图片失败',
                showCancel: false,
                success: function(res) {}
              })
            }
          });
        }
      }
    })
  },
  getDate: function() {
    return this.data.dateTimeArray[0][this.data.dateTime[0]] + '-' + this.data.dateTimeArray[1][this.data.dateTime[1]] + '-' + this.data.day + ' ' + this.data.time;
  },
  formSubmit: function(e) {
    var pages = getCurrentPages(); // 获取页面栈 
    var prevPage = pages[pages.length - 2]; // 上一个页面
    app.post("/busDeal/sign", {
        id: this.data.id,
        signMoney: e.detail.value.price,
        signTime: this.getDate(),
        signPhotoPaths: this.data.images.toString(),
        formId: e.detail.formId
      })
      .then(res => {
        if (res.data != null) {
          prevPage.data.list[this.data.index] = res.data;
          prevPage.setData({
            list: prevPage.data.list,
            isChange: true
          })
          wx.showToast({
            title: `签约成功`,
            icon: 'success',
            duration: 1000,
            success: function () {
              setTimeout(function () {
                wx.navigateBack({})
              }, 1000)
            }
          })
        }
      })
  }
})