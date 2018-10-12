$(function(){
	$('#log-out').click(function(){
		//清除session
		$.ajax({
			url:'/o2o/local/logout',
			async:false,
			cache:false,
			type:"post",
			dataType:'json',
			success:function(data){
				if(data.success){
					$.toast('登出成功');
					var usertype=$('#log-out').attr('usertype');
					//清除成功后退回登录页面
					window.location.href='/o2o/local/login?usertype='+usertype;
					return false;
				}
			},
			error:function(data,error){
				alert(error);
			}
		});
	});
});