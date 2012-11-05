package com.org.iheartrobotics.drinktray;

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
	public Emo getEmo(String name) {
		return emotions.get(name);
	}
	
	/**
	 * Get all available emotions
	 * @return A set of all emotions
	 */
	public Set<String> getAvailableEmotions() {
		return emotions.keySet();
	}
	
	/**
	 * Updates an existing emo
	 * @param updatedName - The name of the emo
	 * @param newEmoticon - The emoticon representing the emo
	 * @param newSource - The file representing the emo
	 */
	public void updateEmo(String existingName, String newEmoticon, String newSource) {
		if (!emotions.containsKey(existingName)) {
			throw new IllegalArgumentException(existingName + " doesn't exist in the set of emotions. Use add() instead.");
		}
		emotions.put(existingName, Emo.CreateEmo(existingName,  newEmoticon, newSource));
	}
	
	/**
	 * Updates an existing emo
	 * @param existingName - The name of an existing emo
	 * @param newEmoticon - The new emoticon to replace this emo
	 */
	public void updateEmo(String existingName, String newEmoticon) {
		updateEmo(existingName, newEmoticon, null);
	}
	
	/**
	 * Deletes an existing emo
	 * @param name - The name of the emo to be deleted
	 * @return - True, if the emo is deleted. False, if otherwise.
	 */
	public boolean deleteEmo(String name) {
		return (emotions.remove(name) != null);
	}
}
