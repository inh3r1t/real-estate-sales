(function (window) {

    var connectUrl = "/app/connect";

    window.WSClient = function (url) {
        this.connected = false;
        this.client = null;
        this.listeners = [];
        this.url = url;
    };

    WSClient.prototype.connect = function () {
        var self = this;
        if (this.url.length == 0) {
            this.connected = false;
            return;
        }
        try {
            if (window.WebSocket) {
                var ws = new SockJS(this.url.split(",")[0]);
                this.client = Stomp.over(ws);
            } else {
                this.client = Stomp.client(this.url.split(",")[1]);
            }
            var token = $.cookie("token");
            this.client.debug = false; // 关闭日志打印
            this.client.connect({},function () {
                self.connected = true;
                //self.client.send(connectUrl, {}, JSON.stringify({token: token}));
            },function () {
                self.connected = false;
            });
        } catch (e) {
            this.connected = false;
        }
    };

    WSClient.prototype.registerOnline = function (callBack) {
        var flag = setInterval(doSubscribe,1000);
        var self = this;
        var sending = false;
        function doSubscribe() {
            if (!self.connected || sending){
                return;
            }
            sending = true;
            self.client.subscribe("/meta/onlineCnt",function (message) {
                if (callBack){
                    callBack(message);
                }
                sending = false;
                clearInterval(flag);
            });
        }
    };

}(window));