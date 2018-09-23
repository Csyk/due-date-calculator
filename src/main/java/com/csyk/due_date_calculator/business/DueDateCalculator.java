package com.csyk.due_date_calculator.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.csyk.due_date_calculator.common.Constant;

public class DueDateCalculator {
	
	private DueDateCalculator() {}
	
	public static Date calculateDueDate(Date submitDate, int time) throws ParseException {
		Calendar startCal;
		Calendar endOfFirstDay;
		startCal = Calendar.getInstance();
		startCal.setTime(submitDate);
		
		endOfFirstDay = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String dayOfSubmitDate = Integer.toString(startCal.get(Calendar.DAY_OF_MONTH));
		String monthOfSubmitDate = Integer.toString(startCal.get(Calendar.MONTH) + 1);
		String yearOfSubmitDate = Integer.toString(startCal.get(Calendar.YEAR));
		String hourOfSubmitDate = Integer.toString(startCal.get(Calendar.HOUR_OF_DAY));
		String minuteOfSubmitDate = Integer.toString(startCal.get(Calendar.MINUTE));
		endOfFirstDay.setTime(simpleDateFormat.parse(dayOfSubmitDate + "/" + monthOfSubmitDate +
				"/" + yearOfSubmitDate + " 17:00"));
		long firstDayLeftover = endOfFirstDay.getTime().getTime() - startCal.getTime().getTime();
		long reminder = Integer.toUnsignedLong(time) * Constant.oneHourInMilliSec - firstDayLeftover;
		if(reminder <= 0) {
			String hourOfDueDate = Integer.toString(Integer.valueOf(hourOfSubmitDate) + time);
			return simpleDateFormat.parse(dayOfSubmitDate + "/" + monthOfSubmitDate +
					"/" + yearOfSubmitDate + " " + hourOfDueDate + ":" + minuteOfSubmitDate);
		}
		Date presentDate = nextWorkingDay(submitDate);
		while((float)reminder / (float)Constant.oneWorkingDayInMilliSec > 1) {
			presentDate = nextWorkingDay(presentDate);
			reminder -= Constant.oneWorkingDayInMilliSec;
		}
		long presentDateEpochTime = presentDate.getTime() + reminder;
		return new Date(presentDateEpochTime);
	}
	
	private static Date nextWorkingDay(Date startDate) throws ParseException {
		Calendar calendar;
		calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String dayOfStartDate = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		String monthOfStartDate = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String yearOfStartDate = Integer.toString(calendar.get(Calendar.YEAR));
		calendar.setTime(simpleDateFormat.parse(dayOfStartDate + "/" + monthOfStartDate +
				"/" + yearOfStartDate + " 09:00"));
		Date presentDate = calendar.getTime();
		do {
			long presentDateEpoch = presentDate.getTime() + (24 * Constant.oneHourInMilliSec);
			presentDate = new Date(presentDateEpoch);
			calendar.setTime(presentDate);
		}while(Arrays.asList(1, 7).contains(calendar.get(Calendar.DAY_OF_WEEK)));
		return presentDate;
	}
	
	//TODO: create calculateReminder, generateNewDate function
}
