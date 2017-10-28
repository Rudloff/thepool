package com.ad.thepool.components;

import com.ad.thepool.Game;
import com.ad.thepool.TileMap;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class RenderTextComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3780348461158675249L;

	public static final int COMP_NAME = 24;

	// #TODO: other positions
	public static final int POS_LEFT_UP = 1;
	public static final int POS_CENTER = 2;
	public static final int POS_RIGHT_UP = 3;

	public static final int PADDING = (int) (Game.raster * 0.1);

	private int width;
	private int height;
	private String text;
	private boolean animate;
	private int position;
	private boolean background;
	private int startCharPos;
	private int animCharPos;
	private int speed;
	private boolean pause;
	private boolean activateButton;
	private String sendkey;
	private int color;
	private int fontColor;
	private boolean topBox;

	private int textFrameTileStart;

	public RenderTextComponent(boolean isActive, int textFrameTileStart) {
		super(COMP_NAME, isActive);
		this.width = 0;
		this.height = 0;
		this.startCharPos = 0;
		setText("");
		this.animate = false;
		this.background = false;
		animCharPos = 0;
		speed = 1;
		pause = false;
		this.position = POS_LEFT_UP;
		this.textFrameTileStart = textFrameTileStart;
		sendkey = null;
		color = GraphicsWrapper.COLOR_BLUE;
		fontColor = GraphicsWrapper.COLOR_WHITE;
		topBox = true;
	}

	public RenderTextComponent(TileMap tilemap, boolean isActive, int width, int height, String text, boolean animate, int position, boolean background, int textFrameTileStart) {
		super(COMP_NAME, isActive);
		this.width = width;
		this.height = height;
		this.startCharPos = 0;
		this.text = text;
		this.animate = animate;
		animCharPos = 0;
		speed = 1;
		pause = false;
		this.position = position;
		this.background = background;
		this.textFrameTileStart = textFrameTileStart;
		sendkey = null;
		color = GraphicsWrapper.COLOR_BLACK;
		fontColor = GraphicsWrapper.COLOR_WHITE;
		topBox = true;
	}

	@Override
	public void paintOverlay(GraphicsWrapper g) {
		if (pause == true) {
			speed = 10;
		}
		if (gobject.getScene().getCounter() % speed == 0) {
			if (animCharPos < text.length() - startCharPos) {
				animCharPos++;
				if (animate == true) {
					getSound("text").play();
				}
			}
		}
		if (background == true) {
			paintBox(g);
		}
		paintText(g);
		if (pause == true) {
			pause = false;
			speed = 1;
		}
	}

	public void paintBox(GraphicsWrapper g) {
		TransformTileComponent tileComp = gobject.getTransformTileComponent();

		int xpos = tileComp.getX();
		int ypos = tileComp.getY();

		if (activateButton == false) {
			g.fillRoundRect(xpos * Game.raster, ypos * Game.raster, width * Game.raster - PADDING, height * Game.raster - PADDING, Game.raster / 2, Game.raster / 2, color, 128);
		} else {
			g.fillRoundRect(xpos * Game.raster, ypos * Game.raster, width * Game.raster - PADDING, height * Game.raster - PADDING, Game.raster / 2, Game.raster / 2, GraphicsWrapper.COLOR_GREY, 192);
		}
	}

	public void paintText(GraphicsWrapper g) {
		TransformTileComponent tileComp = gobject.getTransformTileComponent();
		int xpos = tileComp.getX();
		int ypos = tileComp.getY();
		int fheight = g.getFontHeight();

		String[] tempList;
		String animText;

		if (animate == false) {
			tempList = text.split("\n");
		} else {
			animText = text.substring(0, animCharPos);
			if (animText.endsWith("\t")) {
				pause = true;
			}
			animText.replace("\t", "");
			tempList = animText.split("\n");

		}
		if (position == POS_LEFT_UP) {
			for (int i = 0; i < tempList.length; i++) {
				g.drawChars(tempList[i], 0, tempList[i].length(), xpos * Game.raster + PADDING, ypos * Game.raster + i * fheight + PADDING, fontColor, xpos * Game.raster + PADDING, ypos * Game.raster + PADDING, width * Game.raster - PADDING * 2, height * Game.raster - PADDING * 2, fheight + PADDING * 3);
			}
		} else if (position == POS_CENTER) {
			int textLen;

			for (int i = 0; i < tempList.length; i++) {
				textLen = g.getFontWidth(tempList[i]);

				g.drawChars(tempList[i], 0, tempList[i].length(), xpos * Game.raster + ((width * Game.raster - textLen) / 2), ypos * Game.raster + i * fheight, fontColor, xpos * Game.raster + PADDING, ypos * Game.raster + PADDING, width * Game.raster - PADDING * 2, height * Game.raster - PADDING * 2, fheight + PADDING
						* 3);
			}
		} else if (position == POS_RIGHT_UP) {
			int textLen;

			for (int i = 0; i < tempList.length; i++) {
				textLen = g.getFontWidth(tempList[i]);

				g.drawChars(tempList[i], 0, tempList[i].length(), xpos * Game.raster + width * Game.raster - PADDING - textLen, ypos * Game.raster + i * fheight, fontColor, xpos * Game.raster + PADDING, ypos * Game.raster + PADDING, width * Game.raster - PADDING * 2, height * Game.raster - PADDING * 2, fheight
						+ PADDING * 3);
			}
		}

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isAnimate() {
		return animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getTextFrameTileStart() {
		return textFrameTileStart;
	}

	public void setTextFrameTileStart(int textFrameTileStart) {
		this.textFrameTileStart = textFrameTileStart;
	}

	public boolean isBackground() {
		return background;
	}

	public void setBackground(boolean background) {
		this.background = background;
	}

	@Override
	public void keyDown(int key) {
		if (key == KeyEventWrapper.KEY_DOWN) {
			speed = 1;
		}
	}

	@Override
	public boolean mouseDown(int x, int y) {
		TransformTileComponent tileComp = gobject.getTransformTileComponent();

		int xpos = tileComp.getX();
		int ypos = tileComp.getY();

		if (x >= xpos * Game.raster && x <= (xpos + width) * Game.raster && y >= ypos * Game.raster && y <= (ypos + height) * Game.raster) {
			activateButton = true;
			if (topBox == true)
				gobject.getScene().sendMessage(gobject, null, null, "mouse.click.enter");
			if (sendkey != null) {
				gobject.getScene().sendMessage(gobject, null, null, sendkey);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseMove(int x, int y) {
		speed = 1;
		return true;
	}

	@Override
	public boolean mouseUp(int x, int y) {
		speed = 5;
		activateButton = false;
		return true;
	}

	@Override
	public void keyUp(int key) {
		if (key == KeyEventWrapper.KEY_DOWN) {
			speed = 5;
		}
	}

	public String getSendkey() {
		return sendkey;
	}

	public void setSendkey(String sendkey) {
		this.sendkey = sendkey;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public int getFontColor() {
		return fontColor;
	}

	public int getAnimCharPos() {
		return animCharPos;
	}

	public void setAnimCharPos(int animCharPos) {
		this.animCharPos = animCharPos;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isTopBox() {
		return topBox;
	}

	public void setTopBox(boolean topBox) {
		this.topBox = topBox;
	}
	
	

}
