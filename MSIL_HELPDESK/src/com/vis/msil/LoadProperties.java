/* This Class is used to load all necessary properties at application Start 
 * 
 */
package com.vis.msil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class LoadProperties {

	public Properties properties = new Properties();
	private String propPath;

	public Properties getProperties(String propPath) {
		try {
			FileInputStream fin;
			Properties prop = new Properties();
			fin = new FileInputStream(propPath);
			try {
				prop.load(fin);
				PropertyConfigurator.configure(prop);
				this.properties = prop;
				return this.properties;
			} catch (Exception fie) {
			} finally {
				if (fin != null) {
					fin.close();
					fin = null;
				}
			}
		} catch (FileNotFoundException fle) {
		} catch (IOException e) {
		} catch (Exception ex) {
		}
		return this.properties;
			
		
	}

	public String getPropPath() {
		return this.propPath;
	}

	public void setPropPath() {

	}

}
