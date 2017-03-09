(function($){
	if(!$.fn.dlshouwen.validator.lang){
		$.fn.dlshouwen.validator.lang = {};
	}
	$.fn.dlshouwen.validator.lang['en'] = {
		init : {
			input : 'Please input {validTitle}',
			select : 'Please select {validTitle}'
		},
		unique : {
			validating : 'Checking {validTitle} is validated...',
			success : '{validTitle} can use',
			failure : '{validTitle} alreay in used',
			error : 'Error occur during check {validTitle} is in use'
		},
		validate : {
			typeFormat : '{validTitle} must be {typeName}',
			passwordNotEquals : 'Password do not equals, please input again',
			required : {
				input : '{validTitle} can not be null',
				select : '{validTitle} must select'
			},
			greatLess : {
				number : {
					greatThan : '{validTitle} must great than {min}',
					greatEqualsThan : '{validTitle} must equals or great than {min}',
					lessThan : '{validTitle} must less than {max}',
					lessEqualsThan : '{validTitle} must equals or less than {max}'
				},
				string : {
					greatThan : '{validTitle} length must great than {minLength}',
					greatEqualsThan : '{validTitle} length must equals or great than {minLength}',
					lessThan : '{validTitle} length must less than {maxLength}',
					lessEqualsThan : '{validTitle} length must equals or less than {maxLength}'
				},
				multiple : {
					greatThan : '{validTitle} must select more than {minLength}个',
					greatEqualsThan : '{validTitle} must select less than {minLength}个',
					lessThan : '{validTitle} must select equals or more than {maxLength}个',
					lessEqualsThan : '{validTitle} must select equals or less than {maxLength}个'
				}
			},
			card : {
				wrongLength : '{validTitle} error: card Id is not 15 or 18 length',
				notCard15 : '{validTitle} is not 15 length card Id',
				notCard18 : '{validTitle} is not 18 length card Id',
				wrongCard15 : '{validTitle} is wrong 15 length card Id',
				wrongCard18 : '{validTitle} is wrong 18 length card Id'
			},
			validating : '{validTitle} is validating',
			success : '{validTitle} validate success'
		},
		alert : {
			validating : '{validTitle} is validating, please wait',
			error : 'You inputed contents have some errors, please recheck those.'
		},
		validTypeName : {
			email : 'email',
			phone : 'phone',
			english_number : 'english or number',
			mobile : 'mobile',
			url : 'url',
			money : 'money',
			number : 'number',
			zip : 'zip',
			qq : 'qq',
			integer : 'integer',
			double : 'double',
			english : 'english',
			chinese : 'Chinese',
			card : 'card',
			card15 : '15 length card',
			card18 : '18 length card'
		}
	};
	
})(jQuery);