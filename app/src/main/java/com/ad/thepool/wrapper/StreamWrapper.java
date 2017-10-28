package com.ad.thepool.wrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Context;

import com.ad.thepool.PoolThread;

public class StreamWrapper 
{
	private static Activity activity;

	public static BufferedReader getReader(String filename)
	{
		try {
			InputStream rawRes = PoolThread.mContext.getAssets().open(filename);
			return new BufferedReader(new InputStreamReader(rawRes, "UTF8"));
		} catch (UnsupportedEncodingException e) {
			Log.e(e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(e.getMessage());
			return null;
		} 
	}
	
	public static FileOutputStream getFileOutputStream(String filename)
	{
		try {
			return PoolThread.mContext.openFileOutput(filename, Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			Log.e(e.getMessage());
			return null;
		}
	}
	
	public static FileInputStream getFileInputStream(String filename)
	{
		try {
			return PoolThread.mContext.openFileInput(filename);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public static boolean deleteFile(String filename)
	{
		if(activity != null)
			return activity.deleteFile(filename);
		else
			return true;
		
	}
	
	public static boolean fileExists(String filename)
	{
		
		
//		File[] fileList;
		
		File dir = PoolThread.mContext.getFilesDir();
		
		File file = new File(dir, filename);
		
		Log.d("File exits:" + file.exists());
		
		return file.exists();
	}
//		fileList = dir.listFiles();
//		
//		// fileList = PoolThread.mContext.fileList();
//		
//		Log.d("Files:" + fileList);
//		
//		for (int i = 0; i < fileList.length; i++) {
//			if(fileList[i].getName().equals(filename))
//			{
//				Log.d("File:" + fileList[i]);
//				return true;
//			}
//		}
//		return false;
//	}


	public static void setActivity(Activity activity) {
		StreamWrapper.activity = activity;
	}
	
	
}
