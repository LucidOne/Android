package com.org.iheartrobotics.sandbox;

import java.io.IOException;
import java.net.UnknownHostException;

import com.org.iheartrobotics.Ros.*;

public class RosClientTest {
	public TcpRosClient client = null;
	public RosClientTest() {
		client = new TcpRosClient("localhost", 6789);
	}

	public void Init() {
		try {
			client.Greet();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
