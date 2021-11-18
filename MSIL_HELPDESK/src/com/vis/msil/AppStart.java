package com.vis.msil;

import java.util.Properties;
import com.audium.server.AudiumException;
import com.audium.server.global.ApplicationStartAPI;
import com.audium.server.proxy.StartApplicationInterface;

public class AppStart implements StartApplicationInterface {

	@Override
	public void onStartApplication(ApplicationStartAPI appStartAPI) throws AudiumException {
		// TODO Auto-generated method stub

		System.out.println("Diageo Helpdesk Application Started");

		LoadProperties loadProps = new LoadProperties();

		String configFilePath = Constants.APPLICATION_CONFIG_PATH;
		String appPropFile = Constants.APPLICATION_PROPERTY_FILE;

		Properties appConfigProperties = loadProps.getProperties(configFilePath + appPropFile);
		appStartAPI.setApplicationData(Constants.APP_IVR_PROP, appConfigProperties);
	}

}
