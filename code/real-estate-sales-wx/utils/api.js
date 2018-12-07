var Mock = require('mock.js')

function ajax(data = '', fn, method = "get", header = {}) {
  // 模拟数据
  var res = Mock.mock({
    'error_code': '',
    'error_msg': '',
    'data|10': [{
      'id|+1': 1,
      'img': "@image('200x100', '#4A7BF7','#fff','pic')",
      'title': '@ctitle(3,8)',
      'city': "@county(true)",
      'stock_num': '@integer(0,100)', //库存数量  
      'marketing_start': '@datetime()',
      'marketing_stop': '@now()',
      'price': '@integer(100,2000)', //现价，单位：分  
      'original_price': '@integer(100,3000)'
    }]
  })
  // 输出结果
  // console.log(JSON.stringify(res, null, 2))
  fn(res);
}
module.exports = {
  ajax: ajax
}