﻿// Generated by IcedCoffeeScript 108.0.11
(function () {
    (function (d) {
        var Dialog;
        alertify.defaults = {
            fullScreen: false,
            autoReset: true,
            basic: false,
            closable: true,
            closableByDimmer: true,
            frameless: false,
            maintainFocus: true,
            maximizable: true,
            modal: true,
            movable: true,
            moveBounded: false,
            overflow: true,
            padding: true,
            pinnable: true,
            pinned: true,
            preventBodyShift: false,
            resizable: true,
            startMaximized: false,
            transition: 'pulse',
            notifier: {
                delay: 5,
                position: 'bottom-right'
            },
            glossary: {
                title: '系统',
                ok: '确定',
                cancel: '取消'
            },
            theme: {
                input: 'ajs-input',
                ok: 'ajs-ok',
                cancel: 'ajs-cancel'
            }
        };
        Dialog = function (options) {
            var default_options, self;
            self = new Object();
            default_options = {
                title: " ",
                shadeClose: false,
                maxmin: true,
                size: ["50%", "50%"],
                btns: ["OK", "Cancel"]
            };
            self.options = options = $.extend({}, default_options, options);
            self.getSize = function (size) {
                var result;
                result = size;
                if (typeof size[0] === "number") {
                    result[0] = "" + size[0] + "px";
                }
                if (typeof size[1] === "number") {
                    result[1] = "" + size[1] + "px";
                }
                return result;
            };
            self.setDialogResult = function (result) {
                if (parent) {
                    return parent.dialog_result = result;
                } else {
                    return window.dialog_result = result;
                }
            };

            self.getDialogResult = function () {
                var result;
                result = parent ? parent.dialog_result : window.dialog_result;
                return result;
            };
            self.setDialogResultObject = function (result) {
                if (parent) {
                    return parent.dialog_result_Object = result;
                } else {
                    return window.dialog_result_Object = result;
                }
            };
            self.getDialogResultObject = function () {
                var result;
                result = parent ? parent.dialog_result_Object : window.dialog_result_Object;
                return result;
            };
            self.alert = function (msg, fnCloseOrOptions) {
                options = $.isFunction(fnCloseOrOptions) ? self.options : $.extend({}, self.options, fnCloseOrOptions);
                return parent.layer.alert(msg, {
                    closeBtn: 0,
                    title: options.title,
                    end: function () {
                        if ($.isFunction(fnCloseOrOptions)) {
                            return fnCloseOrOptions();
                        } else if ($.isFunction(options.close)) {
                            return options.close();
                        }
                    }
                });
            };
            self.confirm = (function (_this) {
                return function (msg, fnOK, fnClose, options) {
                    options = $.extend({}, self.options, options);
                    return parent.layer.confirm(msg, {
                        btn: options.btns,
                        title: options.title,
                        end: fnClose
                    }, fnOK);
                };
            })(this);
            self.prompt = (function (_this) {
                return function (title, callback) {
                    return parent.layer.prompt({
                        title: title,
                        formType: 0
                    }, function (value) {
                        if (callback) {
                            return callback(value);
                        }
                    });
                };
            })(this);
            self.msg = (function (_this) {
                return function (msg) {
                    return layer.msg(msg);
                };
            })(this);
            self.info = (function (_this) {
                return function (msg) {
                    return parent.alertify.message(msg);
                };
            })(this);
            self.success = (function (_this) {
                return function (msg) {
                    return parent.alertify.success(msg);
                };
            })(this);
            self.error = (function (_this) {
                return function (msg, options) {
                    if (options) {
                        return parent.alertify.error(msg, options.wait, options.callback);
                    } else {
                        return parent.alertify.error(msg);
                    }
                };
            })(this);
            self.warning = (function (_this) {
                return function (msg) {
                    return parent.alertify.warning(msg);
                };
            })(this);
            self.show = (function (_this) {
                return function (selectorOrContent, options, fnClose) {
                    var content;
                    options = $.extend({}, self.options, options);
                    options.size = self.getSize(options.size);
                    content = selectorOrContent.indexOf('#') === 0 ? $(selectorOrContent) : selectorOrContent;
                    return parent.layer.open({
                        type: 1,
                        shade: 0.3,
                        title: options.title,
                        shadeClose: options.shadeClose,
                        area: options.size,
                        content: $(content).clone().html(),
                        end: function () {
                            if (fnClose) {
                                fnClose(self.getDialogResult());
                            }
                            return self.setDialogResult(null);
                        }
                    });
                };
            })(this);
            self.iframe = (function (_this) {
                return function (url, options, fnClose) {
                    options = $.extend({}, self.options, options);
                    options.size = self.getSize(options.size);
                    var index = parent.layer.open({
                        type: 2,
                        title: options.title,
                        shadeClose: options.shadeClose,
                        shade: 0.3,
                        maxmin: options.maxmin,
                        area: options.size,
                        content: url,
                        end: function () {
                            if (fnClose) {
                                fnClose(self.getDialogResult());
                            }
                            return self.setDialogResult(null);
                        }
                    });
                    if (options.fullScreen)
                        parent.layer.full(index);
                };
            })(this);
            self.loading = function (selector, options) {
                var eleLoading;
                options = $.extend({}, {
                    text: "正在加载..."
                }, options);
                if (selector) {
                    eleLoading = $('<div class="bn-dialog-loading" style="position:absolute;top:0;left:0;text-align:center;"><i class="fa fa-spin fa-spinner"></i> <span>' + options.text + '</span></div>');
                    $(selector).each(function () {
                        var h;
                        if ($(this).find('.bn-dialog-loading').length === 0) {
                            h = eleLoading.clone();
                            $(this).css('position', 'relative');
                            h.width($(this).outerWidth());
                            h.height($(this).outerHeight());
                            h.css('padding-top', h.height() / 2 - 10);
                            return $(this).append(h);
                        } else {
                            return $(this).find('.bn-dialog-loading').show();
                        }
                    });
                } else {
                    layer.load(0, {
                        shade: [0.3, '#fff']
                    });
                }
            };
            self.loaded = function (selector) {
                if (selector) {
                    $(selector).each(function () {
                        $(this).children().show();
                        return $(this).find('.bn-dialog-loading').remove();
                    });
                } else {
                    layer.closeAll('loading');
                }
            };
            self.close = function (dialogResult, obj) {
                var index;
                if (typeof dialogResult !== 'undefined') {
                    self.setDialogResult(dialogResult);
                }
                if (typeof obj !== 'undefined') {
                    self.setDialogResultObject(obj);
                }
                index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            };
            self.closeAll = function (dialogResult) {
                if (typeof dialogResult !== 'undefined') {
                    self.setDialogResult(dialogResult);
                    self.setDialogResultObject(null);
                }
                return setTimeout(function () {
                    layer.closeAll();
                    return parent.layer.closeAll();
                }, 200);
            };
            /**
             * 自定义关闭事件,增加回调避免窗口关闭导致对话框无法关闭的情况
             */
            self.customCloseAll = function (dialogResult, afterCallBack) {
                if (typeof dialogResult !== 'undefined') {
                    self.setDialogResult(dialogResult);
                }
                return setTimeout(function () {
                    layer.closeAll();
                    var result = parent.layer.closeAll();
                    afterCallBack();
                    return result;
                }, 200);
            };
            self.closeAllDialog = function (dialogResult) {
                if (typeof dialogResult !== 'undefined') {
                    self.setDialogResult(dialogResult);
                }
                return setTimeout(function () {
                    layer.closeAll('dialog');
                    return parent.layer.closeAll('dialog');
                }, 200);
            };
            return self;
        };
        d.dialog = Dialog({
            title: "系统",
            btns: ["确定", "取消"]
        });
    })(window);

}).call(this);
