<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/tools.js"></script>
<title>数据分析</title>
<style type="text/css">
.list {border:solid blue 1px;height: auto}
.list ul {background-color: gray;list-style: none;}
.list ul li {}
</style>
<script>
	window.onload = function() {
		document.getElementById("digit").value = "${empty param.digit ? '' : param.digit}";
		document.getElementById("checktype").value = "${empty param.check ? '' : param.check}";
		var list = document.getElementById("list");
		
		var datalist = ${empty dataarray ? "[]" : dataarray};
		for ( var i = 0; i < datalist.length; i++) {
			datalist2 = datalist[i];
			var linename = document.createElement("div");
			linename.id = "line" + i;
			var timeinfo = getTimeInfo(datalist2);
			linename.innerHTML = "<a href='#'>" + timeinfo + "连续" + datalist[i].length + "次</a>";
			linename.onclick = function() {toggle(this);}
			var linedata = document.createElement("ul");
			linedata.style.display = "none";
			linedata.id = "child_" + linename.id;
			for ( var j = 0; j < datalist2.length; j++) {
				var data = document.createElement("li");
				data.innerText = datalist2[j].date + "---" + datalist2[j].issue + "---" + datalist2[j].lotteryNumbers + "---" + getTime(datalist2[j]).toLocaleString();
				linedata.appendChild(data);
			}
			list.appendChild(linename);
			list.appendChild(linedata);
		}
	}
	
	function getTimeInfo(datalist) {
		var end = getTime(datalist[0]);
		var begin = getTime(datalist[datalist.length-1]);
		return begin.toLocaleString() + " ---- " + end.toLocaleString();
	}
	
	function toggle(line) {
		var childDomId = "child_" + line.id; 
		var childDom = document.getElementById(childDomId);
		if (childDom.style.display != 'none' ) {
			childDom.style.display = 'none';
		} else {
			childDom.style.display = 'block';
		}
	}
	
	function validate_required(field, alerttxt) {
		with (field) {
			if (value == null || value == "") {
				alert(alerttxt);
				return false;
			} else {
				return true;
			}
		}
	}

	function validate_form(thisform) {
		with (thisform) {
			if (validate_required(lower, "Lower must be filled out!") == false) {
				lower.focus();
				return false;
			}
			if (validate_required(upper, "Upper must be filled out!") == false) {
				upper.focus();
				return false;
			}
			if (validate_required(checktype, "Checktype must be filled out!") == false) {
				checktype.focus();
				return false;
			}
			if (validate_required(digit, "Digit must be filled out!") == false) {
				digit.focus();
				return false;
			}
			if (validate_required(checktype, "Checktype must be filled out!") == false) {
				checktype.focus();
				return false;
			}
		}
	}
</script>
</head>
<body>
	<form id="dataform" action="datacalc.do" method="post"
		onsubmit="return validate_form(this)">
		<select id="digit" name="digit">
			<option value="">--位数--</option>
			<option value="1">万位</option>
			<option value="2">千位</option>
			<option value="3">百位</option>
			<option value="4">十位</option>
			<option value="5">个位</option>
		</select> 
		<select id="checktype" name="check">
			<option value="">--类型--</option>
			<option value="bigsmall">大小</option>
			<option value="singledouble">单双</option>
		</select> 
		<input id="lower" type="text" name="lower" value="${empty param.lower ? 9 : param.lower}" />
		<input id="upper" type="text" name="upper" value="${empty param.upper ? 30 : param.upper}" />
		<input type="submit" />
	</form>
	<div id="list" class="list"></div>
</body>
</html>