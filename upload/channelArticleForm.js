var channelArticleForm = {
    initForm: function (ue) {
        var id = $.fn.getString("id");
        var channelId = $.fn.getString("channelId");

        var copy = $.fn.getString("copy");

        $.fn.ajax(platform.config.baseUrl + "cms/admin/channelArticle/get/" + id + "/" + channelId, {}, function (data) {

            if (copy) {
                data.id = null;
            }
            for (key in data) {
                $("#" + key).val(data[key]);
            }
            $("#addTime").val(new Date(data["addTime"]).format("yyyy-MM-dd  hh:mm:ss"));
            var url = platform.config.baseUrl + data["imgUrl"];
            $("#img_box").html("<img src='" + url + "' height='60' width='60'/>");
            ue.ready(function () {
                ue.setContent(data['content']);

            });
            // bind dictionary
            $('[data-toggle="dictionary"]').each(function () {

                $(this).select2('val', data[$(this).attr("name")]);
            });

            $('input[name="recommendType"]').each(function () {
                $(this).attr('checked', Boolean(data[($(this).attr("value"))]))
            })
            // $.fn.ajax(platform.config.baseUrl+"/cms/admin/articleAttributeField/listAll",{},function (data) {
            //     data = dataTransfer(data,'title')
            //     $(".select-ajax-data").select2({
            //         data: data
            //     })
            // })

        });

    },
    save: function () {
        console.log(channelArticleForm.validator())
        if (channelArticleForm.validator()) {
            var url;
            if ($("#id").val()) {
                url = platform.config.baseUrl + 'cms/admin/channelArticle/edit';
            } else {
                url = platform.config.baseUrl + 'cms/admin/channelArticle/add';
            }
            var formData = $("#addChannelArticleForm").serializeObject();
            var a = $("#addChannelArticleImgForm").serializeArray();
            $.each(a, function () {
                if (formData[this.name]) {
                    if (!formData[this.name].push) {
                        formData[this.name] = [formData[this.name]];
                    }
                    formData[this.name].push(this.value || '');
                } else {
                    formData[this.name] = [this.value];
                }
            });

            $.fn.ajax(url, formData,
                function (data) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            return false;
        }
    },
    selectArticleCategory: function () {
        var channelId = $.fn.getString("channelId");
        parent.layer.open({
            type: 2,
            area: ['400px', '500px'],
            title: parent.platform.i18n.prop("common.window.selectArticleCategory"),
            btn: [parent.platform.i18n.prop("button.confirm"), parent.platform.i18n.prop("button.cancel")],
            yes: function (index, layero) {
                var iframeWin = layero.find('iframe')[0];
                iframeWin.contentWindow.selectArticleCategoryForm.callback = function (selectedItem) {
                    $("#categoryId").val(selectedItem.id);
                    $("#categoryName").val(selectedItem.title);
                }
                iframeWin.contentWindow.selectArticleCategoryForm.save();
            },
            success: function (layero, index) {
                $(layero).find("iframe")[0].contentWindow.selectedArticleCategory.value = $("#categoryId").val();
            },
            end: function () {
            },
            content: [platform.config.baseUrl + "admin/static/cms/articleCategory/selectArticleCategoryForm.html?channelId=" + channelId, "no"]
        })
    },
    uploadImgUrl: function () {
        $.ajaxFileUpload({
            url: platform.config.baseUrl + "cms/admin/file/upload/", //服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: "file", //文件上传域的ID
            dataType: "json", //返回值类型 一般设置为json
            enctype: "multipart/form-data",//注意一定要有该参数
            success: function (data, status)  //服务器成功响应处理函数
            {
                // alert(data);       //data是从服务器返回来的值
                // alert('上传图片成功!图片地址' + platform.config.baseUrl + "/upload/" + data);
                $("#imgUrl").attr("value", "/upload/" + data);
                var url = platform.config.baseUrl + "/upload/" + data;
                $("#img_box").html("<img src='" + url + "' height='60' width='60'/>");
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                console.log(e);
            }
        })
    },
    uploadMutliImgUrl: function () {
        $.ajaxFileUpload({
            url: platform.config.baseUrl + "cms/admin/file/upload/", //服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: "fileAll", //文件上传域的ID
            dataType: "json", //返回值类型 一般设置为json
            enctype: "multipart/form-data",//注意一定要有该参数
            success: function (data, status)  //服务器成功响应处理函数
            {
                var value = "/upload/" + data;
                $("#addChannelArticleImgForm").append("<input class='form-control' name='imgUrls' value='" + value + "' type='hidden'/>");
                var url = platform.config.baseUrl + "/upload/" + data;
                $("#img_boxs").append("<img src='" + url + "' height='60' width='60'/>");
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                console.log(e);
            }
        })
    },
    uploadExcel: function () {
        $.ajaxFileUpload({
            url: platform.config.baseUrl + "cms/admin/file/parse", //服务器端请求地址
            fileElementId: "myfile", //文件上传域的ID
            dataType: "json", //返回值类型 一般设置为json
            enctype: "multipart/form-data",//注意一定要有该参数
            success: function (data, status)  //服务器成功响应处理函数
            {
                console.log(data.result);
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                console.log(e);
            }
        })
    },
    validator: function () {
        return $("#addChannelArticleForm").validate({
            rules: {}
        }).form();
    }
};

function creationComplete() {



    // $(".upload-img").InitUploader({ filesize: "<%=sysConfig.imgsize %>", sendurl: "../../tools/upload_ajax.ashx", swf: "../../scripts/webuploader/uploader.swf", filetypes: "<%=sysConfig.fileextension %>" });


    $(".upload-album").InitUploader({
        imageOnly:true,
        btntext: "批量上传图片",
        multiple: true,
        water: true,
        thumbnail: true,
        sendurl: platform.config.baseUrl + "resources/upload/images",
        swf: "../../plugins/webuploader/uploader.swf",
        filetypes:"jpg,png,gif"
    });

    $(".upload-attach").InitUploader({
        imageOnly:false,
        btntext: "批量上传附件",
        multiple: true,
        sendurl:platform.config.baseUrl + "resources/upload/files",
        swf: "../../plugins/webuploader/uploader.swf",
        filetypes:"doc,docx,xls,html"
    });

    $(":checkbox").labelauty({
        icon: false
    });
    var ue = UE.getEditor('content', {
        autoHeightEnabled: true,
        autoFloatEnabled: true
    });


    if ($.fn.getString("id") != null && $.fn.getString("channelId") != null) {
        channelArticleForm.initForm(ue);
    }
    if ($.fn.getString("channelId") != null) {
        var channelId = $.fn.getString("channelId");
        $('#channelId').val(channelId);
    }


    $("#fileU").uploadify({
        "uploader": platform.config.baseUrl + "cms/admin/file/one/",
        "method": "post",
        "progressData": "percentage",
        "swf": platform.config.baseUrl + "/admin/static/plugins/uploadify/uploadify.swf",
        "buttonText": "选择要上传的文件",
        //  "auto":false,
        "multi": true,
        "fileSizeLimit": "10000000KB",
        "queueSizeLimit": 5,
        "successTimeout": 600,
        "onUploadSuccess": function (file, data, response) {
            alert('The file ' + file.name
                + ' was successfully uploaded with a response of '
                + response + ':' + data);
        },
        "onUploadError": function (file, errorCode, errorMsg,
                                   errorString) {
            alert('The file ' + file.name
                + ' could not be uploaded: ' + errorString);
        },
        "onSelectError": function () {
            alert('The file ' + file.name
                + ' returned an error and was not added to the queue.');
        }
    });

    $("#image").uploadify({
        "uploader": platform.config.baseUrl + "cms/admin/file/uploadifyImage/",
        "method": "post",
        "progressData": "percentage",
        "swf": platform.config.baseUrl + "/admin/static/plugins/uploadify/uploadify.swf",
        "buttonText": "选择要上传的图片",
        "fileTypeExts": "*.jpg;*.png",
        "multi": true,
        "fileSizeLimit": "10000000KB",
        "queueSizeLimit": 5,
        "successTimeout": 600,
        "onUploadSuccess": function (file, data, response) {
            /*alert('The file ' + file.name
             + ' was successfully uploaded with a response of '
             + response + ':' + data);*/
            var value = data;
            $("#addChannelArticleImgForm").append("<input class='form-control' name='imgUrls' value='" + value + "' type='hidden'/>");

        },
        "onUploadError": function (file, errorCode, errorMsg,
                                   errorString) {
            alert('The file ' + file.name
                + ' could not be uploaded: ' + errorString);
        },
        "onSelectError": function () {
            alert('The file ' + file.name
                + ' returned an error and was not added to the queue.');
        }
    });

    $("#files").uploadify({
        "uploader": platform.config.baseUrl + "cms/admin/file/uploadifyFile/",
        "method": "post",
        "progressData": "percentage",
        "swf": platform.config.baseUrl + "/admin/static/plugins/uploadify/uploadify.swf",
        "buttonText": "选择要上传的文件",
        "multi": true,
        "fileSizeLimit": "10000000KB",
        "queueSizeLimit": 5,
        "successTimeout": 600,
        "onUploadSuccess": function (file, data, response) {
            /*alert('The file ' + file.name
             + ' was successfully uploaded with a response of '
             + response + ':' + data);*/
            var value = data;
            $("#addChannelArticleImgForm").append("<input class='form-control' name='fileUrls' value='" + value + "' type='hidden'/>");

        },
        "onUploadError": function (file, errorCode, errorMsg,
                                   errorString) {
            alert('The file ' + file.name
                + ' could not be uploaded: ' + errorString);
        },
        "onSelectError": function () {
            alert('The file ' + file.name
                + ' returned an error and was not added to the queue.');
        }
    });
};
function dataTransfer(data, flag) {
    if (!flag) return data;
    var arr = [];
    $.each(data, function (i, ele) {
        var obj = {};
        obj.id = ele.id;
        obj.text = ele[flag];
        arr.push(obj);
    })
    return arr;
}
