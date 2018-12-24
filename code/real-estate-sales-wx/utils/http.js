var _baseUrl = "";

function get(url, data) {
  return request(url, "GET", data);
}

function post(url, data) {
  return request(url, "POST", data);
}


function request(url, method, postData) {
  var promise = new Promise((resolve, reject) => {
    /*
    //自动添加签名字段到postData，makeSign(obj)是一个自定义的生成签名字符串的函数
    var that = this;
    postData.signature = that.makeSign(postData);
    */
    //网络请求
    wx.request({
      url: _baseUrl + url,
      data: postData,
      method: method,
      header: {
        'content-type': method == "POST" ? 'application/x-www-form-urlencoded' : 'application/json' , // 默认值
      },
      success: function(res) { //服务器返回数据
        if (res.data.resultCode == '000000') { //res.data 为 后台返回数据，格式为{"data":{...}, "info":"成功", "status":1}, 后台规定：如果status为1,既是正确结果。可以根据自己业务逻辑来设定判断条件
          resolve(res.data.data);
        } else { //返回错误提示信息
          reject(res.data.msg);
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