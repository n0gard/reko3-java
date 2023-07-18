package az.test.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 图像裁剪以及压缩处理工具类
 * <p>
 * 主要针对动态的GIF格式图片裁剪之后，只出现一帧动态效果的现象提供解决方案
 * <p>
 * 提供依赖三方包解决方案（针对GIF格式数据特征一一解析，进行编码解码操作） 提供基于JDK Image I/O 的解决方案(JDK探索失败)
 */
public class ImageUtil {

	public enum IMAGE_FORMAT {
		BMP("bmp"), JPG("jpg"), WBMP("wbmp"), JPEG("jpeg"), PNG("png"), GIF("gif");

		private String value;

		IMAGE_FORMAT(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * 获取图片格式
	 *
	 * @param file 图片文件
	 * @return 图片格式
	 */
	public static String getImageFormatName(File file) throws IOException {
		String formatName = null;

		ImageInputStream iis = ImageIO.createImageInputStream(file);
		Iterator<ImageReader> imageReader = ImageIO.getImageReaders(iis);
		if (imageReader.hasNext()) {
			ImageReader reader = imageReader.next();
			formatName = reader.getFormatName();
		}

		return formatName;
	}

	/*********************** 基于JDK 解决方案 ********************************/

	/**
	 * 读取图片
	 *
	 * @param file 图片文件
	 * @return 图片数据
	 * @throws IOException
	 */
	public static BufferedImage[] readerImage(File file) throws IOException {
		BufferedImage sourceImage = ImageIO.read(file);
		BufferedImage[] images = null;
		ImageInputStream iis = ImageIO.createImageInputStream(file);
		Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
		if (imageReaders.hasNext()) {
			ImageReader reader = imageReaders.next();
			reader.setInput(iis);
			int imageNumber = reader.getNumImages(true);
			images = new BufferedImage[imageNumber];
			for (int i = 0; i < imageNumber; i++) {
				BufferedImage image = reader.read(i);
				if (sourceImage.getWidth() > image.getWidth() || sourceImage.getHeight() > image.getHeight()) {
					image = zoom(image, sourceImage.getWidth(), sourceImage.getHeight());
				}
				images[i] = image;
			}
			reader.dispose();
			iis.close();
		}
		return images;
	}

	/**
	 * 根据要求处理图片
	 *
	 * @param images 图片数组
	 * @param x      横向起始位置
	 * @param y      纵向起始位置
	 * @param width  宽度
	 * @param height 宽度
	 * @return 处理后的图片数组
	 * @throws Exception
	 */
	public static BufferedImage[] processImage(BufferedImage[] images, int x, int y, int width, int height)
			throws Exception {
		if (null == images) {
			return images;
		}
		BufferedImage[] oldImages = images;
		images = new BufferedImage[images.length];
		for (int i = 0; i < oldImages.length; i++) {
			BufferedImage image = oldImages[i];
			images[i] = image.getSubimage(x, y, width, height);
		}
		return images;
	}

	/**
	 * 写入处理后的图片到file
	 * <p>
	 * 图片后缀根据图片格式生成
	 *
	 * @param images     处理后的图片数据
	 * @param formatName 图片格式
	 * @param file       写入文件对象
	 * @throws Exception
	 */
	public static void writerImage(BufferedImage[] images, String formatName, File file) throws Exception {
		Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(formatName);
		if (imageWriters.hasNext()) {
			ImageWriter writer = imageWriters.next();
			String fileName = file.getName();
			int index = fileName.lastIndexOf(".");
			if (index > 0) {
				fileName = fileName.substring(0, index + 1) + formatName;
			}
			String pathPrefix = getFilePrefixPath(file.getPath());
			File outFile = new File(pathPrefix + fileName);
			ImageOutputStream ios = ImageIO.createImageOutputStream(outFile);
			writer.setOutput(ios);

			if (writer.canWriteSequence()) {
				writer.prepareWriteSequence(null);
				for (int i = 0; i < images.length; i++) {
					BufferedImage childImage = images[i];
					IIOImage image = new IIOImage(childImage, null, null);
					writer.writeToSequence(image, null);
				}
				writer.endWriteSequence();
			} else {
				for (int i = 0; i < images.length; i++) {
					writer.write(images[i]);
				}
			}

			writer.dispose();
			ios.close();
		}
	}

	/**
	 * 剪切格式图片
	 * <p>
	 * 基于JDK Image I/O解决方案
	 *
	 * @param sourceFile 待剪切图片文件对象
	 * @param destFile   裁剪后保存文件对象
	 * @param x          剪切横向起始位置
	 * @param y          剪切纵向起始位置
	 * @param width      剪切宽度
	 * @param height     剪切宽度
	 * @throws Exception
	 */
	public static void cutImage(File sourceFile, File destFile, int x, int y, int width, int height) throws Exception {
		// 读取图片信息
		BufferedImage[] images = readerImage(sourceFile);
		// 处理图片
		images = processImage(images, x, y, width, height);
		// 获取文件后缀
		String formatName = getImageFormatName(sourceFile);

		destFile = new File(getPathWithoutSuffix(destFile.getPath()) + formatName);

		// 写入处理后的图片到文件
		writerImage(images, formatName, destFile);
	}

	/**
	 * 获取系统支持的图片格式
	 */
	public static void getOSSupportsStandardImageFormat() {
		String[] readerFormatName = ImageIO.getReaderFormatNames();
		String[] readerSuffixName = ImageIO.getReaderFileSuffixes();
		String[] readerMIMEType = ImageIO.getReaderMIMETypes();
		System.out.println("========================= OS supports reader ========================");
		System.out.println("OS supports reader format name :  " + Arrays.asList(readerFormatName));
		System.out.println("OS supports reader suffix name :  " + Arrays.asList(readerSuffixName));
		System.out.println("OS supports reader MIME type :  " + Arrays.asList(readerMIMEType));

		String[] writerFormatName = ImageIO.getWriterFormatNames();
		String[] writerSuffixName = ImageIO.getWriterFileSuffixes();
		String[] writerMIMEType = ImageIO.getWriterMIMETypes();

		System.out.println("========================= OS supports writer ========================");
		System.out.println("OS supports writer format name :  " + Arrays.asList(writerFormatName));
		System.out.println("OS supports writer suffix name :  " + Arrays.asList(writerSuffixName));
		System.out.println("OS supports writer MIME type :  " + Arrays.asList(writerMIMEType));
	}

	/**
	 * 压缩图片
	 *
	 * @param sourceImage 待压缩图片
	 * @param width       压缩图片高度
	 * @param height      压缩图片宽度
	 */
	private static BufferedImage zoom(BufferedImage sourceImage, int width, int height) {
		BufferedImage zoomImage = new BufferedImage(width, height, sourceImage.getType());
		Image image = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Graphics gc = zoomImage.getGraphics();
		gc.setColor(Color.WHITE);
		gc.drawImage(image, 0, 0, null);
		return zoomImage;
	}

	/**
	 * 获取某个文件的前缀路径
	 * <p>
	 * 不包含文件名的路径
	 *
	 * @param file 当前文件对象
	 * @return
	 * @throws IOException
	 */
	public static String getFilePrefixPath(File file) throws IOException {
		String path = null;
		if (!file.exists()) {
			throw new IOException("not found the file !");
		}
		String fileName = file.getName();
		path = file.getPath().replace(fileName, "");
		return path;
	}

	/**
	 * 获取某个文件的前缀路径
	 * <p>
	 * 不包含文件名的路径
	 *
	 * @param path 当前文件路径
	 * @return 不包含文件名的路径
	 * @throws Exception
	 */
	public static String getFilePrefixPath(String path) throws Exception {
		if (null == path || path.isEmpty())
			throw new Exception("文件路径为空！");
		int index = path.lastIndexOf(File.separator);
		if (index > 0) {
			path = path.substring(0, index + 1);
		}
		return path;
	}

	/**
	 * 获取不包含后缀的文件路径
	 *
	 * @param src
	 * @return
	 */
	public static String getPathWithoutSuffix(String src) {
		String path = src;
		int index = path.lastIndexOf(".");
		if (index > 0) {
			path = path.substring(0, index + 1);
		}
		return path;
	}

	/**
	 * 获取文件名
	 *
	 * @param filePath 文件路径
	 * @return 文件名
	 * @throws IOException
	 */
	public static String getFileName(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new IOException("not found the file !");
		}
		return file.getName();
	}

	public static void pixelsStastics(String filePath) {
		try {
			File file = new File(filePath);
			BufferedImage image = ImageIO.read(file);
			int maxX = image.getWidth();
			int maxY = image.getHeight();
			for (int y = 0; y < maxY; y++) {
				for (int x = 0; x < maxX; x++) {
					System.out.println(image.getRGB(x, y));
				}
			}
		} catch (IOException ioe) {

		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 获取系统支持的图片格式
//        ImageCutterUtil.getOSSupportsStandardImageFormat();
		pixelsStastics("map/01_SiShuiGuan/00x00.png");
	}

	public static void cuttingMap() {
		try {
			int width = 32;
			int height = 32;

			File file = new File("map/1汜水关之战.jpg");

			int fx = 0;
			int fy = 0;

			for (int y = 0; y < 512; y += 32) {
				for (int x = 0; x < 896; x += 32) {
					String targetPath = "map/01_SiShuiGuan/" + String.format("%02d", fy) + "x"
							+ String.format("%02d", fx) + ".png";
					File destFile = new File(targetPath);
					ImageUtil.cutImage(file, destFile, x, y, width, height);
					fx++;
				}
				fx = 0;
				fy++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
