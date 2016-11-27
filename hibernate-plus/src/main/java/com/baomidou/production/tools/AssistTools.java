/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Caratacus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.baomidou.production.tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssistTools {
	private static SimpleDateFormat DATEFORMAT = null;

	/**
	 * 
	 * @param source
	 * @param defaultVale
	 */
	public static String ifNull(String source, String defaultVale) {
		if (source == null || "".equals(source))
			return defaultVale;
		return source;
	}

	public static void ifNullToErr(Object source, String errMsg)
			throws Exception {
		if (source == null || "".equals(source)) {
			throw new Exception(errMsg);
		}
	}

	public static void ifExistsToErr(String path, String errMsg)
			throws Exception {
		File file = new File(path);
		if (file.exists()) {
			throw new Exception(errMsg);
		}
	}

	/**
	 * 当前时间
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		DATEFORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return DATEFORMAT.format(new Date());
	}

	/**
	 * 当前字符串首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUppercase(String str) {
		String tem = str;
		if (tem != null && tem.length() > 0) {
			String b = tem.substring(0, 1);
			String e = tem.substring(1, tem.length());

			tem = b.toUpperCase() + e;
		}
		return tem;
	}

	/**
	 * 当前字符串首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowercase(String str) {
		String tem = str;
		if (tem != null && tem.length() > 0) {
			String b = tem.substring(0, 1);
			String e = tem.substring(1, tem.length());

			tem = b.toUpperCase() + e;
		}
		return tem;
	}

	/**
	 * 
	 * @param source
	 *            源目录
	 * @param toFile
	 *            目标目录
	 */
	public static void copyFile(File source, File toFile) {
		if (source.isFile()) {
			copy(source, toFile);
		} else {
			File[] files = source.listFiles();
			for (File file : files) {
				copyFile(file, new File(toFile.getAbsoluteFile()
						+ File.separator + file.getName()));
			}
		}
	}

	public static void copy(File srcFile, File destFile) {
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteAllFile(File destFile) {
		if (destFile.exists()) {
			if (destFile.isFile()) {

			} else {
				File[] files = destFile.listFiles();
				for (File file : files) {
					deleteAllFile(file);
				}
			}
			destFile.delete();
		}
	}

}
