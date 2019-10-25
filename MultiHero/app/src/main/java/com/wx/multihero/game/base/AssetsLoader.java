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

package com.wx.multihero.game.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.SoundPool;

public class AssetsLoader implements Runnable {
    private static Map<String, Bitmap> mStringBitmapMap = new HashMap<String, Bitmap>();
    private static Map<String, Integer> mStringSoundMap = new HashMap<String, Integer>();
    private static AssetManager mAssetManager = null;
    private static SoundPool mSoundPool = null;
    private static final String ASSETS_GFX = "gfx";
    private static final String ASSETS_SOUND = "sound";
    private static final String SOUND_EXT = ".mp3";
    private static final String GFX_EXT = ".png";
    private static final String ASSETS_MAPS = "maps";
    private int mAssetsTotalCount = 0;
    private int mAssetsLoadedCount = 0;
    private static AssetsLoader mInstance = null;
    public interface LoadNotify {
        void onProgress(int loadedSize, int totalSize);
    }
    LoadNotify mNotify = null;
    private Matrix mMatrix;
    public AssetsLoader() {
        mMatrix = new Matrix();
    }

    public static AssetsLoader getInstance() {
        if(mInstance == null)
            mInstance = new AssetsLoader();
        return mInstance;
    }

    public void setLoadNotify(LoadNotify loadNotify) {
        mNotify = loadNotify;
    }

    public void setAssetManager(AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    public void setSoundPool(SoundPool soundPool) {
        mSoundPool = soundPool;
    }

    private void findFiles(ArrayList<String> fileList, String path) {
        try {
            String fileNames[] = mAssetManager.list(path);
            for(String fileName : fileNames) {
                String fileFullName = Utils.adjustDir(path) + fileName;
                if(fileName.endsWith(SOUND_EXT) || fileName.endsWith(GFX_EXT)) {
                    fileList.add(fileFullName);
                } else if(!fileName.contains(".")) {
                    findFiles(fileList, fileFullName);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFileNameList(String path, String ext) {
        String fileNames[] = new String[0];
        try {
            fileNames = mAssetManager.list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> fileNameList = new ArrayList<String>();
        ext = ext.toLowerCase();
        for(int i=0;i<fileNames.length;i++) {
            String fileName = fileNames[i];
            fileName = fileName.trim().toLowerCase();
            if(fileName.endsWith(ext)) {
                fileNameList.add(fileNames[i]);
            }
        }
        return fileNameList;
    }

    public ArrayList<String> getFileNameList(String path){
        String fileNames[] = new String[0];
        try {
            fileNames = mAssetManager.list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> fileNameList = new ArrayList<String>();
        for(int i=0;i<fileNames.length;i++) {
            fileNameList.add(fileNames[i]);
        }
        return fileNameList;
    }

    public void asycLoad() {
        new Thread(this).start();
    }

    public int loadSound(String fileName) {
        if(mSoundPool==null || mAssetManager==null)
            return -1;
        String actualFileName = getSoundName(fileName);
        Integer soundIdObject = mStringSoundMap.get(actualFileName);
        if(soundIdObject != null)
            return soundIdObject.intValue();
        int soundId = -1;
        try {
            soundId = mSoundPool.load(mAssetManager.openFd(actualFileName), 1);
            if(soundId >= 0) {
                mStringSoundMap.put(actualFileName, soundId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return soundId;
    }

    public Bitmap loadBitmap(String format, java.lang.Object... args) {
        String fileName = String.format(format, args);
        return loadBitmap(fileName);
    }

    public Bitmap loadBitmap(String fileName) {
    	String actualFileName = fileName;

        Bitmap bmp = mStringBitmapMap.get(actualFileName);
        if(bmp != null)
            return bmp;

        try {
            InputStream is0 = mAssetManager.open(actualFileName);
            bmp = BitmapFactory.decodeStream(is0);
            float sx = Utils.getWidthRadio();
            float sy = Utils.getHeightRadio();
            mMatrix.reset();
            mMatrix.postScale(sx, sy);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mMatrix, true);
            mStringBitmapMap.put(actualFileName, bmp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public LittleEndianDataInputStream loadFile(String fileName) {
        try {
            return new LittleEndianDataInputStream(mAssetManager.open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {
        try {
            mAssetsLoadedCount = 0;
            ArrayList<String> fileList = new ArrayList<String>();
            findFiles(fileList, ASSETS_GFX);
            findFiles(fileList, ASSETS_SOUND);
            findFiles(fileList, ASSETS_MAPS);
            mAssetsTotalCount = fileList.size();
            for(String fileName : fileList) {
                if(fileName.endsWith(SOUND_EXT)) {
                    loadSound(fileName);
                } else if(fileName.endsWith(GFX_EXT)) {
                    loadBitmap(fileName);
                }

                if(Utils.DEBUG == false) {
                    Thread.sleep(1);
                }
                mAssetsLoadedCount++;
                if(mNotify != null) {
                    mNotify.onProgress(mAssetsLoadedCount, mAssetsTotalCount);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getRealName(String fileName, String prefix, String suffix) {
        StringBuffer actualFileName = new StringBuffer(fileName);
        prefix = Utils.adjustDir(prefix);
        if(!fileName.startsWith(prefix))
            actualFileName.insert(0, prefix);
        if(!fileName.endsWith(suffix))
            actualFileName.append(suffix);
        return actualFileName.toString();
    }

    private String getSoundName(String fileName) {
        return getRealName(fileName, ASSETS_SOUND, SOUND_EXT);
    }

    private String getGfxName(String fileName) {
        return getRealName(fileName, ASSETS_GFX, GFX_EXT);
    }
}
