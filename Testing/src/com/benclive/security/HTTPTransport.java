package com.benclive.security;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


public class HTTPTransport implements ITransport {

	private String host, addr;
	private int port;
	public HTTPTransport(String deviceUid) {
		host = "http://bubuntu-vm";
		addr = "/device/" + deviceUid + "/";
		port = 8000;

	}
	@Override
	public void sendString(String data) {
		URL dest;
		HttpURLConnection conn = null;
		try {
			dest = new URL(host + ":" + port + addr);
			conn = (HttpURLConnection) dest.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");

			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			
			out.write(data);
			out.flush();
			out.close();
			
			System.out.println(conn.getResponseCode());
			InputStreamReader in;
			if (conn.getResponseCode() != 200) {
				in = new InputStreamReader(conn.getErrorStream());
			} else {
				in = new InputStreamReader(conn.getInputStream());
			}

			StringBuffer inputString = new StringBuffer();
			while(in.ready()) {
				inputString.append((char) in.read());
			}
			//System.out.println("Data: "+ inputString.toString());
			conn.disconnect();
			
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
