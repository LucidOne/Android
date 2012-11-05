package com.org.iheartrobotics.drinktray;

import java.util.*;
import java.io.*;
import android.annotation.TargetApi;
import android.util.JsonReader; //TODO: Maybe get a better json reader? (potential optimizaton)

/**
 * A class capable of parsing text/files and generating Emo objects
 * @author Errol Markland
 *
 */
@TargetApi(11)
public class EmoFaceReader 
{	
	public EmoFaceReader()
	{		
	}
	
	/**
	 * Returns a list of Emo objects read from a file
	 * @param filepath - The file which contains the information about emoticons.
	 * @return A list of Emo Objects
	 * @throws IOException - File may not exist at specified path 
	 */
	public ArrayList<Emo> readEmoticonsFromFile(String filepath) throws IOException {
		return (filepath == null) ? null : readEmoticonsFromStream(new FileReader(filepath));
	}
	
	/**
	 * Returns a list of Emo objects reads from a json array
	 * @param jsonText - The json text containing emoticon information
	 * @return - A list of Emo Objects.
	 * @throws IOException - Error with string being read
	 */
	public ArrayList<Emo> ReadEmoticonsFromJson(String jsonText) throws IOException {
		return (jsonText == null) ? null : readEmoticonsFromStream(new StringReader(jsonText));
	}
	
	/**
	 * Parse the json data from the reader
	 * @param in - The reader containing the emoticon data
	 * @return - A hash map of emoticons
	 * @throws IOException - There is an issue with the input reader
	 */
	@SuppressWarnings("resource")
	public ArrayList<Emo> readEmoticonsFromStream(Reader in) {
		ArrayList<Emo> emotionList = new ArrayList<Emo>();
		JsonReader reader = new JsonReader(in);
		try {
			reader.beginArray();
			// Loop through the json objects
			while (reader.hasNext()) {
				String name = null;
				String emoticon = null;
				String source = null;
				
				reader.beginObject();
				// Loop through the object's key-value pairs
				while (reader.hasNext()) {
					String key = reader.nextName().toLowerCase();
					if (key.equals("name")) {				
						name = reader.nextString();
					} else if (key.equals("emoticon")) {
						emoticon = reader.nextString();
					} else if (key.equals("source")) {
						source = reader.nextString();
					}
				}
				reader.endObject();
				
				emotionList.add(new Emo(name, emoticon, source));
			}
			reader.endArray();
		} catch (IOException io) {
			System.out.println("Problem occured reading json. Maybe there was an extra comma at the end?");
			io.printStackTrace();
		}
		return emotionList; 
	}
}
