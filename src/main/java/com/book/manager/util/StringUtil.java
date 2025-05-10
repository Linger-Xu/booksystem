package com.book.manager.util;


import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目通用工具类
 * @author Administrator
 *
 */
public class StringUtil {


	/**
	 * 返回指定格式的日期字符串
	 *
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String getFormatterDate(Date date, String formatter) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}

}
	


