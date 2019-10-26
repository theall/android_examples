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

package com.wx.multihero.game.variability.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.variability.chunk.Chunk;
import com.wx.multihero.game.variability.chunk.ChunkFactory;

import java.util.ArrayList;

public class ChunkManager implements Stepable, Renderable {
    private static ChunkManager mInstance = null;
    private ArrayList<Chunk> mChunkList = new ArrayList<Chunk>();
    private ChunkFactory mChunkFactory = ChunkFactory.getInstance();

    private ChunkManager() {
    }

    public static ChunkManager getInstance() {
        if(mInstance == null)
            mInstance = new ChunkManager();
        return mInstance;
    }

    public void clear() {
        mChunkList.clear();    
    }

    public void makeChunk(float x, float y, Chunk.Type type) {
        Chunk cloneChunk = mChunkFactory.makeChunk(type);
        if(cloneChunk == null)
            return;

        cloneChunk.moveTo(x, y);
        mChunkList.add(cloneChunk);
    }

    public void makeChunk(Chunk.Item chunkItem) {
        Chunk cloneChunk = mChunkFactory.makeChunk(chunkItem.type);
        if(cloneChunk == null)
            return;

        if(chunkItem.attach == null) {
            FaceDir faceDir = chunkItem.owner.getFaceDir();
            cloneChunk.setFaceDir(chunkItem.owner.getFaceDir());
            float dx = faceDir==FaceDir.LEFT?-chunkItem.dx:chunkItem.dx;
            cloneChunk.moveTo(chunkItem.owner.x+dx, chunkItem.owner.y+chunkItem.dy);
        }
        mChunkList.add(cloneChunk);
    }

    public void render(Canvas canvas, Paint paint) {
        for(Chunk chunk : mChunkList) {
            chunk.render(canvas, paint);
        }
    }

    public void step() {
        for(int i = mChunkList.size()-1; i>=0; i--) {
            Chunk chunk = mChunkList.get(i);
            if(chunk.isRecycled()) {
                mChunkList.remove(i);
                continue;
            }
            chunk.step();
        }
    }
}
