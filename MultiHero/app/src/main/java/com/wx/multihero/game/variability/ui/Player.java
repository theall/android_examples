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

package com.wx.multihero.game.variability.ui;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wx.multihero.game.base.Button;
import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.Character;
import com.wx.multihero.game.ui.widget.ColorProgressBar;
import com.wx.multihero.game.ui.widget.PictureItem;
import com.wx.multihero.game.ui.widget.PrimitiveText;
import com.wx.multihero.game.ui.widget.Widget;
import com.wx.multihero.game.variability.controller.Controller;
import com.wx.multihero.game.variability.controller.KeyboardController;
import com.wx.multihero.game.variability.hero.Action;
import com.wx.multihero.game.variability.hero.Hero;
import com.wx.multihero.game.variability.hero.HeroFactory;
import com.wx.multihero.game.variability.sprite.FaceDir;

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
    private PictureItem mIcon;
    private ColorProgressBar mHpBar;
    private ColorProgressBar mEnergyBar;
    private PrimitiveText mCaption;
    private Controller mController;
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
        mController = new KeyboardController();

        float width = Utils.getRealWidth(100);
        float height = Utils.getRealHeight(24);
        float marginH = Utils.getRealWidth(2);
        float marginV = Utils.getRealHeight(1);
        setBoundingRect(0, 0, width, height);

        mIcon = new PictureItem(this);
        float iconLeft = marginH;
        float iconTop = height - marginV;
        float iconWidth = Utils.getRealWidth(32);
        float iconHeight =  Utils.getRealHeight(32);
        mIcon.setBoundingRect(marginH, marginV, iconWidth, iconHeight);

        mEnergyBar = new ColorProgressBar(this);
        float barLeft = iconLeft + iconWidth + marginH;
        float barHeight = Utils.getRealHeight(4);
        float barTop = height - barHeight - marginV;
        float barWidth = width - iconWidth - iconLeft - marginH * 2;
        mEnergyBar.setBoundingRect(barLeft, barTop, barWidth, barHeight);
        mEnergyBar.setColor(Color.WHITE, Color.BLUE);

        mHpBar = new ColorProgressBar(this);
        barTop -= barHeight + marginV;
        mHpBar.setBoundingRect(barLeft, barTop, barWidth, barHeight);
        mEnergyBar.setColor(Color.WHITE, Color.YELLOW);

        mCaption = new PrimitiveText(this);
        mCaption.setFontSize(16);
        mCaption.setColor(Color.WHITE);
        mCaption.setBoundingRect(barLeft, iconTop, width-barLeft-marginH,barTop-marginV-iconTop);
    }

    public Hero getHero() {
        return mHero;
    }

    public void positionChanged(float dx, float dy) {
        mIcon.offset(dx, dy);
        mCaption.offset(dx, dy);
        mHpBar.offset(dx, dy);
        mEnergyBar.offset(dx, dy);
    }

    public void setCharacterCallback(CharacterChangedCallback callback) {
        mCharacterChangedCallback = callback;
    }
    
    public void setTypeChangedCallback(TypeChangedCallback callback) {
        mTypeChangedCallback = callback;
    }

    public void setTeamChangedCallback(TeamChangedCallback callback) {
        mTeamChangedCallback = callback;
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

    public boolean isActived() {
        return mTeam!=Team.NONE && mType!=Type.UNKNOWN && mHero!=null;
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
        return mCaption.getText();
    }

    public void setName(String name) {
        mCaption.setText(name);
    }

    public Character getCharacter() {
        return mCharacter;
    }

    public void setCharacter(Character character) {
        if(mCharacter == character)
            return;

        if(mCharacter == null) {
            // Set character first time
            if(mType == Type.UNKNOWN) {
                setType(Type.CPU);
            }
            if(mTeam == Team.NONE) {
                setTeam(Team.GREEN);
            }
        }

        Character oldCharacter = mCharacter;
        mCharacter = character;

        if(mCharacterChangedCallback != null) {
            mCharacterChangedCallback.characterChanged(oldCharacter, character);
        }
        update();
    }

    // Prepare hero
    private void update() {
        if(mCharacter == null)
            return;

        mHero = HeroFactory.create(mCharacter);
        mIcon.setBitmap(mCharacter.getIcon());
    }

    public void step() {
        mController.prepare();

        // Update value from hero
        if(mHero == null)
            return;

        Action currentAction = mHero.getCurrentAction();
        if(currentAction.isBreakable() || currentAction.isEnd()) {
            // Process face dir
            boolean leftDown = mController.isButtonDown(Button.LEFT);
            boolean rightDown = mController.isButtonDown(Button.RIGHT);
            Action.ID actionID = Action.ID.READY;
            if(mHero.isOnGround()) {
                if(mController.isButtonDown(Button.DOWN)) {
                    if (mController.isButtonDown(Button.ATTACK)) {
                        actionID = Action.ID.LOW_KICK;
                    } else {
                        actionID = Action.ID.DUCK;
                    }
                } else if(mController.isButtonDown(Button.ATTACK)) {
                    actionID = Action.ID.PUNCH;
                } else if(mController.isButtonDown(Button.BLOCKING)) {
                    actionID = Action.ID.BLOCKING;
                } else if(leftDown || rightDown) {
                    actionID = Action.ID.WALK;
                }
            } else {
                if(leftDown || rightDown) {
                    actionID = Action.ID.WALK_IN_AIR;
                } else {
                    if(mHero.sy < 0) {
                        actionID = Action.ID.AIR;
                    } else {
                        actionID = Action.ID.FALLING;
                    }
                }
            }

            // up blow
            if(mController.isButtonDown(Button.UP) && mController.isButtonDown(Button.ATTACK)) {
                actionID = Action.ID.HIGH_KICK;
            }

            if(mController.isButtonDown(Button.SPECIAL)) {
                if(mController.isButtonDown(Button.UP)) {
                    actionID = Action.ID.UPPERCUT;
                } else if(mController.isButtonDown(Button.DOWN)) {
                    actionID = Action.ID.DOWN_SPECIAL;
                } else {
                    actionID = Action.ID.SPECIAL;
                }
            }

            if(mController.isButtonPressed(Button.JUMP)) {
                if(mHero.isOnGround()) {
                    if(currentAction.getId() == Action.ID.DUCK) {
                        actionID = Action.ID.JUMP_DOWN;
                    } else {
                        actionID = Action.ID.JUMP;
                    }
                } else {
                    actionID = Action.ID.JUMP2;
                }
            }

            if(leftDown) {
                mHero.setFaceDir(FaceDir.LEFT);
            } else if(rightDown) {
                mHero.setFaceDir(FaceDir.RIGHT);
            }
            if(currentAction.getId()==actionID && currentAction.isEnd()) {
                // Reset current action
                mHero.setCurrentAction(Action.ID.READY);
            }
            mHero.setCurrentAction(actionID);
        }

        mHpBar.setProgress((float)mHero.hp / 100);
        mEnergyBar.setProgress((float)mHero.sp / 100);
    }

    public void render(Canvas canvas, Paint paint) {
        // Draw player icon
        mIcon.render(canvas, paint);
        mHpBar.render(canvas, paint);
        mEnergyBar.render(canvas, paint);
        mCaption.render(canvas, paint);
    }
}
