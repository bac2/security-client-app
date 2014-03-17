import java.util.Arrays;


public class Controller {
	
	Software[] softwareList;
	MainView main;
	
	public Controller(IFileGatherer fileGatherer) {
		
		main = new MainView("Security Client");
		
        softwareList = fileGatherer.getInstalledSoftwareList();
        String jsonEnc = new JSONEncoder().encodeList(softwareList);
        System.out.println(jsonEnc);

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

}
