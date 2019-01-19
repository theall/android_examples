package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

import java.util.ArrayList;

public class CharacterManager {
	private ArrayList<Character> mCharacters = new ArrayList<Character>();
	private static CharacterManager mInstance = null;

	public CharacterManager() {
	}

	public static CharacterManager getInstance() {
		if(mInstance == null)
			mInstance = new CharacterManager();
		return mInstance;
	}

	public void loadAssets(String root) {
		ArrayList<String> fileNameList = AssetsLoader.getInstance().getFileNameList(root);
		root = Utils.adjustDir(root);
		for(String fileName : fileNameList) {
			Character character = new Character();
			character.loadAssets(root+fileName);
			mCharacters.add(character);
		}
	}

	public ArrayList<Character> getCharacterList() {
		return mCharacters;
	}

	public int getCharacterCount() {
		return mCharacters.size();
	}
}
