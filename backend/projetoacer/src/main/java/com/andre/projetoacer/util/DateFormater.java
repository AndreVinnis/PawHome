package com.andre.projetoacer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateFormater {
	
	public static Integer returnAge(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String[] currentDate = sdf.format(new Date()).split("/");
		String[] referenceDate = sdf.format(data).split("/");
		
		if(Integer.parseInt(currentDate[1])>= Integer.parseInt(referenceDate[1])) {
			if(Integer.parseInt(currentDate[0])>= Integer.parseInt(referenceDate[0])){
				return Integer.parseInt(currentDate[2]) - Integer.parseInt(referenceDate[2]);
			}	
		}
		return Integer.parseInt(currentDate[2]) - Integer.parseInt(referenceDate[2]) - 1;
	}
	
	public Date returnDate(String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.parse(data);
	}
}
