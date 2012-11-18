package com.org.iheartrobotics.Ros;

import java.io.*;
import java.net.*;

public class RosClient {
	public enum State {
		GREETING, LISTENING, READING, WRITING, EXITING
	}
	
	public State currentState;
	public String connectionString;
	public int port;
	
	public RosClient(String connectionString, int port) {
		this.connectionString = connectionString;
		this.port = port;
		currentState = State.GREETING;
	}
	
	
	public boolean Greet() throws RosClientException, UnknownHostException, IOException {
		return false;
	}
	
	public void Listen() throws RosClientException {
	}
	
	public byte[] Read() throws RosClientException, UnknownHostException, IOException {
		return null;
	}
	
	public void Write() throws RosClientException {
	}
	
	public void Exit() throws RosClientException {
	}
	
	protected void Run() throws RosClientException, UnknownHostException, IOException {
		
		boolean running = true;
		
		while (running) {
			switch (currentState) {
				case GREETING:	// Establish a connection to the server
					if (!Greet()) {
						throw new RosClientException("Failed to initiate handshake with server.");
					} else {
						currentState = State.LISTENING;
					}
					break;
					
				case LISTENING:	// Listen to the server for any response
					Listen();
					break;
					
				case READING:	// Read incoming messages from the server
					Read();
					break;
					
				case WRITING:	// Write outgoing messages to the server
					Write();
					break;
					
				case EXITING:	// Close connection to server
					Exit();
					running = false;
					break;
					
				default:
					throw new RosClientException("Unknown state.");
			}
		}
	}
}