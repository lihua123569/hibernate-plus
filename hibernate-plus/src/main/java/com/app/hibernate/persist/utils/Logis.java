package com.app.hibernate.persist.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * 转换类型工具类
 *
 * @author Caratacus
 * @date 2016/6/24 0024
 */
public class Logis {
	public static final Object[] EMPTY_ARRAY = new Object[0];
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	public static final Object NULL = null;
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	/**
	 * 转换对象为Long
	 *
	 * @param obj
	 * @return Long
	 */
	public static Long getLong(Object obj) {
		return getLong(obj, null);
	}

	/**
	 * 转换对象为Long
	 *
	 * @param obj
	 * @param defaults
	 * @return Long
	 */
	public static Long getLong(Object obj, Long defaults) {
		if (obj == null) {
			return defaults;
		} else {
			if (obj.getClass() == Timestamp.class) {
				return ((Timestamp) obj).getTime();
			} else if (obj instanceof Number) {
				return ((Number) obj).longValue();
			} else {
				try {
					return Long.parseLong(obj.toString().trim());
				} catch (Exception e) {
					return defaults;
				}
			}
		}
	}

	/**
	 * 转换对象为long
	 *
	 * @param obj
	 * @return long
	 */
	public static long getlong(Object obj) {
		return getLong(obj, 0L);
	}

	/**
	 * 转换对象为Double
	 *
	 * @param obj
	 * @return Double
	 */
	public static Double getDouble(Object obj) {
		return getDouble(obj, null);
	}

	/**
	 * 转换对象为Double
	 *
	 * @param obj
	 * @param defaults
	 * @return Double
	 */
	public static Double getDouble(Object obj, Double defaults) {
		return obj == null ? defaults : Double.parseDouble(obj.toString());
	}

	/**
	 * 转换对象为double
	 *
	 * @param obj
	 * @return double
	 */
	public static double getdouble(Object obj) {
		return getDouble(obj, 0.0);
	}

	/**
	 * 转换对象为BigDecimal
	 *
	 * @param obj
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(Object obj) {
		return getBigDecimal(obj, BigDecimal.ZERO);
	}

	/**
	 * 转换对象为BigDecimal
	 *
	 * @param obj
	 * @param defaults
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(Object obj, BigDecimal defaults) {
		return StringUtils.EMPTY_STRING.equals(StringUtils.toString(obj)) ? defaults : new BigDecimal(obj.toString());
	}

	/**
	 * 转换对象为int
	 *
	 * @param obj
	 * @return int
	 */
	public static int getInt(Object obj) {
		return getInteger(obj, 0);
	}

	/**
	 * 转换对象为Integer
	 *
	 * @param obj
	 * @return Integer
	 */
	public static Integer getInteger(Object obj) {
		return getInteger(obj, null);
	}

	/**
	 * 转换对象为Integer
	 *
	 * @param obj
	 * @param defaults
	 * @return Integer
	 */
	public static Integer getInteger(Object obj, Integer defaults) {
		if (obj == null) {
			return defaults;
		} else if (obj.toString().length() == 0) {
			return defaults;
		}
		// 添加double 转换
		else if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else {
			try {
				int intTemp = Integer.valueOf(obj.toString().trim());
				return intTemp;
			} catch (Exception e) {
				return defaults;
			}
		}
	}

	/**
	 * 获取布尔字符串
	 *
	 * @param bo
	 * @return String
	 */
	public static String getBoolean(boolean bo) {
		return bo ? TRUE : FALSE;
	}

	/**
	 * 获取布尔值
	 *
	 * @param bo
	 * @return boolean
	 */
	public static boolean getBoolean(String bo) {
		return TRUE.equals(bo) ? true : false;
	}

	/**
	 * 获取当前线程执行的方法
	 * 
	 * @return
	 */
	public static String fail() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
}
