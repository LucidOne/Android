package com.org.iheartrobotics.Ros;

public class RosClientException extends Exception {
	/**
	 * Exception class for Ros Client
	 */
	private static final long serialVersionUID = 1L;
	public RosClientException(String message) throws RosClientException {
		throw new RosClientException(message, null);
	}
	
	public RosClientException(String message, Exception ex) {
		super(message, ex);
	}
}
