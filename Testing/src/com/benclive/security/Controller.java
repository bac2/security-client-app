package com.benclive.security;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
	
	private List<Software> softwareList;
	private String uniqueId;
	private Properties configFile;
	private ITransport transport;
	private IFileGatherer fileGatherer;
	private TimerTask update;
	
	public Controller(IFileGatherer ifileGatherer) {
		//Load the config file
		configFile = getConfig();
		
		//Generate our Unique ID
        HMAC hmac = new HMAC();
        try {
			uniqueId = hmac.generateHmac();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Set up variables
	    transport = new HTTPTransport(uniqueId);
	    this.fileGatherer = ifileGatherer;
	    softwareList = fileGatherer.getInstalledSoftwareList();
	    
	    
	    //Check for new software and send the list every hour
        Timer timer = new Timer();
        update = new UpdateTask();
        timer.scheduleAtFixedRate(update, 1000, 1000*60*60); //Send every hour after startup.
        
        
        //Start the GUI
		new MainView("Security Client", this);

	}
	
	private void saveConfig() {
	    try {
	    	FileOutputStream fos = new FileOutputStream("src/client.properties");
			configFile.store(fos, "Client properties");
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Properties getConfig() {
		Properties configFile = new Properties();
	    try {
			configFile.load(this.getClass().getClassLoader().getResourceAsStream("client.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			//File not found
		}
	    return configFile;
	}
	//TODO: Implement GUI

	public List<Software> getSoftwareList() {
		return softwareList;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setRegistered(boolean b) {
		configFile.put("registered", Boolean.toString(b));
		saveConfig();
	}

	public boolean isRegistered() {
		return configFile.get("registered").equals("true");
	}

	private class UpdateTask extends TimerTask {

		@Override
		public void run() {
			if (isRegistered()) {
				//Get a new software list
        		softwareList = fileGatherer.getInstalledSoftwareList();
        		
        		//Encode it in JSON
    	        JSONEncoder jsonEnc = new JSONEncoder();
    	        jsonEnc.encodeList(softwareList);
    	        jsonEnc.encodeDeviceName("My Arbitrary Device");
    	        jsonEnc.encodeOS(System.getProperty("os.name"));
    	        
    	        System.out.println(jsonEnc.getEncodedList());
        		
    	        //Send it
        		transport.sendString(jsonEnc.getEncodedList());
    		}
		}
	}
	public static void main(String[] args) {

		String osName = System.getProperty("os.name");
		IFileGatherer fileGatherer;
		
		//Determine the OS and choose impl to use
		if (osName.startsWith("Windows")) {
			fileGatherer = new WindowsFileGatherer();	
		} else {
			fileGatherer = new LinuxFileGatherer();
		}
		
		new Controller(fileGatherer);
	}

	public void shutdown() {
		update.cancel();
		saveConfig();
		System.exit(0);
	}

}
