package com.ad.thepool;

import java.io.Serializable;

import com.ad.thepool.wrapper.ImageWrapper;

public class TileMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086551746357461102L;

	private transient ImageWrapper tileImages;
	private transient ImageWrapper[] tileCacheN = new ImageWrapper[11 * 14];

	private int sizex;
	private int sizey;
	private int sizeTilex;
	private int sizeTiley;
	private String filename;

	public static final int raster = 64;

	public TileMap(String filename) {
		this.filename = filename;
		if (filename != null) {
			loadImages(filename + "_" + raster + ".png");
		}
	}

	public void restoreState() {
		tileCacheN = new ImageWrapper[11 * 14];
		if (filename != null && tileImages == null) {
			loadImages(filename + "_" + raster + ".png");
		}
	}

	public void loadImages(String filename) {
		tileImages = new ImageWrapper(false);
		tileImages.loadImage(filename);
		sizex = tileImages.getWidth();
		sizey = tileImages.getHeight();
		sizeTilex = sizex / raster;
		sizeTiley = sizey / raster;

		for (int i = 0; i < sizeTilex * sizeTiley; i++) {
			tileCacheN[i] = getTileFromMap(i);
		}
	}

	public ImageWrapper getTile(int num) {
		// ImageWrapper img = new ImageWrapper();
		// if(filename != null && tileImages == null)
		// {
		// loadImages(filename + "_" + raster + ".png");
		// }
		return tileCacheN[num];
	}

	public ImageWrapper getTileFromMap(int num) {
		int posx, posy;

		ImageWrapper subImageW;

		posx = num % sizeTilex;
		posy = (int) Math.floor(num / sizeTilex);

		subImageW = tileImages.getSubimage(posx * raster, posy * raster, raster, raster, 0, true);
		subImageW = subImageW.scale(Game.raster, Game.raster, 0, true);

		return subImageW;
	}

}
