import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class HMAC {

	public HMAC() {

	}
	
	public String generateHmac() {

		try {
			InetAddress ip = InetAddress.getLocalHost();
			String hostname = ip.getHostName();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] macAddress = network.getHardwareAddress();
			
			SecretKeySpec signingKey = new SecretKeySpec(macAddress, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			return toHexString(mac.doFinal(hostname.getBytes()));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "";
	}
	public String toHexString(byte[] bytes) {
		
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
		formatter.format("%02x", b);
		}
		 
		return formatter.toString();
	}
}
