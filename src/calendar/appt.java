package calendar;

import java.time.LocalDate;

public class appt {
	LocalDate endDate;
	LocalDate startDate;
	String desc;
	String timeEnd;
	String timeStart;
	
	public appt(LocalDate startDate, LocalDate endDate, String desc, String timeStart, String timeEnd){
		this.endDate = endDate;
		this.startDate = startDate;
		this.desc = desc;
		this.timeEnd = timeEnd;
		this.timeStart = timeStart;
	}
	public appt(LocalDate startDate,  String desc, String timeStart){
		this.startDate = startDate;
		this.desc = desc;	
		this.timeStart = timeStart;
		endDate = null;
		timeEnd = null;	
	}
	public String toString(){
		if (timeEnd==null) {
			return desc + "\n" + timeStart + " on " + newDate(startDate);
		}
		else {
			return desc + "\n" + timeStart + " on " + newDate(startDate) + " to " + timeEnd + " on " + newDate(endDate);
		}
	}
	public String newDate(LocalDate reformat) {
		return reformat.toString().substring(5, reformat.toString().length()).replaceAll("-", "/") + "/" + reformat.toString().substring(0, 4).replaceAll("-", "/");
	}
	public String toAppend() {
		if (timeEnd != null)
			return startDate.getYear() + "," + startDate.getMonthValue() + "," + startDate.getDayOfMonth() + "," + desc + "," +
			timeStart + "," + endDate.getYear() + "," + endDate.getMonthValue() + "," + endDate.getDayOfMonth() + "," + timeEnd;
		else
			return startDate.getYear() + "," + startDate.getMonthValue() + "," + startDate.getDayOfMonth() + "," + desc + "," +
			timeStart;
		
	}
	
}
