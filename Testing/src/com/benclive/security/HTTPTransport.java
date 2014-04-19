package com.benclive.security;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HTTPTransport implements ITransport {

	private String host, addr;
	public HTTPTransport(String deviceUid, String server) {
		host = server;
		addr = "/device/" + deviceUid + "/";

	}
	
	public boolean isRegistered() throws IOException {
		URL dest;
		HttpURLConnection conn = null;
		try {
			dest = new URL(host + addr);
			conn = (HttpURLConnection) dest.openConnection();

			if (conn.getResponseCode() == 200) {
				return true;
			}
			conn.disconnect();
			
		} catch (IOException e) {
			throw e;
		}
		return false;
	}
	
	
	@Override
	public void sendString(String data) {
		URL dest;
		HttpURLConnection conn = null;
		try {
			dest = new URL(host + addr);
			conn = (HttpURLConnection) dest.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");

			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			
			out.write(data);
			out.flush();
			out.close();

			if (conn.getResponseCode() == 200) {
				conn.disconnect();
				return;
			}
			
			//Error case
			InputStreamReader in = new InputStreamReader(conn.getErrorStream());

			StringBuffer inputString = new StringBuffer();
			while(in.ready()) {
				inputString.append((char) in.read());
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}


	}

	@Override
	public void setDestination(String dest) {
		

	}

}
