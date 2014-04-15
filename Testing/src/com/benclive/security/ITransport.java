package com.benclive.security;

public interface ITransport {
	public void sendString(String data);
	public void setDestination(String dest);
}
