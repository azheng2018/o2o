package com.imooc.o2o.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
/**
 * 生成根目录路径
 * @return
 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "f:/phonto/image";
		} else {
			basePath = "/User/baidu/work/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
/**
 * 生成图片路径
 * @param shopId
 * @return
 */
	public static String getShopImgPath(long shopId) {
		String imgaetPath = "/upload/item/shop" + shopId + "/";

		return imgaetPath.replace("/", seperator);

	}
}
