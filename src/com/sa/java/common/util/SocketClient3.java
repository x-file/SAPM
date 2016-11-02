package com.sa.java.common.util;

public class SocketClient3 {
	private byte[] buf  = new byte[200]; //长度为200
	public String name = "";
	/**
	 * 返回要发送的数组
	 */
	public byte[] getbuf() {
		return buf;
	}


	/**
	 * 将byte数组转化成String
	 */
	private static String toStr(char[] valArr,int maxLen) {
		int index = 0;
		while(index < valArr.length && index < maxLen) {
			if(valArr[index] == 0) {
				break;
			}
			index++;
		}
		char[] temp = new char[index];
		System.arraycopy(valArr, 0, temp, 0, index);
		return new String(temp);
	}


	public static SocketClient3 getSocketClient(char[] bArr) {
		String name = "";
		name = toStr(bArr,200);  //长度为200
		return new SocketClient3(name);
	}

	/**
	 * 构造并转换
	 */
	public SocketClient3(String name) {
		this.name = name;
		byte[] temp = name.getBytes();
		System.arraycopy(temp, 0, buf, 0, temp.length);
	}

}
