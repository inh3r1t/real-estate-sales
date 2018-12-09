var Mock = require('mock.js')

function ajax(data = '', fn, method = "get", header = {}) {
  // 模拟数据
  var res = Mock.mock({
    'error_code': '',
    'error_msg': '',
    'data|10': [{
      'id|+1': 1,
      'thumb': "/images/house1.png",
      'name': '@ctitle(5,18)',
      'address': '@city(10,18)', 
      'summary': '@ctitle(10,18)', 
      'create_time': '@datetime()',
      'commission': '@integer(100,2000)'
    }]
  })
  // 输出结果
  // console.log(JSON.stringify(res, null, 2))
  fn(res);
}
module.exports = {
  ajax: ajax
}