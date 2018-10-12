package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.exceptions.ShopOperationException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * 将CommonsMutipartFile转换为File
	 * 
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultpartFileToFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	/**
	 * 处理商品详情图
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail,String targetAddr){
		String realFileName = getRandomFileName();
		// 获取文件的扩展名如png,jpg等
		String extension = getFileExtension(thumbnail.getImageName());
		// 如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
		// 获取文件存储的相对路径(带文件名)
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		// 获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
		// 调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/jinkesi.jpg")), 0.25f)
					.outputQuality(1.0f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			throw new RuntimeException("创建缩图片失败：" + e.toString());
		}
		// 返回图片相对路径地址
		return relativeAddr;
		
	}
	
	

	/**
	 * 处理缩略图，并返回新图片的相对值路径
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;// 相对路径
		logger.error("current relativeAddr is :" + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);// 文件全路径
		logger.error("current complete is :" + PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/jinkesi.jpg")), 0.01f)
					.outputQuality(1.0f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
			try {
				throw new ShopOperationException("创建缩略图失败"+extension.toString());
			} catch (ShopOperationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//返回图片相对路径
		return relativeAddr;

	}
	


	/**
	 * 创建目标路径所涉及到的目录，即home/image/xxx.jpg 那么home，imgae都得创建出来
	 * 
	 * @param targetAdd
	 */
	private static void makeDirPath(String targetAdd) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAdd;// 目标文件夹所属的全路径
		File dirPahth = new File(realFileParentPath);
		if (!dirPahth.exists()) {
			dirPahth.mkdirs();
		}

	}

	/**
	 * 获取输入文件流的扩展名
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

//	public static void main(String[] args) throws IOException {
//		String baseUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//		Thumbnails.of(new File("f://phonto/huangzi.jpg")).size(400, 400)
//				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(baseUrl + "/jinkesi.jpg")), 0.25f)
//				.outputQuality(0.8f).toFile("f://phonto/huangzi1.jpg");
//	}
}
