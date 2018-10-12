$(function() {
	// 从url中获取productId
	var productId = getQueryString('productId');
	// 通过productId获取商品信息的url
	var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
	// 获取当前店铺设定的商品类别的url
	var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
	// 更新商品信息的url
	var productPostUrl = '/o2o/shopadmin/modifyproduct';
	// 由于商品编辑和商品添加是同一个页面，需要用标识符作区分
	var isEdit = false;
	if (productId) {
		// 若有productId为编辑操作
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory();
		productPostUrl = '/o2o/shopadmin/addproduct';
	}

	// 获取需要编辑的商品的商品信息，并赋值给表单
	function getInfo(id) {
		$
				.getJSON(
						infoUrl,
						function(data) {
							if (data.success) {
								// 从返回的JSON当中获取product对象的信息，并赋值给表单
								var product = data.product;
								$('#product-name').val(product.productName);
								$('#product-desc').val(product.productDesc);
								$('#priority').val(product.priority);
								$('#normal-price').val(product.normalPrice);
								$('#promotion-price').val(
										product.promotionPrice);
								// 获取原本已选择商品类别与该店铺下所有的商品类别列表
								var optionHtml = '';
								var optionArr = data.productCategoryList;
								var optionSelected = product.productCategory.productCategoryId;
								// 生成前端的商品类别列表，并默认选择编辑前所选的商品类别
								optionArr
										.map(function(item, index) {
											var isSelect = optionSelected === item.productCategoryId ? 'selected'
													: '';
											optionHtml += '<option data-value="'
													+ item.productCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.productCategoryName
													+ '</option>';
										});
								$('#category').html(optionHtml);
							}
						});
	}

	// 为商品添加提供该店铺下的所有商品类别
	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var productCategoryList = data.data;
				var optionHtml = '';
				productCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}
	// 针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片）且控件数未达6个，则生
	// 成一个新的文件上传控件
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	// 提交按钮的事件响应，分别对商品添加和编辑作不同的响应
	$('#submit').click(
			function() {
				// 创建商品json对象，并从表单里面获取对应的属性值
				var product = {};
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				// 获取选定的商品类别值
				product.productCategory = {
						//不返回未选择的商品类别id，返回已选的
					productCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};
				product.productId = productId;
				// 获取缩略图文件流
				var thumbnail = $('#small-img')[0].files[0];
				// 生成表单对象，用来接收参数并传递给后台
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				// 遍历商品详情图控件，获取里面的文件流
				$('.detail-img').map(
						function(index, item) {
							// 判断该控件是否选择了文件
							if ($('.detail-img')[index].files.length > 0) {
								// 将第i个文件流赋值给key为productImg+i的表单键对值里
								formData.append('productImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});
				// 将product json对象转成字符流保存在表单对象key为productStr的表单键对值中
				formData.append('productStr', JSON.stringify(product));
				// 获取表单里输入的验证码
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('请输入验证码');
					return;
				}
				formData.append("verifyCodeActual", verifyCodeActual);
				// 将数据提交至后台处理相关操作
				$.ajax({
					url : productPostUrl,
					//规定请求的类型（GET 或 POST）。
					type : 'POST',
					//规定要发送到服务器的数据。
					data:formData,
					//contentType：发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"。
					contentType : false,
					//processData :布尔值，规定通过请求发送的数据是否转换为查询字符串。默认是 true。
					processData : false,
					//cache：布尔值，表示浏览器是否缓存被请求页面，默认是ture
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功');
							$('#captcha_img').click();
						} else {
							$.toast('提交失败');
							$('#captcha_img').click();
						}
					}

				});
			});

})