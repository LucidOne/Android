package com.org.iheartengineering.emofaces;

import java.util.HashMap;
import java.io.*;
import android.util.JsonReader; //TODO: Maybe get a better json reader? (potential optimizaton)

/**
 * A class capable of parsing text/files and generating Emo objects
 * @author Errol Markland
 *
 */
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
	public HashMap<String, Emo> ReadEmoticonsFromFile(String filepath) throws IOException {
		return (filepath == null) ? null : ReadEmoticons(new FileReader(filepath));
	}
	
	/**
	 * Returns a list of Emo objects reads from a json array
	 * @param jsonText - The json text containing emoticon information
	 * @return - A list of Emo Objects.
	 * @throws IOException - Error with string being read
	 */
	public HashMap<String, Emo> ReadEmoticonsFromJson(String jsonText) throws IOException {
		return (jsonText == null) ? null : ReadEmoticons(new StringReader(jsonText));
	}
	
	/**
	 * Parse the json data from the reader
	 * @param in - The reader containing the emoticon data
	 * @return - A hash map of emoticons
	 * @throws IOException - There is an issue with the input reader
	 */
	@SuppressWarnings("resource")
	private HashMap<String, Emo> ReadEmoticons(Reader in) throws IOException {
		HashMap<String, Emo> map = new HashMap<String, Emo>();
		JsonReader reader = new JsonReader(in);
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
				if (key == "name") {				
					name = reader.nextString();
				} else if (key == "emoticon") {
					emoticon = reader.nextString();
				} else if (key == "source") {
					source = reader.nextString();
				}
			}
			reader.endObject();
			
			map.put(name, new Emo(name, emoticon, source));
		}
		reader.endArray();
		return map;
	}
}
