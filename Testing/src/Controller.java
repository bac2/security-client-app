
public class Controller {
	
	Software[] softwareList;
	
	public Controller(IFileGatherer fileGatherer) {
        softwareList = fileGatherer.getInstalledSoftwareList();
        for (Software s : softwareList) {
        	System.out.println(s);
        }
	}

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
