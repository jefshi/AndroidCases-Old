package com.csp.library.java.fileSystem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Description: 文件/目录工具类
 * <p>1. 支持[Windows][UNIX]系统，[Mac]系统待测试
 * <p>2. 文件/目录：创建、删除、复制、移动/重命名
 * <p>3. 文件/目录：读取、写入、下载
 * <p>
 * <p>Create Date: 2016/05/15
 * <p>Modify Date: 2017/07/14
 *
 * @author csp
 * @version 1.0.3
 * @since JavaLibrary 1.0.0
 */
@SuppressWarnings({"unused", "UnusedAssignment", "WeakerAccess"})
public class FileUtil {
    private final static String UTF_8 = "utf-8";
    private final static int EOF = -1;
    private final static int MIN_BUFFER_LENGTH = 1_024; // 1 KB，不建议设置的很大

    /**
     * 判定字符串是否为空
     *
     * @param str 字符串
     * @return true: 字符串为空
     */
    private static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 获取文件后缀名
     *
     * @param filePath 文件路径
     * @return 后缀名
     */
    public static String getSuffix(String filePath) {
        int index = filePath.lastIndexOf(".");
        if (index > -1) {
            return filePath.substring(index + 1, filePath.length());
        } else {
            return null;
        }
    }

    /**
     * 获取目录文件列表, 模拟[ls]命令
     * <p>1. 模拟命令: ls TODO ls -a?
     *
     * @param file 文件或目录
     * @return 文件列表, 指定路径不存在则列表长度为0, 排列: 文件在前, 目录在后; 父目录在前, 子目录在后
     */
    public static File[] getListFiles(File file) {
        if (!file.exists())
            return new File[0];

        // 追加文件
        List<File> fileList = new ArrayList<>();
        fileList.add(file);

        // 遍历目录
        for (int i = 0; i < fileList.size(); i++) {
            File fileItem = fileList.get(i);

            // 跳过文件(已添加到集合中)
            if (fileItem.isFile()) {
                continue;
            }

            // 获取子目录内容列表
            File[] subFileList = fileItem.listFiles();
            ListIterator<File> fileIter = fileList.listIterator(i + 1);

            // 先添加子目录文件
            for (File subFile : subFileList) {
                if (subFile.isFile())
                    fileIter.add(subFile);
            }

            // 再添加子目录的子目录
            for (File subFile : subFileList) {
                if (subFile.isDirectory())
                    fileIter.add(subFile);
            }
        }

        return fileList.toArray(new File[fileList.size()]);
    }

    /**
     * 文件、目录创建
     * <p>1. 模拟命令: mkdir -p + touch, mkdir -p
     * <p>2. 创建指定目录或文件，若上层目录不存在，则先创建上层目录
     *
     * @param file   文件或目录
     * @param isFile true: 文件类型
     * @return true: 操作成功
     */
    public static boolean create(File file, boolean isFile) {
        if (isFile) {
            try {
                return file.getParentFile().mkdirs() && file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        } else {
            return file.mkdirs();
        }
    }

    /**
     * 文件、目录删除,
     * <p>1. 模拟命令: rm -r
     *
     * @param file 文件或目录
     * @return true: 操作成功
     */
    public static boolean delete(File file) {
        File[] list = getListFiles(file);
        boolean result = list.length != 0;
        for (int i = list.length - 1; i >= 0; i--)
            result &= list[i].delete();

        return result;
    }

    /**
     * 文件、目录复制
     * <p>1. 模拟命令: cp -r
     * <p>2. [dest], [src]均为绝对路径, 相对路径见[@see]说明
     * <p>3. [src]文件或目录复制到[dest]目录下
     * <p>4. [src]目录复制为[dest]目录
     * <p>5. [src]文件复制为[dest]文件
     *
     * @param src       源文件或目录
     * @param dest      目标文件或目录
     * @param overwrite true: 覆盖
     * @return true: 操作成功
     * @see #copy(String, String, boolean)
     */
    public static boolean copy(File src, File dest, boolean overwrite) {
        if (!dest.exists())
            overwrite = true;

        if (!overwrite)
            return true;

        if (src.isDirectory() && dest.isFile()
                || src.isFile() && dest.isDirectory()) {
            return false;
        }

        File[] list = getListFiles(src);
        boolean result = list.length != 0;
        String destPath = dest.getAbsolutePath();
        int srcPathLength = src.getAbsolutePath().length();
        String filePath = null;
        File destFile = null;
        for (File srcFile : list) {
            filePath = srcFile.getAbsolutePath();
            filePath = filePath.substring(srcPathLength, filePath.length());
            destFile = new File(destPath + filePath);

            if (srcFile.isDirectory())
                result &= srcFile.list().length == 0 ?
                        destFile.mkdirs() : (destFile.mkdirs() || result);
            else
                try {
                    result &= copyFile(srcFile, destFile);
                } catch (IOException e) {
                    result = false;
                }
        }
        return result;
    }

    /**
     * 文件、目录复制
     * <p>1. 模拟命令: cp -r
     * <p>TODO 2. [dest], [src]可能是相对路径
     * <p>3. 其他说明见[@see]说明
     *
     * @param src       源文件或目录
     * @param dest      目标文件或目录
     * @param overwrite true: 覆盖
     * @return true: 操作成功
     * @see #copy(File, File, boolean)
     */
    @Deprecated
    public static boolean copy(String src, String dest, boolean overwrite) {
        return false;
    }

    /**
     * 文件拷贝(默认覆盖)
     *
     * @param src  源文件
     * @param dest 目标文件
     * @return true: 操作成功, false: [src]为目录, 或[dest]为目录, 或操作失败
     * @throws IOException If an I/O error occurred
     */
    private static boolean copyFile(File src, File dest) throws IOException {
        FileInputStream fis = null;
        boolean result = false;
        try {
            fis = new FileInputStream(src);
            result = write(fis, dest, false);
        } finally {
            if (fis != null)
                fis.close();
        }
        return result;
    }

    /**
     * 文件、目录移动/重命名
     * <p>1. 模拟命令: mv
     * <p>2. [dest], [src]均为绝对路径, 相对路径见[@see]说明
     * <p>3. [dest]为目录(存在, 下同), 则[src]移动到[dest]目录下
     * <p>4. [dest]为文件, 则[src]文件移动并覆盖为[dest]文件，或不移动(操作成功)
     * <p>5. [dest]为文件, 则[src]目录移动失败(操作失败)
     * <p>6. [dest]不存在, 则[src]移动为[dest]
     * <p>7. [src]不存在, 则移动失败(操作失败)
     *
     * @param src       源文件或目录
     * @param dest      目标文件或目录
     * @param overwrite true: 覆盖
     * @return true: 操作成功
     * @see #move(String, String, boolean)
     */
    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean move(File src, File dest, boolean overwrite) {
        if (!src.exists() || dest.isFile() && src.isDirectory())
            return false;

        if (!dest.exists())
            return src.renameTo(dest);

        if (dest.isFile())
            if (overwrite)
                return dest.delete() && src.renameTo(dest);
            else
                return true;

        String destPath = dest.getAbsoluteFile() + File.separator + src.getName();
        return src.renameTo(new File(destPath));
    }

    /**
     * 文件、目录移动/重命名
     * <p>1. 模拟命令: mv
     * <p>TODO 2. [dest], [src]可能是相对路径
     * <p>3. 其他说明见[@see]说明
     *
     * @param src       源文件或目录
     * @param dest      目标文件或目录
     * @param overwrite true: 覆盖
     * @return true: 操作成功
     * @see #move(File, File, boolean)
     */
    @Deprecated
    public static boolean move(String src, String dest, boolean overwrite) {
        return false;
    }

    /**
     * 文件读取(默认编码UTF-8)
     *
     * @param src      源文件
     * @param encoding 文本编码
     * @return 文本内容
     * @throws IOException if an I/O error occurs.
     */
    public static String read(File src, String encoding) throws IOException {
        String content = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(src),
                    isEmpty(encoding) ? UTF_8 : encoding
            ));

            String line = null;
            while ((line = br.readLine()) != null) {
                content += line + '\n';
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return content;
    }

    /**
     * 文件写入(二进制方式)
     *
     * @param is     输入流
     * @param dest   目标文件
     * @param append true: 追加内容
     * @return true: 操作成功
     * @throws IOException if an I/O error occurs.
     */
    public static boolean write(InputStream is, File dest, boolean append) throws IOException {
        create(dest.getParentFile(), false);
        try (BufferedInputStream bis = new BufferedInputStream(is);
             BufferedOutputStream bos = new BufferedOutputStream(
                     new FileOutputStream(dest, append))) {
            int len;
            byte[] bArr = new byte[MIN_BUFFER_LENGTH];
            while ((len = bis.read(bArr)) != EOF) {
                bos.write(bArr, 0, len);
                bos.flush();
            }
        }
        return true;
    }

    /**
     * 文件写入(二进制方式)
     *
     * @param data   二进制数据
     * @param dest   目标文件
     * @param append true: 追加内容
     * @return true: 操作成功
     * @throws IOException if an I/O error occurs.
     */
    public static boolean write(byte[] data, File dest, boolean append) throws IOException {
        try (BufferedOutputStream bos =
                     new BufferedOutputStream(
                             new FileOutputStream(dest, append))) {
            bos.write(data);
            bos.flush();
        }
        return true;
    }

    /**
     * 文件写入(文本方式, 默认编码UTF-8)
     *
     * @param content  写入内容
     * @param dest     目标文件
     * @param encoding 文本编码
     * @param append   true: 追加内容
     * @return true: 操作成功
     * @throws IOException if an I/O error occurs.
     */
    public static boolean write(String content, File dest, String encoding, boolean append) throws IOException {
        // CR/LF
        String OS = System.getProperty("os.name");
        if (OS.contains("Windows")) {
            content = content.replaceAll("\n", "\r\n");
        } else if (OS.contains("Mac")) {
            content = content.replaceAll("\n", "\r");
        }

        // 写入文本
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(dest, append),
                    isEmpty(encoding) ? UTF_8 : encoding
            ));
            bw.write(content);
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
        return true;
    }

    /**
     * 文件下载
     *
     * @param url       URL
     * @param dest      目标文件
     * @param overwrite true: 覆盖
     * @return true: 操作成功
     * @throws IOException if an I/O error occurs.
     */
    public static boolean download(String url, File dest, boolean overwrite) throws IOException {
        InputStream in = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            in = connection.getInputStream();
            write(in, dest, !overwrite);
        } finally {
            if (in != null)
                in.close();
        }
        return true;
    }
}
