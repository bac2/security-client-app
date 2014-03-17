import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WindowsFileGatherer implements IFileGatherer {

	List<Software> softwareList = new ArrayList<Software>();
	
	public void getDisplayName(Software soft, String line) {
		Pattern displayName = Pattern.compile("DisplayName.*");
		Matcher match = displayName.matcher(line);
		if (match.find()) {
			soft.setName(match.group(0).split("    ")[2]);
		}
	}
	
	public void getDisplayVersion(Software soft, String line) {
        Pattern displayVersion = Pattern.compile("DisplayVersion.*");
		Matcher match = displayVersion.matcher(line);
		if (match.find()) {
			soft.setVersionString(match.group(0).split("    ")[2]);
		}
	}
	
	public void getSoftwareFromOS() {
		runRegCommand("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall /s /f Display*");
		runRegCommand("HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall /s /f Display*");
		
	}
	
	private void runRegCommand(String query) {
		try {
			// Run reg query, then read output with StreamReader (internal class)
	        
	        Process process = Runtime.getRuntime().exec("reg QUERY " + query);
	        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line = "";
	        Pattern itemStart = Pattern.compile("HKEY.*");
	        
	        while ( (line = br.readLine()) != null) {

	        	if( itemStart.matcher(line).matches()) {
		        	//Start a group
        			Software s = new Software();
	        		while (!(line = br.readLine()).equals("") && line != null) {
	        			getDisplayName(s, line);
	        			getDisplayVersion(s, line);
	        		}
	        		if (s.getName() != null) {
	        			softwareList.add(s);
	        		}
	        	}
	        }
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Software[] getInstalledSoftwareList() {
		getSoftwareFromOS();
		return softwareList.toArray(new Software[0]);
	}

}
