var _baseUrl = "";

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
      url: _baseUrl + url,
      data: postData,
      method: method,
      header: {
        'content-type': 'application/json', // 默认值
        'token': wx.getStorageSync('token')
      },
      success: function(res) { //服务器返回数据
        // if (res.data.resultCode == '000000') { //res.data 为 后台返回数据，格式为{"data":{...}, "info":"成功", "status":1}, 后台规定：如果status为1,既是正确结果。可以根据自己业务逻辑来设定判断条件
        console.log(res)
        resolve(res.data);
        // } else { //返回错误提示信息
        // reject(res.data.msg);
        // }
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