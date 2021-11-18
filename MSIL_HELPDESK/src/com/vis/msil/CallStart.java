package com.vis.msil;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.audium.server.AudiumException;
import com.audium.server.proxy.StartCallInterface;
import com.audium.server.session.CallStartAPI;

public class CallStart implements StartCallInterface {

	@Override
	public void onStartCall(CallStartAPI callStartAPI) throws AudiumException {
		// TODO Auto-generated method stub

		try {
			callStartAPI.addToLog("", "-------------------------------------------------------------------");
			callStartAPI.addToLog("", "*************   MSIL-Helpdesk Main Code - NEW CALL ARRIVED	 ***********");
			callStartAPI.addToLog("", "-------------------------------------------------------------------");

			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");// 24hrs
			Date date = new Date();
			String CallStartDateTime = dateFormat.format(date);
			callStartAPI.setSessionData(Constants.CALL_STARTDATETIME, CallStartDateTime);
			callStartAPI.addToLog("", "CALL_STARTDATETIME >> " + CallStartDateTime);

			StringBuilder sblog_details = new StringBuilder();
			Properties appConfigProperties = new Properties();
			String strElementName = "CallStart";
			SessionBuilder sessionBuilder = new SessionBuilder();

			try {
				InetAddress serveraddr = InetAddress.getLocalHost();
				String strCVPVXMLServerIP = serveraddr.getHostAddress();
				String strCVPVXMLServerName = serveraddr.getHostName();
				callStartAPI.setSessionData(Constants.VXMLSERVER_IPADDRESS, strCVPVXMLServerIP);
				callStartAPI.setSessionData(Constants.VXMLSERVER_NAME, strCVPVXMLServerName);
			} catch (UnknownHostException e) {
				sblog_details.append("  Can't detect localhost : ERROR: " + e.getMessage());
			}

			try {
				appConfigProperties = (Properties) callStartAPI.getApplicationAPI()
						.getApplicationData(Constants.APP_IVR_PROP);
				callStartAPI.addToLog("", " :: Application Properties object is retrieved from Application data");
				if (appConfigProperties == null) {
					appConfigProperties = (new LoadProperties())
							.getProperties(Constants.APPLICATION_CONFIG_PATH + Constants.APPLICATION_PROPERTY_FILE);
					callStartAPI.addToLog("",
							" :: application Properties object is NULL, hence reading from property file again");

				}
			} catch (Exception e) {
				callStartAPI.addToLog("",
						" :: Exception occurred while retrieving Application Properties object, hence reading from property file again. Exception: "
								+ e.getMessage());
				appConfigProperties = (new LoadProperties())
						.getProperties(Constants.APPLICATION_CONFIG_PATH + Constants.APPLICATION_PROPERTY_FILE);
			}
			sessionBuilder.setToSession(strElementName, appConfigProperties, callStartAPI);

			callStartAPI.setDefaultAudioPath((String) callStartAPI.getSessionData(Constants.STANDARD_PROMPTS_PATH));

			/**
			 * ---------------Get the call variable names configured in ICM from config.
			 * file.-----------------
			 */

			String strANI = (String) callStartAPI.getAni();
			String strDNIS = (String) callStartAPI.getDnis();

			callStartAPI.addToLog("", "ANI received from system: " + strANI);
			callStartAPI.addToLog("", "DNIS received from system: " + strDNIS);

			callStartAPI.setSessionData(Constants.ANI, strANI);
			callStartAPI.setSessionData(Constants.DNIS, strDNIS);

			boolean emergency_flag = checkExists(callStartAPI,
					callStartAPI.getSessionData(Constants.EMERGENCY_PATH).toString(),
					callStartAPI.getSessionData(Constants.EMERGENCY_MSG).toString());
			String emergency_flag_str = (emergency_flag == true) ? "Y" : "N";
			callStartAPI.addToLog("file Check value", emergency_flag_str);
			callStartAPI.setSessionData(Constants.EMERGENCY_MSG_FLAG, emergency_flag_str);

			if (sblog_details.length() > 0) {
				callStartAPI.addToLog("Session builder:", sblog_details.toString());
			}

		} catch (Exception ex) {
			callStartAPI.addToLog("Error in call start class", ex.getMessage());
		}

	}

	private boolean checkExists(CallStartAPI callStartAPI, String directory, String file) {

		String temp = file;
		callStartAPI.addToLog("file to search", temp);
		boolean check = new File(directory, temp).exists();
		return check;
	}

}
