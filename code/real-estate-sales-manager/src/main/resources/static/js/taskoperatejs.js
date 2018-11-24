/**
 * Created by feidengke on 2017/12/14.
 */
//全名+登录账号格式
function sysUsrName(fullname, username) {
    if (fullname != null && username != null) {
        return "<span>" + fullname + "</span>" + "<span class='input-tips'>[" + username + "]</span>";
    } else {
        return "";
    }
}
// 根据状态名称着色
function statusNameStyle(statusname) {
    /* 待分派(0), 待签收(3), 处理中(6), 处理结束(9), 已确认(12), 已中止(98), 已作废(99);*/
    var returnValue = "";
    switch (statusname) {
        case "待分派":
            returnValue = "background-color:#e16281;";
            break;
        case "待签收":
            returnValue = "background-color: #f74040;";
            break;
        case "处理中":
            returnValue = "background-color:#e78f4c;";
            break;
        case "处理结束":
            returnValue = "background-color:#b7a74e;";
            break;
        case "已确认":
            returnValue = "background-color:#6aad52;";
            break;
        case "已中止":
            returnValue = "background-color:#9b9b9b;";
            break;
        case "已作废":
            returnValue = "background-color:#f5604e;";
            break;
        default:
            returnValue = "background-color:#00ff22";
            break;
    }
    return "<span" + " style='width:50px;text-align: center; color: white; padding: 2px 5px 2px 5px;" + returnValue + "'>" + statusname + "</span>"
}

// 根据状态名称按钮类型判断是否显示
function showMenu(statusname, operatetype, endtime) {
    var returnValue = false;
    endtime = moment(endtime).format('YYYY/MM/DD HH:mm:ss ');
    endtime = new Date(endtime)
    var date = new Date();
    if (date > endtime) {
        returnValue = false;
        return returnValue;
    }
    switch (statusname) {
        case  "待分派":
            if (operatetype == "edit") {
                return true;
            }
            break;
        case "待签收":
            if (operatetype == "sign") {
                returnValue = true;
            }
            if (operatetype == "edit") {
                returnValue = true;
            }
            break;
        case "处理中":
            if (operatetype == "entryResults") {
                returnValue = true;
            }
            break;
        case "处理结束":
            if (operatetype == "entryResults") {
                returnValue = true;
            }
            if (operatetype == "confirm") {
                returnValue = true;
            }
            break;
        default:
            break
    }
    return returnValue;
}