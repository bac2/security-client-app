import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Controller {
	
	List<Software> softwareList;
	MainView main;
	String uniqueId;
	
	public Controller(IFileGatherer fileGatherer) {
        HMAC hmac = new HMAC();
        try {
			uniqueId = hmac.generateHmac();
		} catch (Exception e) {
			e.printStackTrace();
		}
        softwareList = fileGatherer.getInstalledSoftwareList();
        
		main = new MainView("Security Client", this);
		

        JSONEncoder jsonEnc = new JSONEncoder();
        jsonEnc.encodeList(softwareList);
        jsonEnc.encodeDeviceName("My Arbitrary Device");
        jsonEnc.encodeOS(System.getProperty("os.name"));
        System.out.println(jsonEnc.getEncodedList());
        

        ITransport transport = new HTTPTransport(uniqueId);
        transport.sendString(jsonEnc.getEncodedList());
	}
	
	//TODO: Implement GUI

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

	public List<Software> getSoftwareList() {
		return softwareList;
	}

	public String getUniqueId() {
		return uniqueId;
	}


}
