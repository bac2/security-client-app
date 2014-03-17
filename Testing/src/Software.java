
public class Software implements Comparable<Software> {
	private String name;
	private String versionString;
	
	public Software(String name, String versionString) {
		this.name = name;
		this.versionString = versionString;
	}

	public Software() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setVersionString(String versionString) {
		this.versionString = versionString;
	}
	
	public String toString() {
		return "{ name : " + name + ", versionString : " + versionString + " }";
	}

	@Override
	public int compareTo(Software other) {
		return this.name.toLowerCase().compareTo(
				other.getName().toLowerCase());
	}
}
