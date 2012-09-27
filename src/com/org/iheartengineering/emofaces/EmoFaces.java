package com.org.iheartengineering.emofaces;

import java.io.*;
import java.util.*;

public class EmoFaces {	
	HashMap<String, Emo> emotions;
	Emo currentEmotion = null;
	public EmoFaces() {
		emotions = new HashMap<String, Emo>();
	}
	
	/**
	 * Adds an emotion of the current set of emotions
	 * @param emotion - The emotion to be added
	 */
	public void addEmotion(Emo emotion) {
		if (emotions.containsKey(emotion.name)) {
			throw new IllegalArgumentException("The name " + emotion.name + " already exists. Try a different name!");
		}
		emotions.put(emotion.name, emotion);
	}
	
	/**
	 * Adds a list of emotions to the current set of emotions
	 * @param emotions - The list of emotions to be added
	 */
	public void addEmotions(ArrayList<Emo> emotions) {
		for (Emo e : emotions) {			
			addEmotion(e);
		}
	}
	
	public void addEmotionsFromFile(InputStream inputStream) {
		EmoFaceReader reader = new EmoFaceReader();
		try {
			for (Emo e : reader.readEmoticonsFromStream(new InputStreamReader(inputStream))) {
				emotions.put(e.name,  e);
			}
		} catch (IOException ex) {
			System.out.println("Failed to read input stream.");
			ex.printStackTrace();
		}
	}
	/**
	 * Get emotions from file
	 * @param filename - File which contains the emotions to be added
	 */
	public void addEmotionsFromFile(String filename) {
		EmoFaceReader reader = new EmoFaceReader();
		try {
			for (Emo e : reader.readEmoticonsFromFile(filename)) {
				emotions.put(e.name,  e);
			}			
		} catch (IOException ex) {
			System.out.println("Problem occured reading emoticon from file...Here's the stack trace!");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Get the emoticon
	 * @param name - The name of the emoticon
	 * @return The emo object, which contains the emoticon and optional source file, or null if the emo doesn't exist
	 */
	public Emo getEmotion(String name) {
		return emotions.containsKey(name) ? emotions.get(name) : null;
	}
	
	/**
	 * Returns all available strings in this set.
	 * @return A set of all emotions
	 */
	public Set<String> getAvailableEmotions() {
		return emotions.keySet();
	}
}
