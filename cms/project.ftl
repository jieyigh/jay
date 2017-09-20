<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>${siteChannel.title} - ${site.name}</title>
    <meta name="keywords" content="${site.seoKeyword}"/>
    <meta name="description" content="${site.seoDescription}"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/css/icon/font-awesome/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/css/project.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/res/templates/main/scripts/artdialog/ui-dialog.css" />

    <script type="text/javascript" charset="utf-8"
            src="${base}/res/templates/main/scripts/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/res/templates/main/scripts/artdialog/dialog-plus-min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/res/templates/main/js/common.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/res/templates/main/js/jquery.marquee.js"></script>

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
<!--/当前位置-->
<div class="main_info clearfix">
    <div class="main_left">
        <div class="home-donate-status" id="last_count_wrap">
            <@projectCount>
            <span class="h-d-s-t">平台项目统计：</span>
            <span class="h-d-s-n">审核中项目<a href="" class="a-num-1" title="点击查看所有审核中的项目" id="h_donate_s1">${projectcount.auditCount!"0"}</a>个</span>
            <span class="h-d-s-n">执行中项目<a href="" class="a-num-1" title="点击查看所有执行中的项目" id="h_donate_s2">${projectcount.recruitCount!"0"}</a>个</span>
            <span class="h-d-s-n">已结束项目<a href="" class="a-num-1" title="点击查看所有已结束的项目" id="h_donate_s3">${projectcount.closeCount!"0"}</a>个</span>
            </@projectCount>
        </div>

        <div id="project_list_wrap">
            <div class="mod box-section list clearfix"><h2 class="main_h2"><span class="bd"></span>本周推荐</h2>
                <a href="${base}/project/list" class="more">更多</a>
                <ul class="recomment_ul list_content">
                <@projectlist  categoryId="" sort="t.create_time" order="desc" pageNumber="1" pageSize="3" connectedSql=" 1=1 and t.is_red=1 and t.state=2">
                    <#list projects.list as project>
                        <li class="list_item">
                            <#if project.isHot == 1><i class="icon-real"></i></#if>
                            <a href="${base}/project/show-${project.id}">
                                <img src="${base}${project.imgUrl}" title="${project.name}" alt="${project.name}" class="p-focus-img">
                            </a>
                            <h3 class="main_h3">
                                <a href="${base}/project/show-${project.id}" title="${project.name}">${project.name}</a>
                            </h3>
                            <div class="donate_infor clearfix">
                                <p class="donate_content">
                                    招募资源：<span class="m_num">${project.totalCount}</span>个 <br>
                                    已参与资源: <span>${project.count!"0"}个</span>
                                </p>
                                <a href="javascript:;" onclick="clickSubmit('${base}/projectUser/save?projectId=${project.id}&userId=${user.id}');"
                                   pname="${project.name}" pid="12771"
                                   fid="40"
                                   class="donate_btn orange">我要参与</a>
                            </div>
                        </li>
                    </#list>
                </@projectlist>
                </ul>
            </div>

        <@orgcategorylist type="0" flag="1" sort="sort_id" order="asc">
            <#list categories as category>
                <div class="mod box-section list clearfix"><h2 class="main_h2"><span class="bd"></span>${category.categoryName}</h2>
                    <a href="${base}/project/list?categoryId=${category.id}" class="more">更多</a>
                    <ul class="list_content">
                        <@projectlist  categoryId="${category.id}" sort="t.create_time" order="desc" pageNumber="1" pageSize="3" connectedSql=" 1=1 and t.state=2">
                            <#list projects.list as project>
                                <li class="list_item">
                                    <#if project.isHot == 1><i class="icon-real"></i></#if>
                                    <a href="${base}/project/show-${project.id}">
                                        <img src="${base}${project.imgUrl}" title="${project.name}" alt="${project.name}" class="p-focus-img">
                                    </a>
                                    <h3 class="main_h3">
                                        <a href="${base}/project/show-${project.id}" title="${project.name}">${project.name}</a>
                                    </h3>
                                    <div class="donate_infor clearfix">
                                        <p class="donate_content">
                                            招募资源：<span class="m_num">${project.totalCount}</span>个 <br>
                                            已参与资源: <span>${project.count!"0"}个</span>
                                        </p>
                                        <a href="javascript:;" onclick="clickSubmit('${base}/projectUser/save?projectId=${project.id}&userId=${user.id}');"
                                            pname="${project.name}"
                                           pid="12771" fid="40" class="donate_btn">我要参与</a>
                                    </div>
                                </li>
                            </#list>
                        </@projectlist>

                    </ul>
                </div>
            </#list>
        </@orgcategorylist>
        </div>
    </div>
    <div class="main_right">
        <div id="lp2" clearfix="clearfix">
            <a class="login0" href="${base}/project/edit"></a>
            <a class="login0-1" href="${base}/project/list"></a>
        </div>
        <div class="get_invoice box-section">
            <dl class="lp_project_invoice">
                <dt>
                <h3 class="main_right_h3"><i class="icon flow_icon"></i>招募流程图</h3></dt>
                <dd id="reg" class="invoice_dd">
                    <a  class="invoice_link">
                        <span class="invoice_num">1</span>
                        <span class="invocie_word">注册</span>
                        <span class="invoice_otherword">个人实名认证/公益机构注册</span>
                    </a>
                </dd>
                <dd id="youjian" class="invoice_dd">
                    <a  class="invoice_link">
                        <span class="invoice_num">2</span>
                        <span class="invocie_word">发起</span>
                        <span class="invoice_otherword">实名用户/公益组织发起项目</span>
                    </a>
                </dd>
                <dd id="chuli" class="invoice_dd">
                    <a  class="invoice_link">
                        <span class="invoice_num">3</span>
                        <span class="invocie_word">审核</span>
                        <span class="invoice_otherword">平台审核并确认项目</span>
                    </a>
                </dd>
                <dd id="fapiao" class="invoice_dd">
                    <a  class="invoice_link">
                        <span class="invoice_num">4</span>
                        <span class="invocie_word">招募</span>
                        <span class="invoice_otherword">招募项目上线接受公众参与</span>
                    </a>
                </dd>
                <dd id="execute" class="invoice_dd">
                    <a  class="invoice_link">
                        <span class="invoice_num">5</span>
                        <span class="invocie_word">执行</span>
                        <span class="invoice_otherword">项目执行者线下开展活动</span>
                    </a>
                </dd>
                <dd id="finish" class="invoice_dd">
                    <a class="invoice_link">
                        <span class="invoice_num">6</span>
                        <span class="invocie_word">结项</span>
                        <span class="invoice_otherword">项目执行者公示项目进度与成果</span>
                    </a>
                </dd>
            </dl>
        </div>
        <div class="main_bottom_right_question box-section">
            <h3 class="questions_t main_right_h3"><i class="icon ques_icon"></i>常见问题</h3>
            <ol class="question_c">
                <li><a href="http://kf.qq.com/faq/120315VjMJBf140312UvAR3y.html" title="乐捐平台项目审核规则是什么？" target="_blank">1.
                    乐捐平台项目审核规则是什么？</a></li>
                <li><a href="http://kf.qq.com/faq/120315VjMJBf140312fQ3iIz.html" title="哪些用户可以在乐捐平台上发起项目？"
                       target="_blank">2.哪些用户可以在乐捐平台上发起项目？</a></li>
                <li><a href="http://kf.qq.com/faq/120315VjMJBf140312YFN7RF.html" title="如何在乐捐平台上发起项目？" target="_blank">3.如何在乐捐平台上发起项目？</a>
                </li>
                <li><a href="http://kf.qq.com/faq/120315VjMJBf14031263MNjM.html" title="完成项目募款后，如何进行拨款？"
                       target="_blank">4.完成项目募款后，如何进行拨款？</a></li>
            </ol>
        </div>

    </div>
</div>
<!--页面底部-->
<#include "/templates/main/_footer.ftl">
<!--/页面底部-->
</body>
</html>