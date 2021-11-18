package com.vis.msil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import com.audium.server.session.CallStartAPI;
import com.audium.server.session.DecisionElementData;

public class SessionBuilder {

	private HashMap<String, String> defaultValueMap = new HashMap<String, String>();
	private HashMap<String , String> monthToValueMap = new HashMap<String, String>();
	private ArrayList<String> nonZeroList = new ArrayList<String>();
	private DecisionElementData decdataAPI;
	
	public SessionBuilder(DecisionElementData decdata){
		this.decdataAPI = decdata;
	}
	
	public SessionBuilder() {
		// TODO Auto-generated constructor stub
	}

	public void setToSession(String elementName, Properties properties, CallStartAPI callStartAPI) {
		Enumeration<?> propertyNames = properties.propertyNames();
		buildDefaultValueMap();
		buildMonthToValueMap();
		buildNonZeroList();
		String strKey = "";
		String strValue = "";
		StringBuilder sb = new StringBuilder();
		try {
			while (propertyNames.hasMoreElements()) {
				strKey = (String) propertyNames.nextElement();
				strValue = properties.getProperty(strKey);

				if (strValue == null || strValue.equals("") || (strValue.equals("0") && nonZeroList.contains(strKey) )) {
					strValue = getDefaultValue(strKey);
				}
				if (sb.length()==0){
					sb.append("S_"+strKey+"="+strValue);
				}else {
					sb.append("|S_"+strKey+"="+strValue);			    	  
				}
				callStartAPI.setSessionData("S_" + strKey, strValue);
			}
			if(sb.length()>0) {
				callStartAPI.addToLog("Session builder:", sb.toString());
			}
		}

		catch (Exception e) {
			callStartAPI.addToLog("", "Exception Occured while setting the session");
		} finally {
			defaultValueMap = null;
			nonZeroList = null;
			propertyNames = null;
		}
		
	}
	
	/**
	 * Build the DefaultValues for properties Add to Map if any other Properties
	 * have defaultValue
	 */
	private void buildDefaultValueMap() {		
		defaultValueMap.put("MAX_TRIES", "3");
		defaultValueMap.put("REPEAT_TRIES", "3");
		defaultValueMap.put("REPEAT_MAXTRIES_COUNTER", "3");
		defaultValueMap.put("CONFIRM_MAXTRIES_COUNTER", "3");
		defaultValueMap.put("NO_INPUT_TIMEOUT", "5s");
		defaultValueMap.put("INTER_DIGIT_TIMEOUT", "2s");
		defaultValueMap.put("QUERY_TIMEOUT", "5");
		defaultValueMap.put("CONNECTION_TIMEOUT", "5s");
		defaultValueMap.put("TERM_CHAR", "#");
		defaultValueMap.put("TERM_TIMEOUT", "2s");
		defaultValueMap.put("FETCH_TIMEOUT", "12s");
		defaultValueMap.put("MEDIA_SERVER_PORT", "7000");
		defaultValueMap.put("IVR_LANG", "ENG");
		defaultValueMap.put("ICM_DNIS_VAR_NAME", "DNIS");
		defaultValueMap.put("ICM_ANI_VAR_NAME", "ANI");
		defaultValueMap.put("ICM_CALLID_VAR_NAME", "CALLID");

	}
	
	private void buildMonthToValueMap() {
		monthToValueMap.put("JAN", "01");
		monthToValueMap.put("FEB", "02");
		monthToValueMap.put("MAR", "03");
		monthToValueMap.put("APR", "04");
		monthToValueMap.put("MAY", "05");
		monthToValueMap.put("JUN", "06");
		monthToValueMap.put("JUL", "07");
		monthToValueMap.put("AUG", "08");
		monthToValueMap.put("SEP", "08");
		monthToValueMap.put("OCT", "10");
		monthToValueMap.put("NOV", "11");
		monthToValueMap.put("DEC", "12");
	}
	
	
	
	/**
	 * Build list of Properties that require non-zero value Add to List if any other
	 * Properties should not have zero
	 */
	private void buildNonZeroList() {
		nonZeroList.add("NO_INPUT_TIMEOUT");
		nonZeroList.add("QUERY_TIMEOUT");
		nonZeroList.add("LOGIN_TIMEOUT");
		nonZeroList.add("TERM_TIMEOUT");
		nonZeroList.add("INTER_DIGIT_TIMEOUT");
	}
	
	/** Get the default value from Map */
	private String getDefaultValue(String strKey)
	{
		String strDefaultValue = "";		
		if(defaultValueMap.containsKey(strKey)){
			strDefaultValue = (String)defaultValueMap.get(strKey);
		}
		
		if(monthToValueMap.containsKey(strKey)){
			strDefaultValue = (String)monthToValueMap.get(strKey);
		}
		return strDefaultValue;
	}
}
