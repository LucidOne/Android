package com.org.iheartrobotics.Ros;
import java.security.*;
import java.util.*;
import java.io.*;
import java.nio.*;

public class TcpRosMessage {
	
	public byte[] RawMessage;
	public int HeaderLength;
	public int MessageLength;
	public HashMap<String, String> Headers;
	public String Message;
	
	public TcpRosMessage() {	
		RawMessage = null;
		HeaderLength = 0;
		Headers = new HashMap<String, String>();
		MessageLength = 0;
		Message = null;
	}
	
	/**
	 * Return the message as a string
	 * @param messageBytes - the byte array to be processed
	 * @return A string representation of the message bytes 
	 */
	public static String ParseAsString(byte[] messageBytes) {
		return new String(messageBytes);
	}
	
	/**
	 * Parse the message bytes into a message container
	 * @param messageBytes - the byte array to be processed
	 * @return A container representing the contents of the message bytes
	 */
	public static TcpRosMessage Parse(byte[] messageBytes) {
		TcpRosMessage msg = new TcpRosMessage();
		msg.RawMessage = messageBytes;
				
		// first 4 bytes represent the header length
		int messageByteLength = 4;
		ByteBuffer bb = ByteBuffer.wrap(messageBytes, 0, messageByteLength);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		msg.HeaderLength = bb.getInt();
		
		int bytesRead = messageByteLength;
		int fieldSize = -1;
		String[] fieldValue = null;
		while (bytesRead < msg.HeaderLength) {
			// get field length
			bb = ByteBuffer.wrap(messageBytes, bytesRead, messageByteLength);
			fieldSize = bb.getInt();
			
			// get field value
			bb = ByteBuffer.wrap(messageBytes, bytesRead + messageByteLength, fieldSize);
			fieldValue = new String(bb.array()).split("=");
			
			// add values to map
			msg.Headers.put(fieldValue[0], fieldValue[1]);
			
			// increment number of bytes read
			bytesRead += messageByteLength + fieldSize;
		}
		
		// Read next 4 bytes, representing the message length
		bb = ByteBuffer.wrap(messageBytes, msg.HeaderLength, messageByteLength);
		fieldSize = bb.getInt();
		
		// Read the message
		bb = ByteBuffer.wrap(messageBytes, msg.HeaderLength + messageByteLength, fieldSize);
		msg.Message = new String(bb.array());
		
		return msg;
	}
	
	public static TcpRosMessage CreateMessage(String callerID, String topic, String message) throws RosClientException, NoSuchAlgorithmException, UnsupportedEncodingException {
		if ((callerID == null) || 
			(topic == null) || 
			(message == null)) {
			throw new RosClientException("Cannot create message with any null parameters.");
		}
		
		TcpRosMessage msg = new TcpRosMessage();
		
		int headerLength = 0;
		msg.Headers.put("message_definition", "string-data");
		headerLength += ("message_definition=string-data").length();
		
		msg.Headers.put("callerid", callerID);
		headerLength += ("callerid=" + callerID).length();
		
		msg.Headers.put("latching", "1");
		headerLength += ("latching=1").length();
				
		msg.Headers.put("topic", topic);
		headerLength += ("topic=" + topic).length();
		
		msg.Headers.put("type", "std_msgs/String");
		headerLength += ("type=std_msgs/String").length();
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		String md5 = md.digest(("type=std_msgs/String").getBytes("UTF8")).toString();
		msg.Headers.put("md5sum", md5);
		headerLength += ("md5sum=" + md5).length();
		
		msg.HeaderLength = headerLength;
		
		msg.Message = message;
		msg.MessageLength = message.length();
		
		return msg;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		
		TcpRosMessage that = (TcpRosMessage)obj;
		if ((this.RawMessage != that.RawMessage) ||
			(this.HeaderLength != that.MessageLength) ||
			(this.Headers != that.Headers) ||
			(this.MessageLength != that.MessageLength) ||
			(this.Message != that.Message)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		// arbitrary prime values
		int hash = 13;
		int prime1 = 17;
		int prime2 = 31;
		
		hash += (prime2 * this.HeaderLength)^prime1 + prime1;
		hash += (prime2 * (this.Headers != null ? 0 : this.Headers.hashCode()))^prime1 + prime1;
		hash += (prime2 * this.MessageLength)^prime1 + prime1;
		hash += (prime2 * (this.Message != null ? 0 : this.Message.hashCode()))^prime1 + prime1;
		hash += (prime2 * (this.RawMessage != null ? 0 : this.RawMessage.hashCode()))^prime1 + prime1;
		return hash;
	}
	
	@Override
	public String toString() {
		if (this.RawMessage != null) return new String(this.RawMessage);
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.HeaderLength);
		if (this.HeaderLength != 0) {
			for (String field : this.Headers.keySet()) {
				String fieldContents = field + "=" +  this.Headers.get(field);
				sb.append(fieldContents.length() + fieldContents);
			}
		}
		
		sb.append(this.MessageLength);
		if (this.MessageLength != 0) {
			sb.append(this.Message);
		}
		
		if (this.RawMessage == null) {
			// store this value for faster future calls
			try {
				this.RawMessage = sb.toString().getBytes("UTF8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
}
