package com.wx.multihero.variability.Sprite;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Character;
import com.wx.multihero.variability.Chunk.ChunkManager;

public class Player extends Sprite implements Stepable {
    public void step() {

    }

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
    private String mName;

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
        mType = Type.UNKNOWN;
        mTeam = Team.NONE;
        mCharacterChangedCallback = null;
        mTypeChangedCallback = null;
        mTeamChangedCallback = null;
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
}
