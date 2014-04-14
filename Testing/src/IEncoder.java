
public interface IEncoder {
	public void encodeList(Software[] list);
	public String getEncodedList();
	public void encodeDeviceName(String nickname);
	public void encodeOS(String os);
}
