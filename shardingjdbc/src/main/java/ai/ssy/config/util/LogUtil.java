package ai.ssy.config.util;

import org.slf4j.LoggerFactory;

public class LogUtil {

	public static void error(String msg) {
		LoggerFactory.getLogger(getClassName()).error(msg);
	}

	public static void error(String msg, Object... obj) {
		LoggerFactory.getLogger(getClassName()).error(msg, obj);
	}

	public static void warn(String msg) {
		LoggerFactory.getLogger(getClassName()).error(msg);
	}

	public static void warn(String msg, Object... obj) {
		LoggerFactory.getLogger(getClassName()).error(msg, obj);
	}

	public static void info(String msg) {
		LoggerFactory.getLogger(getClassName()).info(msg);
	}

	public static void info(String msg, Object... obj) {
		LoggerFactory.getLogger(getClassName()).info(msg, obj);
	}

	public static void debug(String msg) {
		LoggerFactory.getLogger(getClassName()).debug(msg);
	}

	public static void debug(String msg, Object... obj) {
		LoggerFactory.getLogger(getClassName()).debug(msg, obj);
	}

	// 获取调用 error,info,debug静态类的类名
	private static String getClassName() {
		return new SecurityManager() {
			public String getClassName() {
				return getClassContext()[3].getName();
			}
		}.getClassName();
	}

     public static void error(String message, Throwable e) {
		 LoggerFactory.getLogger(getClassName()).error(message,e);
	 }


     public static void error(Throwable e) {
		 LoggerFactory.getLogger(getClassName()).error("异常:",e);
 	 }

}
