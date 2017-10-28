package com.ad.thepool.components;

public class TransformTileComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3687969428140402398L;
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private int priority = 0;
	private int rot = ROT_N;
	private char levTile;
	public static final int COMP_NAME = 29;

	public static final int ROT_N = 0;
	public static final int ROT_E = 1;
	public static final int ROT_S = 2;
	public static final int ROT_W = 3;

	public TransformTileComponent(char levTile, boolean isActive, int z) {
		super(COMP_NAME, isActive);
		this.levTile = levTile;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	// public void setX(int newx, boolean inBuffer)
	// {
	// if(inBuffer == true)
	// {
	// gobject.getScene().changeBufferObject(x, y, z, newx, y, z);
	// }
	// this.x = newx;
	// }
	public int getY() {
		return y;
	}

	// public void setY(int newy, boolean inBuffer) {
	// if(inBuffer == true)
	// {
	// gobject.getScene().changeBufferObject(x, y, z, x, newy, z);
	// }
	// this.y = newy;
	// }

	public void setXY(int newx, int newy, boolean inBuffer) {
		if (inBuffer == true) {
			gobject.getScene().changeBufferObject(x, y, z, newx, newy, z);
		}
		this.x = newx;
		this.y = newy;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int newz, boolean inBuffer) {
		if (inBuffer == true) {
			gobject.getScene().changeBufferObject(x, y, z, x, y, newz);
		}
		this.z = newz;
	}

	public char getLevTile() {
		return levTile;
	}

	public void setLevTile(char levTile) {
		this.levTile = levTile;
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
