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
	private String SERVER_URI;
	private boolean registered;
	private boolean registrationChecked;
	private String DEFAULT_SERVER = "http://bubuntu-vm:8000";
	
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
        registered = false;
        registrationChecked = false;
        SERVER_URI = configFile.getProperty("server");
        if (SERVER_URI == null) {
        	SERVER_URI = DEFAULT_SERVER;
        }
	    transport = new HTTPTransport(uniqueId, SERVER_URI);
	    this.fileGatherer = ifileGatherer;
	    softwareList = fileGatherer.getInstalledSoftwareList();
    
	    //Check for new software and send the list every hour
        Timer timer = new Timer();
        update = new UpdateTask();
        timer.scheduleAtFixedRate(update, 1000, 1000*60*60); //Send every hour after startup.
        
        //Start the GUI
		new MainView("VulMo Vulnerability Monitor", this);

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
	
	public List<Software> getSoftwareList() {
		return softwareList;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public synchronized boolean isRegistered() {
		try {
			if (!registrationChecked) {
				registered = transport.isRegistered();
				registrationChecked = true;
			}
			return registered;
		} catch (IOException e) {
			//Could not connect to server :(
			e.printStackTrace();
		}
		return false;
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

	public String getServerURI() {
		return SERVER_URI;
	}

	public void setServer(String serverURL) {
		configFile.setProperty("server", serverURL);
		System.out.println(serverURL);
		SERVER_URI = serverURL;
		transport = new HTTPTransport(uniqueId, SERVER_URI);
		saveConfig();
	}

}
