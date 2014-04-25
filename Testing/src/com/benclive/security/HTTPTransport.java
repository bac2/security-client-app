package com.benclive.security;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HTTPTransport implements ITransport {

	private String host, addr;
	private String crsftoken = "";
	private CookieManager manager;
	
	public HTTPTransport(String deviceUid, String server) {
		host = server;
		addr = "/api/device/" + deviceUid + "/";
		manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
		CookieHandler.setDefault(manager);
	}
	
	public boolean isRegistered() throws IOException {
		URL dest;
		HttpURLConnection conn = null;
		try {
			dest = new URL(host + addr);
			conn = (HttpURLConnection) dest.openConnection();
			conn.getContent();

			if (conn.getResponseCode() == 200) {

				return true;
			} else {
				
				InputStreamReader ir = new InputStreamReader(conn.getErrorStream());
				StringBuffer sb = new StringBuffer();
				while (ir.ready()) {
					sb.append(ir.read());
				}
				System.out.println(sb.toString());
				
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
