<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 2016/7/16
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
    <script type="text/javascript" src="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/jquery/jquery.min.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/bootstrap/css/bootstrap.min.css" />
    <!--[if lt IE 9]>
    <script src="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/bootstrap/plugins/ie/html5shiv.js"></script>
    <script src="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/bootstrap/plugins/ie/respond.js"></script>
    <![endif]-->
    <!--[if lt IE 8]>
    <script src="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/bootstrap/plugins/ie/json2.js"></script>
    <![endif]-->
    <!-- font-awesome -->
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/fontAwesome/css/font-awesome.min.css" media="all" />
    <!-- DLShouWen Grid -->
    <script type="text/javascript" src="${contextPath}/static/dlshouwen.grid.v1.2.1/dlshouwen.grid.js"></script>
    <script type="text/javascript" src="${contextPath}/static/dlshouwen.grid.v1.2.1/i18n/zh-cn.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/dlshouwen.grid.v1.2.1/dlshouwen.grid.css" />
    <!-- datePicker -->
    <script type="text/javascript" src="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/datePicker/WdatePicker.js" defer="defer"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/datePicker/skin/WdatePicker.css" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/dlshouwen.grid.v1.2.1/dependents/datePicker/skin/default/datepicker.css" />
    <script type="text/javascript">
        var gridColumns = [
            {id:'id', title:'用户编号', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
            {id:'username', title:'用户名称', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'lk'},
            {id:'password', title:'用户密码', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'lk'},
            {id:'sex', title:'性别', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq', resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                if(value == 1){
                    content += '<span style="background:#00a2ca;padding:2px 10px;color:white;">男</span>';
                }else{
                    content += '<span style="background:#c447ae;padding:2px 10px;color:white;">女</span>';
                }
                return content;
            }},
            {id:'brithday', title:'生日', type:'date', columnClass:'text-center', fastQuery:true, fastQueryType:'lk'}
        ];
        var gridOption = {
            lang : 'zh-cn',
            ajaxLoad : true,
            loadURL : '${contextPath}/datagrid/get-datagrid-data',
            exportFileName : '用户列表',
            columns : gridColumns,
            gridContainer : 'gridContainer',
            toolbarContainer : 'gridToolBarContainer',
            tools : 'fastQuery',
            pageSize : 10,
            pageSizeLimit : [10, 20, 50]
        };
        var grid = $.fn.dlshouwen.grid.init(gridOption);
        $(function(){
            grid.load();
        });

    </script>
</head>
<body>
<h1>主页</h1>
<div id="gridContainer" class="dlshouwen-grid-container">
</div>
<div id="gridToolBarContainer" class="dlshouwen-grid-toolbar-container"></div>
</body>
</html>
