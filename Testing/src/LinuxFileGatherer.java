
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//Uses apt-show-versions to get information on Ubuntu
public class LinuxFileGatherer implements IFileGatherer {

	private String[] cmds = {"yum", "apt-show-versions"};
	@Override
	public List<Software> getInstalledSoftwareList() {
		// Get using different approachs on different OSes.
		// which <cmd> is a good one
		List<Software> installed = findInstalledPackageManager();
		
		return installed;
	}
	
	private List<Software> findInstalledPackageManager() {
		List<Software> installed = new ArrayList<Software>();
		for (String cmd : cmds) {
			Process process;
			try {
				process = Runtime.getRuntime().exec("which " + cmd);
		        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		        String line = br.readLine();
		        if (line != null) {
		        	//Found a working package manager
		        	Process versions = Runtime.getRuntime().exec(cmd);
			        BufferedReader output = new BufferedReader(new InputStreamReader(versions.getInputStream()));
			        
			        String item = "";
			        
			        while ((item = output.readLine()) != null) {
			        	
			        	String[] parts = item.split(" ");
			        	String name = parts[0].split(":")[0];
			        	String version = parts[1].split("-")[0];
			        	
			        	Software s = new Software();
			        	s.setName(name);
			        	s.setVersionString(version);
			        	installed.add(s);
			        }
		        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return installed;
	}
}
