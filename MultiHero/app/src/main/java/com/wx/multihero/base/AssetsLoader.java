package com.wx.multihero.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.SoundPool;

import com.wx.multihero.MainView;

public class AssetsLoader implements Runnable {
    private static Map<String, Bitmap> mStringBitmapMap = new HashMap<String, Bitmap>();
    private static Map<String, Integer> mStringSoundMap = new HashMap<String, Integer>();
    private static AssetManager mAssetManager = null;
    private static SoundPool mSoundPool = null;
    private static final String ASSETS_GFX = "gfx";
    private static final String ASSETS_SOUND = "sound";
    private int mAssetsTotalCount = 0;
    private int mAssetsLoadedCount = 0;

    public interface LoaderNotify {
        void onProgress(int loadedSize, int totalSize);
    }
    LoaderNotify mNotify = null;
    public AssetsLoader(Context context, SoundPool soundPool, LoaderNotify notify) {
        mSoundPool = soundPool;
        mAssetManager = context.getAssets();
        mNotify = notify;
    }

    private void findFiles(ArrayList<String> fileList, String path) {
        try {
            String fileNames[] = mAssetManager.list(path);
            for(String fileName : fileNames) {
                String fileFullName = path + "/" + fileName;
                if(fileName.endsWith("mp3") || fileName.endsWith("png")) {
                    fileList.add(fileFullName);
                } else if(!fileName.contains(".")) {
                    findFiles(fileList, fileFullName);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asycLoad() {
        new Thread(this).start();
    }

    public static int loadSound(String fileName) {
        if(mSoundPool==null || mAssetManager==null)
            return -1;
        String soundPrefix = ASSETS_SOUND + "/";
        String actualFileName = fileName;
        if(!fileName.startsWith(soundPrefix))
        	actualFileName = soundPrefix + actualFileName;
        if(!fileName.endsWith(".mp3"))
        	actualFileName += ".mp3";
        Integer soundIdObject = mStringSoundMap.get(actualFileName);
        if(soundIdObject != null)
            return soundIdObject.intValue();
        int soundId = -1;
        try {
            soundId = mSoundPool.load(mAssetManager.openFd(actualFileName), 1);
            if(soundId >= 0) {
                mStringSoundMap.put(actualFileName, Integer.valueOf(soundId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return soundId;
    }

    public static Bitmap loadBitmap(String fileName) {
    	String actualFileName = fileName;
    	String gfxPrefix = ASSETS_GFX + "/";
        if(!fileName.startsWith(gfxPrefix))
        	actualFileName = gfxPrefix + actualFileName;
        if(!fileName.endsWith(".png"))
        	actualFileName += ".png";
        Bitmap bmp = mStringBitmapMap.get(actualFileName);
        if(bmp != null)
            return bmp;

        try {
            InputStream is0 = mAssetManager.open(actualFileName);
            bmp = BitmapFactory.decodeStream(is0);
            Matrix matrix = new Matrix();
            float sx = (float)(MainView.screenWidth/Utils.BASE_SCREEN_WIDTH);
            float sy = (float)(MainView.screenHeight/Utils.BASE_SCREEN_HEIGHT);
            matrix.postScale(sx, sy);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            mStringBitmapMap.put(actualFileName, bmp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public void run() {
        try {
            mAssetsLoadedCount = 0;
            ArrayList<String> fileList = new ArrayList<String>();
            findFiles(fileList, ASSETS_GFX);
            findFiles(fileList, ASSETS_SOUND);
            mAssetsTotalCount = fileList.size();
            for(String fileName : fileList) {
                if(fileName.endsWith("mp3")) {
                    int id = loadSound(fileName);
                    mStringSoundMap.put(fileName, id);
                } else if(fileName.endsWith("png")) {
                    Bitmap bitmap = loadBitmap(fileName);
                    mStringBitmapMap.put(fileName, bitmap);
                }

                Thread.sleep(1);
                mAssetsLoadedCount++;
                if(mNotify != null)
                {
                    mNotify.onProgress(mAssetsLoadedCount, mAssetsTotalCount);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
