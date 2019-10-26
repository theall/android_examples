package com.wx.multihero.game.variability.chunk;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChunkFactory {
    private static ChunkFactory mInstance = null;
    private Map<Chunk.Type, Chunk> mChunkMap = new HashMap<Chunk.Type, Chunk>();
    private ArrayList<Chunk> mChunkList = new ArrayList<Chunk>();

    private ChunkFactory() {
    }

    public static ChunkFactory getInstance() {
        if(mInstance == null)
            mInstance = new ChunkFactory();
        return mInstance;
    }

    public void loadAssets() {
        mChunkMap.clear();
        addChunk(new AirTrailGoingUp());
        addChunk(new BatmanBombSmoke());
        addChunk(new BigRockBreaking());
        addChunk(new BigWeb());
        addChunk(new Blocking());
        addChunk(new Blood());
        addChunk(new Blueray());
        addChunk(new Blueray2());
        addChunk(new BluerayImpact());
        addChunk(new BluerayImpact2());
        addChunk(new BrightDot());
        addChunk(new Coins());
        addChunk(new DragonBall());
        addChunk(new DragonBallDouble());
        addChunk(new Electricity());
        addChunk(new Explosion());
        addChunk(new Explosion40());
        addChunk(new FireBall());
        addChunk(new FireBallImpact());
        addChunk(new FireGoingUp());
        addChunk(new FourWayExplosion());
        addChunk(new GreenPickUpSign());
        addChunk(new GreenRayImpact());
        addChunk(new ImpactBall());
        addChunk(new JimmyAttack());
        addChunk(new Knife());
        addChunk(new LavaRock());
        addChunk(new LavaRockBreaking());
        addChunk(new LittleRockBreaking());
        addChunk(new LittleSmoke());
        addChunk(new LittleSmokeGoingUp());
        addChunk(new MVSCHit());
        addChunk(new PowerBall());
        addChunk(new RashHit());
        addChunk(new RayBallImpact());
        addChunk(new RedRay());
        addChunk(new RedRay2());
        addChunk(new RedRayImpact());
        addChunk(new RoundIntroduction());
        addChunk(new RyuBlueBallImpact());
        addChunk(new Smoke());
        addChunk(new Test());
        addChunk(new TmntHit());
        addChunk(new VulcanoExplosion());
        addChunk(new WaterSplash());
        addChunk(new WebShot());
        addChunk(new WebShotImpact());
        addChunk(new WhiteStar());
        addChunk(new WhiteStarHit());
        addChunk(new YellowRay());
        addChunk(new YellowRay2());
        addChunk(new YellowRayImpact());
        addChunk(new YellowRayImpact2());
    }

    private void addChunk(Chunk chunk) {
        mChunkMap.put(chunk.getType(), chunk);
    }
    
    public Chunk makeChunk(Chunk.Type type) {
        Chunk chunk = mChunkMap.get(type);
        if(chunk == null) {
            Log.e("chunk", "Invalid chunk type: "+type.name());
            return null;
        }
        Chunk cloneChunk = null;
        try {
            cloneChunk = (Chunk)chunk.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloneChunk;
    }
}
