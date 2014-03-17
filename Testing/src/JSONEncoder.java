
public class JSONEncoder implements IEncoder {

	private StringBuffer stringBuffer;
	public JSONEncoder() {
		stringBuffer = new StringBuffer("[\n");
	}
	
	public String encodeList(Software[] list) {
		StringBuffer stringBuffer = new StringBuffer("[\n");
		for (Software s : list) {
			stringBuffer.append("{ name: "+ s.getName() + ", versionString: " + s.getVersionString() + " },\n");
		}
		
		return stringBuffer.append("]").toString();
	}
	
	@Override
	public void addSoftware(Software s) {
		stringBuffer.append("{ name: "+ s.getName() + ", versionString: " + s.getVersionString() + " },\n");
	}

	@Override
	public String getEncodedList() {
		return stringBuffer.append("]").toString();
	}

}
