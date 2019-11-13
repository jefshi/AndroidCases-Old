package com.csp.eclipselibrary.com.csp.eclipselibrary.android.model.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import com.common.constant.AConstant;

import android.content.Context;

/**
 * 文件工具类(使用了Android API)
 */
public class AFileUtil {
	/**
	 * 将对象写入到应用程序私有目录下的文件中
	 * @param context  Context
	 * @param object   对象
	 * @param fileName 文件名
	 * @return boolean 写入成功
	 */
	public static <T extends Serializable> boolean writeObject(T object, String fileName) {
		boolean isSuccess = false;

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(
					AConstant.context.openFileOutput(fileName, Context.MODE_PRIVATE));
			oos.writeObject(object);
			oos.flush();
			isSuccess = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return isSuccess;
	}

	/**
	 * 从应用程序私有目录下的文件中读取对象
	 * @param context  Context
	 * @param fileName 
	 * @return
	 */
	public static Serializable readObject(String fileName) {
		Serializable object = null;

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(
					AConstant.context.openFileInput(fileName));
			object = (Serializable) ois.readObject();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return object;
	}
}
