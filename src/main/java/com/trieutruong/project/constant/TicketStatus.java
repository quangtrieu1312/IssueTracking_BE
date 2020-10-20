package com.trieutruong.project.constant;

import java.util.List;
import java.util.Arrays;

public class TicketStatus {

	public final static String NEW = "NEW";
	public final static String ADMITTED = "ADMITTED";
	public final static String IN_PROCESS = "IN_PROCESS";
	public final static String ON_HOLD = "ON_HOLD";
	public final static String REVISED = "REVISED";
	public final static String SOLVED = "SOLVED";
	public final static String CLOSED = "CLOSED";
	public final static List<String> STATUSES = Arrays.asList(NEW, ADMITTED, IN_PROCESS, ON_HOLD, REVISED, SOLVED,
			CLOSED);
	public final static String DEFAULT = NEW;

}
