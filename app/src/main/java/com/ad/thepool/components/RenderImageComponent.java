package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Scene;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.ImageWrapper;

public class RenderImageComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6321104169010782661L;

	public static final int COMP_NAME = 23;

	// #TODO: other positions
	public static final int POS_SCALE = 1;
	public static final int POS_CENTER = 2;
	public static final int POS_NOSCALE = 3;
	public static final int POS_OFFSET = 4;
	public static final int POS_PARALLAX_SLOW = 5;
	public static final int POS_PARALLAX_FAST = 6;
	private transient ImageWrapper image = null;

	private int width;
	private int height;
	private boolean background;
	private int position;
	private int offsetX;
	private int offsetY;
	
	private int colorReplacement;
	
	private String filename;

	public RenderImageComponent(String filename, boolean isActive) {
		super(COMP_NAME, isActive);
		this.width = 0;
		this.height = 0;
		this.background = false;
		this.position = POS_SCALE;
		this.filename = filename;
		if (filename != null) {
			loadImage(filename);
		}
		colorReplacement = -1;
	}

	@Override
	public void restoreSerializedState() {
		if (filename != null && image == null) {
			loadImage(filename);
		}
	}

	public void loadImage(String filename) {
		image = new ImageWrapper(false);
		image.loadImage(filename);
	}

	@Override
	public void paintOverlay(GraphicsWrapper g) {
		if (background == true) {
			paintBox(g);
		}
		ImageWrapper imageInt = new ImageWrapper(Scene.DIMX * Game.raster, Scene.DIMY * Game.raster, false);
		GraphicsWrapper gInt = imageInt.createGraphics();

		TransformTileComponent tileComp = gobject.getTransformTileComponent();
		int xpos = tileComp.getX();
		int ypos = tileComp.getY();

		paintImage(gInt);

		gInt.setClip(xpos * Game.raster,ypos * Game.raster, width * Game.raster,height * Game.raster);

		g.drawImage(imageInt, 0, 0, 0);
	}

	public void paintBox(GraphicsWrapper g) {
		TransformTileComponent tileComp = gobject.getTransformTileComponent();

		int xpos = tileComp.getX();
		int ypos = tileComp.getY();

		g.fillRoundRect(xpos * Game.raster, ypos * Game.raster, width * Game.raster, height * Game.raster, Game.raster / 2, Game.raster / 2, GraphicsWrapper.COLOR_BLACK, 255);
	}

	public void paintImage(GraphicsWrapper g) {
		if (image != null) 
		{
			TransformTileComponent tileComp = gobject.getTransformTileComponent();
			int xpos = tileComp.getX();
			int ypos = tileComp.getY();
			int zpos = tileComp.getZ();
			int qual = gobject.getScene().getGame().getQuality();

			if (position == POS_SCALE) 
			{
				if(zpos == GObject.Z_BACKGROUND && qual < Game.QUAL_MED)
				{
					if(colorReplacement != -1)
					{
						g.fillRectRGB(xpos * Game.raster, ypos * Game.raster, width * Game.raster, height * Game.raster, colorReplacement);
					}
				}
				else
				{
					g.drawImage(image, xpos * Game.raster, ypos * Game.raster, width * Game.raster, height * Game.raster,0);
				}
			}
			else if(position == POS_OFFSET)
			{
				if(zpos == GObject.Z_BACKGROUND && qual < Game.QUAL_MED)
				{
					if(colorReplacement != -1)
					{
						g.fillRectRGB(xpos * Game.raster, ypos * Game.raster, width * Game.raster, height * Game.raster, colorReplacement);
					}
				}
				else
				{
					drawWrapImage(g, image, xpos * Game.raster + offsetX, ypos * Game.raster + offsetY, width * Game.raster, height * Game.raster);
				}
			}
			else if(position == POS_PARALLAX_SLOW)
			{
				
				offsetX = gobject.getScene().getParSlowModx();
				offsetY = gobject.getScene().getParSlowMody();
				if(zpos == GObject.Z_BACKGROUND && qual < Game.QUAL_MED)
				{
					if(colorReplacement != -1)
					{
						g.fillRectRGB(xpos * Game.raster, ypos * Game.raster, width * Game.raster, height * Game.raster, colorReplacement);
					}
				}
				else
				{
					drawWrapImage(g, image, xpos * Game.raster + offsetX, ypos * Game.raster + offsetY, width * Game.raster, height * Game.raster );
				}
			}
			else if(position == POS_PARALLAX_FAST)
			{
				offsetX = gobject.getScene().getParFastModx();
				offsetY = gobject.getScene().getParFastMody();
				if(zpos == GObject.Z_BACKGROUND && qual < Game.QUAL_MED)
				{
					if(colorReplacement != -1)
					{
						g.fillRectRGB(xpos * Game.raster, ypos * Game.raster, width * Game.raster, height * Game.raster, colorReplacement);
					}
				}
				else
				{
					drawWrapImage(g, image, xpos * Game.raster + offsetX, ypos * Game.raster + offsetY, width * Game.raster, height * Game.raster );
				}
			}
		}
	}
	
	private void drawWrapImage(GraphicsWrapper g, ImageWrapper img, int x, int y, int width, int height) {

		g.drawImage(img, x, y, width,height,0);
		g.drawImage(img, x - width, y,width,height, 0);
		g.drawImage(img, x, y - height,width,height, 0);
		g.drawImage(img, x - width, y - height, width,height,0);
	}
	

	public ImageWrapper getImage() {
		return image;
	}

	public void setImage(ImageWrapper image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isBackground() {
		return background;
	}

	public void setBackground(boolean background) {
		this.background = background;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
		loadImage(filename);
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int getColorReplacement() {
		return colorReplacement;
	}

	public void setColorReplacement(int colorReplacement) {
		this.colorReplacement = colorReplacement;
	}
	
	
	

}
