package com.csyk.due_date_calculator;


import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.csyk.due_date_calculator.business.DueDateCalculator;

public class DueDateCalculatorTest {
	
	@Test
	public void calculate_onThatDay_ExpectedBehavior() throws ParseException {
		
		//2018. September 24., Monday 9:00:00
		Date submitDate = new Date(1537779600000l);
		int turnaourndTime = 1;
		
		//2018. September 24., Monday 10:00:00
		Date expected = new Date(1537783200000l);
		Date dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaourndTime);
		assertEquals(expected, dueDate);
	}
	
	@Test
	public void calculate_nextDay_ExpectedBehavior() throws ParseException {
		
		//2018. September 24., Monday 9:00:00
		Date submitDate = new Date(1537779600000l);
		int turnaourndTime = 9;
		
		//2018. September 25., Tuesday 10:00:00
		Date expected = new Date(1537869600000l);
		Date dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaourndTime);
		assertEquals(expected, dueDate);
	}
	
	@Test
	public void calculate_afterWeekend_ExpectedBehavior() throws ParseException {
		
		//2018. September 21., Friday  9:00:00
		Date submitDate = new Date(1537520400000l);
		int turnaourndTime = 9;
		
		//2018. September 24., Monday 10:00:00
		Date expected = new Date(1537783200000l);
		Date dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaourndTime);
		assertEquals(expected, dueDate);
	}
	
	@Test
	public void calculate_afterWeekendWithMinutes_ExpectedBehavior() throws ParseException {
		
		//2018. September 21., Friday  9:11:00
		Date submitDate = new Date(1537521060000l);
		int turnaourndTime = 9;
		
		//2018. September 24., Monday 10:11:00
		Date expected = new Date(1537783860000l);
		Date dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaourndTime);
		assertEquals(expected, dueDate);
	}
	
	@Test
	public void calculate_zeroTurnaroundTime_ExpectedBehavior() throws ParseException {
		
		//2018. September 21., Friday  9:11:00
		Date submitDate = new Date(1537521060000l);
		int turnaourndTime = 0;
		
		//2018. September 21., Friday  9:11:00
		Date expected = new Date(1537521060000l);
		Date dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaourndTime);
		assertEquals(expected, dueDate);
	}
	
	@Test
	public void calculate_negativeTurnaroundTime_ExpectedBehavior() throws ParseException {
		
		//2018. September 21., Friday  9:11:00
		Date submitDate = new Date(1537521060000l);
		int turnaourndTime = -1;
		Date expected = null;		
		Date dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaourndTime);
		assertEquals(expected, dueDate);
	}
	
	@Test
	public void calculate_nullSubmitDate() throws ParseException {
		Date submitDate = null;
		int turnaourndTime = 9;
		Date expected = null;
		Date dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaourndTime);
		assertEquals(expected, dueDate);
	}
}
