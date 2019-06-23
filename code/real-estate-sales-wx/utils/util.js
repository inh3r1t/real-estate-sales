// const http = require('../../../utils/http.js')
import http from "./http.js"
const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const distinct = (a, b) => {
  let arr = a.concat(b);
  for (let i = 0, len = arr.length; i < len; i++) {
    for (let j = i + 1; j < len; j++) {
      if (arr[i] == arr[j]) {
        arr.splice(j, 1);
        // splice 会改变数组长度，所以要将数组长度 len 和下标 j 减一
        len--;
        j--;
      }
    }
  }
  return arr
}
/*获取当前页url*/
const getCurrentPageUrl = () => {
  var pages = getCurrentPages(); //获取加载的页面
  var currentPage = pages[pages.length - 1]; //获取当前页面的对象
  var url = currentPage.route; //当前页面url
  return url
}

/*获取当前页带参数的url*/
const getCurrentPageUrlWithArgs = () => {
  var pages = getCurrentPages(); //获取加载的页面
  var currentPage = pages[pages.length - 1]; //获取当前页面的对象
  var url = currentPage.route; //当前页面url
  var options = currentPage.options; //如果要获取url中所带的参数可以查看options

  //拼接url的参数
  var urlWithArgs = url + '?';
  for (var key in options) {
    var value = options[key]
    urlWithArgs += key + '=' + value + '&'
  }
  urlWithArgs = urlWithArgs.substring(0, urlWithArgs.length - 1);

  return urlWithArgs;
}


module.exports = {
  formatTime: formatTime,
  distinct: distinct,
  getCurrentPageUrl: getCurrentPageUrl,
  getCurrentPageUrlWithArgs: getCurrentPageUrlWithArgs
}