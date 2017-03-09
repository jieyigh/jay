/*!
 * DLShouWen Validator v1.1.9
 *
 * includes: jquery, bootstrap, fontawesome
 * Copyright 2016, http://os.dlshouwen.com/validator, http://www.dlshouwen.com
 */
(function($){
	if(!$.fn.dlshouwen){
		$.fn.dlshouwen = {};
	}
	$.fn.dlshouwen.validator = {
		//初始化方法
		init : function(form, option){
			//定义基础验证对象
			var validator = {
				//option对象
				option : $.extend(true, {}, $.fn.dlshouwen.validator.defaultOption, option),
				//form对象
				form : form,
				/**
				 * 单体对象验证
				 * @param element 需要验证的对象
				 * @returns {Boolean} 验证是否成功
				 */
				singleValid : function(element) {
					//定义validor对象映射
					var validatorReflectionObj = this;
					//获取验证内容
					var valid = $(element).attr('valid');
					//获取表达式数组、值、名称、提示名称、长度
					var expressions = valid.split('|');
					var value = $(element).val();
					var name = $(element).attr('name');
					var validTitle = $(element).attr('validTitle');
					//如果验证内容为空，则直接验证成功
					if(valid.length<=0){
						var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.success, '{validTitle}', validTitle);
						validatorReflectionObj.validSingleHandler(element, 'success', message);
						return;
					}
					//处理radio/select
					if($(element).is(':radio') || ($(element).is('select')&&$(element).attr('multiple')!='multiple')){
						var length = 0;
						if($(element).is(':radio')){
							length = $(validatorReflectionObj.form).find('input[name='+name+']:checked').length;
						}else{
							length = $.fn.dlshouwen.validator.tools.length(value);
						}
						for(var i=0; i<expressions.length; i++){
							var expression = expressions[i];
							//判断是否为空
							if(expression=='r'){
								if(length<=0){
									var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.required.select, '{validTitle}', validTitle);
									validatorReflectionObj.validSingleHandler(element, 'failure', message);
									return;
								}
								break;
							}
						}
						var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.success, '{validTitle}', validTitle);
						validatorReflectionObj.validSingleHandler(element, 'success', message);
						return;
					//处理checkbox/select-multiple
					}else if($(element).is(':checkbox') || ($(element).is('select')&&$(element).attr('multiple')=='multiple')){
						var length = 0;
						if($(element).is(':checkbox')){
							length = $(validatorReflectionObj.form).find('input[name='+name+']:checked').length;
						}else{
							length = $(validatorReflectionObj.form).find('select[name='+name+'] option:selected').length;
						}
						//如果验证内容为空，则默认为成功
						if(valid.length<=0){
							var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.success, '{validTitle}', validTitle);
							validatorReflectionObj.validSingleHandler(element, 'success', message);
							return;
						}
						for(var i=0; i<expressions.length; i++){
							var expression = expressions[i];
							//判断是否为空
							if(expression=='r'){
								if(length<=0){
									var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.required.select, '{validTitle}', validTitle);
									validatorReflectionObj.validSingleHandler(element, 'failure', message);
									return;
								}
							}
							//判断长度
							if(expression.match(/^le?[0-9]*\-le?[0-9]*$/)){
								//获取表达式
								var minLengthExpression = expression.split('-')[0];
								var maxLengthExpression = expression.split('-')[1];
								var minLengthE = minLengthExpression.indexOf('le')!=-1?'>=':'>';
								var maxLengthE = maxLengthExpression.indexOf('le')!=-1?'<=':'<';
								var minLength = minLengthExpression.match(/[0-9]+$/i);
								var maxLength = maxLengthExpression.match(/[0-9]+$/i);
								//验证
								if(minLength!=null){
									if(eval('!('+length+minLengthE+minLength+')')){
										var message = minLengthExpression.indexOf('le')!=-1?$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.multiple.greatEqualsThan:$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.multiple.greatThan;
										message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
										message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{minLength}', minLength);
										validatorReflectionObj.validSingleHandler(element, 'failure', message);
										return;
									}
								}
								if(maxLength!=null){
									if(eval('!('+length+maxLengthE+maxLength+')')){
										var message = minLengthExpression.indexOf('le')!=-1?$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.multiple.lessEqualsThan:$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.multiple.lessThan;
										message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
										message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{maxLength}', maxLength);
										validatorReflectionObj.validSingleHandler(element, 'failure', message);
										return;
									}
								}
								break;
							}
						}
						var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.success, '{validTitle}', validTitle);
						validatorReflectionObj.validSingleHandler(element, 'success', message);
						return;
					}else{
						//验证是否为密码
						var validPassword = $(element).attr('validPassword');
						if(validPassword){
							if(value!=$('input[name='+validPassword+']').val()){
								var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.passwordNotEquals, '{validTitle}', validTitle);
								validatorReflectionObj.validSingleHandler(element, 'failure', message);
								return;
							}
						}
						//获取长度
						var length = $.fn.dlshouwen.validator.tools.length(value);
						//遍历表达式
						for(var j=0; j<expressions.length; j++){
							var expression = expressions[j];
							//判断是否为空
							if(expression=='r'){
								if(value==null||value.replace(/^\s+|\s+$/g, '')==''){
									var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.required.input, '{validTitle}', validTitle);
									validatorReflectionObj.validSingleHandler(element, 'failure', message);
									return;
								}
								continue;
							}
							if(value!=null&&value!=''){
								//判断长度
								if(expression.match(/^le?[0-9]*\-le?[0-9]*$/)){
									//获取表达式
									var minLengthExpression = expression.split('-')[0];
									var maxLengthExpression = expression.split('-')[1];
									var minLengthE = minLengthExpression.indexOf('le')!=-1?'>=':'>';
									var maxLengthE = maxLengthExpression.indexOf('le')!=-1?'<=':'<';
									var minLength = minLengthExpression.match(/[0-9]+$/i);
									var maxLength = maxLengthExpression.match(/[0-9]+$/i);
									//验证
									if(minLength!=null){
										if(eval('!('+length+minLengthE+minLength+')')){
											var message = minLengthExpression.indexOf('le')!=-1?$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.string.greatEqualsThan:$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.string.greatThan;
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{minLength}', minLength);
											validatorReflectionObj.validSingleHandler(element, 'failure', message);
											return;
										}
									}
									if(maxLength!=null){
										if(eval('!('+length+maxLengthE+maxLength+')')){
											var message = maxLengthExpression.indexOf('le')!=-1?$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.string.lessEqualsThan:$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.string.lessThan;
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{maxLength}', maxLength);
											validatorReflectionObj.validSingleHandler(element, 'failure', message);
											return;
										}
									}
									continue;
								}
								//格式判断
								for(var key in validatorReflectionObj.option.validType){
									if(expression==key){
										if(typeof(validatorReflectionObj.option.validType[key]) == 'function'){
											var result = validatorReflectionObj.option.validType[key](value, validatorReflectionObj.option);
											if(!result.validResult){
												var message = result.validMessage;
												message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
												message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{typeName}', $.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validTypeName[key]);
												validatorReflectionObj.validSingleHandler(element, 'failure', message);
												return;
											}
										} else {
											if(!value.match(validatorReflectionObj.option.validType[key])){
												var message = $.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.typeFormat;
												message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
												message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{typeName}', $.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validTypeName[key]);
												validatorReflectionObj.validSingleHandler(element, 'failure', message);
												return;
											}
										}
									}
								}
								//判断大小
								if(expression.match(/^me?[0-9]*\-me?[0-9]*$/)){
									//获取表达式
									var minExpression = expression.split('-')[0];
									var maxExpression = expression.split('-')[1];
									var minE = minExpression.indexOf('me')!=-1?'>=':'>';
									var maxE = maxExpression.indexOf('me')!=-1?'<=':'<';
									var min = minExpression.match(/[0-9]+$/i);
									var max = maxExpression.match(/[0-9]+$/i);
									//验证
									if(min!=null){
										if(eval('!(value'+minE+min+')')){
											var message = minExpression.indexOf('me')!=-1?$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.number.greatEqualsThan:$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.number.greatThan;
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{min}', min);
											validatorReflectionObj.validSingleHandler(element, 'failure', message);
											return;
										}
									}
									if(max!=null){
										if(eval('!(value'+maxE+max+')')){
											var message = minExpression.indexOf('me')!=-1?$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.number.lessEqualsThan:$.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.greatLess.number.lessThan;
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{validTitle}', validTitle);
											message = $.fn.dlshouwen.validator.tools.replaceAll(message, '{max}', max);
											validatorReflectionObj.validSingleHandler(element, 'failure', message);
											return;
										}
									}
									continue;
								}
							}
						}
					}
					//验证成功
					var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].validate.success, '{validTitle}', validTitle);
					validatorReflectionObj.validSingleHandler(element, 'success', message);
					//处理唯一性校验
					var validUnique = $(element).attr('validUnique');
					if(validUnique){
						//验证正在进行
						var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].unique.validating, '{validTitle}', validTitle);
						validatorReflectionObj.validSingleHandler(element, 'validating', message);
						//执行后台验证
						var sqlCode = validUnique.split('|')[0];
						var url = validatorReflectionObj.option.unique;
						var validParams = new Object();
						validParams.sqlCode = sqlCode;
						validParams.key = $(element).val();
						var attrs = new Array();
						for(var i=1; i<validUnique.split('|').length; i++){
							var key = validUnique.split('|')[i];
							var value = $('#'+key).val();
							attrs.push(value);
						}
						validParams.attrs = attrs;
						var params = new Object();
						params.validParams = JSON.stringify(validParams);
						$.ajax({
							type:'post',
							url:url,
							data:params,
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							beforeSend: function(xhr) {xhr.setRequestHeader("__REQUEST_TYPE", "AJAX_REQUEST");},
							success:function(data){
								data = $.parseJSON(data);
								if(data){
									var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].unique.success, '{validTitle}', validTitle);
									validatorReflectionObj.validSingleHandler(element, 'unique_success', message);
								}else{
									var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].unique.failure, '{validTitle}', validTitle);
									validatorReflectionObj.validSingleHandler(element, 'unique_failure', message);
								}
							},
							failure:function(XMLHttpRequest, textStatus, failureThrown){
								var message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].unique.error, '{validTitle}', validTitle);
								validatorReflectionObj.validSingleHandler(element, 'unique_error', message);
							}
						});
						return;
					}
				},
				/**
				 * 验证后的处理，包含区域显示信息、区域CSS处理、元素CSS处理、元素成功设置
				 */
				validSingleHandler : function(element, type, message){
					//定义validor对象映射
					var validatorReflectionObj = this;
					//处理区域的显示信息及区域的CSS
					var validInfoArea = $(element).attr('validInfoArea');
					//如果包含显示区域则进行处理
					if(validInfoArea){
						//获取显示区域的ID、初始化样式表、*正在验证样式表、*验证成功样式表、*验证失败样式表
						var areaId = validInfoArea.split('|')[0];
						var initCss = validInfoArea.split('|')[1];
						var validatingCss = validInfoArea.split('|')[2];
						var successCss = validInfoArea.split('|')[3];
						var failureCss = validInfoArea.split('|')[4];
						//如果样式表信息并未填写则设置为默认值
						if(!initCss){
							initCss = validatorReflectionObj.option.css.area.init;
						}
						if(!validatingCss){
							validatingCss = validatorReflectionObj.option.css.area.validating;
						}
						if(!successCss){
							successCss = validatorReflectionObj.option.css.area.success;
						}
						if(!failureCss){
							failureCss = validatorReflectionObj.option.css.area.failure;
						}
						//清除当前的样式表
						$('#'+areaId).removeClass(initCss);
						$('#'+areaId).removeClass(validatingCss);
						$('#'+areaId).removeClass(successCss);
						$('#'+areaId).removeClass(failureCss);
						if('init' == type){
							//如果为初始化
							$('#'+areaId).addClass(initCss);
							$('#'+areaId).html(validatorReflectionObj.option.icon.init + message);
						}else if('validating' == type){
							//如果为正在验证
							$('#'+areaId).addClass(validatingCss);
							$('#'+areaId).html(validatorReflectionObj.option.icon.validating + message);
						}else if('success' == type || 'unique_success' == type){
							//如果为验证成功
							$('#'+areaId).addClass(successCss);
							$('#'+areaId).html(validatorReflectionObj.option.icon.success + message);
						}else if('failure' == type || 'unique_failure' == type || 'unique_failure' == type){
							//如果为验证失败
							$('#'+areaId).addClass(failureCss);
							$('#'+areaId).html(validatorReflectionObj.option.icon.failure + message);
						}
					}
					//处理元素的CSS
					var validElementCss = $(element).attr('validElementCss');
					if(!validElementCss){
						validElementCss = validatorReflectionObj.option.css.element;
					}else{
						//获取元素CSS
						var initCss = validElementCss.split('|')[0];
						var validatingCss = validElementCss.split('|')[1];
						var successCss = validElementCss.split('|')[2];
						var failureCss = validElementCss.split('|')[3];
						//如果未定义则重置为默认
						validElementCss = validatorReflectionObj.option.css.element;
						var customValidElementCss = new Object();
						customValidElementCss.init = initCss;
						customValidElementCss.validating = validatingCss;
						customValidElementCss.success = successCss;
						customValidElementCss.failure = failureCss;
						validElementCss = $.extend({}, validElementCss, customValidElementCss);
					}
					//执行处理
					if('init' == type){
						validatorReflectionObj.option.elementCallback.init(element, validElementCss);
					}else if('validating' == type){
						validatorReflectionObj.option.elementCallback.validating(element, validElementCss);
					}else if('success' == type || 'unique_success' == type){
						validatorReflectionObj.option.elementCallback.success(element, validElementCss);
					}else if('failure' == type || 'unique_failure' == type || 'unique_failure' == type){
						validatorReflectionObj.option.elementCallback.failure(element, validElementCss);
					}
					//处理元素的验证内容
					var name = $(element).attr('name');
					if($(element).is(':checkbox') || $(element).is(':radio')){
						$('input[name='+name+']').attr('validResult', type);
					}else{
						$(element).attr('validResult', type);
					}
				},
				//执行验证
				valid : function(){
					//定义临时的formValidor本对象
					var validatorReflectionObj = this;
					//遍历所有的form内框
					$(validatorReflectionObj.form).find(':input').each(function(i){
						//file/button/hidden及没指名valid参数的均不验证
						if($(this).is(':file') || $(this).is(':button') || $(this).is(':submit') || $(this).is(':hidden') || $(this).attr('valid')==undefined)
							return true;
						//验证
						validatorReflectionObj.singleValid(this);
					});
				},
				/**
				 * 验证操作
				 * @returns {Boolean} 全局是否验证成功
				 */
				validResult : function() {
					//定义临时的formValidor本对象
					var validatorReflectionObj = this;
					//外层的验证结果，默认为true
					var validResult = true;
					var isValidating = false;
					//遍历form内所有的对象
					$(validatorReflectionObj.form).find(':input').each(function(i){
						//file/button/hidden及没指名valid参数的均不验证
						if($(this).is(':file') || $(this).is(':button') || $(this).is(':submit') || $(this).is(':hidden') || $(this).attr('valid')==undefined)
							return true;
						//如果没做过验证就重新验证
						if(!$(this).attr('validResult')||$(this).attr('validResult')=='init'){
							validatorReflectionObj.singleValid(this);
						}
						//处理验证中的状态
						if($(this).attr('validResult')=='validating'){
							var validTitle = $(this).attr('validTitle');
							$.fn.dlshouwen.validator.tools.toast($.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].alert.validating, '{validTitle}', validTitle), 'info', 3000);
							validResult = false;
							isValidating = true;
							return false;
						}
						//处理失败的状态
						if($(this).attr('validResult')=='unique_failure' || $(this).attr('validResult')=='failure'){
							validResult = false;
							$(this).focus();
							return false;
						}
					});
					if(!isValidating&&!validResult){
						$.fn.dlshouwen.validator.tools.toast($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].alert.error, 'error', 3000);
					}
					return validResult;
				},
				/**
				 * 重置方法
				 */
				reset : function(){
					this.init();
				}
			};
			//定义validor对象映射
			var validatorReflectionObj = validator;
			//初始化信息，遍历form中所有Input元素
			$(validatorReflectionObj.form).find(':input').each(function(i){
				//file/button/hidden及没指名valid参数的均不验证
				if($(this).is(':file') || $(this).is(':button') || $(this).is(':submit') || $(this).is(':hidden') || $(this).attr('valid')==undefined)
					return true;
				//去掉验证结果
				$(this).removeAttr('validResult');
				//初始化信息
				var validTitle = $(this).attr('validTitle');
				var message = '';
				if($(this).is(':radio') || $(this).is(':checkbox') || $(this).is('select')){
					message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].init.select, '{validTitle}', validTitle);
				}else{
					message = $.fn.dlshouwen.validator.tools.replaceAll($.fn.dlshouwen.validator.lang[validatorReflectionObj.option.lang].init.input, '{validTitle}', validTitle);
				}
				//执行初始化验证
				validatorReflectionObj.validSingleHandler(this, 'init', message);
				//初始化绑定事件，如果是radio/checkbox则在onclick时触发；如果是select则在onchange时出发，否则为onblur
				if($(this).is(':radio') || $(this).is(':checkbox')){
					$(this).bind('click', function(){
						validatorReflectionObj.singleValid(this);
					});
				}else if($(this).is('select')){
					$(this).bind('change', function(){
						validatorReflectionObj.singleValid(this);
					});
				}else{
					$(this).bind('blur', function(){
						validatorReflectionObj.singleValid(this);
					});
				}
			});
			//绑定reset对象的验证重置
			$(validatorReflectionObj.form).find(':reset').bind('click', function(){
				validatorReflectionObj.reset();
			});
			return validator;
		},
		tools : {
			/**
			 * 身份证校验
			 */
			//定义加权因子及验证位
			cardWi : [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ],
			cardValideCode : [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ],
			//验证身份证
			cardIdValidate : function(cardId, option){
				if (cardId.length == 15) {
					return $.fn.dlshouwen.validator.tools.cardId15Validate(cardId, option);
				}else if (cardId.length == 18) {
					return $.fn.dlshouwen.validator.tools.cardId18Validate(cardId, option);
				}else{
					var result = new Object();
					result.validResult = false;
					result.validMessage = $.fn.dlshouwen.validator.lang[option.lang].validate.card.wrongLength;
					return result;
				}
			},
			//验证15位身份证
			cardId15Validate : function(cardId, option){
				var result = new Object();
				if (cardId.length != 15) {
					result.validResult = false;
					result.validMessage = $.fn.dlshouwen.validator.lang[option.lang].validate.card.notCard15;
					return result;
				}
				//进行15位身份证的验证
				if($.fn.dlshouwen.validator.tools.isValidityBrithBy15IdCard(cardId)){
					result.validResult = true;
					return result;
				}else{
					result.validResult = false;
					result.validMessage = $.fn.dlshouwen.validator.lang[option.lang].validate.card.wrongCard15;
					return result;
				}
			},
			//验证18位身份证
			cardId18Validate : function(cardId, option){
				var result = new Object();
				if (cardId.length != 18) {
					result.validResult = false;
					result.validMessage = $.fn.dlshouwen.validator.lang[option.lang].validate.card.notCard18;
					return result;
				}
				var a_cardId = cardId.split("");
				if ($.fn.dlshouwen.validator.tools.isValidityBrithBy18IdCard(cardId) 
						&& $.fn.dlshouwen.validator.tools.isTrueValidateCodeBy18IdCard(a_cardId)) {
					result.validResult = true;
					return result;
				} else {
					result.validResult = false;
					result.validMessage = $.fn.dlshouwen.validator.lang[option.lang].validate.card.wrongCard18;
					return result;
				}
			},
			//验证15位数身份证号码中的生日是否是有效生日
			isValidityBrithBy15IdCard : function(cardId15){
				var year = cardId15.substring(6, 8);
				var month = cardId15.substring(8, 10);
				var day = cardId15.substring(10, 12);
				var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
				if (temp_date.getYear() != parseFloat(year)
						|| temp_date.getMonth() != parseFloat(month) - 1
						|| temp_date.getDate() != parseFloat(day)) {
					return false;
				} else {
					return true;
				}
			},
			//判断身份证号码为18位时最后的验证位是否正确
			isTrueValidateCodeBy18IdCard : function(a_cardId){
				var sum = 0;
				if (a_cardId[17].toLowerCase() == 'x') {
					a_cardId[17] = 10;
				}
				for ( var i = 0; i < 17; i++) {
					sum += $.fn.dlshouwen.validator.tools.cardWi[i] * a_cardId[i];
				}
				valCodePosition = sum % 11;
				if (a_cardId[17] == $.fn.dlshouwen.validator.tools.cardValideCode[valCodePosition]) {
					return true;
				} else {
					return false;
				}
			},
			//验证18位数身份证号码中的生日是否是有效生日
			isValidityBrithBy18IdCard : function(cardId18){
				var year = cardId18.substring(6, 10);
				var month = cardId18.substring(10, 12);
				var day = cardId18.substring(12, 14);
				var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
				if (temp_date.getFullYear() != parseFloat(year)
						|| temp_date.getMonth() != parseFloat(month) - 1
						|| temp_date.getDate() != parseFloat(day)) {
					return false;
				} else {
					return true;
				}
			},
			/**
			 * 提示框
			 */
			toastZIndex : 1090,
			toastThread : null,
			toastFadeInAnimateTime : 500,
			toastFadeOutAnimateTime : 500,
			openToast : function(content, level, time){
				// default level、time
				level = level?level:'info';
				time = time?time:3000;
				// get the level class
				var levelClass = '';
				if(level=='info') levelClass='text-primary';
				if(level=='warning') levelClass='text-warning';
				if(level=='error') levelClass='text-danger';
				if(level=='success') levelClass='text-success';
				// close other toast div
				clearTimeout($.fn.dlshouwen.validator.tools.toastThread);
				$('.toast').remove();
				// constructs the html content
				var toastContent = '<div class="toast '+levelClass+'" style="z-index:'+$.fn.dlshouwen.validator.tools.toastZIndex+'">'+content+'</div>';
				// append to the ducoment
				$('body').append(toastContent);
				// set the offset
				var x = $(window).width()/2-$('.toast').width()/2-20;
				//toast(x);
				$('.toast').css("left", x);
				// show the div
				$('.toast').fadeIn($.fn.dlshouwen.validator.tools.toastFadeInAnimateTime, function(){
					// callback close
					if(time){
						$.fn.dlshouwen.validator.tools.toastThread = setTimeout($.fn.dlshouwen.validator.tools.removeToast, time);
					}
				});
			},
			//close an toast
			removeToast : function(){
				$('.toast').fadeOut($.fn.dlshouwen.validator.tools.toastFadeOutAnimateTime, function(){
					$('.toast').remove();
				});
			},
			//整理Toast方法
			toast : function(content, level, time){
				$.fn.dlshouwen.validator.tools.openToast(content, level, time);
			},
			//字符串全局替换
			replaceAll : function(s, s1, s2){
				return new String(s).replace(new RegExp(s1, "gm"), s2);
			},
			//取得字符串的长度，双字节按照两个长度计算
			length : function(s){
				return new String(s).replace(/[^\x00-\xff]/g,"**").length;
			}
		}
	};
})(jQuery);

//初始化参数
(function($){
	$.fn.dlshouwen.validator.defaultOption = {
		//默认语言
		lang : 'zh-cn',
		//验证唯一的服务器地址
		unique : '/validator/unique',
		//验证类型
		validType : {
			email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			phone : /^(\(?\+?[0-9]*\)?)?[0-9_\- \(\)]*$/,
			english_number : /^\w+$/,
			mobile : /^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/,
			url : /^https?:\/\/(([a-zA-Z0-9_-])+(\.)?)*(:\d+)?(\/((\.)?(\?)?=?&?[a-zA-Z0-9_-](\?)?)*)*$/i,
			money : /^-?((\d{1,3}(,\d{3})*?)|\d+)(\.\d{1,2})?$/,
			number : /^\d+$/,
			zip : /^[1-9]\d{5}$/,
			qq : /^[1-9]\d{4,10}$/,
			integer : /^[-\+]?\d+$/,
			double : /^[-\+]?\d+(\.\d+)?$/,
			english : /^[A-Za-z]+$/,
			chinese : /^[\u0391-\uFFE5]+$/,
			card : $.fn.dlshouwen.validator.tools.cardIdValidate,
			card15 : $.fn.dlshouwen.validator.tools.cardId15Validate,
			card18 : $.fn.dlshouwen.validator.tools.cardId18Validate
		},
		//默认样式
		css : {
			element : {
				init : '',
				validating : '',
				success : '',
				failure : 'has-error'
			},
			area : {
				init : '',
				validating : 'info-area-validating',
				success : 'info-area-success',
				failure : 'info-area-failure'
			}
		},
		//前缀图标
		icon : {
			init : '<i class="fa fa-exclamation-circle"></i>&nbsp;&nbsp;',
			validating: '<i class="fa fa-spinner fa-spin"></i>&nbsp;&nbsp;',
			success : '<i class="fa fa-check"></i>&nbsp;&nbsp;',
			failure : '<i class="fa fa-times"></i>&nbsp;&nbsp;'
		},
		//元素样式处理
		elementCallback : {
			init : function(element, css){
				
			},
			validating : function(element, css){
				
			},
			success : function(element, css){
				if($(element).is(':checkbox') || $(element).is(':radio')){
					var name = $(element).attr('name');
					$('input[name='+name+']').parents('.form-group').removeClass(css.failure);
				}else{
					$(element).parents('.form-group').removeClass(css.failure);
				}
			},
			failure : function(element, css){
				if($(element).is(':checkbox') || $(element).is(':radio')){
					var name = $(element).attr('name');
					$('input[name='+name+']').parents('.form-group').addClass(css.failure);
				}else{
					$(element).parents('.form-group').addClass(css.failure);
				}
			}
		}
	};
})(jQuery);