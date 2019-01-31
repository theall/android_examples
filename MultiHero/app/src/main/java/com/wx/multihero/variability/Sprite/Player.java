package com.wx.multihero.variability.Sprite;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Character;
import com.wx.multihero.variability.Chunk.ChunkManager;

public class Player extends Sprite implements Stepable {
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
    public enum Blow {
        NONE,
        BLOCKING,
        PUNCH,
        FLYING_KICK,
        LOW_KICK,
        UPPERCUT,
        THROWING_ITEM,
        SPECIAL,
        DOGDING,
        DOWN_SPECIAL,
        HIGH_KICK,
        CLUB,
        SHOOTING_POSITION,
        ITEM_PICKUP,
        SUPER_SPECIAL,
        THROW
    }
    private Type mType;
    private Team mTeam;
    private Character mCharacter;
    private String mName;
    public int mHP;
    public int mSP;
    public int mLifes;
    public FaceDir mDir;
    public float mSpeed;
    public float mAcceleration;
    public float mBlockSpeed;
    public int mShieldTime;
    public int mHeight;
    public int mUpHeight;
    public int mDuckHeight;
    public int mBlockLife;
    public int mBlockMaxLife;
    private int mTempShieldFrames;
    private int mAntiPlatFrames;
    private int mTrailEffectFrames;
    private int mFrameCounter;
    private boolean mIsShield;
    private boolean mCanFly;
    private boolean mIsBlocking;
    private boolean mIsBlowing;
    private boolean mIsGrabbed;
    private Blow mCurrentBlow;

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
        mDir = FaceDir.NONE;
        mType = Type.UNKNOWN;
        mTeam = Team.NONE;
        mCharacterChangedCallback = null;
        mTypeChangedCallback = null;
        mTeamChangedCallback = null;
        mFrameCounter = 0;
    }

    private void reset() {
        mHP = 100;
        mSP = 0;
        mLifes = 3;
        mHeight = 45;
        mUpHeight = mHeight;
        mDuckHeight = 25;
        mBlockLife = 100;
        mBlockMaxLife = 100;
        mAcceleration = 0.2f;
        mBlockSpeed = 0.8f;
        mTempShieldFrames = 0;
        mAntiPlatFrames = 0;
        mTrailEffectFrames = 0;
        mIsShield = false;
        mCanFly = false;
    }

    public void step() {
        int renderIndex = mFrameCounter%3;
        if(mTempShieldFrames > 0) {
            mTempShieldFrames--;
            int seed = Utils.getRandValue(1, 3);
            if(seed == 2) {
                float x = Utils.getRandWidth(-10, 10);
                float y = Utils.getRandHeight(-40, 0);
                ChunkManager.getInstance().makeChunk(x, y);
            }
        }
        if(mAntiPlatFrames > 0) {
            mAntiPlatFrames--;
        }
        if(mTrailEffectFrames > 0) {
            mTrailEffectFrames--;
            if(renderIndex==1) {
                float x = Utils.getRandWidth(-8,8);
                float y = Utils.getRandHeight(-30,-10);
            }
        }

        doAi();

        mFrameCounter++;
    }

    public void setShield() {
        mTempShieldFrames = mShieldTime;
    }

    public void setAntiPlatform() {
        mAntiPlatFrames = 5;
    }

    public void setTrailEffect(int type) {

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
        if(mCharacter != character) {
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
    }

    private void doAi() {
        if(!mIsGrabbed) {

        }
    }
}
