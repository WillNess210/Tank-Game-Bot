package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BotParameters {
	public static final String FILENAME = "parameters.properties";
	private Properties prop;
	public BotParameters() throws FileNotFoundException, IOException {
		this.prop = new Properties();
		this.prop.load(new FileInputStream(FILENAME));
	}
	public BotParameters(String customFile) throws FileNotFoundException, IOException {
		this.prop = new Properties();
		this.prop.load(new FileInputStream(customFile));
	}
	public int getInt(String propname) {
		return (int) this.prop.get(propname);
	}
	public String getString(String propname) {
		return (String) this.prop.get(propname);
	}
}
