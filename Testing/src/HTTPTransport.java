import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HTTPTransport implements ITransport {

	private String host, addr;
	private int port;
	public HTTPTransport(String deviceUid) {
		host = "http://localhost";
		addr = "/device/" + deviceUid + "/";
		port = 8000;

	}
	@Override
	public void sendString(String data) {
		URL dest;
		try {
			dest = new URL(host + ":" + port + addr);
			URLConnection conn = dest.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			
			out.write(data);

			out.append("\n");
			out.flush();
			out.close();
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			String inputString = "";
			while(in.ready()) {
				inputString += (char) in.read();
			}
			System.out.println(inputString);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@Override
	public void setDestination(String dest) {
		

	}

}
