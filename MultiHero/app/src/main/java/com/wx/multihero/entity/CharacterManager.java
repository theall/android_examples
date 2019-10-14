/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CharacterManager {
	private ArrayList<Character> mCharacters = new ArrayList<Character>();
	private static CharacterManager mInstance = null;
	private static String mRoot = "gfx/character";

    private class SortComparator implements Comparator {
        public int compare(Object a, Object b) {
            String na = (String)a;
            String nb = (String)b;
            return (Integer.parseInt(na) - Integer.parseInt(nb));
        }
    }

	public CharacterManager() {
	}

	public static CharacterManager getInstance() {
		if(mInstance == null)
			mInstance = new CharacterManager();
		return mInstance;
	}

	public void loadAssets() {
		String root = mRoot;
		ArrayList<String> fileNameList = AssetsLoader.getInstance().getFileNameList(root);
        Collections.sort(fileNameList, new SortComparator());
		root = Utils.adjustDir(root);
		for(String fileName : fileNameList) {
			Character character = new Character(0);
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

	public Character getCharacter(int index) {
		if(index<0 || index>=mCharacters.size())
			return null;
		return mCharacters.get(index);
	}
}
