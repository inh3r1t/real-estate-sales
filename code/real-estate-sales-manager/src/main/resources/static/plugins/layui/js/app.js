
var tab;
var elementTemp;
layui.define(['element', 'nprogress', 'form', 'table', 'loader', 'tab', 'navbar', 'onelevel'], function (exports) {
    var $ = layui.jquery,
        element = layui.element,
        layer = layui.layer,
        _win = $(window),
        _doc = $(document),
        _body = $('.kit-body'),
        form = layui.form,
        table = layui.table,
        loader = layui.loader,
        navbar = layui.navbar,
        _componentPath = 'components/';
    tab = layui.tab
    elementTemp = layui.element
    var app = {
        hello: function (str) {
            layer.alert('Hello ' + (str || 'test'));
        },
        config: {
            type: 'iframe'
        },
        set: function (options) {
            var that = this;
            $.extend(true, that.config, options);
            return that;
        },
        init: function () {
            var that = this,
                _config = that.config;
            if (_config.type === 'page') {
                $('a[kit-loader]').on('click', function () {
                    var url = $(this).data('url'),
                        name = $(this).data('name'),
                        id = $(this).data('id');
                    loader.load({
                        url: url,
                        name: name,
                        id: id === undefined ? new Date().getTime() : id,
                        onSuccess: success
                    });

                    function success(data) {
                        switch (data.name) {
                            case 'table':
                                loader.getScript(_componentPath + 'table/table.js', function () {
                                    var tableIns = table.render(moduleTable.config);
                                    moduleTable.extend({
                                        currTable: tableIns,
                                        table: table,
                                        layer: layer,
                                        form: form,
                                        jquery: $
                                    });
                                });
                                break;
                            case 'form':
                                form.render();
                                break;
                            default:
                                break;
                        }
                    };
                });
            }
            if (_config.type === 'iframe') {
                tab.set({
                    elem: '#container',
                    onSwitch: function (data) { //选项卡切换时触发
                        //console.log(data.layId); //lay-id值
                        //console.log(data.index); //得到当前Tab的所在下标
                        //console.log(data.elem); //得到当前的Tab大容器
                    },
                    closeBefore: function (data) { //关闭选项卡之前触发
                        // console.log(data);
                        // console.log(data.icon); //显示的图标
                        // console.log(data.id); //lay-id
                        // console.log(data.title); //显示的标题
                        // console.log(data.url); //跳转的地址
                        return true; //返回true则关闭
                    }
                }).render();
                //navbar加载方式一，直接绑定已有的dom元素事件                
                navbar.bind(function (data) {
                    tab.tabAdd(data);
                });
                //navbar加载方式二，设置远程地址加载
                // navbar.set({
                //     remote: {
                //         url: '/datas/navbar1.json'
                //     }
                // }).render(function(data) {
                //     tab.tabAdd(data);
                // });
                //navbar加载方式三，设置data本地数据
                // navbar.set({
                //     data: [{
                //         id: "1",
                //         title: "基本元素",
                //         icon: "fa-cubes",
                //         spread: true,
                //         children: [{
                //             id: "7",
                //             title: "表格",
                //             icon: "&#xe6c6;",
                //             url: "test.html"
                //         }, {
                //             id: "8",
                //             title: "表单",
                //             icon: "&#xe63c;",
                //             url: "form.html"
                //         }]
                //     }, {
                //         id: "5",
                //         title: "这是一级导航",
                //         icon: "fa-stop-circle",
                //         url: "https://www.baidu.com",
                //         spread: false
                //     }]
                // }).render(function(data) {
                //     tab.tabAdd(data);
                // });
                 
            }
            return that;
        }
    };

    //输出test接口
    exports('app', app);
});
///自定义弹出tab
function AppendMenu(url, title, id, icon) {
    if (!id || id == "") {
        id = "AppendMenu";
    }
    elementTemp.tabDelete("kitTab", id);
    var data = new Object;
    data.url = url;
    data.title = title;
    data.id = id;
    if (icon) {
        data.icon = icon;
    } else {
        data.icon = "";
    }
    tab.tabAdd(data);
}
