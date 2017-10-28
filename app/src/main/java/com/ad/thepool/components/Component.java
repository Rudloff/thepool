package com.ad.thepool.components;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Scene;
import com.ad.thepool.objects.ExplodeRound;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.SoundWrapper;

public class Component implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4555848846874292697L;
	protected int id;
	protected boolean isActive;
	protected GObject gobject;

	public static final int STOP = -2;
	public static final int UNKNOWN = -1;
	public static final int DOWN = 0;
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int SHIFT_DOWN = 4;
	public static final int SHIFT_UP = 5;
	public static final int SHIFT_LEFT = 6;
	public static final int SHIFT_RIGHT = 7;
	public static final int DOWN_LEFT = 8;
	public static final int DOWN_RIGHT = 9;
	public static final int UP_LEFT = 10;
	public static final int UP_RIGHT = 11;

	public static final int AREA_OUT = -1;
	public static final int AREA_DOWN = 0;
	public static final int AREA_UP = 1;
	public static final int AREA_LEFT = 2;
	public static final int AREA_RIGHT = 3;
	public static final int AREA_CENTER = 4;

	// Collider matrix conversion
	// private static String collAreaDown[][] =
	// {{"00","10","20"},{"01","11","21"},{"02","12","22"}};
	// private static String collAreaUp[][] =
	// {{"22","12","02"},{"21","11","01"},{"20","10","00"}};
	// private static String collAreaLeft[][] =
	// {{"20","21","22"},{"10","11","12"},{"00","01","02"}};
	// private static String collAreaRight[][] =
	// {{"02","01","00"},{"12","11","10"},{"22","21","20"}};

	// Collider matrix conversion
	private static int collAreaDownX[][] = { { 0, 1, 2 }, { 0, 1, 2 }, { 0, 1, 2 } };
	private static int collAreaUpX[][] = { { 2, 1, 0 }, { 2, 1, 0 }, { 2, 1, 0 } };
	private static int collAreaLeftX[][] = { { 2, 2, 2 }, { 1, 1, 1 }, { 0, 0, 0 } };
	private static int collAreaRightX[][] = { { 0, 0, 0 }, { 1, 1, 1 }, { 2, 2, 2 } };

	// Collider matrix conversion
	private static int collAreaDownY[][] = { { 0, 0, 0 }, { 1, 1, 1 }, { 2, 2, 2 } };
	private static int collAreaUpY[][] = { { 2, 2, 2 }, { 1, 1, 1 }, { 0, 0, 0 } };
	private static int collAreaLeftY[][] = { { 0, 1, 2 }, { 0, 1, 2 }, { 0, 1, 2 } };
	private static int collAreaRightY[][] = { { 2, 1, 0 }, { 2, 1, 0 }, { 2, 1, 0 } };

	// Render Movements
	// down,up, left,right
	public static int moveDown[] = { DOWN, UP, LEFT, RIGHT, SHIFT_DOWN, SHIFT_UP, SHIFT_LEFT, SHIFT_RIGHT };
	public static int moveUp[] = { UP, DOWN, RIGHT, LEFT, SHIFT_UP, SHIFT_DOWN, SHIFT_RIGHT, SHIFT_LEFT };
	public static int moveLeft[] = { LEFT, RIGHT, UP, DOWN, SHIFT_LEFT, SHIFT_RIGHT, SHIFT_UP, SHIFT_DOWN };
	public static int moveRight[] = { RIGHT, LEFT, DOWN, UP, SHIFT_RIGHT, SHIFT_LEFT, SHIFT_DOWN, SHIFT_UP };

	// Shifts
	// public static String shiftAreaDown[][] =
	// {{"-1-1"," 0-1"," 1-1"},{"-1 0"," 0 0"," 1 0"},{"-1 1"," 0 1"," 1 1"}};
	// public static String shiftAreaUp[][] =
	// {{" 1 1"," 0 1","-1 1"},{" 1 0"," 0 0","-1 0"},{" 1-1"," 0-1","-1-1"}};
	// public static String shiftAreaLeft[][] =
	// {{" 1-1"," 1 0"," 1 1"},{" 0-1"," 0 0"," 0 1"},{"-1-1","-1 0","-1 1"}};
	// public static String shiftAreaRight[][] =
	// {{"-1 1","-1 0","-1-1"},{" 0 1"," 0 0"," 0-1"},{" 1 1"," 1 0"," 1-1"}};

	public static int shiftAreaDownX[][] = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
	public static int shiftAreaUpX[][] = { { 1, 0, -1 }, { 1, 0, -1 }, { 1, 0, -1 } };
	public static int shiftAreaLeftX[][] = { { 1, 1, 1 }, { 0, 0, 0 }, { -1, -1, -1 } };
	public static int shiftAreaRightX[][] = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };

	public static int shiftAreaDownY[][] = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
	public static int shiftAreaUpY[][] = { { 1, 1, 1 }, { 0, 0, 0 }, { -1, -1, -1 } };
	public static int shiftAreaLeftY[][] = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
	public static int shiftAreaRightY[][] = { { 1, 0, -1 }, { 1, 0, -1 }, { 1, 0, -1 } };

	public static NumberFormat nf = NumberFormat.getInstance();

	public Component(int id, boolean isActive) {
		this.id = id;
		this.isActive = isActive;
	}

	public void paint(GraphicsWrapper g) {
	}

	public void paintOverlay(GraphicsWrapper g) {
	}

	public void destroy() {
	}

	public void animateMove(GraphicsWrapper g, int frame) {
	}

	public void onCollisionEnter(GObject collidedObject, int x, int y) {
	}

	public void onCollisionStay(GObject collidedObject, int x, int y) {
	}

	public void onCollisionLeave(GObject collidedObject, int x, int y) {
	}

	public void onTriggerEnter(GObject collidedObject, int x, int y) {
	}

	public void onTriggerStay(GObject collidedObject, int x, int y) {
	}

	public void onTriggerLeave(GObject collidedObject, int x, int y) {
	}

	public void initFrame() {
	}

	public void initClone() {
	}

	public void update() {
	}

	public void keyDown(int key) {
	}

	public void keyUp(int key) {
	}

	public boolean mouseDown(int x, int y) {
		return false;
	}

	public boolean mouseUp(int x, int y) {
		return false;

	}

	public boolean mouseMove(int x, int y) {
		return false;
	}

	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
	}

	public int mouseArea(int x, int y) {
		int width = Game.raster * 25;
		int height = Game.raster * 15;
		int area;

		int perX = x * 100 / width;
		int perY = y * 100 / height;

		if (perX >= 0 && perX <= (100 - Game.AREA_CENTER) / 2) {
			area = AREA_LEFT;
		} else if (perX >= Game.AREA_CENTER + ((100 - Game.AREA_CENTER) / 2) && perX <= 100) {
			area = AREA_RIGHT;
		} else if (perY >= 0 && perY <= (100 - Game.AREA_CENTER) / 2) {
			area = AREA_UP;
		} else if (perY >= Game.AREA_CENTER + ((100 - Game.AREA_CENTER)) / 2 && perY <= 100) {
			area = AREA_DOWN;
		} else if (perX > (100 - Game.AREA_CENTER) / 2 && perX < Game.AREA_CENTER + ((100 - Game.AREA_CENTER) / 2) && perY > (100 - Game.AREA_CENTER) / 2 && perY < Game.AREA_CENTER + ((100 - Game.AREA_CENTER) / 2)) {
			area = AREA_CENTER;
		} else {
			area = AREA_OUT;
		}
		return area;
	}

	public int[] mouseCentralPos(int area) {
		int width = Game.raster * 25;
		int height = Game.raster * 15;
		int[] pos = new int[2];
		int xPer;
		int yPer;

		switch (area) {
		case AREA_CENTER:
			pos[0] = Scene.DIMX * Game.raster / 2;
			pos[1] = Scene.DIMY * Game.raster / 2;
			break;
		case AREA_LEFT:
			xPer = (100 - Game.AREA_CENTER) / 4;
			pos[0] = width * xPer / 100;
			pos[1] = Scene.DIMY * Game.raster / 2;
			break;
		case AREA_RIGHT:
			xPer = 100 - ((100 - Game.AREA_CENTER) / 4);
			pos[0] = width * xPer / 100;
			pos[1] = Scene.DIMY * Game.raster / 2;
			break;
		case AREA_UP:
			yPer = (100 - Game.AREA_CENTER) / 4;
			pos[0] = Scene.DIMX * Game.raster / 2;
			pos[1] = height * yPer / 100;
			break;
		case AREA_DOWN:
			yPer = 100 - ((100 - Game.AREA_CENTER) / 4);
			pos[0] = Scene.DIMX * Game.raster / 2;
			pos[1] = height * yPer / 100;
			break;
		default:
			break;
		}
		return pos;
	}

	public GObject getObj(GObject[][] area, int x, int y) {
		return area[convertCollider(x, y)[0]][convertCollider(x, y)[1]];
	}

	public GObject getObjNorm(GObject[][] area, int x, int y) {
		return area[x][y];
	}

	public int[] convertCollider(int x, int y) {
		int[] pos = new int[2];
		int gravity = DOWN;

		PhysicsComponent physics = gobject.getPhysicsComponent();

		if (physics != null) {
			gravity = physics.getGravity();
		}

		if (gravity == DOWN) {
			pos[0] = collAreaDownX[y][x];
			pos[1] = collAreaDownY[y][x];
		} else if (gravity == UP) {
			pos[0] = collAreaUpX[y][x];
			pos[1] = collAreaUpY[y][x];
		} else if (gravity == LEFT) {
			pos[0] = collAreaLeftX[y][x];
			pos[1] = collAreaLeftY[y][x];
		} else if (gravity == RIGHT) {
			pos[0] = collAreaRightX[y][x];
			pos[1] = collAreaRightY[y][x];
		}

		return pos;
	}

	public int convertMove(int movei) {
		int gravity = DOWN;

		PhysicsComponent physics = gobject.getPhysicsComponent();

		if (physics != null) {
			gravity = physics.getGravity();
		}

		if (gravity == DOWN) {
			return (moveDown[movei]);
		} else if (gravity == UP) {
			return (moveUp[movei]);
		} else if (gravity == LEFT) {
			return (moveLeft[movei]);
		} else if (gravity == RIGHT) {
			return (moveRight[movei]);
		}

		return -1;
	}

	public int[] convertShift(int x, int y) {
		int[] pos = new int[2];
		int gravity = DOWN;

		PhysicsComponent physics = gobject.getPhysicsComponent();

		if (physics != null) {
			gravity = physics.getGravity();
		}

		if (gravity == DOWN) {
			pos[0] = shiftAreaDownX[y][x];
			pos[1] = shiftAreaDownY[y][x];
		} else if (gravity == UP) {
			pos[0] = shiftAreaUpX[y][x];
			pos[1] = shiftAreaUpY[y][x];
		} else if (gravity == LEFT) {
			pos[0] = shiftAreaLeftX[y][x];
			pos[1] = shiftAreaLeftY[y][x];
		} else if (gravity == RIGHT) {
			pos[0] = shiftAreaRightX[y][x];
			pos[1] = shiftAreaRightY[y][x];
		}

		return pos;
	}

	public int[] convertShiftNorm(int x, int y) {
		int[] pos = new int[2];

		pos[0] = shiftAreaDownX[y][x];
		pos[1] = shiftAreaDownY[y][x];

		return pos;
	}

	protected void damageCollide(GObject destroyCollideObj) {
		DestroyAbleComponent destroy = gobject.getDestroyAbleComponent();
		DestroyCollideComponent destroyCol = destroyCollideObj.getDestroyCollideComponent();
		if (destroy != null && destroyCol != null) {
			if (!gobject.hasTag(destroyCol.getIgnoreTag()) && destroy.decreaseEnergy(destroyCol.getDamageEnergy()) == true) {
				if (gobject.getScene().getGame().getQuality() > Game.QUAL_LOW) {
					ExplodeRound newExplode = (ExplodeRound) gobject.getScene().cloneGObject('G');
					TransformTileComponent transComp = newExplode.getTransformTileComponent();
					TransformTileComponent transCompPlay = gobject.getTransformTileComponent();
					transComp.setXY(transCompPlay.getX(), transCompPlay.getY(), false);
					gobject.getScene().addBufferList(newExplode);
				}
				getSound("explode").play();

				for (Iterator<Component> iterator = gobject.getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					comp.destroy();
				}
				if (destroy.gobject.hasTag("player")) {
					gobject.getScene().sendMessage(destroyCollideObj, gobject, null, destroyCol.getKey() + ".kill");
				} else {
					gobject.getScene().sendMessage(destroyCollideObj, gobject, null, destroyCol.getKey() + ".elsekill");
				}
			}

		}
	}

	public void initRestore() {

	}

	public void restoreSerializedState() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public GObject getGobject() {
		return gobject;
	}

	public void setGobject(GObject gobject) {
		this.gobject = gobject;
	}

	public SoundWrapper getSound(String key) {
		return getGobject().getScene().getGame().getSound(key);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
