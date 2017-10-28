package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Scene;
import com.ad.thepool.Vector2D;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class ControlComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4848225501461783233L;
	public static final int COMP_NAME = 6;
	private boolean pressLeft = false;
	private boolean pressRight = false;
	private boolean pressUp = false;
	private boolean pressDown = false;
	private boolean jump = false;
	private int moveDir;

	private boolean keyCommands;

	public static final int MSG_DOWN = 0;
	public static final int MSG_UP = 1;
	public static final int MSG_LEFT = 2;
	public static final int MSG_RIGHT = 3;

	private int state;
	public static final int STATE_WALK = 0;
	public static final int STATE_CLIMB = 1;
	public static final int STATE_ROBE = 2;
	public static final int STATE_SWIM = 3;

	private int mouseState;
	public static final int MSTATE_MOVE = 1;
	public static final int MSTATE_RELEASE = 2;

	private int mouseInitX;
	private int mouseInitY;
	private int mouseDestX;
	private int mouseDestY;
	private int mouseFrame;
	private int mouseSpeed;
	private boolean mouseAllow;
	private boolean followKeyRight, followKeyLeft;

	public ControlComponent(boolean isActive, boolean keyCommands) {
		super(COMP_NAME, isActive);
		state = STATE_WALK;
		mouseState = MSTATE_RELEASE;
		mouseInitX = -1;
		mouseInitY = -1;
		mouseSpeed = 4;
		this.keyCommands = keyCommands;
	}

	@Override
	public void update() {
		ColliderFrameComponent coll = gobject.getColliderFrameComponent();
		RenderTileComponent render = gobject.getRenderTileComponent();
		PhysicsComponent physics = gobject.getPhysicsComponent();
		TransformTileComponent trans = gobject.getTransformTileComponent();
		ActivateComponent activate = gobject.getActivateComponent();

		moveDir = UNKNOWN;
		mouseFrame++;
		if(mouseSpeed > 0)
		{
			if (mouseFrame % mouseSpeed == 0) {
				mouseAllow = true;
			} else {
				mouseAllow = false;
			}
		}
		else
		{
			mouseAllow = false;
		}
		
		if (coll == null || render == null || trans == null || activate == null) {
			return;
		}

		GObject[][] area = coll.getColliderMatrix(GObject.Z_MAIN, CollideAbleComponent.COMP_NAME);
		GObject[][] eraseArea = coll.getColliderMatrix(GObject.Z_MAIN, EraseAbleComponent.COMP_NAME);
		GObject[][] destroyCollideArea = coll.getColliderMatrix(GObject.Z_MAIN, DestroyCollideComponent.COMP_NAME);

		boolean grounded = false;
		boolean isShift = false;
		int gravity = UNKNOWN;

		if (physics == null || physics.isActive == false) {
			grounded = true;
			isShift = false;
			gravity = DOWN;
		} else {
			grounded = physics.isGrounded();
			isShift = physics.isShiftGround();
			gravity = physics.getGravity();
		}

		if (state == STATE_WALK) {
			if (physics == null) {
				grounded = true;
				isShift = false;
			} else {
				grounded = physics.isGrounded();
				isShift = physics.isShiftGround();
			}

			if (isShift == false) {
				// Check damages in the wanted position
				GObject colObj;
				colObj = getObj(destroyCollideArea, 0, 1);
				if (colObj != null && pressLeft == true && grounded == true) {
					damageCollide(colObj);
					getSound("collide").play();

				}
				colObj = getObj(destroyCollideArea, 2, 1);
				if (colObj != null && pressRight == true && grounded == true) {
					damageCollide(colObj);
					getSound("collide").play();
				}
				colObj = getObj(destroyCollideArea, 1, 0);
				if (colObj != null && pressUp == true && grounded == true) {
					damageCollide(colObj);
					getSound("collide").play();
				}
				colObj = getObj(destroyCollideArea, 1, 2);
				if (colObj != null && pressDown == true && grounded == true) {
					damageCollide(colObj);
					getSound("collide").play();
				}
				if ((getObj(area, 0, 1) == null || getObj(eraseArea, 0, 1) != null) && pressLeft == true && grounded == true) {
					render.addPathStep(convertMove(LEFT));
					render.setActiveAnimation("walkL", true);
					getSound("step").play();
					moveDir = LEFT;
				} else if ((getObj(area, 2, 1) == null || getObj(eraseArea, 2, 1) != null) && pressRight == true && grounded == true) {
					render.addPathStep(convertMove(RIGHT));
					render.setActiveAnimation("walkR", true);
					getSound("step").play();
					moveDir = RIGHT;
				} else if ((getObj(area, 0, 0) == null || getObj(eraseArea, 0, 0) != null) && (getObj(area, 1, 0) == null || getObj(eraseArea, 1, 0) != null) && (pressLeft == true || followKeyLeft == true) && (grounded == true || jump == true)) {
					render.addPathStep(convertMove(UP));
					render.addPathStep(convertMove(LEFT));
					render.setActiveAnimation("stepL", true);
					moveDir = UP_LEFT;
					if (pressUp == true) {
						getSound("jump").play();
					}
					followKeyLeft = false;
				} else if ((getObj(area, 2, 0) == null || getObj(eraseArea, 2, 0) != null) && (getObj(area, 1, 0) == null || getObj(eraseArea, 1, 0) != null) && (pressRight == true || followKeyRight == true) && (grounded == true || jump == true)) {
					render.addPathStep(convertMove(UP));
					render.addPathStep(convertMove(RIGHT));
					render.setActiveAnimation("stepR", true);
					if (pressUp == true) {
						getSound("jump").play();
					}
					moveDir = UP_RIGHT;
					followKeyRight = false;
				} else if ((getObj(area, 1, 0) == null || getObj(eraseArea, 1, 0) != null) && pressUp == true && grounded == true) {
					render.addPathStep(convertMove(UP));
					render.setActiveAnimation("jump", true);
					if (pressUp == true) {
						getSound("jump").play();
					}
					moveDir = UP;
					if (physics != null) {
						jump = true;
					}
				} else if (getObj(eraseArea, 1, 2) != null && pressDown == true && grounded == true) {
					render.addPathStep(convertMove(DOWN));
					moveDir = DOWN;
				}
			}
			// else {
			// if ((getObj(area, 1, 0) == null || getObj(eraseArea, 1, 0) !=
			// null) && pressUp == true && grounded == true) {
			// render.addPathStep(convertMove(UP));
			// render.setActiveAnimation("jump", true);
			// if (pressUp == true) {
			// getSound("jump").play();
			// }
			// moveDir = UP;
			// if (physics != null) {
			// jump = true;
			// }
			// }
			// }

			if (pressDown == false && pressUp == false && pressLeft == false && pressRight == false) {
				if (render.getActiveAnimation().startsWith("walk") || render.getActiveAnimation().startsWith("step") || render.getActiveAnimation().equals("jump")) {
					render.setActiveAnimation("stand", true);
				}
			}

			if (moveDir != UP && physics != null) {
				jump = false;
			}
		}
		if (state == STATE_SWIM) {

			// Check damages in the wanted position
			GObject colObj;
			colObj = getObj(destroyCollideArea, 0, 1);
			if (colObj != null && pressLeft == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 2, 1);
			if (colObj != null && pressRight == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 1, 0);
			if (colObj != null && pressUp == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 1, 2);
			if (colObj != null && pressDown == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			// TODO: Swimmoves
			if ((getObj(area, 0, 1) == null || getObj(eraseArea, 0, 1) != null) && pressLeft == true) {
				render.addPathStep(convertMove(LEFT));
				render.setActiveAnimation("swimL", true);
				getSound("swim").play();
				moveDir = LEFT;
			} else if ((getObj(area, 2, 1) == null || getObj(eraseArea, 2, 1) != null) && pressRight == true) {
				render.addPathStep(convertMove(RIGHT));
				render.setActiveAnimation("swimR", true);
				getSound("swim").play();
				moveDir = RIGHT;
			} else if ((getObj(area, 1, 0) == null || getObj(eraseArea, 1, 0) != null) && pressUp == true) {
				render.addPathStep(convertMove(UP));
				render.setActiveAnimation("swimL", true);
				getSound("swim").play();
				moveDir = UP;
			} else if ((getObj(area, 1, 2) == null || getObj(eraseArea, 1, 2) != null) && pressDown == true) {
				render.addPathStep(convertMove(DOWN));
				render.setActiveAnimation("swimR", true);
				getSound("swim").play();
				moveDir = DOWN;
			}

			if (pressDown == false && pressUp == false && pressLeft == false && pressRight == false) {
				if (render.getActiveAnimation().startsWith("walk") || render.getActiveAnimation().startsWith("step") || render.getActiveAnimation().equals("jump")) {
					render.setActiveAnimation("stand", true);
				}
			}
		} else if (state == STATE_CLIMB) {
			// Check damages in the wanted position
			GObject colObj;
			colObj = getObj(destroyCollideArea, 0, 1);
			if (colObj != null && pressLeft == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 2, 1);
			if (colObj != null && pressRight == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 1, 0);
			if (colObj != null && pressUp == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 1, 2);
			if (colObj != null && pressDown == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}

			GObject[][] areaBack = coll.getColliderMatrix(GObject.Z_BACK, ClimbAbleComponent.COMP_NAME);

			if ((getObj(area, 0, 1) == null || getObj(eraseArea, 0, 1) != null) && pressLeft == true) {
				render.addPathStep(convertMove(LEFT));
				render.setActiveAnimation("climb", true);
				getSound("climb").play();
				moveDir = LEFT;
				if (getObj(areaBack, 0, 1) == null) {
					jump = false;
				}
			} else if ((getObj(area, 2, 1) == null || getObj(eraseArea, 2, 1) != null) && pressRight == true) {
				render.addPathStep(convertMove(RIGHT));
				render.setActiveAnimation("climb", true);
				getSound("climb").play();
				moveDir = RIGHT;
				if (getObj(areaBack, 2, 1) == null) {
					jump = false;
				}
			} else if (getObj(area, 1, 0) == null && getObj(areaBack, 1, 0) != null && pressUp == true) {
				render.addPathStep(convertMove(UP));
				render.setActiveAnimation("climb", true);
				getSound("climb").play();
				moveDir = UP;
			} else if ((getObj(area, 1, 2) == null || getObj(eraseArea, 1, 0) != null) && pressDown == true) {
				render.addPathStep(convertMove(DOWN));
				render.setActiveAnimation("climb", true);
				getSound("climb").play();
				moveDir = DOWN;
			}
		} else if (state == STATE_ROBE) {
			// Check damages in the wanted position
			GObject colObj;
			colObj = getObj(destroyCollideArea, 0, 1);
			if (colObj != null && pressLeft == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 2, 1);
			if (colObj != null && pressRight == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 1, 0);
			if (colObj != null && pressUp == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObj(destroyCollideArea, 1, 2);
			if (colObj != null && pressDown == true && grounded == true) {
				damageCollide(colObj);
				getSound("collide").play();
			}

			GObject[][] areaBack = coll.getColliderMatrix(GObject.Z_BACK, RobeAbleComponent.COMP_NAME);
			GObject robeBackObj;
			RobeAbleComponent robeAble = null;
			if ((getObj(area, 0, 1) == null || getObj(eraseArea, 0, 1) != null) && pressLeft == true) {
				render.addPathStep(convertMove(LEFT));
				moveDir = LEFT;
				render.setActiveAnimation("robe", true);
				getSound("robe").play();
				robeBackObj = getObj(areaBack, 0, 1);
				if (robeBackObj != null) {
					robeAble = robeBackObj.getRobeAbleComponent();
				}

				if (getObj(areaBack, 0, 1) == null || (robeAble != null && robeAble.getForGravity() == gravity)) {
					jump = false;
				}
			} else if ((getObj(area, 2, 1) == null || getObj(eraseArea, 2, 1) != null) && pressRight == true) {
				render.addPathStep(convertMove(RIGHT));
				render.setActiveAnimation("robe", true);
				getSound("robe").play();
				moveDir = RIGHT;
				robeBackObj = getObj(areaBack, 2, 1);
				if (robeBackObj != null) {
					robeAble = robeBackObj.getRobeAbleComponent();
				}

				if (getObj(areaBack, 1, 2) == null || (robeAble != null && robeAble.getForGravity() == gravity)) {
					jump = false;
				}
			} else if ((getObj(area, 1, 2) == null || getObj(eraseArea, 1, 2) != null) && pressDown == true) {
				render.addPathStep(convertMove(DOWN));
				render.setActiveAnimation("stand", true);
				moveDir = DOWN;

				if (getObj(areaBack, 1, 2) == null) {
					jump = false;
				}
			}
		}

		// Do moves, #todo: in different method?

		int x = trans.getX();
		int y = trans.getY();

		if (moveDir == LEFT) {
			// 0 1, -1, 0
			GObject eraseObj = getObj(eraseArea, 0, 1);
			if (eraseObj != null) {
				gobject.getScene().removeBufferList(eraseObj);
			}

			int newX = Scene.calcPositionX(x, convertShift(0, 1)[0]);
			int newY = Scene.calcPositionY(y, convertShift(0, 1)[1]);
			trans.setXY(newX, newY, true);
		} else if (moveDir == RIGHT) {
			// 2 1, 1 0
			GObject eraseObj = getObj(eraseArea, 2, 1);
			if (eraseObj != null) {
				gobject.getScene().removeBufferList(eraseObj);
			}

			int newX = Scene.calcPositionX(x, convertShift(2, 1)[0]);
			int newY = Scene.calcPositionY(y, convertShift(2, 1)[1]);
			trans.setXY(newX, newY, true);
		} else if (moveDir == UP_LEFT) {
			// 0 0
			GObject eraseObj1 = getObj(eraseArea, 0, 0);
			GObject eraseObj2 = getObj(eraseArea, 1, 0);
			if (eraseObj1 != null) {
				gobject.getScene().removeBufferList(eraseObj1);
			}
			if (eraseObj2 != null) {
				gobject.getScene().removeBufferList(eraseObj2);
			}
			int newX = Scene.calcPositionX(x, convertShift(0, 0)[0]);
			int newY = Scene.calcPositionY(y, convertShift(0, 0)[1]);
			trans.setXY(newX, newY, true);
			// trans.setY(newY);
		} else if (moveDir == UP_RIGHT) {
			// 2 0, 1 -1
			GObject eraseObj1 = getObj(eraseArea, 2, 0);
			GObject eraseObj2 = getObj(eraseArea, 1, 0);
			if (eraseObj1 != null) {
				gobject.getScene().removeBufferList(eraseObj1);
			}
			if (eraseObj2 != null) {
				gobject.getScene().removeBufferList(eraseObj2);
			}
			int newX = Scene.calcPositionX(x, convertShift(2, 0)[0]);
			int newY = Scene.calcPositionY(y, convertShift(2, 0)[1]);
			trans.setXY(newX, newY, true);
			// trans.setY(newY);
		} else if (moveDir == UP) {
			// 1 0
			GObject eraseObj = getObj(eraseArea, 1, 0);
			if (eraseObj != null) {
				gobject.getScene().removeBufferList(eraseObj);
			}

			int newX = Scene.calcPositionX(x, convertShift(1, 0)[0]);
			int newY = Scene.calcPositionY(y, convertShift(1, 0)[1]);
			trans.setXY(newX, newY, true);
		} else if (moveDir == DOWN) {
			// 1 2
			GObject eraseObj = getObj(eraseArea, 1, 2);
			if (eraseObj != null) {
				gobject.getScene().removeBufferList(eraseObj);
			}
			int newX = Scene.calcPositionX(x, convertShift(1, 2)[0]);
			int newY = Scene.calcPositionY(y, convertShift(1, 2)[1]);
			trans.setXY(newX, newY, true);
		}
		// Check state
		activate.processBackObj();
	}

	@Override
	public void keyDown(int key) {
		if (keyCommands == true) {
			convertKey(key, -1, true);
		}
	}

	@Override
	public void keyUp(int key) {
		if (keyCommands == true) {
			convertKey(key, -1, false);
		}
	}

	@Override
	public boolean mouseDown(int x, int y) {
		if (keyCommands == true) {
			int area = mouseArea(x, y);
			if (mouseState == MSTATE_RELEASE && area == AREA_CENTER) {
				mouseInitX = x;
				mouseInitY = y;
				mouseDestX = x;
				mouseDestY = y;
				mouseState = MSTATE_MOVE;
				drawCursor();
			}
		}
		return true;
	}

	@Override
	public boolean mouseMove(int x, int y) {
		if (keyCommands == true) {

			if (mouseState == MSTATE_MOVE) {
				mouseDestX = x;
				mouseDestY = y;
				Vector2D resVec = Vector2D.sub(new Vector2D(mouseInitX, mouseInitY), new Vector2D(mouseDestX, mouseDestY));
				double len = resVec.getLen();

				int refWidth = Scene.DIMY * Game.raster / 2;
				mouseSpeed = 0;
				if (len > refWidth * 0.1 && len <= refWidth * 0.25) {
					mouseSpeed = 5;
				} else if (len > refWidth * 0.25 && len <= refWidth * 0.5) {
					mouseSpeed = 4;
				} else if (len > refWidth * 0.5 && len <= refWidth * 0.75) {
					mouseSpeed = 3;
				} else if (len > refWidth * 0.75 && len <= refWidth) {
					mouseSpeed = 2;
				} else if (len > refWidth) {
					mouseSpeed = 1;
				}
				if (mouseAllow == true) {

					double rot = Math.toDegrees(resVec.getRot());

					if (rot >= 60 && rot < 120) {
						releaseKeys();
						convertKey(-1, MSG_UP, true);
					} else if (rot >= 120 && rot < 150) {
						releaseKeys();
						if (mouseSpeed > 1) {
							convertKey(-1, MSG_UP, true);
							convertKey(-1, MSG_RIGHT, true);
							if (pressLeft == true && pressUp == true) {
								followKeyLeft = true;
							}
							if (pressRight == true && pressUp == true) {
								followKeyRight = true;
							}

							releaseKeys();
						} else {
							convertKey(-1, MSG_UP, true);
							convertKey(-1, MSG_RIGHT, true);
						}
					} else if ((rot >= 150 && rot <= 180) || (rot < -150 && rot > -180)) {
						releaseKeys();
						convertKey(-1, MSG_RIGHT, true);
					} else if (rot <= -120 && rot > -150) {
						releaseKeys();
						if (mouseSpeed > 1) {
							convertKey(-1, MSG_DOWN, true);
							convertKey(-1, MSG_RIGHT, true);
							if (pressLeft == true && pressUp == true) {
								followKeyLeft = true;
							}
							if (pressRight == true && pressUp == true) {
								followKeyRight = true;
							}
							releaseKeys();
						} else {
							convertKey(-1, MSG_DOWN, true);
							convertKey(-1, MSG_RIGHT, true);
						}
					} else if (rot <= -60 && rot > -120) {
						releaseKeys();
						convertKey(-1, MSG_DOWN, true);
					} else if (rot <= -30 && rot > -60) {
						releaseKeys();
						if (mouseSpeed > 1) {
							convertKey(-1, MSG_DOWN, true);
							convertKey(-1, MSG_LEFT, true);
							if (pressLeft == true && pressUp == true) {
								followKeyLeft = true;
							}
							if (pressRight == true && pressUp == true) {
								followKeyRight = true;
							}

							releaseKeys();
						} else {
							convertKey(-1, MSG_DOWN, true);
							convertKey(-1, MSG_LEFT, true);
						}
					} else if ((rot <= 30 && rot >= 0) || (rot >= -30 && rot < 0)) {
						releaseKeys();
						convertKey(-1, MSG_LEFT, true);
					} else if (rot >= 30 && rot < 60) {
						releaseKeys();
						if (mouseSpeed > 1) {
							convertKey(-1, MSG_UP, true);
							convertKey(-1, MSG_LEFT, true);
							if (pressLeft == true && pressUp == true) {
								followKeyLeft = true;
							}
							if (pressRight == true && pressUp == true) {
								followKeyRight = true;
							}

							releaseKeys();
						} else {
							convertKey(-1, MSG_UP, true);
							convertKey(-1, MSG_LEFT, true);
						}
					}

				} else {
					releaseKeys();
				}

				drawCursor();
			}
		}
		return true;
	}

	@Override
	public boolean mouseUp(int x, int y) {
		if (keyCommands == true) {
			if (mouseState == MSTATE_MOVE) {
				mouseState = MSTATE_RELEASE;
				mouseInitX = -1;
				mouseInitY = -1;
				mouseDestX = -1;
				mouseDestY = -1;
				drawCursor();
				releaseKeys();
			}
		}
		return true;
	}

	private void drawCursor() {
		ArrayList<GObject> cursors = gobject.getScene().searchGObjectByTag("cursor");

		for (Iterator<GObject> iterator = cursors.iterator(); iterator.hasNext();) {
			GObject elem = iterator.next();

			MouseCursorComponent comp = elem.getMouseCursorComponent();

			if (comp != null) {
				comp.setxStart(mouseInitX);
				comp.setyStart(mouseInitY);
				comp.setxEnd(mouseDestX);
				comp.setyEnd(mouseDestY);
				comp.setLaser(false);
			}
		}

	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		if (tag != null && gobject.hasTag(tag)) {
			if (sendkey.equals("left.down")) {
				convertKey(-1, MSG_LEFT, true);
			} else if (sendkey.equals("left.up")) {
				convertKey(-1, MSG_LEFT, false);
			} else if (sendkey.equals("right.down")) {
				convertKey(-1, MSG_RIGHT, true);
			} else if (sendkey.equals("right.up")) {
				convertKey(-1, MSG_RIGHT, false);
			} else if (sendkey.equals("down.down")) {
				convertKey(-1, MSG_DOWN, true);
			} else if (sendkey.equals("down.up")) {
				convertKey(-1, MSG_DOWN, false);
			} else if (sendkey.equals("up.down")) {
				convertKey(-1, MSG_UP, true);
			} else if (sendkey.equals("up.up")) {
				convertKey(-1, MSG_UP, false);
			} else if (sendkey.equals("release")) {
				releaseKeys();
			}
		}
	}

	private void convertKey(int key, int message, boolean press) {
		PhysicsComponent physics = gobject.getPhysicsComponent();
		int gravity = DOWN;

		if (physics != null) {
			gravity = physics.getGravity();
		}

		if (key == KeyEventWrapper.KEY_LEFT || message == MSG_LEFT) {
			switch (gravity) {
			case DOWN:
				pressLeft = press;
				break;
			case UP:
				pressRight = press;
				break;
			case LEFT:
				pressDown = press;
				break;
			case RIGHT:
				pressUp = press;
				break;
			default:
				pressLeft = press;
				break;
			}
		}
		if (key == KeyEventWrapper.KEY_RIGHT || message == MSG_RIGHT) {
			switch (gravity) {
			case DOWN:
				pressRight = press;
				break;
			case UP:
				pressLeft = press;
				break;
			case LEFT:
				pressUp = press;
				break;
			case RIGHT:
				pressDown = press;
				break;
			default:
				pressRight = press;
				break;
			}
		}
		if (key == KeyEventWrapper.KEY_UP || message == MSG_UP) {
			switch (gravity) {
			case DOWN:
				pressUp = press;
				break;
			case UP:
				pressDown = press;
				break;
			case LEFT:
				pressLeft = press;
				break;
			case RIGHT:
				pressRight = press;
				break;
			default:
				pressUp = press;
				break;
			}
		}
		if (key == KeyEventWrapper.KEY_DOWN || message == MSG_DOWN) {
			switch (gravity) {
			case DOWN:
				pressDown = press;
				break;
			case UP:
				pressUp = press;
				break;
			case LEFT:
				pressRight = press;
				break;
			case RIGHT:
				pressLeft = press;
				break;
			default:
				pressDown = press;
				break;
			}
		}

	}

	@Override
	public void initRestore() {
		super.initRestore();
		releaseKeys();
	}

	public void releaseKeys() {
		pressDown = false;
		pressUp = false;
		pressLeft = false;
		pressRight = false;

	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public boolean isJump() {
		return jump;
	}

	public int getMouseState() {
		return mouseState;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ControlComponent comp = (ControlComponent) super.clone();

		return comp;
	}

}
