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

package com.wx.multihero.game.variability.chunk;

import android.graphics.Bitmap;
import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.VectorF;
import com.wx.multihero.game.variability.sprite.AnimationSprite;
import com.wx.multihero.game.variability.sprite.Frame;
import com.wx.multihero.game.variability.sprite.Sprite;

public class Chunk extends AnimationSprite implements Cloneable {
    public enum Type {
        NULL,
        AIR_TRAIL_GOING_UP,
        BATMAN_BOMB_SMOKE,
        BIG_ROCK_BREAKING,
        BIG_WEB,
        BLOCKING,
        BLOOD,
        BLUERAY,
        BLUERAY_2,
        BLUERAY_IMPACT,
        BLUERAY_IMPACT_2,
        BRIGHT_DOT,
        COINS,
        DRAGON_BALL,
        DRAGON_BALL_DOUBLE,
        ELECTRICITY,
        EXPLOSION,
        EXPLOSION_40,
        FIRE_BALL,
        FIRE_BALL_IMPACT,
        FIRE_GOING_UP,
        FOUR_WAY_EXPLOSION,
        GREEN_PICK_UP_SIGN,
        GREEN_RAY_IMPACT,
        IMPACT_BALL,
        JIMMY_ATTACK,
        KNIFE,
        LAVA_ROCK,
        LAVA_ROCK_BREAKING,
        LITTLE_ROCK_BREAKING,
        LITTLE_SMOKE,
        LITTLE_SMOKE_GOING_UP,
        M_VS_C_HIT,
        NO_AIR_SPECIAL_ALLOWED,
        POWER_BALL,
        RASH_HIT,
        RAY_BALL_IMPACT,
        RED_RAY,
        RED_RAY_2,
        RED_RAY_IMPACT,
        ROUND_INTRODUCTION,
        RYU_BLUE_BALL_IMPACT,
        SMOKE,
        SPECIAL_EVENT_FOR_LEVEL50,
        TEST,
        TMNT_HIT,
        TUTORIAL_1_DOUBLE_JUMP,
        TUTORIAL_2_UP_SPECIAL,
        TUTORIAL_3_FIGHT,
        TUTORIAL_4_USE_SWITCH,
        TUTORIAL_5_PICK_UP_ITEM,
        TUTORIAL_6_GO_DOWN_FROM_PLATFORM,
        TUTORIAL_7_THROW_ITEM_DIOGANALLY,
        TUTORIAL_8_SUPER_SPECIAL,
        VULCANO_EXPLOSION,
        WATER_SPLASH,
        WEBSHOT,
        WEB_SHOT_IMPACT,
        WHIP_1,
        WHIP_2,
        WHIP_3,
        WHIP_4,
        WHIP_5,
        WHIP_6,
        WHIP_7,
        WHIP_8,
        WHITE_STAR,
        WHITE_STAR_HIT,
        YELLOW_RAY,
        YELLOW_RAY_2,
        YELLOW_RAY_IMPACT,
        YELLOW_RAY_IMPACT_2
    }
    public static class Item {
        public Type type;
        public Sprite owner;
        public Sprite attach;
        public float dx;
        public float dy;
        public Item() {
            type = Type.NULL;
        }
        public Item(Type type) {
            this.type = type;
        }
        public Item(Type type, Sprite owner) {
            this.type = type;
            this.owner = owner;
        }
        public Item(Type type, Sprite owner, float x, float y, Sprite attach) {
            this.type = type;
            this.owner = owner;
            this.dx = x;
            this.dy = y;
            this.attach = attach;
        }

        public boolean isNull() {
            return type==Type.NULL;
        }
    }
    private Item mItem = new Item();

    public Chunk(Type type) {
        mItem.type = type;
        setAnchor(Anchor.CENTER);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Item getItem() {
        return mItem;
    }

    public void setItem(Item item) {
        this.mItem = item;
    }

    public Type getType() {
        return mItem.type;
    }

    public void setAttachSprite(Sprite sprite) {
        mItem.attach = sprite;
    }

    public void setAttachSprite(Sprite sprite, float offsetX, float offsetY) {
        mItem.attach = sprite;
        mItem.dx = offsetX;
        mItem.dy = offsetY;
    }

    public Frame add(int n, int setNumber, int index) {
        String assetName = String.format("gfx/stuff/pt%d_a%d.png", setNumber, index);
        Bitmap bitmap = AssetsLoader.getInstance().loadBitmap(assetName);
        Frame frame = add(n, bitmap);
        frame.ignoreGravity = true;
        frame.setVectorType(VectorF.Type.RELATIVE);
        return frame;
    }

    @Override
    public void step() {
        if(mItem.attach != null) {
            x = mItem.attach.x + mItem.dx;
            y = mItem.attach.y + mItem.dy;
        }
        super.step();
    }
}
