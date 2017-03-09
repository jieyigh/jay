(function($){
	if(!$.fn.dlshouwen.validator.lang){
		$.fn.dlshouwen.validator.lang = {};
	}
	$.fn.dlshouwen.validator.lang['zh-tw'] = {
		init : {
			input : '請填寫{validTitle}',
			select : '請選擇{validTitle}'
		},
		unique : {
			validating : '正在檢查{validTitle}是否可用...',
			success : '{validTitle}可以使用',
			failure : '{validTitle}已經被使用',
			error : '{validTitle}檢查使用過程中遇到錯誤'
		},
		validate : {
			typeFormat : '{validTitle}必須為{typeName}格式',
			passwordNotEquals : '密碼錄入不一致，請重新錄入',
			required : {
				input : '{validTitle}不能為空',
				select : '{validTitle}必須選擇'
			},
			greatLess : {
				number : {
					greatThan : '{validTitle}的值必須大於{min}',
					greatEqualsThan : '{validTitle}的值必須大於或等於{min}',
					lessThan : '{validTitle}的值必須小於{max}',
					lessEqualsThan : '{validTitle}的值必須小於或等於{max}'
				},
				string : {
					greatThan : '{validTitle}的長度必須大於{minLength}',
					greatEqualsThan : '{validTitle}的長度最少為{minLength}',
					lessThan : '{validTitle}的長度必須小於{maxLength}',
					lessEqualsThan : '{validTitle}的長度最多為{maxLength}'
				},
				multiple : {
					greatThan : '{validTitle}的數量請大於{minLength}個',
					greatEqualsThan : '{validTitle}最少選擇{minLength}個',
					lessThan : '{validTitle}選擇的數量請小於{maxLength}個',
					lessEqualsThan : '{validTitle}最多選擇{maxLength}個'
				}
			},
			card : {
				wrongLength : '{validTitle}驗證失敗：身份證位數不正確',
				notCard15 : '{validTitle}並非15位身份證',
				notCard18 : '{validTitle}並非18位身份證',
				wrongCard15 : '{validTitle}是錯誤的15位元身份證格式',
				wrongCard18 : '{validTitle}是錯誤的18位元身份證格式'
			},
			validating : '{validTitle}正在驗證',
			success : '{validTitle}驗證成功'
		},
		alert : {
			validating : '{validTitle}正在驗證，請您稍後執行操作',
			error : '您錄入的內容存在錯誤，請您核實。'
		},
		validTypeName : {
			email : '郵件',
			phone : '電話',
			english_number : '英文或數位',
			mobile : '手機',
			url : '連結',
			money : '金額',
			number : '數字',
			zip : '郵編',
			qq : 'QQ號',
			integer : '整數',
			double : '小數',
			english : '英文',
			chinese : '中文',
			card : '身份證',
			card15 : '15位身份證',
			card18 : '18位身份證'
		}
	};
	
})(jQuery);