package com.org.iheartrobotics.Ros;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UdpRosClient extends RosClient {
	//TODO: Add support for UDP, maybe?
	public UdpRosClient(String connectionString, int port) {
		super(connectionString, port);
		throw new NotImplementedException();
	}
}
