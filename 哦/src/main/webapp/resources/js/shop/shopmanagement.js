$(function(){
	var shopId=getQueryString('shopId');
	var shopInfoUrl='/o2o/shopadmin/getshopmanagementinfo?shopId='+shopId;
	//$.get(url，function（data）)url是请求的url，function（data）是回调函数，返回请求的内容
	$.getJSON(shopInfoUrl,function(data){
		if(data.redirect){
			window.location.href=data.url;
		}else{
			if(data.shopId!=undefined&&data.shopId!=null){
				shopId=data.shopId;
			}
			//设置shopInfo的属性和值,shopInfo在html是/o2o/shopadmin/shopoperation
			$('#shopInfo').attr('href','/o2o/shopadmin/shopoperation?shopId='+shopId);
		}
	});
	
});