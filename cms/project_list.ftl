<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>${siteChannel.title} - ${site.name}</title>
    <meta name="keywords" content="${site.seoKeyword}"/>
    <meta name="description" content="${site.seoDescription}"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/css/icon/font-awesome/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/admin/static/plugins/select2/css/select2.min.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/css/projectlist.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/scripts/artdialog/ui-dialog.css" />
    <script type="text/javascript" charset="utf-8" src="${base}/res/templates/main/scripts/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/res/templates/main/scripts/artdialog/dialog-plus-min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/res/templates/main/js/jquery.marquee.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/res/templates/main/js/common.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/admin/static/plugins/select2/js/select2.min.js"></script>
</head>
<body id="${siteChannel.name}">
<!--页面头部-->
<#include "/templates/main/_header.ftl">
<!--/页面头部-->
<!--当前位置-->
<div class="bread-crumb">
    <div class="section location">
        <span>当前位置：</span>
        <a href="${base}">首页</a> &gt;
        <a href="${base}/channel/${siteChannel.name}">${siteChannel.title}</a>
    </div>
</div>
<div class="pro_title">
    <div class="pro_select_wrap">
        <form method="post" action="" name="searchForm">
            <input type="hidden" name="categoryId">
            <input type="hidden" name="fstate">
        </form>
        <div class="title">项目状态:</div>
        <div class="select_wrap_search">
            <select name="projectStatus" id="projectStatus" class="w80">
                <option value="">全部</option>
            </select>
        </div>
        <div class="title">项目类别:</div>
        <div class="select_wrap_search">
            <select name="categoryId" id="categoryId" class="w100">
                <option value="">全部</option>
            </select>
        </div>
    </div>
    <div class="pro_se">
        <div class="pro_se3" style="width:260px;">
            <form method="post" action="" name="searchNameForm">
            <input type="text" class="pro_search" name="key_words" id="key_words" style="width:180px;" maxlength="40" value="${key_words}">
            </form>
                <input type="button" class="subm_btn" id="key_btn" value="搜索" onclick="searchByName();"></div>
    </div>
</div>
<!--/当前位置-->
<div class="pro_main box-section">
    <@projectlist  categoryId="${categoryId}" name="${key_words}" fstate="${fstate}" sort="t.create_time" order="desc" pageNumber="${pageNo}" pageSize="5" connectedSql=" 1=1 and t.state=2">
    <ul class="pro_ul" id="projectList_wrap">
            <#list projects.list as project>
                <li class="pro_li">
                    <div class="pro_li_img"><a href="${base}/project/show-${project.id}" >
                        <#if project.isHot == 1>
                            <i class="icon-real"></i>
                        </#if>

                        <img
                            src="${base}${project.imgUrl}"
                            height="120" border="0"></a></div>
                    <div class="pro_li_cen">
                        <div class="pro_sp1 font_bl"><a href="${base}/project/show-${project.id}"  class="titless">${project.name}</a>
                            <div class="clearfix"></div>
                        </div>
                        <div class="pro_cen_p">
                            <div class="pro_list_cen_left"><p class="pro_sp2">项目简介 </p></div>
                            <div class="pro_cen_right"><p class="pro_sp4">${project.info!project.name}</p></div>
                            <div class="clearfix"></div>
                        </div>
                        <p><span class="pro_sp2">招募资源</span><b class="money-s">${project.totalCount!"0"}</b>个</p>
                        <p><span class="pro_sp2">项目周期</span><span class="time-s">${project.startTime?string("yyyy-MM-dd")}</span> 至 <span class="time-s">${project.endTime?string("yyyy-MM-dd")}</span>
                        </p>
                        <p><span class="pro_sp2 sp_block">执 行 方  </span> <span class="pro_sp4 sp_block">${project.nickName!project.userName}</span></p>
                        <div class="clearfix"></div>
                    </div>
                    <div class="pro_li_jin pro_list_stat"><p class="pro_jindu_2">项目状态:
                        <#assign state="${project.state}"/>
                        <#if state == 0>
                            审核不通过
                        <#elseif state==1>
                            审核中
                        </#if></p>
                        <p class="pro-donate-stat">已有：<span>${project.curCount!"0"}</span>个&nbsp;&nbsp;<span></span>参与</p>
                        <div class="pro_tiao_2">
                            <div class="jindu_xm_2"><p class="jindu_xm_p_2"><span style="width:${project.countPercent?number?string("percent")}" class="istrue"></span></p>
                            </div>
                            <div class="pro_dis_2">${project.countPercent?number?string("percent")}</div>
                        </div>
                        <div class="wol_bto">
                            <a class="wol_btoa"
                               href="javascript:;" onclick="clickSubmit('${base}/projectUser/save?projectId=${project.id}&userId=${user.id}');"
                               pname="${project.name}" pid="34643"
                               fid="149"">我要参与</a></div>
                    </div>
                    <div class="clearfix"></div>
                </li>
            </#list>
    </ul>
    <!-- pro pages s-->
    <div class="common_pages" id="projectPages_wrap">
        <#import "/templates/main/pager.ftl" as q>
        <#if projects.count??>
            <@q.pager pageNo=projects.pageNumber pageSize=projects.pageSize recordCount=projects.count toURL="${base}/project/list" current=""/>
        </#if>

    </div>
        </@projectlist>
    <!-- pro pages e-->

</div> <!-- pro main e-->

<div class="clearfix"></div>
<!--页面底部-->
<#include "/templates/main/_footer.ftl">
<!--/页面底部-->
<script type="text/javascript">
    $(function () {
        $.fn.ajax('${base}/cms/orgCategory/listSelect',{},function (data) {
            var ndata = $.map(data, function (obj) {
                obj.text = obj.categoryName;
                obj.id = obj.id;
                return obj;
            });
            $("#projectStatus").select2({minimumResultsForSearch: -1});
            $("#categoryId").select2({minimumResultsForSearch: -1, data: ndata});

            $("#categoryId").val("${categoryId}").trigger('change')

        });


        $.fn.ajax('${base}/sys/dictionary/listSelect',{"groupName":"projectState"},function (data) {
            var ndata = $.map(data, function (obj) {
                obj.text = obj.name;
                obj.id = obj.value;
                return obj;
            });
            $("#projectStatus").select2({minimumResultsForSearch: -1,data: ndata});

            $("#projectStatus").select2("val","${fstate}");
        });


        $("#categoryId").on("select2:select", function (evt) {
            //这里是选中触发的事件
            //evt.params.data 是选中项的信息
            //获取ID
            var id = evt.params.data.id;
            search(id);
        });

        $("#projectStatus").on("select2:select", function (evt) {
            //这里是选中触发的事件
            //evt.params.data 是选中项的信息
            //获取ID
            var id = evt.params.data.id;
            searchByState(id);
        });


    });

    function search(id){
        var qForm=document.searchForm;
        qForm.categoryId.value=id;
        qForm.action="${base}/project/list";
        qForm.submit();
    }

    function searchByState(id){
        var qForm=document.searchForm;
        qForm.fstate.value=id;
        qForm.action="${base}/project/list";
        qForm.submit();
    }

    function searchByName(){
        var qForm=document.searchNameForm;
        qForm.key_words.value=$("#key_words").val();
        qForm.action="${base}/project/list";
        qForm.submit();
    }
</script>
</body>
</html>