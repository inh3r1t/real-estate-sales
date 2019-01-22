function get(url, data) {
  return request(url, "GET", data);
}

function post(url, data) {
  return request(url, "POST", data);
}

function request(url, method, postData) {
  var promise = new Promise((resolve, reject) => {
    //网络请求
    postData = JSON.stringify(postData);
    wx.request({
      url: url,
      data: postData,
      method: method,
      header: {
        'content-type': 'application/json', // 默认值
        'token': wx.getStorageSync('token')
      },
      success: function(res) { //服务器返回数据
        // console.log(res)
        if (res.data.resultCode == '000000') { //res.data 为 后台返回数据，格式为{"data":{...}, "info":"成功", "status":1}, 后台规定：如果status为1,既是正确结果。可以根据自己业务逻辑来设定判断条件
          resolve(res.data);
        } else if (res.data.resultCode == '000001') {
          reject('验证码已发送，请勿重复获取');
        } else if (res.data.resultCode == '001000') {
          reject('数据请求失败');
        } else if (res.data.resultCode == '001002') {
          reject('用户不存在');
        } else if (res.data.resultCode == '001001' || res.data.resultCode == '001003' || res.data.resultCode == '001004') {
          reject('登录过期');
          wx.navigateTo({
            url: '/pages/login/index'
          })
        } else if (res.data.resultCode == '001005') {
          reject('用户名密码错误');
        } else if (res.data.resultCode == '001006') {
          reject('注册码错误');
        } else if (res.data.resultCode == '001007') {
          reject('微信已绑定过其他账号');
        } else if (res.data.resultCode == '001008') {
          reject('该手机号已经被注册');
        } else {
          reject('系统异常，稍后再试');
        }
      },
      fail: function(e) {
        reject('网络出错');
      }
    })
  });
  return promise;
}

module.exports = {
  get: get,
  post: post
}