package com.wx.multihero;

import java.util.ArrayList;

public class CharacterManager {
	private ArrayList<Character> mCharacters = new ArrayList<Character>();
	
	public void loadAssets() {
		for(int i=1;i<13;i++) {
			Character character = new Character();
			character.loadAssets(i);
			mCharacters.add(character);
		}
	}
}
