package com.org.iheartrobotics.Ros;

import java.io.*;
import java.net.*;

public class TcpRosClient extends RosClient {
	
	public TcpRosClient(String connectionString, int port) {
		super(connectionString, port);
	}
	
	private BufferedReader reader = null;
	private DataOutputStream writer = null;
	private Socket serverSocket = null;
	
	@Override
	public boolean Greet() throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		// Establish the initial handshake with the server in question
		serverSocket = new Socket(connectionString, port);
		reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		writer = new DataOutputStream(serverSocket.getOutputStream());
		
		// Send a greeting to the client
		//TODO: Write a proper message to the ros server! (using the ROS/TCP connection guide)
		/*
		callerid: name of subscriber
		topic: name of the topic the subscriber is connecting to
		md5sum: md5sum of the message type
		type: message type
		*/
		
		writer.writeBytes("howdy\n");
		writer.flush();
		
		// See if the server responses with hello
		String response = reader.readLine();
		boolean connectionEstablished = response.equals("hello");
		if (connectionEstablished) {
			// really make sure that this person who sent the response is indeed who they say they are
			//EX: Check the connection header type and checksum of the message
		}
		return connectionEstablished;
	}
	
	@Override
	public void Listen() {
		// TODO Auto-generated method stub
		// Detect when reading the initial message size
	}
	
	@Override
	public byte[] Read() throws RosClientException, UnknownHostException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Write() {
		// TODO Auto-generated method stub
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}
}
