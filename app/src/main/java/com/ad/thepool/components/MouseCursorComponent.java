package com.ad.thepool.components;

import com.ad.thepool.Game;
import com.ad.thepool.Vector2D;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class MouseCursorComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1045185704160530165L;

	public static final int COMP_NAME = 39;

	private int timer;

	private int xStart;
	private int yStart;
	private int xEnd;
	private int yEnd;
	private boolean isLaser;

	public static final int MAX_TIMER = 15;

	public MouseCursorComponent(boolean isActive) {
		super(COMP_NAME, isActive);
		xStart = -1;
		yStart = -1;
		xEnd = -1;
		yEnd = -1;
		isLaser = false;
		resetTimer();
	}

	@Override
	public void update() {
		if (timer > 0) {
			timer--;
		}
	}

	public void resetTimer() {
		timer = MAX_TIMER;
	}

	@Override
	public void initClone() {
		// TODO Auto-generated method stub
		super.initRestore();
		resetTimer();
	}

	@Override
	public void initRestore() {
		// TODO Auto-generated method stub
		super.initRestore();
		resetTimer();
	}

	public boolean isTimerFinish() {
		if (timer <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBlink() {
		if (isTimerFinish() == true) {
			return false;
		} else {
			if (timer % 2 == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void paintOverlay(GraphicsWrapper g) {
		if (isBlink() == true) {
			int[] cent;

			cent = mouseCentralPos(Component.AREA_CENTER);
			int arrX = cent[0], arrY = cent[1];
			// if(arrTimer % 4 == 0)
			// {
			// arrX = cent[0] + Game.raster * 6;
			// arrY = cent[1];
			// }
			// else if(arrTimer % 4 == 1)
			// {
			// arrX = cent[0];
			// arrY = cent[1] + Game.raster * 6;
			// }
			// else if(arrTimer % 4 == 2)
			// {
			// arrX = cent[0] - Game.raster * 6;
			// arrY = cent[1];
			// }
			// else if(arrTimer % 4 == 3)
			// {
			// arrX = cent[0];
			// arrY = cent[1] - Game.raster * 6;
			// }
			// if(timer % 4 == 0)
			// arrTimer ++;
			paintCentralCursor(g, cent[0], cent[1], arrX, arrY);
			cent = mouseCentralPos(Component.AREA_LEFT);
			paintOuterCursor(g, cent[0], cent[1], 128);
			cent = mouseCentralPos(Component.AREA_RIGHT);
			paintOuterCursor(g, cent[0], cent[1], 128);
			cent = mouseCentralPos(Component.AREA_UP);
			paintOuterCursor(g, cent[0], cent[1], 128);
			cent = mouseCentralPos(Component.AREA_DOWN);
			paintOuterCursor(g, cent[0], cent[1], 128);
		}

		if (xStart != -1) {
			if (isLaser == false) {
				paintCentralCursor(g, xStart, yStart, xEnd, yEnd);
			} else {
				paintOuterCursor(g, xStart, yStart, 128);
			}
		}
		if (gobject.getScene().getGame().getQuality() > Game.QUAL_LOW) {
			int[] cent;

			cent = mouseCentralPos(Component.AREA_LEFT);
			paintOuterCursor(g, cent[0], cent[1], 16);
			cent = mouseCentralPos(Component.AREA_RIGHT);
			paintOuterCursor(g, cent[0], cent[1], 16);
			cent = mouseCentralPos(Component.AREA_UP);
			paintOuterCursor(g, cent[0], cent[1], 16);
			cent = mouseCentralPos(Component.AREA_DOWN);
			paintOuterCursor(g, cent[0], cent[1], 16);
		}
	}

	public void paintCentralCursor(GraphicsWrapper g, int x1, int y1, int x2, int y2) {
		g.drawCircle(x1, y1, Game.raster * 2, GraphicsWrapper.COLOR_WHITE, GraphicsWrapper.COLOR_BLACK, 128);
		g.drawLine(x1, y1, x2, y2, GraphicsWrapper.COLOR_WHITE, -1, 3);
		Vector2D vec1 = new Vector2D(x1, y1);
		Vector2D vec2 = new Vector2D(x2, y2);
		if (x1 != x2 || y1 != y2) {
			Vector2D vecRes = Vector2D.sub(vec2, vec1);
			vecRes.rotate(10);
			vecRes.setLen(Game.raster);
			Vector2D vecLeft = Vector2D.add(vec2, vecRes);
			vecRes.rotate(-20);
			vecRes.setLen(Game.raster);
			Vector2D vecRight = Vector2D.add(vec2, vecRes);

			g.drawLine(x2, y2, (int) vecLeft.getX(), (int) vecLeft.getY(), GraphicsWrapper.COLOR_WHITE, -1, 3);
			g.drawLine(x2, y2, (int) vecRight.getX(), (int) vecRight.getY(), GraphicsWrapper.COLOR_WHITE, -1, 3);
		}
	}

	public void paintOuterCursor(GraphicsWrapper g, int x, int y, int alpha) {
		g.drawCircle(x, y, Game.raster * 2, GraphicsWrapper.COLOR_RED, GraphicsWrapper.COLOR_BLACK, alpha);
	}

	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public int getxEnd() {
		return xEnd;
	}

	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	public int getyEnd() {
		return yEnd;
	}

	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}

	public boolean isLaser() {
		return isLaser;
	}

	public void setLaser(boolean isLaser) {
		this.isLaser = isLaser;
	}

}
