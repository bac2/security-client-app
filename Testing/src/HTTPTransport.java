import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HTTPTransport implements ITransport {

	private String addr;
	private int port;
	public HTTPTransport() {
		addr = "http://fridge.lan";
		port = 5555;

	}
	@Override
	public void sendString(String data) {
		URL dest;
		try {
			dest = new URL(addr + ":" + port);
			URLConnection conn = dest.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			
			out.write(data);

			out.append("\n");
			out.flush();
			out.close();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void setDestination(String dest) {
		

	}

}
