package com.benclive.security;
import java.util.List;


public interface IEncoder {
	public void encodeList(List<Software> list);
	public String getEncodedList();
	public void encodeDeviceName(String nickname);
	public void encodeOS(String os);
}
