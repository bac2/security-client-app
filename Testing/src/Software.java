
public class Software {
	private String name;
	private String versionString;
	
	public Software(String name, String versionString) {
		this.name = name;
		this.versionString = versionString;
	}

	public Software() {
		// TODO Auto-generated constructor stub
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
}
