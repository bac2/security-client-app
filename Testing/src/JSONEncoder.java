import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONEncoder implements IEncoder {
	private JSONObject obj;
	private JSONObject meta;
	
	public JSONEncoder() {
		obj = new JSONObject();
		meta = new JSONObject();
		obj.put("meta", meta);
	}

	@SuppressWarnings("unchecked")
	public void encodeList(List<Software> softwareList) {
		JSONArray arr = new JSONArray();
		arr.addAll(softwareList);
		obj.put("software", arr);
	}
	
	@Override
	public String getEncodedList() {
		return obj.toJSONString();
	}

	@Override
	public void encodeDeviceName(String nickname) {
		meta.put("nickname", nickname);
		
	}

	@Override
	public void encodeOS(String os) {
		meta.put("os_name", os);
		
	}

}
