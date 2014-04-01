import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Controller {
	
	Software[] softwareList;
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
		

        String jsonEnc = new JSONEncoder().encodeList(softwareList);
        

        
        System.out.println(jsonEnc);
        ITransport transport = new HTTPTransport(uniqueId);
        transport.sendString(jsonEnc);
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

	public Software[] getSoftwareList() {
		return softwareList;
	}

	public String getUniqueId() {
		return uniqueId;
	}


}
