(function($) {

    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var path = pathName.substr(0,index+1);

	$.main = {

        /**
         * 注册操作
         */
        register : function () {

            var username = $("#username").val();
            var password = $("#password").val();
            var nickname = $("#nickname").val();

            var usernameFlag = $("#username").attr("flag");
            var pwdFlag = $("#password").attr("flag");
            var rpwdFlag = $("#repassword").attr("flag");
            var nicknameFlag = $("#nickname").attr("flag");

            if (usernameFlag=="0" && pwdFlag=="0" && rpwdFlag=="0" && nicknameFlag=="0") {
                $.ajax({
                    type : "post",
                    dataType : "json",
                    url : path+"/register",
                    data : {"username":username,"password":password,"nickname":nickname},
                    success : function (result) {
                        if (result.flag=="1") {
                            //注册成功
                            window.location.href = path+"/successRegist";
                        } else {
                            //注册失败
                        }
                    },
                    error : function () {
                        alert("网络异常！");
                    }
                });
            }

        },

        /**
         * 登录时校验账号是否为空
         */
        loginCheckUsername : function () {
            var cueText=$("#username").parent().parent().find(".cueText");
            var username = $.trim($("#username").val());
            if (username=="") {
                $("#username").css("border","1px solid red");
                cueText.find("span").html("登录账号不能为空");
                cueText.show();
                $("#username").siblings("span[name='error']").show();
                $("#username").siblings("span[name='right']").hide();
                $("#username").attr("flag","1");
            } else {
                cueText.hide();
                $("#username").css("border","1px solid #b0b5bd");
                $("#username").siblings("span[name='error']").hide();
                $("#username").siblings("span[name='right']").show();
                $("#username").attr("flag","0");
            }
        },

        /**
         * 登录时校验密码是否为空
         */
        loginCheckPwd : function () {
            var cueText=$("#password").parent().parent().find(".cueText");
            var password = $.trim($("#password").val());
            if (password=="") {
                $("#password").css("border","1px solid red");
                cueText.find("span").html("登录密码不能为空");
                cueText.show();
                $("#password").siblings("span[name='error']").show();
                $("#password").siblings("span[name='right']").hide();
                $("#password").attr("flag","1");
            } else {
                cueText.hide();
                $("#password").css("border","1px solid #b0b5bd");
                $("#password").siblings("span[name='error']").hide();
                $("#password").siblings("span[name='right']").show();
                $("#password").attr("flag","0");
            }
        },

        /**
         * 注册时，校验账号是否已存在
         */
        checkLegal : function () {
            var cueText=$("#username").parent().parent().find(".cueText");
            var username = $.trim($("#username").val());
            var flag;

            if (tmpField==username) {
                flag = "false";
            } else {
                flag = "true";
            }

            tmpField = username;
            if (username!="" && flag=="true") {
                $("#loadingWrap").show();
                $.ajax({
                    type : "post",
                    dataType : "json",
                    url : path+"/checkLegal",
                    data : {"username":username},
                    success : function (result) {
                        if (result.rst=="1") {
                            //唯一
                            cueText.hide();
                            $("#username").css("border","1px solid #b0b5bd");
                            $("#username").siblings("span[name='error']").hide();
                            $("#username").siblings("span[name='right']").show();
                            $("#username").attr("flag","0");
                            $("#loadingWrap").hide();
                        } else if (result.rst=="0") {
                            //已存在
                            $("#username").css("border","1px solid red");
                            cueText.find("span").html("账号已存在，请重新输入");
                            cueText.show();
                            $("#username").siblings("span[name='error']").show();
                            $("#username").siblings("span[name='right']").hide();
                            $("#username").attr("flag","1");
                            $("#loadingWrap").hide();
                        }
                    },
                    error : function () {
                        $("#loadingWrap").hide();
                        alert("网络异常！");
                    }
                });
            } else if (username=="") {
                //账号输入为空
                $("#username").css("border","1px solid red");
                cueText.find("span").html("请输入账号");
                cueText.show();
                $("#username").siblings("span[name='error']").show();
                $("#username").siblings("span[name='right']").hide();
                $("#username").attr("flag","1");
            }
        },

        /**
         * 密码校验
         */
        checkPwd : function () {
            var cueText=$("#password").parent().parent().find(".cueText");
            var password = $.trim($("#password").val());
            if (!$.common.validatePwd(password)) {
                $("#password").css("border","1px solid red");
                cueText.find("span").html("请输入6-16位字母、数字的组合");
                cueText.show();
                $("#password").siblings("span[name='error']").show();
                $("#password").siblings("span[name='right']").hide();
                $("#password").attr("flag","1");
            } else {
                cueText.hide();
                $("#password").css("border","1px solid #b0b5bd");
                $("#password").siblings("span[name='error']").hide();
                $("#password").siblings("span[name='right']").show();
                $("#password").attr("flag","0");
            }
        },

        /**
         * 确认密码校验
         */
        checkRPwd : function () {
            var cueText=$("#repassword").parent().parent().find(".cueText");
            var repassword = $("#repassword").val();
            var password = $.trim($("#password").val());

            if ($.trim(repassword)=="" || !$.common.validatePwd(repassword)) {
                $("#repassword").css("border","1px solid red");
                cueText.find("span").html("请输入6-16位字母、数字的组合");
                cueText.show();
                $("#repassword").siblings("span[name='error']").show();
                $("#repassword").siblings("span[name='right']").hide();
                $("#repassword").attr("flag","1");
            } else {
                if (password=="") {
                    $("#repassword").css("border","1px solid red");
                    cueText.find("span").html("请先输入密码，再确认密码");
                    cueText.show();
                    $("#repassword").siblings("span[name='error']").show();
                    $("#repassword").siblings("span[name='right']").hide();
                    $("#repassword").attr("flag","1");
                } else {
                    if (repassword!=password) {
                        $("#repassword").css("border","1px solid red");
                        cueText.find("span").html("两次输入的密码不一致，请重新输入");
                        cueText.show();
                        $("#repassword").siblings("span[name='error']").show();
                        $("#repassword").siblings("span[name='right']").hide();
                        $("#repassword").attr("flag","1");
                    } else {
                        cueText.hide();
                        $("#repassword").css("border","1px solid #b0b5bd");
                        $("#repassword").siblings("span[name='error']").hide();
                        $("#repassword").siblings("span[name='right']").show();
                        $("#repassword").attr("flag","0");
                    }
                }
            }

        },

        /**
         * 校验昵称
         */
        checkName : function () {
            var nickName = $.trim($("#nickname").val());
            var cueText=$("#nickname").parent().parent().find(".cueText");
            if (nickName=="") {
                $("#nickname").css("border","1px solid red");
                cueText.find("span").html("请输入昵称");
                cueText.show();
                $("#nickname").siblings("span[name='error']").show();
                $("#nickname").siblings("span[name='right']").hide();
                $("#nickname").attr("flag","1");
            } else {
                cueText.hide();
                $("#nickname").css("border","1px solid #b0b5bd");
                $("#nickname").siblings("span[name='error']").hide();
                $("#nickname").siblings("span[name='right']").show();
                $("#nickname").attr("flag","0");
            }
        },

        /**
         * 登录校验
         */
        loginCheck : function () {
            var username = $("#username").val();
            var password = $("#password").val();

            var userText=$("#username").parent().parent().find(".cueText");
            var pwdText=$("#password").parent().parent().find(".cueText");

            var usernameFlag = $("#username").attr("flag");
            var pwdFlag = $("#password").attr("flag");

            if (usernameFlag=="0" && pwdFlag=="0") {
                //开始登录操作
                $("#loadingWrap").show();
                $.ajax({
                    type : "post",
                    dataType : "json",
                    url : path+"/login",
                    data : {"username":username,"password":password},
                    success : function (result) {
                        if (result.flag=="1") {
                            //登录成功
                            window.location.href = path+"/toLogin";
                        } else {
                            //登录失败
                            if (result.flag=="2") {
                                //账号不存在
                                $("#username").css("border","1px solid red");
                                userText.find("span").html("登录账号不存在");
                                userText.show();
                                $("#username").siblings("span[name='error']").show();
                                $("#username").siblings("span[name='right']").hide();
                                $("#username").attr("flag","1");
                                $("#loadingWrap").hide();
                            } else {
                                //密码错误
                                $("#password").css("border","1px solid red");
                                pwdText.find("span").html("登录密码错误");
                                pwdText.show();
                                $("#password").siblings("span[name='error']").show();
                                $("#password").siblings("span[name='right']").hide();
                                $("#password").attr("flag","1");
                                $("#loadingWrap").hide();
                            }
                        }
                    },
                    error : function () {
                        $("#loadingWrap").hide();
                        alert("网络异常");
                    }
                });


            } else if (usernameFlag=="" && pwdFlag=="") {
                $("#username").css("border","1px solid red");
                userText.find("span").html("登录账号不能为空");
                userText.show();
                $("#username").siblings("span[name='error']").show();
                $("#username").siblings("span[name='right']").hide();
                $("#username").attr("flag","1");

                $("#password").css("border","1px solid red");
                pwdText.find("span").html("登录密码不能为空");
                pwdText.show();
                $("#password").siblings("span[name='error']").show();
                $("#password").siblings("span[name='right']").hide();
                $("#password").attr("flag","1");
            }

        },

        /**
         * 添加文章
         */
        addArticle : function () {
            var flag = 0;
            //获取标题
            var title = $("#title").val();
            //获取ckeditor富文本的内容(html内容)
            var text = CKEDITOR.instances.edi.getData();

            if ($.trim(title)=="") {
                flag = flag+1;
                alert("请输入标题...");
            } else {
                if ($.trim(text)=="") {
                    flag = flag+1;
                    alert("请输入内容...");
                }
            }

            if (flag==0) {
                $("#loadingWrap").show();
                $.ajax({
                    type : "post",
                    dataType : "json",
                    url : path+"/addArticleConfirm",
                    data : {"title":title,"text":text},
                    success : function (data) {
                        if (data.result!=null) {
                            //添加成功--重定向到个人首页，查询所有文章
                            window.location.href = path+"/showArticle?id="+data.result;
                            $("#loadingWrap").hide();
                        }
                    },
                    error : function () {
                        $("#loadingWrap").hide();
                        alert("网络异常！");
                    }
                });
            }

        },

        /**
         * 保存草稿
         * 保存时校验--标题、内容不能为空
         */
        saveCraft : function () {
            var flag = 0;
            //获取标题
            var title = $("#title").val();
            //获取ckeditor富文本的内容(html内容)
            var text = CKEDITOR.instances.edi.getData();
            //用户主键
            var userId = $("#userId").val();

            if ($.trim(title)=="") {
                flag = flag+1;
                alert("请输入标题...");
            } else {
                if ($.trim(text)=="") {
                    flag = flag+1;
                    alert("请输入内容...");
                }
            }

            if (flag==0) {
                $("#loadingWrap").show();
                $.ajax({
                    type : "post",
                    dataType : "json",
                    url : path+"/saveCraft",
                    data : {"title":title,"text":text,"userId":userId},
                    success : function (data) {
                        if (data.result=="1") {
                            //添加成功--弹框提示：保存成功+保存时间
                            $("#loadingWrap").hide();
                            alert("保存成功")
                        }
                    },
                    error : function () {
                        $("#loadingWrap").hide();
                        alert("网络异常！");
                    }
                });
            }

        },

        /**
         * 发表评论
         */
        criCommit : function () {
            var flag = 0;
            //获取评论内容
            var text = CKEDITOR.instances.edi.getData();
            if ($.trim(text)=="") {
                flag = flag+1;
                alert("评论内容不能为空...")
            }

            //文章id
            var aid = $("#aid").val();

            if (flag==0) {
                $("#loadingWrap").show();
                //发表评论
                $.ajax({
                    type : "post",
                    dataType : "json",
                    url : path+"/criCommit",
                    data : {"aid":aid,"text":text},
                    success : function (result) {
                        if (result.aid!=null) {
                            //评论发表成功--重定向到该篇文章，并将评论按照时间倒序排序显示
                            window.location.href = path+"/showArticle?id="+result.aid;
                            $("#loadingWrap").hide();
                        }
                    },
                    error : function () {
                        $("#loadingWrap").hide();
                        alert("网络异常！");
                    }
                });
            }

        },

	};
})(jQuery);