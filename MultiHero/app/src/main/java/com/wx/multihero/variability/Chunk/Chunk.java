package com.wx.multihero.variability.Chunk;

import android.graphics.Bitmap;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.variability.Sprite.AnimationSprite;

public class Chunk extends AnimationSprite {
    public enum Type {
        TEST,
        BLOCKING,
        ROUND_INTRODUCTION,
        RYU_BLUE_BALL_IMPACT,
        EXPLOSION_40,
        WHITE_STAR_HIT,
        WEB_SHOT_IMPACT,
        FIRE_BALL_IMPACT,
        COINS,
        LAVA_ROCK_BREAKING,
        M_VS_C_HIT,
        VULCANO_EXPLOSION,
        AIR_TRAIL_GOING_UP,
        BRIGHT_DOT,
        BLOOD,
        GREEN_PICK_UP_SIGN,
        SMOKE,
        RED_RAY_IMPACT,
        BLUERAY,
        BLUERAY_2,
        RASH_HIT,
        BLUERAY_IMPACT,
        BLUERAY_IMPACT_2,
        POWER_BALL,
        FIRE_GOING_UP,
        FOUR_WAY_EXPLOSION,
        BATMAN_BOMB_SMOKE,
        GREEN_RAY_IMPACT,
        LITTLE_SMOKE,
        LITTLE_SMOKE_GOING_UP,
        RAY_BALL_IMPACT,
        ELECTRICITY,
        RED_RAY,
        RED_RAY_2,
        BIG_ROCK_BREAKING,
        LITTLE_ROCK_BREAKING,
        WATER_SPLASH,
        BIG_WEB,
        YELLOW_RAY,
        YELLOW_RAY_2,
        TUTORIAL_1_DOUBLE_JUMP,
        TUTORIAL_2_UP_SPECIAL,
        TUTORIAL_3_FIGHT,
        TUTORIAL_4_USE_SWITCH,
        TUTORIAL_5_PICK_UP_ITEM,
        TUTORIAL_6_GO_DOWN_FROM_PLATFORM,
        TUTORIAL_7_THROW_ITEM_DIOGANALLY,
        TUTORIAL_8_SUPER_SPECIAL,
        SPECIAL_EVENT_FOR_LEVEL50,
        NO_AIR_SPECIAL_ALLOWED,
        TMNT_HIT,
        YELLOW_RAY_IMPACT,
        YELLOW_RAY_IMPACT_2,
        WHIP_1,
        WHIP_2,
        WHIP_3,
        WHIP_4,
        WHIP_5,
        WHIP_6,
        WHIP_7,
        WHIP_8,
        DRAGON_BALL,
        JIMMY_ATTACK,
        KNIFE,
        DRAGON_BALL_DOUBLE        
    }
    private Type mType;

    public Chunk() {
        mType = Type.TEST;
    }

    public Chunk(Type type) {
        mType = type;
    }
    
    public void add(int n, int setNumber, int index) {
        String assetName = String.format("gfx/stuff/pt%d_a%d.png", setNumber, index);
        Bitmap bitmap = AssetsLoader.getInstance().loadBitmap(assetName);
        add(n, bitmap);
    }
}
