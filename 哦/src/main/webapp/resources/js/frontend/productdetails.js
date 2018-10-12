$(function(){
	//从地址栏获取productId
	var productId=getQueryString('productId');
	//获取商品详情url
	var listUrl='/o2o/frontend/listproductdetailpageinfo?productId='+productId;
	//访问后台获取商品信息并渲染
	$.getJSON(listUrl,function(data){
		if(data.success){
			//获取商品信息
			var product=data.product;
			//给商品信息赋值
			//商品缩略图
			$('#product-img').attr('src',product.imgAddr);
			//商品更新时间
			$('#product-time').text(
					new Date(product.lastEditTime).Format("yyyy-MM-dd hh:mm:ss"));
			//商品名称
			$('#product-name').text(product.productName);
			//商品描述
			$('#product-desc').text(product.productDesc);
			//商品价格显示逻辑，判断商品原价或现价是否为空，如果都为空都不展示
			if(product.normalPrice!=undefined&&product.promotionPrice!=undefined){
				//如果现价和原价都不为空都展示，给原价加个删除符号
				$('#price').show();
				$('#normalPrice').html('<del>'+'￥'+product.normalPrice+'</del>');
				$('#promotionPrice').text('￥'+product.promotionPrice);
			}else if(product.normalPrice!=undefined&&product.promotionPrice==undefined){
				//如果原价不为空，现价为空，则展示原价
				$('#price').show();
				$('#promotionPrice').text('￥'+product.normalPrice);
			}else if(prodcut.normalPrice==undefined&&product.promotionPrice!=undefined){
				//如果原价为空，现价不为空，展示现价
				$('#price').show();
				$('#promotionPrice').text('￥'+product.promotionPrice);
			}
			var imgListHtml='';
			product.productImgList.map(function(item,index){
				imgListHtml+='<div>'+'<img src="'+item.imgAddr+'" width="80%"/>'+'</div>';
			});
			$('#imgList').html(imgListHtml);
		}
	});
	
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
	
})