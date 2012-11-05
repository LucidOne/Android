package com.org.iheartrobotics.allemosmustdie;

import java.util.*;
import android.util.Log;

public class EmoFacesException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum ERROR_CODE {
		INIT_FAILURE,
		GENERAL_ERROR
	}
	static HashMap<ERROR_CODE, String> ErrorDetail = new HashMap<ERROR_CODE, String>() {
		private static final long serialVersionUID = 1L;

	{
		put(ERROR_CODE.INIT_FAILURE, "Failed to initialize the application.");
		put(ERROR_CODE.GENERAL_ERROR, "An error occured with the EmoFaces app.");
	}};
	
	public EmoFacesException()
	{
		this(ERROR_CODE.GENERAL_ERROR.toString(), 
			ErrorDetail.get(ERROR_CODE.GENERAL_ERROR), 
			Log.INFO,
			null);
	}
	
	public EmoFacesException(String msg, boolean isSevere)
	{
		this(ERROR_CODE.GENERAL_ERROR.toString(), 
			msg, 
			isSevere ? Log.ERROR : Log.INFO,
			null);
	}

	EmoFacesException(String tag, String msg, int logLevel, Exception ex) {
		super(msg, ex);
		switch (logLevel) {
			case Log.DEBUG:
				Log.d(tag, msg);
			case Log.INFO:
				Log.i(tag, msg);
			case Log.WARN:
				Log.w(tag, msg);
			case Log.ERROR:
				Log.e(tag, msg);
		}
	}
	
	public static EmoFacesException InitError(Exception e) throws EmoFacesException {
		throw new EmoFacesException(ERROR_CODE.INIT_FAILURE.toString(), "Failed to load the default emotions.", Log.ERROR, e);
	}
}
