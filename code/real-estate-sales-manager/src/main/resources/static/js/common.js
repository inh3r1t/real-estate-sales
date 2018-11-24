//日期转换为YYYY/M/D HH:MM:SS
function toTime(date) {
    var d = moment(date).format('YYYY/MM/DD HH:mm:ss');
    return d == 'Invalid date' ? '' : d;
}

function toDate(date) {
    var d = moment(date).format('YYYY/MM/DD');
    return d == 'Invalid date' ? '' : d;
}

function openTabPage(url, title, id, icon) {
    parent.AppendMenu(url, title, id, icon);
}

function closelayer() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

//ajax 下载文件
function ajaxDownload(url) {
    var form = $("<form>");   //定义一个form表单
    form.attr('style', 'display:none');   //在form表单中添加查询参数
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', url);
    $('body').append(form);  //将表单放置在web中
    form.submit();
}

(function ($, window) {
    function init(option) {
        var self = this;
        var $this = $(this);

        var defaults = {
            rules: undefined,
            messages: undefined,
            submitHandler: null, // function
            ajaxBefore: null, // function
            ajaxSuccessHandler: null, // function
            ajaxErrorHandler: null, // function
            isClose: true,
            isCallback: true
        };
        self.options = $.extend(true, defaults, option);
        $this.validate({
            ignore: ":hidden:not(select,:checkbox)", // layui框架冲突：layui渲染机制渲染视图时隐藏原来的标签并生成新的标签，导致验证失效。
            rules: self.options.rules || {},
            messages: self.options.messages || {},
            submitHandler: function (form) {
                if (self.options.submitHandler) {
                    return self.options.submitHandler && self.options.submitHandler();
                }
                if (self.options.ajaxBefore) {
                    self.options.ajaxBefore && self.options.ajaxBefore(form);
                }
                var url = $this.attr("action");
                if (!url) {
                    return false;
                }
                var $submitBtn = $this.find("[type=submit]");
                var method = $this.attr("method") ? $this.attr("method") : "GET";
                $submitBtn && $submitBtn.attr("disabled", "disabled"); // 防重复提交
                $this.ajaxSubmit({
                    type: method,
                    url: url,
                    success: function (data) {
                        if (self.options.ajaxSuccessHandler) {
                            self.options.ajaxSuccessHandler(data)
                        } else {
                            // 如果返回值是统一的格式，处理返回结果并相应给用户
                            if (data.hasOwnProperty("state") && data.hasOwnProperty("message")) {
                                if (data.state) {
                                    dialog.success(data.message);
                                    if (self.options.isClose) {
                                        dialog.closeAll(self.options.isCallback);
                                    }
                                } else {
                                    dialog.msg(data.message);
                                }
                            }
                        }
                    }, error: function (data) {
                        self.options.ajaxErrorHandler && self.options.ajaxErrorHandler(data);
                        console.log(data);
                    }, complete: function (xhr, status, $form) {
                        $submitBtn.attr("disabled", "").removeAttr("disabled");
                    }
                });
                return false;
            }
        });
    }

    jQuery.fn.extend({
        initForm: init
    })

}(jQuery, window));




