package com.vis.msil;

import com.audium.server.proxy.VoiceElementInterface;
import com.audium.server.session.ElementAPI;
import com.audium.server.xml.VoiceElementConfig;

public class GetEmergencyMsg implements VoiceElementInterface {

	@Override
	public VoiceElementConfig getConfig(String name, ElementAPI data, VoiceElementConfig defaults) {
		
		/** Get the element name for logging purpose. */
		String strElementName = data.getCurrentElement();
		data.addToLog("GetEmergencyMsg", "Inside " + this.getClass().getName());
		
		/** SET THE INITIAL AUDIO GROUP */
		VoiceElementConfig.AudioGroup initialAudioGroup = defaults.new AudioGroup("initial_audio_group", false);
		
		try {
			String emergencyFlag = (String) data.getSessionData(Constants.EMERGENCY_MSG_FLAG);

			if (emergencyFlag .equalsIgnoreCase("y")) {
				
			}
			else {
				
			}

			/** SET THE EWT initial audio.. */
			VoiceElementConfig.StaticAudio ewtAudio = defaults.new StaticAudio("agentQueueWaitTime.wav");
			initialAudioGroup.addAudioItem(ewtAudio);
			data.addToLog("EWT_PP", "Added audio item: ewtIs.wav");

			

		} catch (Exception e) {
			data.addToLog("EWT_PP", "Caught exception inside while playing EWT");
			data.addToLog("EWT_PP", "Application Error: " + e.getMessage());
		}
		return defaults;
	}

}
