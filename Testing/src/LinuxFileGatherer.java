//Uses apt-show-versions to get information on Ubuntu
public class LinuxFileGatherer implements IFileGatherer {

	@Override
	public Software[] getInstalledSoftwareList() {
		// Get using different approachs on different OSes.
		// type -P <cmd> is a good one
		return new Software[1];
	}

}
