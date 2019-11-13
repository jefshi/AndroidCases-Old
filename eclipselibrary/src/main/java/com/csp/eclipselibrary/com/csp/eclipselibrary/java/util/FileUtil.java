package com.csp.eclipselibrary.com.csp.eclipselibrary.java.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * 文件系统应用类，支持Windows与UNIX系统
 * 内容: 文件(夹)创建/删除、文件读取/写入，文件上传/下载
 * @version 1.2
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-05-15 05:05:06
 * <p style='font-weight:bold'>AlterDate:</p> 2016-11-10 18:10:00
 */
public class FileUtil {
	public final static int	BUFFER_LENGTH	= 1048576;	// 1MB
	public final static int	EOF				= -1;

	// ===================================================
	// 其他
	// ===================================================
	/**
	 * 获取文件后缀
	 * @param filePath 文件路径
	 * @return String  后缀
	 */
	public static String getSuffix(String filePath) {
		int index = filePath.lastIndexOf(".") + 1;
		if (index > 0) {
			return filePath.substring(index, filePath.length());
		} else {
			return null;
		}
	}

	// ===================================================
	// 文件、目录：增、删、重命名、拷贝、移动
	// ===================================================
	/**
	 * 文件、目录创建(不覆盖已存在的文件或目录)
	 * @version 1.0.0
	 * @param filePath 指定的文件或目录路径。注：以"\\"或"/"结尾则表示该路径为目录。
	 * @return boolean: 是否成功创建(指定文件已存在则返回false)
	 * @throws IOException 
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-05-15 2:33:31
	 * <p style='font-weight:bold'>AlterDate:</p> 2016-09-22 16:41:57
	 */
	public static boolean createNew(String filePath) throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			return false;
		}

		if (filePath.endsWith("\\") || filePath.endsWith("/")) {
			return file.mkdirs();
		} else {
			return (file.getParentFile().exists() || createNew(file.getParent() + "/"))
					&& file.createNewFile();
		}
	}

	/**
	 * 文件、目录删除
	 * @version 1.0.0
	 * @param file 指定的文件或目录
	 * @return boolean 是否删除成功(指定文件不存在则返回false)
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-05-15 2:33:31
	 * <p style='font-weight:bold'>AlterDate:</p> 2016-09-22 16:41:57
	 */
	public static boolean delete(File file) {
		// 获取文件列表
		File[] fileAllList = getAllListFiles(file);
		if (fileAllList == null) {
			return false;
		}

		// 删除文件与目录
		boolean succeeded = true;
		for (int i = fileAllList.length - 1; i >= 0; i--) {
			if (!fileAllList[i].delete()) {
				succeeded = false;
			}
		}

		return succeeded;
	}

	/**
	 * 文件、目录重命名
	 * @param file    指定的文件或目录
	 * @param newName 新名称
	 * @return        是否重命名成功(指定文件不存在，则返回false)
	 */
	public static boolean rename(File file, String newName) {
		if (file.exists()) {
			String newFullName = file.getParentFile().getAbsoluteFile() + "/" + newName;
			return file.renameTo(new File(newFullName));
		}
		return false;
	}

	/**
	 * 文件、目录拷贝
	 * @param src 源路径
	 * @param dest 目标路径，必须是目录
	 * @param overwrite 是否覆盖
	 * @return boolean 是否拷贝成功()
	 * @throws IOException
	 */
	public static boolean copy(File src, File dest, boolean overwrite) throws IOException {
		// TODO
		// 文件检测
		if (!src.exists()) {
			return false;
		}

		if (src.isFile()) {
			if (dest.exists() && dest.isDirectory()) {
				return false;
			}
			copyFile(src, dest, overwrite);
		}

		if (src.isDirectory()) {
			if (dest.exists() && dest.isFile()) {
				return false;
			}
			// copyDir();
		}

		// 获取文件列表
		File[] fileAllList = getAllListFiles(src);
		if (fileAllList == null) {
			return false;
		}

		// 遍历文件列表，依次拷贝
		boolean succeeded = false;
		String srcFilePath = src.getAbsolutePath();
		String destFilePath = dest.getAbsolutePath();
		for (File file : fileAllList) {
			// 获取目标路径
			String relativePath = file.getAbsolutePath().substring(srcFilePath.length());
			File destFile = new File(destFilePath + relativePath);

			// 目录拷贝
			if (file.isDirectory()) {
				if (!destFile.mkdirs()) {
					succeeded = false;
					destFile.delete();
					break;
				}
				continue;
			}

			// 文件拷贝
			succeeded = copyFile(src, destFile, true);
		}

		return succeeded;
	}

	/**
	 * 目录拷贝
	 * @param src
	 * @param dest
	 * @param overwrite
	 * @return
	 * @throws IOException
	 */
	public static List<File> copyDirectory(File src, File dest, boolean overwrite) {
		// 拷贝失败的文件列表
		List<File> failFiles = new ArrayList<File>();

		// 目录检测
		if (!src.exists() || !src.isDirectory()
				|| dest.exists() && !dest.isDirectory()) {
			failFiles.add(src);
			return failFiles;
		}

		// 获取源目录文件列表
		File[] fileAllList = getAllListFiles(src);
		if (fileAllList == null) {
			failFiles.add(src);
			return failFiles;
		}

		// 遍历文件列表，依次拷贝
		String srcFilePath = src.getAbsolutePath();
		String destFilePath = dest.getAbsolutePath();
		for (File srcfile : fileAllList) {
			// 获取目标路径
			String relativePath = srcfile.getAbsolutePath().substring(srcFilePath.length());
			File destFile = new File(destFilePath + relativePath);

			// 文件拷贝
			try {
				System.out.println(srcfile.getAbsolutePath());
				if (srcfile.isFile() && !copyFile(srcfile, destFile, true)) {
					failFiles.add(destFile);
				}
			} catch (IOException e) {
				System.out.println("IOException");
				failFiles.add(destFile);
				continue;
			}
		}
		return failFiles;
	}

	public static void main(String[] args) throws IOException {
		File src = new File("F:\\zTest\\00.语言学习");
		File dest = new File("F:\\zTest\\test");
		List<File> failFiles = copyDirectory(src, dest, true);
		System.out.println(failFiles.toString());
	}

	/**
	 * 单个文件拷贝
	 * @param src       源文件
	 * @param dest      目标文件
	 * @param overwrite 是否覆盖
	 * @return          是否拷贝成功
	 * @throws IOException
	 */
	public static boolean copyFile(File src, File dest, boolean overwrite) throws IOException {
		// 文件检测
		if (!src.exists() || !src.isFile()) {
			return false;
		}

		// 文件拷贝
		boolean succeeded = false;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(src));
			succeeded = write(bis, dest, overwrite);
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
		return succeeded;
	}

	/**
	 * 文件、目录移动
	 * @version 1.0.0
	 * @param src
	 * @param dest
	 * @return
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-09-22 16:41:57
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	public static boolean move(File src, File dest) {
		// TODO
		return true;
	}

	// ===================================================
	// 文件内容列表
	// ===================================================
	/**
	 * 获取文件列表，含子目录的文件列表
	 * @version 1.0.0
	 * @param file 指定的文件或目录
	 * @return File[]: 输入为文件时，则仅包含其本身<br>
	 *                 输入为目录时，则还含该目录下所有文件与目录列表(含子目录)<br>
	 *                 列表顺序1: 父目录在前，其内容以及子目录内容均在其后<br>
	 *                 列表顺序2: 文件在前，目录在后<br>
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-09-23 12:18:03
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	public static File[] getAllListFiles(File file) {
		// 文件检测
		if (!file.exists()) {
			return null;
		}

		List<File> fileList = new ArrayList<File>();
		fileList.add(file);

		// 遍历目录
		for (int i = 0; i < fileList.size(); i++) {
			File fileItem = fileList.get(i);

			// 跳过文件
			if (fileItem.isFile()) {
				continue;
			}

			// 获取子目录内容列表
			File[] subFileList = fileItem.listFiles();
			ListIterator<File> fileIter = fileList.listIterator(i + 1);

			// 先添加文件
			for (File subFile : subFileList) {
				if (subFile.isFile()) {
					fileIter.add(subFile);
				}
			}

			// 再添加目录
			for (File subFile : subFileList) {
				if (subFile.isDirectory()) {
					fileIter.add(subFile);
				}
			}
		}

		return fileList.toArray(new File[fileList.size()]);
	}

	// ===================================================
	// 文件写入、读取
	// ===================================================
	/**
	 * TODO
	 * @param bis
	 * @param dest
	 * @param overwrite
	 * @return
	 * @throws IOException
	 */
	public static boolean write(BufferedInputStream bis, File dest, boolean overwrite) throws IOException {
		// 准备
		if (bis == null || (dest.exists() && !dest.isFile())) {
			return false;
		}

		// 创建目标目录
		File parentFile = dest.getParentFile();
		if (!parentFile.exists() && !parentFile.mkdirs()) {
			return false;
		}

		// 写入
		boolean succeeded = false;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(dest, !overwrite));
			byte[] bArr = new byte[Math.min(bis.available(), BUFFER_LENGTH)];

			int len = 0;
			while ((len = bis.read(bArr)) != EOF) {
				bos.write(bArr, 0, len);
				bos.flush();
			}
			succeeded = true;
		} finally {
			if (bos != null) {
				bos.close();
			}

			if (!succeeded) {
				dest.delete();
			}
		}
		return succeeded;
	}

	/**
	 * 文件写入
	 * @version 1.0.1
	 * @param filePath 文件名(含路径)
	 * @param append 是否附加
	 * @param content 文件内容
	 * @param encoding 文本编码
	 * @throws IOException 文件读取错误
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-05-15 02:43:47
	 * <p style='font-weight:bold'>AlterDate:</p> 2016-09-22 16:41:57
	 */
	public static void write(String filePath, boolean append, String[] content, String encoding) throws IOException {
		// 文件检测与新文本创建
		if (filePath.endsWith("\\") || filePath.endsWith("/")) {
			throw new IOException("Directory cann't write");
		}
		if (!new File(filePath).exists() && !createNew(filePath)) {
			throw new IOException("new file created is fail");
		}
		;

		// 设置默认文本编码
		if ("".equals(encoding) || encoding == null) {
			encoding = "utf-8";
		}

		// 换行符确认
		String lineFeed = "\n";
		if (System.getProperty("os.name").indexOf("Windows") > -1) {
			lineFeed = "\r" + lineFeed;
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(filePath, append), encoding));

			// 写入内容
			for (int i = 0; i < content.length; i++) {
				bw.write(content[i]);
				if (i != content.length - 1) {
					bw.write(lineFeed);
				}
			}
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * 文件读取
	 * @version  1.0.0
	 * @param filePath 文件名(含路径)
	 * @param encoding 文本编码
	 * @return String 文本内容
	 * @throws IOException 
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-5-15 17:05:10
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	public static String read(String filePath, String encoding) throws IOException {
		// 文件检测
		File file = new File(filePath);
		if (!file.exists() || file.isDirectory()) {
			throw new IOException("the file cann't read effectively");
		}
		;

		// 设置默认文本编码
		if ("".equals(encoding) || encoding == null) {
			encoding = "utf-8";
		}

		String content = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filePath), encoding));

			String line = null;
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return content;
	}
}
