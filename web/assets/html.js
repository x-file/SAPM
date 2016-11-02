/**
 * Created by Administrator on 2016/6/10.
 */
//摄像机ID
var camId;
//需要添加播放器的div的ID
var divId;
//播放器的默认高度
var height = 390;
//页面上最多可以放多少个播放器(格子数),默认为4格，即4个视频
var videoCount = 4;
//当前选中的div
var selectedDiv = 0;
//当前选中div中的视频id
var selectedCamId = 0;
//景区ID
var sceneryid = 0;

var rtmp = 'rtmp://192.168.1.138:1935/live...vhost...players/demo';
var video = [''];
//播放器配置参数
var flashvars;

function flashVars(rtmp) {
    flashvars = {
        f: rtmp,
        c: 0,
        p: 1,
        b: 0
    };
}


$(function () {
    //调用给播放器所在div添加红色边框的函数
    $.redborder();


    //控制摄像机运动
    $("button[id^='ctrl']").mousedown(function () {
        if( $("#textId3").val()!=0 &&  $("#textId2").val()!=0){
            $.ajax({
                type: "POST",
                url: '/monitor/ctrl',
                data: "sceneryId=" + $("#textId3").val() + "&camName=" + $("#textId2").val() + "&command=" + $(this).attr("id")
            });
        }else{
            alert("没有选中摄像点!")
        }
    });
    //鼠标抬起停止控制
    $("button[id^='ctrl']").mouseup(function () {
        if( $("#textId3").val()!=0 &&  $("#textId2").val()!=0) {
            $.ajax({
                type: "POST",
                url: '/monitor/ctrl',
                data: "sceneryId=" + $("#textId3").val() + "&camName=" + $("#textId2").val() + "&command=ctrl_stop"
            });
        }
    });

    //关闭直播
    $("#close").click(function () {
        $.ajax({
            type: "POST",
            url: '/monitor/close'
        });
        this.close();
    });

    //不同播放页面的切换4/6/9格
    $("button[id^='btn']").click(function () {
        //重置页面参数
        $.resetVar();
        if ($(this).attr("id") == "btn1") {
            //播放器高度
            height = 800;
            //将复选框都设为false
            $("input[name='cbox']").attr("checked", false);
            $("#main").html(
                '<!-- video 1 row -->' +
                '<div class="row" style="height: 100%">' +
                '<div id="video1" data-video="0" data-sceneryid="0" class="col-xs-12" style="color:#ffffff;padding:0px;height: 100%">video1</div>' +
                '</div>');
        } else if ($(this).attr("id") == "btn4") {
            //播放器高度
            height = 390;
            //将复选框都设为false
            $("input[name='cbox']").attr("checked", false);
            $("#main").html(
                '<!-- video 1/2 row -->' +
                '<div class="row" style="height: 50%">' +
                '<div id="video1" data-video="0" data-sceneryid="0" class="col-xs-6" style="color:#ffffff;padding:0;height: 100%">video1</div>' +
                '<div id="video2" data-video="0" data-sceneryid="0" class="col-xs-6" style="color:#ffffff;padding:0;height: 100%">video2</div>' +
                '</div>' +
                '<!-- video 3/4 row -->' +
                '<div class="row" style="height: 50%">' +
                '<div id="video3" data-video="0" data-sceneryid="0" class="col-xs-6" style="color:#ffffff;padding:0;height: 100%">video3</div>' +
                '<div id="video4" data-video="0" data-sceneryid="0" class="col-xs-6" style="color:#ffffff;padding:0;height: 100%">video4</div>' +
                '</div>');
        } else if ($(this).attr("id") == "btn6") {
            //播放器高度
            height = 260;
            //将复选框都设为false
            $("input[name='cbox']").attr("checked", false);
            $("#main").html(
                '<!-- video 1 row -->' +
                '<div class="row" style="height: 66.6%">' +
                '<div id="video1" data-video="0" data-sceneryid="0" class="col-xs-8" style="color:#ffffff;padding:0;height: 100%">video1</div>' +
                '<div class="col-xs-4" style="padding:0;height: 100%">' +
                '<!-- video 2/3 row -->' +
                '<!-- video2 -->' +
                '<div id="video2" data-video="0" data-sceneryid="0" style="color:#ffffff;padding:0;height: 50%">video2</div>' +
                '<!-- video3 -->' +
                '<div id="video3" data-video="0" data-sceneryid="0" style="color:#ffffff;padding:0;height: 50%;">video3</div>' +
                '</div>' +
                '</div>' +
                '<!-- video 4/5/6 row -->' +
                '<div class="row" style="height: 33.3%">' +
                '<div id="video4" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video4</div>' +
                '<div id="video5" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video5</div>' +
                '<div id="video6" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video6</div>' +
                '</div>');
        } else if ($(this).attr("id") == "btn9") {
            //播放器高度
            height = 260;
            //将复选框都设为false
            $("input[name='cbox']").attr("checked", false);
            $("#main").html(
                '<!-- video 1/2/3 row -->' +
                '<div class="row" style="height: 33.3%">' +
                '<div id="video1" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video1</div>' +
                '<div id="video2" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video2</div>' +
                '<div id="video3" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video3</div>' +
                '</div>' +

                '<!-- video 4/5/6 row -->' +
                '<div class="row" style="height: 33.3%">' +
                '<div id="video4" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video4</div>' +
                '<div id="video5" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video5</div>' +
                '<div id="video6" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video6</div>' +
                '</div>' +

                '<!-- video 7/8/9 row -->' +
                '<div class="row" style="height: 33.3%">' +
                '<div id="video7" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video7</div>' +
                '<div id="video8" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video8</div>' +
                '<div id="video9" data-video="0" data-sceneryid="0" class="col-xs-4" style="color:#ffffff;padding:0;height: 100%;">video9</div>' +
                '</div>');
        }
        $.redborder();//调用给播放器所在div添加红色边框的函数


    });


});

//点击播放窗口，给播放器所在div添加边框
$.redborder = function () {
    $("div[id^='video']").addClass("video-border");
    //点击播放器将边框颜色改为红色
    $("div[id^='video']").mousedown(function () {
        if ($(this).attr("id") == selectedDiv) {
            $("div[id^='video']").removeClass("video-border-color-red")
            $.resetVar();
        } else {
            //选中播放窗口
            $("div[id^='video']").removeClass("video-border-color-red")
            $("#" + $(this).attr("id")).addClass("video-border-color-red");

            //当前选中div的ID
            selectedDiv = $(this).attr("id");
            //当前选中的摄像机的ID
            selectedCamId = $(this).attr("data-video");
            //景区ID
            sceneryid = $(this).attr("data-sceneryid");


        }
        $("#textId").val(selectedDiv);
        $("#textId2").val(selectedCamId);
        $("#textId3").val(sceneryid);

        if($("#textId2").val()!=0 && $("#textId3").val()!=0){
            $.ajax({
                type: "POST",
                url: '/monitor/choose',
                data: "sceneryId=" + $("#textId3").val() + "&camName=" + $("#textId2").val()
            });
        }


    });


    //切换景区
    $("#scenerySelect").change(function () {
        //alert( $(this).find("option:selected").text()+"--"+$(this).val());
        $("#cam").empty();//切换景区时清空列表
        $.ajax({
            type: "POST",
            url: '/monitor/cam',
            data: "sceneryId=" + $(this).val(),
            dataType: "json",
            success: function (data) {
                //alert(JSON.stringify(data));
                var cams = (data.cams).split(",");
                //alert("分割后：" + cams);
                for (var i = 0; i < cams.length; i++) {
                    if (cams[i] != null && cams[i] != "") {
                        $("#cam").prepend(
                            '<div class="checkbox">' +
                            '<label><input name="cbox" type="checkbox" id="' + cams[i] + '">' + cams[i] + '</label>' +
                            '</div>');
                    }

                }
                //checkBox点击事件
                checkBoxClick();

            },
            error: function () {
                alert("ERROR");
            }
        });
    });

}


//重置页面参数
$.resetVar = function () {
    //当前选中的div
    selectedDiv = 0;
    //当前选中div中的视频id
    selectedCamId = 0;
    //景区ID
    sceneryid = 0;
}

function checkBoxClick() {
    //复选框点击事件
    $('input[type=checkbox]').click(function () {
        //摄像机ID,复选框的value值
        camId = $(this).attr("id");
        //景区ID
        sceneryid = $("#scenerySelect").val();
        //1.勾选复选框
        if ($(this).prop('checked')) {

            //2.获取视频应该再哪个div中打开
            //3.判断是否“选中”div窗口，(选中即selectedDiv != 0,未选中==0）
            if (selectedDiv != 0) {
                //目标div
                divId = selectedDiv;
                //点击复选框的时候，把复选框的值(camId) 赋给视频所在的div的data-video属性
                $("#" + divId).attr("data-video", camId);
                //景区ID赋给视频所在的div的data-video属性
                $("#" + divId).attr("data-sceneryid", sceneryid);


                //在选中的播放窗口切换视频的时候，将上一个视频的复选框取消勾选
                if (selectedCamId != camId) {
                    $("#" + selectedCamId).attr("checked", false);
                    selectedCamId = camId;
                }
                //4.如果没有“选中”div窗口，则计算按顺序应该在哪个div中打开，需要判断显示的视频是否超过当前视频总数
            } else {
                var result = new Array();
                //获取选中的checkbox,计算有几个被选中
                $('input[name="cbox"]:checked').each(function () {
                    result.push($(this).val());
                });
                var str = result.join(',');
                if (result.length > videoCount) {
                    alert('只能选择' + videoCount + '路直播！');
                    return false;
                } else {
                    var div = $("div[data-video='0']");
                    //目标div
                    divId = div.attr("id");
                    //点击复选框的时候，把复选框的值(camId) 赋给视频所在的div的data-video属性
                    $("#" + divId).attr("data-video", camId);
                    //景区ID赋给视频所在的div的data-video属性
                    $("#" + divId).attr("data-sceneryid", sceneryid);
                }
            }


            //$("#textId").val(divId);
            //$("#textId2").val(camId);
            //$("#textId3").val(sceneryid);


            var videoNum = $("div[id^='video']").length;
            //如果播放页面可以显示视频的总数量，height为播放器的高度
            switch (videoNum) {
                case 1:
                    //最多可放4格视频窗口
                    videoCount = 1;
                    height = 800;
                    break;
                case 4:
                    //最多可放4格视频窗口
                    videoCount = 4;
                    break;
                case 6:
                    //最多可放6格视频窗口,调整第一个播放器的大小(第一个video1的高度为524)
                    videoCount = 6;
                    if (divId == 'video1')
                        height = 524;
                    else
                        height = 260
                    break;
                case 9:
                    //最多可放9格视频窗口
                    videoCount = 9;
                    break;
            }


            //获取摄像机能力
            $.ajax({
                type: "POST",
                url: '/monitor/state',
                data: "camName=" + camId,
                dataType: "json",
                success: function (data) {
                    //                alert("--"+JSON.stringify(data));
                    //                alert(data.cam.rtmp);
                    flashVars(data.cam.rtmp);
                    CKobject.embed('/assets/ckplayer/ckplayer.swf', divId, 'ckplayer_' + divId, '100%', '100%', false, flashvars, video);
                },
                error: function () {
                    alert("ERROR");
                }
            });


        } else {
            //取消勾选后将视频从页面试去除，并将data-video、data-sceneryid的值赋为0
            $("div[data-video='" + camId + "']").each(function () {
                $("#" + ($(this).attr("id"))).empty();
                $(this).attr("data-video", 0);
                $(this).attr("data-sceneryid", 0);
                return false;
            });


        }


    });
}

