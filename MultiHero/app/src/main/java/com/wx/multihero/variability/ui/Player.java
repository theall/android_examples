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

package com.wx.multihero.variability.ui;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.entity.Character;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.ProgressBar;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.hero.Hero;
import com.wx.multihero.variability.hero.HeroFactory;

public class Player extends Widget implements Stepable, Renderable {
    public enum Type {
        HUM,
        CPU,
        UNKNOWN
    }
    public enum Team {
        NONE,
        RED,
        BLUE,
        GREEN,
        YELLOW,
        WHITE,
        BROWN,
        ORANGE,
        PURPLE,
        PINK
    }
    private Type mType;
    private Team mTeam;
    private Character mCharacter;
    private Hero mHero;
    private String mName;
    private PictureItem mIcon;
    private ProgressBar mHpBar;
    private ProgressBar mEnergyBar;
    public interface CharacterChangedCallback {
        void characterChanged(Character oldCharacter, Character newCharacter);
    }
    public interface TypeChangedCallback {
        void typeChanged(Type oldType, Type newType);
    }
    public interface TeamChangedCallback {
        void teamChanged(Team oldTeam, Team newTeam);
    }
    
    private CharacterChangedCallback mCharacterChangedCallback;
    private TypeChangedCallback mTypeChangedCallback;
    private TeamChangedCallback mTeamChangedCallback;
    
    public Player() {
        super(null);

        mType = Type.UNKNOWN;
        mTeam = Team.NONE;
        mCharacterChangedCallback = null;
        mTypeChangedCallback = null;
        mTeamChangedCallback = null;
        mIcon = new PictureItem(this);
        mHpBar = new ProgressBar(this);
        mEnergyBar = new ProgressBar(this);
    }

    public void positionChanged(float dx, float dy) {
        mIcon.offset(dx, dy);
        mHpBar.offset(dx, dy);
        mEnergyBar.offset(dx, dy);
    }

    public void setCharacterCallback(CharacterChangedCallback callback) {
        mCharacterChangedCallback = callback;
    }
    
    public void setTypeChangedCallback(TypeChangedCallback callback) {
        mTypeChangedCallback = callback;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        if(mType != type) {
            Type oldType = mType;
            mType = type;
            if(mTypeChangedCallback != null) {
                mTypeChangedCallback.typeChanged(oldType, type);
            }
        }
    }

    public Team getTeam() {
        return mTeam;
    }

    public void setTeam(Team team) {
        if(mTeam != team) {
            Team oldTeam = mTeam;
            mTeam = team;
            if(mTeamChangedCallback != null) {
                mTeamChangedCallback.teamChanged(oldTeam, team);
            }
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Character getCharacter() {
        return mCharacter;
    }

    public void setCharacter(Character character) {
        if(mCharacter == character)
            return;

        if(mCharacter == null) {
            // Set character first time
            setType(Type.CPU);
        }

        Character oldCharacter = mCharacter;
        mCharacter = character;

        if(mCharacterChangedCallback != null) {
            mCharacterChangedCallback.characterChanged(oldCharacter, character);
        }
    }

    // Prepare hero
    public void init() {
        mHero = HeroFactory.create(mCharacter);
    }

    public void step() {

    }

    public void render(Canvas canvas, Paint paint) {
        // Draw player icon
        mIcon.render(canvas, paint);
        mHpBar.render(canvas, paint);
        mEnergyBar.render(canvas, paint);
    }
}
