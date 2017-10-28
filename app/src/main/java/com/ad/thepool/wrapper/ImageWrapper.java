package com.ad.thepool.wrapper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import com.ad.thepool.PoolThread;

public class ImageWrapper 
{
	BitmapDrawable image[] = new BitmapDrawable[4];
	boolean withRot;
	
	public static final int ROT_N = 0;
	public static final int ROT_E = 1;
	public static final int ROT_S = 2;
	public static final int ROT_W = 3;
	
	public ImageWrapper(boolean withRot)
	{
		this.withRot = withRot;
	}


	public ImageWrapper(int x, int y, boolean withRot) 
	{
		if(withRot == true)
		{
			for (int i = 0; i < image.length; i++) {
				Bitmap bitmap = Bitmap.createBitmap(x, y, Config.ARGB_8888);
				bitmap.setDensity(Bitmap.DENSITY_NONE);
				image[i] = new BitmapDrawable(PoolThread.mContext.getResources(),bitmap);
			}
		}
		else
		{
			Bitmap bitmap = Bitmap.createBitmap(x,y, Config.ARGB_8888);
			bitmap.setDensity(Bitmap.DENSITY_NONE);
			image[0] = new BitmapDrawable(PoolThread.mContext.getResources(),bitmap);
			image[1] = null;
			image[2] = null;
			image[3] = null;
		}
		this.withRot = withRot;
	}
	
	public GraphicsWrapper createGraphics(int rotation)
	{
		Bitmap bitmap = image[rotation].getBitmap();
		Canvas canvas = new Canvas(bitmap);
		
		GraphicsWrapper graphicsWrap = new GraphicsWrapper(canvas);
		return graphicsWrap;
	}

	public GraphicsWrapper createGraphics()
	{
		Bitmap bitmap = image[0].getBitmap();
		Canvas canvas = new Canvas(bitmap);
		
		GraphicsWrapper graphicsWrap = new GraphicsWrapper(canvas);
		return graphicsWrap;
	}

	public int getHeight()
	{
		return image[0].getBitmap().getHeight();
	}
	
	public int getWidth()
	{
		return image[0].getBitmap().getWidth();
	}
	
	public ImageWrapper getSubimage(int x, int y, int width, int height, int rot, boolean withRot)
	{
		ImageWrapper subImageWrapper = new ImageWrapper(width,height,withRot);
		BitmapDrawable subImage;
		
		Bitmap bitmap;
		Bitmap subBitmap;
		
		bitmap = image[rot].getBitmap();
		subBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
		subImage = new BitmapDrawable(PoolThread.mContext.getResources(),subBitmap);
		subImageWrapper.setImage(subImage);
		return subImageWrapper;
		
	}
	
	public ImageWrapper scale(int nwidth, int nheight, int rot, boolean withRot)
	{
		ImageWrapper subImage = new ImageWrapper(nwidth, nheight, withRot);
		
		Bitmap newBitmap = Bitmap.createScaledBitmap(image[0].getBitmap(), nwidth, nheight, false);

		subImage.setImage(new BitmapDrawable(PoolThread.mContext.getResources(),newBitmap));

		return subImage;
	}

	
	public boolean loadImage(String fileName)
	{
		Resources res = PoolThread.mContext.getResources();
		
		fileName = fileName.substring(0,fileName.indexOf("."));
		
		image[0] = (BitmapDrawable)res.getDrawable(res.getIdentifier("com.ad.thepool:drawable/" + fileName, null, null));
		if(withRot == true)
		{
			Matrix matrix = new Matrix();
			matrix.postRotate(90);

			for (int i = 1; i < image.length; i++) 
			{
				Bitmap newBitmap = Bitmap.createBitmap(image[i-1].getBitmap(), 0, 0, getWidth(), getHeight(), matrix, true);
				image[i] = new BitmapDrawable(PoolThread.mContext.getResources(),newBitmap);
			}

		}
		
		return true;
	}


	public BitmapDrawable getImage(int rot) {
		return image[rot];
	}

	public void setImage(BitmapDrawable imagenew) {
		image[0] = imagenew;
		if(withRot == true)
		{
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			for (int i = 1; i < image.length; i++) 
			{
				Bitmap newBitmap = Bitmap.createBitmap(image[i-1].getBitmap(), 0, 0, getWidth(), getHeight(), matrix, true);
				image[i] = new BitmapDrawable(PoolThread.mContext.getResources(),newBitmap);
			}
		}
	}

}
