// JavaScript Document
$(document).ready(function () {
    var _path = "/plugins/disk-manage/";
    //首先将#back-to-top隐藏
    $("#back-to-top").hide();
    //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
    $(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 100) {
                $("#back-to-top").fadeIn(1500);
            }
            else {
                $("#back-to-top").fadeOut(1500);
            }
        });
        //当点击跳转链接后，回到页面顶部位置
        $("#back-to-top").click(function () {
            $('body,html').animate({scrollTop: 0}, 500);
            return false;
        });
    });
    //点击视图切换图片
    $("#menu_list").toggle(
        function () {
            $(this).addClass("menu_right");
        },
        function () {
            $(this).removeClass("menu_right");
        }
    );
    $('#toMyShare').click(function () {
        $('#disk_nav_arrow').addClass('arrow_share');
    });
    $('#toMyComputer').click(function () {
        $('#disk_nav_arrow').removeClass('arrow_share');
    });

    $("body").on('click', '#file_root_dir,#file_prev_path,#currentPath a', function () {
        var nowId = $(this).attr('id');
        var nowPath = $('#' + nowId + ' input').val();
        if (nowPath)
            getFileDetail(nowPath);
    });
    //触发ajax内容事件
    $("body").on('dblclick', '#showContent .clickMe', function () {
        var nowId = $(this).attr('id');
        var nowPath = $('#' + nowId + ' input').val();
        if (nowPath)
            getFileDetail(nowPath);
    });
    //根据状态栏获取目录目录
    // $("body").on('click','.location_show.other', function () {
    //     $('#disk_path').html('<a href="javascript:void(0);" class="location_show" id="to_root_nav">我的电脑</a>');
    //     getFileDetail($(this).text());
    // });

    // $('#get_root_a').click(getRootDetail);
    // $("body").on('click','#to_root_nav', getRootDetail);

    //用tooltip显示文件信息
    //$('#showContent .fileDetail').tooltip();

    function getRootDetail() {
        // $('#showContent').before('<p id="reload_gif"><img src="' + _path + 'images/load.gif" width="25px"  height="25px"  align="absmiddle"/>&nbsp;&nbsp;正在加载……</p>');
        $.ajax({
            type: "post",
            url: "/file/iterateRoot",
            async: true,
            dataType: "JSON",
            success: function (data) {
                $('#reload_gif').remove();
                $('#showContent').html("");
                $('#disk_path').html('<a href="javascript:void(0);" class="location_show" id="to_root_nav">我的电脑</a>');
                $.each(data, function (i, item) {
                    if (item.diskName == 'C') {
                        $('#showContent').append('<a href="javascript:void(0);" class="clickMe" id="disk_' + i + '"><p><img src="' + _path + 'images/disk_c.png" class="disk_c_img" align="absmiddle"/>&nbsp;&nbsp;&nbsp;&nbsp;容量：' + item.diskSize + '&nbsp;&nbsp&nbsp;&nbsp;剩余空间：' + item.avilableSize + '<input type="hidden" value=' + item.diskPath + '/></p></a>');
                    } else {
                        $('#showContent').append('<a href="javascript:void(0);" class="clickMe" id="disk_' + i + '"><p ><img src="' + _path + 'images/disk_other.png" class="disk_c_img" align="absmiddle"/>&nbsp;&nbsp;&nbsp;&nbsp;容量：' + item.diskSize + '&nbsp;&nbsp;&nbsp;&nbsp;剩余空间：' + item.avilableSize + '<input type="hidden" value=' + item.diskPath + '/></p></a>');
                    }
                });
            }
        });
    }

    function getFileDetail(nowPath) {
        // $('#showContent').before('<p id="reload_gif"><img src="' + _path + 'images/load.gif" width="25px"  height="25px"  align="absmiddle"/>&nbsp;&nbsp;正在加载……</p>');
        $.ajax({
            type: "post",
            url: "/file/iterateDir",
            data: "path=" + nowPath,
            async: true,
            dataType: "JSON",
            success: function (data) {
                // $('#reload_gif').remove();
                $('#showContent').html("");
                if (data.prevPath) {
                    $('#disk_path #prevPath').html('<a href="javascript:void(0);" class="location_show clickMe" id="file_prev_path">返回上一级<input type="hidden" value="' + data.prevPath + '"></a>');
                } else {
                    $('#disk_path #prevPath').html('');
                }
                $('#disk_path #currentPath').html('<a href="javascript:void(0);" class="clickMe" id="file_current_path">当前路径：' + data.dirPath + '/<input type="hidden" value="' + data.dirPath + '/"></a>');
                $.each(data.files, function (n, file) {
                    if (file.fileType == 'DIR') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);" class="clickMe" id="file_' + n + '"><img src="' + _path + 'images/floder.png" title="' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><input type="hidden" value=' + file.filePath + '/><span class="file-tools"><i onclick="deleteDir(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i></a></span></div>');
                    }
                    else if (file.fileType == 'TXT' || file.fileType == 'JAVA' || file.fileType == 'PDF') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/txt.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'MP3' || file.fileType == 'WMA' || file.fileType == 'WAV' || file.fileType == 'MOD') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/music.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'HTML' || file.fileType == 'HTM' || file.fileType == 'JSP') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/html.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'RAR' || file.fileType == 'JAR' || file.fileType == 'ZIP') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/zip.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'DOC' || file.fileType == 'WPS' || file.fileType == 'DOCX') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/word.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'XLS' || file.fileType == 'XLSX') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/excel.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'PPT') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/ppt.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'EXE' || file.fileType == 'BAT') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/exe.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'BMP' || file.fileType == 'PNG' || file.fileType == 'GIF' || file.fileType == 'JPEG' || file.fileType == 'JPG') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/photo.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else if (file.fileType == 'RMVB' || file.fileType == 'MKV' || file.fileType == 'MP4' || file.fileType == 'AVI' || file.fileType == 'WMV' || file.fileType == '3GP') {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/movie.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                    else {
                        $('#showContent').append('<div class="fileDetail"><a href="javascript:void(0);"><img src="' + _path + 'images/other.png" title="大小：' + file.fileSize + '&#10;' + file.fileName + '"/><span title="' + file.fileName + '">' + file.fileName + '</span><span class="file-tools"><i onclick="deleteFile(\'' + data.dirPath + '/' + file.fileName + '\')">删除</i><i onclick="download(\'' + data.dirPath + '/' + file.fileName + '\')">下载</i></span></a></div>');
                    }
                });
            }
        });
    }

});

