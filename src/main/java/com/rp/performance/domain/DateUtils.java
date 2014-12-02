package com.rp.performance.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	private static SimpleDateFormat DF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static Date getDate() {
		String data = System.getProperty("DATA");
		if (StringUtils.isNotBlank(data)) {
			try {
				return parse(data);
			} catch (ParseException e) {
				// NADA A FAZER MESMO
			}
		}
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	public static Calendar getCalendar() {
		String data = System.getProperty("DATA");
		if (StringUtils.isNotBlank(data)) {
			try {
				Date d = parse(data);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				return c;
			} catch (ParseException e) {
				// NADA A FAZER
			}
		}
		return Calendar.getInstance();
	}

	private static Date parse(String data) throws ParseException {
		return DF.parse(data);
	}
}
