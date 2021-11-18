package com.vis.msil;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.audium.server.AudiumException;
import com.audium.server.proxy.EndCallInterface;
import com.audium.server.session.CallEndAPI;

public class CallEnd implements EndCallInterface {

	@Override
	public void onEndCall(CallEndAPI callEndAPI) throws AudiumException {
		// TODO Auto-generated method stub

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");// 24hrs
		Date date = new Date();
		String CallStartDateTime = dateFormat.format(date);
		callEndAPI.setSessionData(Constants.CALL_ENDDATETIME, CallStartDateTime);
		callEndAPI.addToLog("", "CALL_ENDTIME >> " + CallStartDateTime);

		callEndAPI.getAllSessionData().entrySet().stream().forEach(e -> callEndAPI.addToLog("", e.toString()));
		callEndAPI.addToLog("CAll End", "CALL TERMINATED");
	}

}