package helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
	
	
	private static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM,Locale.FRANCE);
	
	


	public static Date toDate(String formattedDate){
		Date date = null;
		try {
			date = df.parse(formattedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String toText(Date date){
		return df.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(DateHelper.toDate("09/03/13 11:49:30"));
		System.out.println(DateHelper.toText(new Date()));
	}

}
