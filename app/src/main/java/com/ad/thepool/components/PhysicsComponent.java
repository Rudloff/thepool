package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;
import com.ad.thepool.wrapper.Log;

public class PhysicsComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3394611236381883070L;

	public static final int COMP_NAME = 19;

	private int fall;
	private boolean rollLeftRight;
	private boolean highFall;

	private int gravity;

	public PhysicsComponent(boolean isActive, boolean rollLeftRight, int gravity) {
		super(COMP_NAME, isActive);
		this.fall = UNKNOWN;
		this.rollLeftRight = rollLeftRight;
		this.gravity = gravity;
	}

	@Override
	public void update() {

		boolean fallDown = false;
		boolean fallLeftDown = false;
		boolean fallRightDown = false;

		ColliderFrameComponent coll = gobject.getColliderFrameComponent();
		RenderTileComponent render = gobject.getRenderTileComponent();
		ControlComponent control = gobject.getControlComponent();
		ActivateComponent activate = gobject.getActivateComponent();

		if (coll == null || render == null || activate == null) {
			return;
		}
		if (control != null && control.isJump() == true) {
			return;
		}

		GObject[][] area = coll.getColliderMatrix(GObject.Z_MAIN, CollideAbleComponent.COMP_NAME);
		GObject[][] eraseArea = coll.getColliderMatrix(GObject.Z_MAIN, EraseAbleComponent.COMP_NAME);
		GObject[][] destroyCollideArea = coll.getColliderMatrix(GObject.Z_MAIN, DestroyCollideComponent.COMP_NAME);

		GObject colObj;
		colObj = getObj(destroyCollideArea, 1, 2);
		if (colObj != null) {
			damageCollide(colObj);
		}

		colObj = getObj(destroyCollideArea, 0, 2);
		if (colObj != null && rollLeftRight == true) {
			damageCollide(colObj);
			getSound("collide").play();
		}
		colObj = getObj(destroyCollideArea, 2, 2);
		if (colObj != null && rollLeftRight == true) {
			damageCollide(colObj);
			getSound("collide").play();
		}

		fallDown = false;
		fallLeftDown = false;
		fallRightDown = false;
		this.fall = UNKNOWN;
		if (getObj(area, 1, 2) == null) {
			fallDown = true;
		} else if (getObj(area, 0, 1) == null && getObj(area, 0, 2) == null && rollLeftRight == true) {
			fallLeftDown = true;
		} else if (getObj(area, 2, 1) == null && getObj(area, 2, 2) == null && rollLeftRight == true) {
			fallRightDown = true;
		} else if (getObj(area, 1, 2) != null) {
			// Shift Plattforms
			if (highFall == true) {
				getSound("crash").play();
				highFall = false;
			}
			GObject obj = getObj(area, 1, 2);
			ShiftPlattformComponent shiftplatt = obj.getShiftPlattformComponent();
			if (shiftplatt != null) {
				int shiftdir = shiftplatt.getDirection();
				int xdir = -1, ydir = -1;
				// Damage in wanted dir

				if (shiftdir == LEFT) {
					xdir = 0;
					ydir = 1;
				}
				if (shiftdir == RIGHT) {
					xdir = 2;
					ydir = 1;
				}
				if (shiftdir == UP) {
					xdir = 1;
					ydir = 0;
				}
				if (shiftdir == DOWN) {
					xdir = 1;
					ydir = 2;
				}
				// int xnew = convertCollider(xdir,ydir)[0];
				// int ynew = convertCollider(xdir,ydir)[1];
				//
				// xdir = xnew;
				// ydir = ynew;

				if (xdir != -1) {
					colObj = getObj(destroyCollideArea, xdir, ydir);
					if (colObj != null) {
						damageCollide(colObj);
						getSound("collide").play();
					}
				}
				// if(xdir != -1 && (getObj(area,xdir,ydir) == null ||
				// getObj(eraseArea,xdir,ydir) != null))
				if (xdir != -1 && (area[xdir][ydir]) == null || eraseArea[xdir][ydir] != null) {
					if (gravity == DOWN && shiftdir == LEFT) {
						render.addPathStep(convertMove(LEFT));
						getSound("shift").play();
						fall = LEFT;
					} else if (gravity == DOWN && shiftdir == RIGHT) {
						render.addPathStep(convertMove(RIGHT));
						getSound("shift").play();
						fall = RIGHT;
					} else if (gravity == LEFT && shiftdir == UP) {
						render.addPathStep(convertMove(LEFT));
						getSound("shift").play();
						fall = LEFT;
					} else if (gravity == LEFT && shiftdir == DOWN) {
						render.addPathStep(convertMove(RIGHT));
						getSound("shift").play();
						fall = RIGHT;
					} else if (gravity == UP && shiftdir == LEFT) {
						render.addPathStep(convertMove(RIGHT));
						getSound("shift").play();
						fall = RIGHT;
					} else if (gravity == UP && shiftdir == RIGHT) {
						render.addPathStep(convertMove(LEFT));
						getSound("shift").play();
						fall = LEFT;
					} else if (gravity == RIGHT && shiftdir == UP) {
						render.addPathStep(convertMove(RIGHT));
						getSound("shift").play();
						fall = RIGHT;
					} else if (gravity == RIGHT && shiftdir == DOWN) {
						render.addPathStep(convertMove(LEFT));
						getSound("shift").play();
						fall = LEFT;
					}

					// if (gravity == DOWN || gravity == LEFT) {
					// if (shiftdir == LEFT || shiftdir == UP) {
					// render.addPathStep(convertMove(LEFT));
					// fall = LEFT;
					// } else if (shiftdir == RIGHT || shiftdir == DOWN) {
					// render.addPathStep(convertMove(RIGHT));
					// fall = RIGHT;
					// }
					// } else if (gravity == UP || gravity == RIGHT) {
					// if (shiftdir == LEFT || shiftdir == UP) {
					// render.addPathStep(convertMove(RIGHT));
					// fall = RIGHT;
					// } else if (shiftdir == RIGHT || shiftdir == DOWN) {
					// render.addPathStep(convertMove(LEFT));
					// fall = LEFT;
					// }
					// }
				}
			}
		}

		if (fallDown == true) {
			fall = DOWN;
			render.addPathStep(convertMove(DOWN));
			highFall = true;
		} else if (fallLeftDown == true && fallRightDown == true) {
			fall = DOWN_LEFT;
			render.addPathStep(convertMove(LEFT));
			render.addPathStep(convertMove(DOWN));
			highFall = true;
		} else if (fallLeftDown == true) {
			fall = DOWN_LEFT;
			render.addPathStep(convertMove(LEFT));
			render.addPathStep(convertMove(DOWN));
			highFall = true;
		} else if (fallRightDown == true) {
			fall = DOWN_RIGHT;
			render.addPathStep(convertMove(RIGHT));
			render.addPathStep(convertMove(DOWN));
			highFall = true;
		}

		// Do Moves

		TransformTileComponent trans = gobject.getTransformTileComponent();
		int x = trans.getX();
		int y = trans.getY();

		if (fall == DOWN) {
			int newX = Scene.calcPositionX(x, convertShift(1, 2)[0]);
			int newY = Scene.calcPositionY(y, convertShift(1, 2)[1]);
			trans.setXY(newX, newY, true);
		} else if (fall == DOWN_LEFT) {
			int newX = Scene.calcPositionX(x, convertShift(0, 2)[0]);
			int newY = Scene.calcPositionY(y, convertShift(0, 2)[1]);
			trans.setXY(newX, newY, true);
		} else if (fall == DOWN_RIGHT) {
			int newX = Scene.calcPositionX(x, convertShift(2, 2)[0]);
			int newY = Scene.calcPositionY(y, convertShift(2, 2)[1]);
			trans.setXY(newX, newY, true);
		} else if (fall == LEFT) {
			int newX = Scene.calcPositionX(x, convertShift(0, 1)[0]);
			int newY = Scene.calcPositionY(y, convertShift(0, 1)[1]);
			trans.setXY(newX, newY, true);
			GObject eraseObj = getObj(eraseArea, 0, 1);
			if (eraseObj != null) {
				gobject.getScene().removeBufferList(eraseObj);
			}

		} else if (fall == RIGHT) {
			int newX = Scene.calcPositionX(x, convertShift(2, 1)[0]);
			int newY = Scene.calcPositionY(y, convertShift(2, 1)[1]);
			trans.setXY(newX, newY, true);
			GObject eraseObj = getObj(eraseArea, 2, 1);
			if (eraseObj != null) {
				gobject.getScene().removeBufferList(eraseObj);
			}

		}

		activate.processBackObj();
	}

	// public void onCollisionEnter(GObject collidedObject, int x, int y) {
	//
	// // TODO: Gravity
	// if (x == convertCollider(1,2)[0] && y == convertCollider(1,2)[1])
	// {
	// grounded = true;
	// }
	// }
	//
	//
	//
	// public void onCollisionStay(GObject collidedObject, int x, int y) {
	//
	// if (x == convertCollider(1,2)[0] && y == convertCollider(1,2)[1]) {
	// grounded = true;
	// }
	//
	// }
	//
	// public void onCollisionLeave(GObject collidedObject, int x, int y) {
	// if (x == convertCollider(1,2)[0] && y == convertCollider(1,2)[1]) {
	// grounded = false;
	// }
	// }

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public boolean isGrounded() {
		ColliderFrameComponent coll = gobject.getColliderFrameComponent();

		GObject[][] area = coll.getColliderMatrix(GObject.Z_MAIN, CollideAbleComponent.COMP_NAME);
		if (getObj(area, 1, 2) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isShiftGround() {
		boolean rc = false;
		ColliderFrameComponent coll = gobject.getColliderFrameComponent();

		GObject[][] area = coll.getColliderMatrix(GObject.Z_MAIN, CollideAbleComponent.COMP_NAME);

		GObject obj = getObj(area, 1, 2);
		if (obj == null) {
			return rc;
		}
		ShiftPlattformComponent shiftplatt = obj.getShiftPlattformComponent();
		if (shiftplatt != null) {
			int shiftdir = shiftplatt.getDirection();

			// if (gravity == DOWN || gravity == LEFT) {
			// if (shiftdir == LEFT || shiftdir == UP) {
			// rc = true;
			// } else if (shiftdir == RIGHT || shiftdir == DOWN) {
			// rc = true;
			// }
			// } else if (gravity == UP || gravity == RIGHT) {
			// if (shiftdir == LEFT || shiftdir == UP) {
			// rc = true;
			// } else if (shiftdir == RIGHT || shiftdir == DOWN) {
			// rc = true;
			// }
			// }
			if ((gravity == DOWN && shiftdir == LEFT) || (gravity == DOWN && shiftdir == RIGHT) || (gravity == LEFT && shiftdir == UP) || (gravity == LEFT && shiftdir == DOWN) || (gravity == UP && shiftdir == LEFT) || (gravity == UP && shiftdir == RIGHT) || (gravity == RIGHT && shiftdir == LEFT)
					|| (gravity == RIGHT && shiftdir == RIGHT)) {
				rc = true;
			}
		}
		return rc;
	}

	public boolean isRollLeftRight() {
		return rollLeftRight;
	}

	public void setRollLeftRight(boolean rollLeftRight) {
		this.rollLeftRight = rollLeftRight;
	}
}
