package com.vis.msil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;

public class CheckWorkingHours extends DecisionElementBase {

	@SuppressWarnings({ "deprecation", "unlikely-arg-type" })
	@Override
	public String doDecision(String name, DecisionElementData data) throws Exception {

		String workingHoursFlag = "Y";

		HashMap<Integer, String> dayOfWeek = new HashMap<Integer, String>();
		dayOfWeek.put(1, "MON");
		dayOfWeek.put(2, "TUE");
		dayOfWeek.put(3, "WED");
		dayOfWeek.put(4, "THU");
		dayOfWeek.put(5, "FRI");
		dayOfWeek.put(6, "SAT");
		dayOfWeek.put(0, "SUN");

		// Reading valid Working days
		// Date workStartTime =
		// timeFormatter.parse(data.getSessionData(Constants.OFC_WORK_ST_HRS).toString());

		try {
			LocalTime workStartTime = LocalTime.parse(data.getSessionData(Constants.OFC_WORK_ST_HRS).toString());
			LocalTime workEndTime = LocalTime.parse(data.getSessionData(Constants.OFC_WORK_END_HRS).toString());

			String workingDays = data.getSessionData(Constants.WORK_DAYS).toString();
			String[] workingDaysArr = workingDays.split(",");
			ArrayList<String> workDaysList = new ArrayList<String>(Arrays.asList(workingDaysArr));
			
			String callHour = (String.valueOf(data.getStartDate().getHours())); 
			String callMin = (String.valueOf(data.getStartDate().getMinutes()));
			int callDay = (data.getStartDate().getDay());
			String callDayStr = dayOfWeek.get(callDay); 
			
			if(callMin.length()<2) {
				callMin = "0"+callMin;
			}
			
			if(callHour.length()<2) {
				callHour = "0"+callHour;
			}

			LocalTime currentCallTime = LocalTime.parse(callHour +":"+ callMin) ;

			data.addToLog("CheckWorkingHours : Current Call Time", String.valueOf(currentCallTime) + "||Office Start Time" + String.valueOf(workStartTime) + "||Office End Time" + String.valueOf(workEndTime));

			if (workDaysList.contains(callDayStr)) {
				if (currentCallTime.isAfter(workStartTime) && currentCallTime.isBefore(workEndTime)) {
					data.addToLog("CheckWorkingHours", "Caller calling in working hours");
				} else {
					data.addToLog("CheckWorkingHours", "Caller calling in Non-working hours");
					workingHoursFlag = "N";
				}
			} else {
				data.addToLog("CheckWorkingHours", "Caller calling in Non-Working Day");
				workingHoursFlag = "N";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workingHoursFlag;
	}

}
