package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.ad.thepool.Game;
import com.ad.thepool.Scene;
import com.ad.thepool.TileMap;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.ImageWrapper;

public class RenderTileComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8645405132813330057L;

	public static final int COMP_NAME = 25;

	private boolean toMove = false;
	private boolean drawEnergy = false;

	private boolean renderInAnimCycle = false;

	private ArrayList<Integer> pathStepList;
	private int lastXpos;
	private int lastYpos;
	private int lastPathElem;
	// Current move of tile
	private int moveoffsetx, moveoffsety;
	// pixel center of tile
	private int centerx, centery;

	private TileMap tilemap;

	private HashMap<String, Animation> animationList;
	private String activeAnimation;

	private int energyX, energyY;
	private float energyPercent;

	private int tilePos;

	public RenderTileComponent(TileMap tilemap, int tilePos, boolean isActive) {
		super(COMP_NAME, isActive);
		this.tilePos = tilePos;
		this.tilemap = tilemap;
		animationList = new HashMap<String, Animation>();
		pathStepList = new ArrayList<Integer>();
	}

	public RenderTileComponent(TileMap tilemap, int tilePos, boolean isActive, HashMap<String, Animation> animationList) {
		super(COMP_NAME, isActive);
		this.tilePos = tilePos;
		this.tilemap = tilemap;
		this.animationList = animationList;
		pathStepList = new ArrayList<Integer>();
		activeAnimation = new String();
	}

	@Override
	public void paint(GraphicsWrapper g) {

		int frame = gobject.getScene().getFrame();
		float bdarkest = gobject.getScene().getBrightnessDarkest();
		float bscale = gobject.getScene().getBrightnessScale();

		float percent = -1;

		boolean qual = true;

		if (getGobject().getScene().getGame().getQuality() == Game.QUAL_LOW) {
			qual = false;
		}

		TransformTileComponent transComp = gobject.getTransformTileComponent();
		DestroyAbleComponent destroyAble = gobject.getDestroyAbleComponent();

		if (destroyAble != null) {
			if (destroyAble.getShowEnergy() > 0) {
				percent = (float) destroyAble.getEnergy() / (float) destroyAble.getMaxEnergy();
			}
		}

		if (transComp == null) {
			return;
		}
		ImageWrapper img;
		if (animationList.size() > 0 && activeAnimation.length() > 0) {
			Animation actAnimation = animationList.get(activeAnimation);
			if (actAnimation != null) {
				int animTilePos = actAnimation.getTilePos(frame);
				if (animTilePos != -1) {
					tilePos = animTilePos;
					img = tilemap.getTile(tilePos);
				}
			}
		}

		img = tilemap.getTile(tilePos);
		int rot = transComp.getRot();

		int x = transComp.getX();
		int y = transComp.getY();
		int rgb = gobject.getScene().lightmap[y][x];

		if (frame == 0 || (frame != 0 && toMove == false)) {
			int raster2 = Game.raster / 2;
			MoveComponent moveComp = gobject.getMoveComponent();
			boolean drawFrame = true;

			if (moveComp != null) {
				if (moveComp.isActive() && moveComp.isShiftAppear() == true) {
					drawFrame = false;
				}
			}
			if (drawFrame == true) {
				g.drawImage(img, transComp.getX() * Game.raster, transComp.getY() * Game.raster, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
			}

			if (percent != -1) {
				markEnergy(transComp.getX() * Game.raster, transComp.getY() * Game.raster - Game.raster / 2, percent);
			}
			lastXpos = transComp.getX();
			lastYpos = transComp.getY();
			lastPathElem = 0;
			// pathStepList.clear();
			moveoffsetx = 0;
			moveoffsety = 0;
			centerx = transComp.getX() * Game.raster + raster2;
			centery = transComp.getY() * Game.raster + raster2;

			CameraComponent camera = gobject.getCameraComponent();
			if (camera != null && camera.isActive == true) {

				camera.setCamera(centerx, centery);
			}

		}

		if (frame != 0 && pathStepList.size() > 0) {
			// TODO: Interpolate moves
			if (pathStepList.size() == 3) {
				pathStepList.add(UNKNOWN);
			} else if (pathStepList.size() == 5) {
				pathStepList.add(4, UNKNOWN);
				pathStepList.add(3, UNKNOWN);
				pathStepList.add(2, UNKNOWN);
			} else if (pathStepList.size() == 6) {
				pathStepList.add(3, UNKNOWN);
				pathStepList.add(2, UNKNOWN);
			} else if (pathStepList.size() == 7) {
				pathStepList.add(4, UNKNOWN);
			}
			int step = gobject.getScene().getAnimSeq() / pathStepList.size();
			if (step == 0) {
				step = 1;
			}
			int stepElement = (int) Math.floor(frame / step);

			if (stepElement > lastPathElem) {
				Integer dir = pathStepList.get(lastPathElem);
				lastPathElem = stepElement;
				switch (dir) {
				case UP:
				case SHIFT_UP:
					lastYpos = Scene.calcPositionY(lastYpos, -1);
					break;
				case DOWN:
				case SHIFT_DOWN:
					lastYpos = Scene.calcPositionY(lastYpos, 1);
					break;
				case LEFT:
				case SHIFT_LEFT:
					lastXpos = Scene.calcPositionX(lastXpos, -1);
					break;
				case RIGHT:
				case SHIFT_RIGHT:
					lastXpos = Scene.calcPositionX(lastXpos, 1);
					break;
				default:
					break;
				}
			}

			Integer dir = pathStepList.get(stepElement);
			int move = (Game.raster / step) * (frame % step);
			CameraComponent camera = gobject.getCameraComponent();

			int paintx = lastXpos * Game.raster;
			int painty = lastYpos * Game.raster;
			int raster2 = Game.raster / 2;

			switch (dir) {
			case UNKNOWN:
				moveoffsetx = 0;
				moveoffsety = 0;
				centerx = paintx + raster2;
				centery = painty + raster2;
				drawWrapImage(g, img, centerx, centery, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				if (percent != -1) {
					markEnergy(paintx, painty - raster2, percent);

					// drawEnergy(g, paintx, painty - raster2, Game.raster,
					// Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}

				break;
			case UP:
				moveoffsetx = 0;
				moveoffsety = -move;
				centerx = paintx + Game.raster / 2;
				centery = painty + Game.raster / 2 - move;
				drawWrapImage(g, img, paintx, painty - move, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				if (percent != -1) {
					markEnergy(paintx, (painty - move) - raster2, percent);

					// drawEnergy(g, paintx, (painty - move) - raster2,
					// Game.raster, Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}

				break;
			case SHIFT_UP:
				moveoffsetx = 0;
				moveoffsety = -move;
				centerx = paintx + raster2;
				centery = (painty - move) + raster2;
				if (move > 0) {
					img = img.getSubimage(0, 0, Game.raster, move, rot, false);
					drawWrapImage(g, img, paintx, (painty + Game.raster) - move, Game.raster, move, rgb, bdarkest, bscale, rot, qual);
				} else {
					drawWrapImage(g, img, paintx, painty - move, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				}
				if (percent != -1) {
					markEnergy(paintx, (painty - move) - raster2, percent);
					// drawEnergy(g, paintx, (painty - move) - raster2,
					// Game.raster, Game.raster / 4, percent,qual);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}

				break;
			case DOWN:
				moveoffsetx = 0;
				moveoffsety = move;
				centerx = paintx + raster2;
				centery = (painty + move) + raster2;
				drawWrapImage(g, img, paintx, painty + move, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				if (percent != -1) {
					markEnergy(paintx, (painty + move) - raster2, percent);
					// drawEnergy(g, paintx,(painty + move) - raster2,
					// Game.raster, Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}
				break;
			case SHIFT_DOWN:
				moveoffsetx = 0;
				moveoffsety = move;
				centerx = paintx + raster2;
				centery = (painty + move) + raster2;
				if (move > 0) {
					img = img.getSubimage(0, Game.raster - move, Game.raster, move, rot, false);
					drawWrapImage(g, img, paintx, painty, Game.raster, move, rgb, bdarkest, bscale, 0, qual);
				} else {
					drawWrapImage(g, img, paintx, painty + move, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				}
				if (percent != -1) {
					markEnergy(paintx, (painty + move) - raster2, percent);
					// drawEnergy(g, paintx,(painty + move) - raster2,
					// Game.raster, Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}
				break;
			case LEFT:
				moveoffsetx = -move;
				moveoffsety = 0;
				centerx = (paintx - move) + raster2;
				centery = painty + raster2;
				drawWrapImage(g, img, paintx - move, painty, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				if (percent != -1) {
					markEnergy(paintx - move, painty - raster2, percent);
					// drawEnergy(g, paintx - move, painty - raster2,
					// Game.raster, Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}
				break;
			case SHIFT_LEFT:
				moveoffsetx = -move;
				moveoffsety = 0;
				centerx = (paintx - move) + raster2;
				centery = painty + raster2;
				if (move > 0) {
					img = img.getSubimage(0, 0, move, Game.raster, rot, false);
					drawWrapImage(g, img, paintx + Game.raster - move, painty, move, Game.raster, rgb, bdarkest, bscale, 0, qual);
				} else {
					drawWrapImage(g, img, paintx - move, painty, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				}

				if (percent != -1) {
					markEnergy(paintx - move, painty - raster2, percent);
					// drawEnergy(g, paintx - move, painty - raster2,
					// Game.raster, Game.raster / 4, percent,qual);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}
				break;
			case RIGHT:
				moveoffsetx = move;
				moveoffsety = 0;
				centerx = (paintx + move) + raster2;
				centery = painty + raster2;
				drawWrapImage(g, img, paintx + move, painty, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				if (percent != -1) {
					markEnergy(paintx + move, painty - raster2, percent);
					// drawEnergy(g, paintx + move, painty - raster2,
					// Game.raster, Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}

				break;
			case SHIFT_RIGHT:
				moveoffsetx = move;
				moveoffsety = 0;
				centerx = (paintx + move) + raster2;
				centery = painty + raster2;
				if (move > 0) {
					img = img.getSubimage(Game.raster - move, 0, move, Game.raster, rot, false);
					drawWrapImage(g, img, paintx, painty, move, Game.raster, rgb, bdarkest, bscale, 0, qual);
				} else {
					drawWrapImage(g, img, paintx + move, painty, Game.raster, Game.raster, rgb, bdarkest, bscale, rot, qual);
				}
				if (percent != -1) {
					markEnergy(paintx + move, painty - raster2, percent);
					// drawEnergy(g, paintx + move, painty - raster2,
					// Game.raster, Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}

				break;

			default:
				drawWrapImage(g, img, paintx, painty + move, Game.raster, Game.raster, rgb, bdarkest, bscale, 0, qual);
				centerx = paintx + raster2;
				centery = painty + raster2;
				if (percent != -1) {
					markEnergy(paintx, painty - raster2, percent);
					// drawEnergy(g, paintx, painty - raster2, Game.raster,
					// Game.raster / 4, percent);
				}
				if (camera != null && camera.isActive == true) {
					camera.setCamera(centerx, centery);
				}

				break;
			}
		}
		if (frame == gobject.getScene().getAnimSeq() - 1) {
			toMove = false;
			pathStepList.clear();
		}
	}

	@Override
	public void paintOverlay(GraphicsWrapper g) {
		// TODO Auto-generated method stub
		super.paintOverlay(g);
		if (drawEnergy == true) {
			drawEnergy(g, energyX, energyY, Game.raster, Game.raster / 4, energyPercent);
			drawEnergy = false;
		}

	}

	private void markEnergy(int x, int y, float p) {
		energyX = x;
		energyY = y;
		energyPercent = p;
		drawEnergy = true;
	}

	public static int calcDir(int x, int y) {
		if (x < 0 && y == 0) {
			return LEFT;
		} else if (x > 0 && y == 0) {
			return RIGHT;
		} else if (x == 0 && y < 0) {
			return UP;
		} else if (x == 0 && y > 0) {
			return DOWN;
		} else {
			return -1;
		}
	}

	private void drawWrapImage(GraphicsWrapper g, ImageWrapper img, int x, int y, int width, int height, int rgb, float bdarkest, float bscaled, int rot, boolean qual) {
		int screenx = Scene.DIMX * Game.raster;
		int screeny = Scene.DIMY * Game.raster;

		// TODO: Blinking
		g.drawImage(img, x, y, width, height, rgb, bdarkest, bscaled, rot, qual);

		if (x < 0) {
			g.drawImage(img, screenx + x, y, width, height, rgb, bdarkest, bscaled, rot, qual);
		}
		if (x + width > screenx) {
			g.drawImage(img, -screenx + x, y, width, height, rgb, bdarkest, bscaled, rot, qual);
		}
		if (y < 0) {
			g.drawImage(img, x, screeny + y, width, height, rgb, bdarkest, bscaled, rot, qual);
		}
		if (y + height > screeny) {
			g.drawImage(img, x, -screeny + y, width, height, rgb, bdarkest, bscaled, rot, qual);
		}
	}

	private void drawEnergy(GraphicsWrapper g, int x, int y, int width, int height, float percent) {
		int screenx = Scene.DIMX * Game.raster;
		int screeny = Scene.DIMY * Game.raster;
		int colorbar;

		if (percent > 0.95F) {
			colorbar = GraphicsWrapper.COLOR_GREEN;
		} else {
			colorbar = GraphicsWrapper.COLOR_RED;
		}

		g.fillRect(x, y, (int) (width * percent), height, colorbar, 255);

		g.drawRect(x, y, width, height, GraphicsWrapper.COLOR_BLACK, 255);

		if (x < 0) {
			g.fillRect(screenx + x, y, (int) (width * percent), height, colorbar, 255);
			g.drawRect(screenx + x, y, width, height, GraphicsWrapper.COLOR_BLACK, 255);
		}
		if (x + width > screenx) {
			g.fillRect(-screenx + x, y, (int) (width * percent), height, colorbar, 255);
			g.drawRect(-screenx + x, y, width, height, GraphicsWrapper.COLOR_BLACK, 255);
		}
		if (y < 0) {
			g.fillRect(x, screeny + y, (int) (width * percent), height, colorbar, 255);
			g.drawRect(x, screeny + y, width, height, GraphicsWrapper.COLOR_BLACK, 255);
		}
		if (y + height > screeny) {
			g.fillRect(x, -screeny + y, (int) (width * percent), height, colorbar, 255);
			g.drawRect(x, -screeny + y, width, height, GraphicsWrapper.COLOR_BLACK, 255);
		}
	}

	@Override
	public void animateMove(GraphicsWrapper g, int frame) {

	}

	public TileMap getTilemap() {
		return tilemap;
	}

	public void setTilemap(TileMap tilemap) {
		this.tilemap = tilemap;
	}

	public int getTilePos() {
		return tilePos;
	}

	public void setTilePos(int tilePos) {
		this.tilePos = tilePos;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		RenderTileComponent comp = (RenderTileComponent) super.clone();
		comp.pathStepList = new ArrayList<Integer>();

		HashMap<String, Animation> newAnimationList = new HashMap<String, Animation>();

		for (Iterator<String> iterator = animationList.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Animation anim = animationList.get(key);
			Animation newAnim = (Animation) anim.clone();
			newAnimationList.put(key, newAnim);
		}
		comp.setAnimationList(newAnimationList);

		return comp;

	}

	public ArrayList<Integer> getPathStepList() {
		return pathStepList;
	}

	public void setPathStepList(ArrayList<Integer> pathStepList) {
		this.pathStepList = pathStepList;
	}

	public void addPathStep(int direction) {
		toMove = true;
		pathStepList.add(direction);
	}

	public void clearPathStep() {
		pathStepList.clear();
	}

	public boolean isRenderInAnimCycle() {
		return renderInAnimCycle;
	}

	public void setRenderInAnimCycle(boolean renderInAnimCycle) {
		this.renderInAnimCycle = renderInAnimCycle;
	}

	public boolean isDrawEnergy() {
		return drawEnergy;
	}

	public void setDrawEnergy(boolean drawEnergy) {
		this.drawEnergy = drawEnergy;
	}

	public HashMap<String, Animation> getAnimationList() {
		return animationList;
	}

	public void setAnimationList(HashMap<String, Animation> animationList) {
		this.animationList = animationList;
	}

	public String getActiveAnimation() {
		return activeAnimation;
	}

	public void setActiveAnimation(String activeAnimation, boolean active) {
		this.activeAnimation = activeAnimation;
		Animation actAnimation = animationList.get(activeAnimation);
		if (actAnimation != null) {
			actAnimation.setActive(active);
		}

	}

	public int getMoveoffsetx() {
		return moveoffsetx;
	}

	public int getMoveoffsety() {
		return moveoffsety;
	}

	public int getCenterx() {
		return centerx;
	}

	public int getCentery() {
		return centery;
	}

}
