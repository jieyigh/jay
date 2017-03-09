<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 2016/7/22
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>session测试页面</title>
    <!-- jQuery -->
    <script type="text/javascript" src="static/dlshouwen.validator.v1.1.9/dependents/jquery/jquery.min.js"></script>
    <!-- font-awesome -->
    <link rel="stylesheet" type="text/css" href="static/dlshouwen.validator.v1.1.9/dependents/fontAwesome/css/font-awesome.min.css" media="all" />
    <!-- DLShouWen Validator -->
    <script type="text/javascript" src="static/dlshouwen.validator.v1.1.9/dlshouwen.validator.js"></script>
    <script type="text/javascript" src="static/dlshouwen.validator.v1.1.9/i18n/en.js"></script>
    <script type="text/javascript" src="static/dlshouwen.validator.v1.1.9/i18n/zh-cn.js"></script>
    <script type="text/javascript" src="static/dlshouwen.validator.v1.1.9/i18n/zh-tw.js"></script>
    <link rel="stylesheet" type="text/css" href="static/dlshouwen.validator.v1.1.9/dlshouwen.validator.css" />
    <!-- bootstrap -->
    <script type="text/javascript" src="static/dlshouwen.validator.v1.1.9/dependents/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="static/dlshouwen.validator.v1.1.9/dependents/bootstrap/css/bootstrap.min.css">
    <!-- datePicker -->
    <script type="text/javascript" src="${contextPath}/static/dlshouwen.validator.v1.1.9/dependents/datePicker/WdatePicker.js" defer="defer"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/dlshouwen.validator.v1.1.9/dependents/datePicker/skin/WdatePicker.css" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/dlshouwen.validator.v1.1.9/dependents/datePicker/skin/default/datepicker.css" />

    <script type="text/javascript">
      var validator;
      function testSubmit(){
        if(!validator.validResult()){
          return;
        }
        $.fn.dlshouwen.validator.tools.toast('验证通过！', 'success', 3000);
      }
      $(function(){
        validator = $.fn.dlshouwen.validator.init($('#testForm'));
      });

    </script>
  </head>
  <body>
  <h1>表单验证测试</h1>
  <h3>此表单只验证一小部分，更多验证示例请访问 http://os.dlshouwen.com/validator/doc/example.html </h3>
  <h3>使用文档请访问 http://os.dlshouwen.com/validator/doc/example.html </h3>
  <div class="layout main-container">
  <div class="form-container">
    <form id="testForm" class="form-horizontal" method="post">
      <div class="form-group">
        <label class="col-sm-2 control-label text-right">文本必填：</label>
        <div class="col-sm-4">
          <input type="text" id="test_2_1_1_1" name="test_2_1_1_1" class="form-control" valid="r"
                 validTitle="文本必填测试内容" validInfoArea="test_2_1_1_1_info_area" />
        </div>
        <div class="col-sm-6"><p class="help-block" id="test_2_1_1_1_info_area"></p></div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label text-right">Email格式：</label>
        <div class="col-sm-4">
          <input type="text" id="test_2_1_1_2" name="test_2_1_1_2" class="form-control" valid="r|email"
                 validTitle="Email格式测试内容" validInfoArea="test_2_1_1_2_info_area" />
        </div>
        <div class="col-sm-6"><p class="help-block" id="test_2_1_1_2_info_area"></p></div>
      </div>
      <%-- 单选框验证 Radio --%>
      <div class="form-group">
        <label class="col-sm-2 control-label text-right">性别：</label>
        <div class="col-sm-4">
          <label class="radio-inline">
            <input type="radio" name="test_2_1_2_1" value="0" valid="r" validTitle="性别" validInfoArea="test_2_1_2_1_info_area" /> 男
          </label>
          <label class="radio-inline">
            <input type="radio" name="test_2_1_2_1" value="1" valid="r" validTitle="性别" validInfoArea="test_2_1_2_1_info_area" /> 女
          </label>
        </div>
        <div class="col-sm-6"><p class="help-block" id="test_2_1_2_1_info_area"></p></div>
      </div>

      <%-- 复选框验证 Checkbox --%>
      <div class="form-group">
        <label class="col-sm-2 control-label text-right">特点：</label>
        <div class="col-sm-4">
          <label class="checkbox-inline">
            <input type="checkbox" name="checkbox_hobby" value="0" valid="le2-le3" validTitle="特点" validInfoArea="test_2_1_3_2_info_area" /> 活泼
          </label>
          <label class="checkbox-inline">
            <input type="checkbox" name="checkbox_hobby" value="1" valid="le2-le3" validTitle="特点" validInfoArea="test_2_1_3_2_info_area" /> 内敛
          </label>
          <label class="checkbox-inline">
            <input type="checkbox" name="checkbox_hobby" value="2" valid="le2-le3" validTitle="特点" validInfoArea="test_2_1_3_2_info_area" /> 稳重
          </label>
          <label class="checkbox-inline">
            <input type="checkbox" name="checkbox_hobby" value="3" valid="le2-le3" validTitle="特点" validInfoArea="test_2_1_3_2_info_area" /> 执着
          </label>
        </div>
        <div class="col-sm-6"><p class="help-block" id="test_2_1_3_2_info_area"></p></div>
      </div>


      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <button type="button" class="btn btn-primary" onclick="testSubmit();">
            <i class="fa fa-save"></i>  提交表单
          </button>
        </div>
      </div>
    </form>
  </div>
  </div>
  </body>
</html>
