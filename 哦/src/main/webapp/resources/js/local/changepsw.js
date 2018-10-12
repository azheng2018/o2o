$(function() {
	// 修改平台密码的controller url
	var url = 'o2o/local/changelocalpwd';
	// 获取usertype
	var usertype = getQueryString('usertype');
	$('#sumibt').click(function() {
		var userName = $('#username').val();
		var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var confirmPassword = $('#confirmPassword').val();
		if (newPassword != confirmPassword) {
			$.toast('两次输入密码不一致，请重新输入');
			return;
		}
		// 添加表单数据
		var fromdata = new Fromdata();
		fromdata.append('userName', userName);
		fromdata.append('password', password);
		fromdata.append('newPassword', newPassword);
		// 获取验证码
		var verifyCodeActual = $('#j_captcha');
		if (!verifyCodeActual) {
			$.toast('请输入验证码');
			return;
		}
		fromdata.append('verifyCodeActual', verifyCodeAutual);
		// 将参数提交给后台
		$.ajax({
			url : url,
			cache : false,
			data : formdata,
			type : 'post',
			contentType : false,
			processData : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功');
					if (usertype == 1) {
						// 若用户在前端展示系统页面则自动退回到前端展示系统首页
						window.location.href('/o2o/frontend/index');
					} else {
						// 若用户是在店家管理系统页面则自动回退到店铺列表页中
						window.location.href('/o2o/shopadmin/shoplist');
					}
				} else {
					$.toast('提交失败' + data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});
});
