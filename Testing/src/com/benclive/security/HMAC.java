package com.benclive.security;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class HMAC {

	public HMAC() {

	}
	
	public String generateHmac() throws Exception {

		try {
			//Find the MAC address of an available interface (eth0 in Linux)
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			byte[] macAddress = null;
			String hostname = "";
			while (en.hasMoreElements() && (macAddress == null || macAddress.length == 0)) {
				NetworkInterface network = en.nextElement();
				Enumeration<InetAddress> addresses = network.getInetAddresses();
				while (addresses.hasMoreElements() && (macAddress == null || macAddress.length == 0)) {
					InetAddress ip = addresses.nextElement();

					NetworkInterface netinterface = NetworkInterface.getByInetAddress(ip);

					macAddress = netinterface.getHardwareAddress();
					hostname = ip.getHostName();
				}
			}
			if (macAddress == null) {
				throw new Exception("Couldn't find any network interfaces");
			}

			SecretKeySpec signingKey = new SecretKeySpec(macAddress, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			return toHexString(mac.doFinal(hostname.getBytes()));
		} catch (InvalidKeyException e) {
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
		
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
		formatter.format("%02x", b);
		}
		 
		return formatter.toString();
	}
}
