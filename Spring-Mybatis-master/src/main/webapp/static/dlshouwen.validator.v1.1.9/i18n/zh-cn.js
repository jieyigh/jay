(function($){
	if(!$.fn.dlshouwen.validator.lang){
		$.fn.dlshouwen.validator.lang = {};
	}
	$.fn.dlshouwen.validator.lang['zh-cn'] = {
		init : {
			input : '请填写{validTitle}',
			select : '请选择{validTitle}'
		},
		unique : {
			validating : '正在检查{validTitle}是否可用...',
			success : '{validTitle}可以使用',
			failure : '{validTitle}已经被使用',
			error : '{validTitle}检查使用过程中遇到错误'
		},
		validate : {
			typeFormat : '{validTitle}必须为{typeName}格式',
			passwordNotEquals : '密码录入不一致，请重新录入',
			required : {
				input : '{validTitle}不能为空',
				select : '{validTitle}必须选择'
			},
			greatLess : {
				number : {
					greatThan : '{validTitle}的值必须大于{min}',
					greatEqualsThan : '{validTitle}的值必须大于或等于{min}',
					lessThan : '{validTitle}的值必须小于{max}',
					lessEqualsThan : '{validTitle}的值必须小于或等于{max}'
				},
				string : {
					greatThan : '{validTitle}的长度必须大于{minLength}',
					greatEqualsThan : '{validTitle}的长度最少为{minLength}',
					lessThan : '{validTitle}的长度必须小于{maxLength}',
					lessEqualsThan : '{validTitle}的长度最多为{maxLength}'
				},
				multiple : {
					greatThan : '{validTitle}的数量请大于{minLength}个',
					greatEqualsThan : '{validTitle}最少选择{minLength}个',
					lessThan : '{validTitle}选择的数量请小于{maxLength}个',
					lessEqualsThan : '{validTitle}最多选择{maxLength}个'
				}
			},
			card : {
				wrongLength : '{validTitle}验证失败：身份证位数不正确',
				notCard15 : '{validTitle}并非15位身份证',
				notCard18 : '{validTitle}并非18位身份证',
				wrongCard15 : '{validTitle}是错误的15位身份证格式',
				wrongCard18 : '{validTitle}是错误的18位身份证格式'
			},
			validating : '{validTitle}正在验证',
			success : '{validTitle}验证成功'
		},
		alert : {
			validating : '{validTitle}正在验证，请您稍后执行操作',
			error : '您录入的内容存在错误，请您核实。'
		},
		validTypeName : {
			email : '邮件',
			phone : '电话',
			english_number : '英文或数字',
			mobile : '手机',
			url : '链接',
			money : '金额',
			number : '数字',
			zip : '邮编',
			qq : 'QQ号',
			integer : '整数',
			double : '小数',
			english : '英文',
			chinese : '中文',
			card : '身份证',
			card15 : '15位身份证',
			card18 : '18位身份证'
		}
	};
	
})(jQuery);