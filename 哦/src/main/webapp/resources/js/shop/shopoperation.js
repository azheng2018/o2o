/**
 * 
 */
$(function() {
	var shopId=getQueryString('shopId');
	var isEdit=shopId?true:false;//如果传了shopId则默认是更新店铺，如果shopId为空则为注册店铺
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	var shopInfoUrl='/o2o/shopadmin/getshopbyid?shopId='+shopId
	var editShopUrl='/o2o/shopadmin/modifyshop';

	// 判断是编辑操作还是注册操作
	if (!isEdit) {
		getShopInitInfo();
	} else {
		getShopInfo(shopId);
	}
	// 通过店铺Id获取店铺信息
	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				// 若访问成功，则依据后台传递过来的店铺信息为表单元素赋值
				//val（）方法设置或返回表单字段的值
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				// 给店铺类别选定原先的店铺类别值
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				// 初始化区域列表
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				// 不允许选择店铺类别
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				// 给店铺选定原先的所属的区域
				$("#area option[data-id='" + shop.area.areaId + "']").attr(
						"selected", "selected");
			}
		});
	}
	
	
	function getShopInitInfo() {// 获取区域信息及店铺分类信息给填充至前端html
		$.getJSON(initUrl,function(data) {
			if (data.success) {
				var tempHtml = '';	
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '" >'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});

	}
		$('#submit').click(
				function() {
					var shop = {};
					if (isEdit){
						shop.shopId=shopId;
					}
					shop.shopName = $('#shop-name').val();
					shop.shopAddr = $('#shop-addr').val();
					shop.phone = $('#shop-phone').val();
					shop.shopDesc = $('#shop-desc').val();
					shop.shopCategory = {// 获取分类里的id
						shopCategoryId : $('#shop-category').find('option')
								.not(function() {
									return !this.selected;
								}).data('id')
					};
					shop.area = {
						areaId : $('#area').find('option').not(function() {
							return !this.selected;
						}).data('id')
					};
					var shopImg = $('#shop-img')[0].files[0];
					var formData = new FormData();
					formData.append('shopImg', shopImg);
					formData.append('shopStr', JSON.stringify(shop));

					var verifyCodeActual = $('#j_captcha').val();
					if (!verifyCodeActual) {
						$.toast('请输入验证码！');
						return;
					}
					formData.append('verifyCodeActual', verifyCodeActual);
					// 将数据提交至后台处理相关操作
					$.ajax({
						url : (isEdit ? editShopUrl : registerShopUrl),
						type : 'POST',
						data : formData,
						contentType : false,
						processData : false,
						cache : false,
						success : function(data) {
							if (data.success) {
								$.toast('提交成功！');
								if (!isEdit) {
									// 若为注册操作，成功后返回店铺列表页
									window.location.href = "/o2o/shopadmin/shoplist";
								}
							} else {
								$.toast('提交失败！' + data.errMsg);
							}
							// 点击验证码图片的时候，注册码会改变
							$('#captcha_img').click();
						}
					});
				});

			})