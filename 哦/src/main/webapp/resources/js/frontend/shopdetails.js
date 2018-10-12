$(function(){
	var loading=false;
	//分页返回的最大条数
	var maxItems=999;
	//每页返回的最大条数
	var pageSize=3;
	//商品列表url
	var listUrl='/o2o/frontend/listproductsbyshop';
	//默认页码
		var pageNum=1;
	//获取商铺id
	var shopId=getQueryString('shopId');
	var productCategoryId='';
	var productName='';
	//获取店铺信息和商品类别的url
	var searchDivUrl= '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
	//渲染出商品类别信息和商品类别信息
	getSearchDivData();
	//预加载10条商品信息
	addItems(pageSize,pageNum);
	
	//获取本店铺信息以及商品类别信息列表
	function getSearchDivData(){
		var url=searchDivUrl;
          $.getJSON(url,function(data){
        	  if(data.success){
        		  var shop=data.shop;
        		  $('#shop-cover-pic').attr('src',shop.shopImg);
        		  $('#shop-update-time').html(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
        		  $('#shop-name').html(shop.shopName);
        		  $('#shop-desc').html(shop.shopDesc);
        		  $('#shop-addr').html(shop.shopAddr);
        		  $('#shop-phone').html(shop.phone);
        		  //获取后台返回的该店铺的商品列表
        		  var productCategoryList=data.productCategoryList;
        		  var html='';
        		  //遍历商品列表，生成可以点击搜索相应商品列表下的商品
        		  productCategoryList.map(function(item,index){
        			  html+='<a href="#" class="button" data-product-search-id='
        				  +item.productCategoryId
        				  +'>'
        				  +item.productCategoryName
        				  +'</a>';
        		  });
        		  //将商品类别标签绑定在相应的html组件中
        		  $('#shopdetail-button-div').html(html);
        	  }
          });     
	}
	
	/**
	 * 获取分页展示的商品信息
	 */
	function addItems(pageSize,pageIndex){
		//拼接查询的url。默认值为空去掉该条件限制
		var url=listUrl+'?'+'pageIndex='+pageIndex+'&pageSize='+pageSize+'&productCategoryId='+productCategoryId
		        +'&productName='+productName+'&shopId='+shopId;
		//设定加载符号,若后台还在取数据不能再次访问后台
		loading=true;
		//访问后台获取相应查询条件下的商品列表
		$.getJSON(url,function(data){
			if(data.success){
				//获取当前查询条件下的商品总数
				maxItems=data.count;
				var html='';
				//遍历商品列表，查询卡片集合
				data.productList.map(function(item,index){
					html+='<div class="card" data-product-id='+item.productId+'>'+'<div class="card-header">'
						+item.productName+'</div>'+' <div class="card-content">'+'<div class="list-block media-list">'
						+'<ul>'+' <li class="item-content">'+'<div class="item-media">'+'<img src="'+item.imgAddr
						+'"width="44">'+'</div>'+'<div class="item-inner">'+' <div class="item-subtitle">'+item.productDesc
						+'</div>'+'</div>'+'</li>'+'</ul>'+'</div>'+'</div>'+'<div class="card-footer">'+'<p="color-gray">'
						+new Date(item.lastEditTime).Format('yyyy-MM-dd hh:mm:ss')+'更新</p>'+'<span>点击查看</span>'
						+'</div>'+'</div>';
				});
				//将卡片添加在html里面
				$('.list-div').append(html);
				//获取卡片总数
				var total=$('.list-div').length;
				//若卡片总数达到与该查询条件下的商品总数一样，则停止加载
				if(total>=maxItems){
					//删除加载提示符
					$('.infinite-scroll-preloader').hide();
				}else{
					$('.infinite-scroll-preloader').show();
				}
				//否则页码加一，继续加载新的店铺
				pageNum+=1;
				loading=false;
				//刷新页面，显示新的店铺
				$.refreshScroller();
			}
		});	
	}
	//下滑屏幕进行分页搜素
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if 
		
		
		(loading)
			return;
		addItems(pageSize, pageNum);
	});
	//选定新的商品类别后，重置原来的页码，清空原来的商品列表，按照新的列表去查询
	$('#shopdetail-button-div').on('click','.button',function(e){
		//获取商品类别id
		productCategoryId=e.target.dataset.productSearchId;
		if(productCategoryId){
			//若之前已选定了别的商品类别，则移除选定效果，改成选定新的
			if($(e.target).hasClass('button-fill')){
				$(e.target).removeClass('button-fill');
				productCategoryId='';
			}else{
				$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
			}
			$('.list-div').empty();
			pageNum=1;
			addItems(pageSize,pageNum);
		}	
	});
	//点击卡片进入商品详情页
	$('.list-div').on('click','.card',function(e){
		var productId=e.currentTarget.dataset.productId;
		window.location.href='/o2o/frontend/productdetail?productId='+productId;
	});
	//需要查询的商品名字发生变化后，重置页码，清空原来的商品列表
	$('#search').on('change',function(e){
		productName=e.target.value;
		$('.list-div').empty();
		pageNum=1;
		addItems(pageSize,pageNum);
	});
	//点击我的打开右侧栏
	$('#me').click(function(){
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
	
