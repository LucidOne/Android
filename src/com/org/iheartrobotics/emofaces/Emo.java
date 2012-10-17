package com.org.iheartrobotics.emofaces;

/**
 * An object representation representation of an emoticon
 * @author Errol Markland
 */
public class Emo {
	Emo() {
	}
	
	public Emo(String name, String emoticon) {
		this.name = name;
		this.Emoticon = emoticon;
		this.Source = null;
	}
	
	public Emo(String name, String emoticon, String source) {
		this.name = name;
		this.Emoticon = emoticon;
		this.Source = source;
	}
	
	/**
	 *  The name of the emotion
	 */
	public String name;
	
	/**
	 * The text representation of the emotion
	 */
	public String Emoticon;
	
	/**
	 * The path to the image file (optional).
	 */
	public String Source;
	
	@Override
	public int hashCode() {
		int hash = 13;
		hash = 31 * hash + name.hashCode();
		hash = 31 * hash + Emoticon.hashCode();
		hash = 31 * hash + ((Source == null) ? 0 : Source.hashCode());
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null) return false;
		if (obj.getClass() != this.getClass())  return false;
		Emo that = (Emo)obj;
		if ((this.name != that.name) || (this.Emoticon != that.Emoticon)) return false;
		if ((this.Source != null) && (this.Source != that.Source)) return false;
		
		return true;
	}
	
	/**
	 * Create an emo
	 * @param name - The name of the emo
	 * @param emoticon - The emoticon representing this emo
	 * @param source - The source file representing this emo
	 * @return
	 */
	public static Emo CreateEmo(String name, String emoticon, String source) {
		if ((name == null) || (emoticon == null)) {
			throw new IllegalArgumentException("'name' or 'emoticon' can be null!");
		}
		return new Emo(name, emoticon, source);
	}
}
