package com.wx.multihero;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wx.multihero.MainView;

public class Utils {
	public static Bitmap loadBitmap(String path) {
		AssetManager assetManager = MainView.context.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		return bitmap;
	}
}
