package com.benclive.security;

import java.io.IOException;

public interface ITransport {
	public void sendString(String data);
	public boolean isRegistered() throws IOException;
	public void setDestination(String dest);
}
