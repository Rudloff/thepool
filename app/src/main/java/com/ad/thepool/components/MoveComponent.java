package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;
import com.ad.thepool.objects.ExplodeRound;

public class MoveComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4606486778650973267L;

	public static final int COMP_NAME = 17;

	private int direction;
	private int speed;
	private int frameMove = -1;
	private int type;
	private int duration;
	private int timer;
	private boolean destroyCollide;
	private int randomForce;
	private boolean forceMove;
	private boolean shiftAppear;

	public static final int TYPE_ROUND_MOVE = 1;
	public static final int TYPE_UPDOWN_MOVE = 2;
	public static final int TYPE_LEFTRIGHT_MOVE = 3;
	public static final int TYPE_DOWN_DESTROY = 4;
	public static final int TYPE_UP_DESTROY = 5;
	public static final int TYPE_LEFT_DESTROY = 6;
	public static final int TYPE_RIGHT_DESTROY = 7;
	public static final int TYPE_RANDOM_ROUND_MOVE = 8;

	public MoveComponent(boolean isActive, int speed, int type) {
		super(COMP_NAME, isActive);
		direction = UNKNOWN;
		this.speed = speed;
		this.type = type;
		this.duration = 0;
		this.timer = 0;
		destroyCollide = true;
		forceMove = false;
		randomForce = 100;
		shiftAppear = false;
	}

	public MoveComponent(boolean isActive, int speed, int type, int duration) {
		super(COMP_NAME, isActive);
		direction = UNKNOWN;
		this.speed = speed;
		this.type = type;
		this.duration = duration;
		this.timer = 0;
		destroyCollide = true;
		forceMove = false;
		randomForce = 100;
		shiftAppear = false;
	}

	@Override
	public void update() {
		frameMove++;
		timer++;

		if (duration != 0 && timer > duration) {
			gobject.getScene().removeBufferList(gobject);
		}

		if (type == TYPE_RANDOM_ROUND_MOVE && ((frameMove % randomForce) == 0)) {
			forceMove = true;
		}

		if ((frameMove % speed) == 0) {

			ColliderFrameComponent coll = gobject.getColliderFrameComponent();
			RenderTileComponent render = gobject.getRenderTileComponent();
			TransformTileComponent trans = gobject.getTransformTileComponent();
			if (coll == null || render == null || trans == null) {
				return;
			}

			GObject[][] area = coll.getColliderMatrix(trans.getZ(), CollideAbleComponent.COMP_NAME);
			GObject[][] destroyAbleArea = coll.getColliderMatrix(trans.getZ(), DestroyAbleComponent.COMP_NAME);

			if (getObj(destroyAbleArea, 1, 2) != null && direction == DOWN) {
				damageCollide(getObj(destroyAbleArea, 1, 2));
				getSound("collide").play();
			} else if (getObj(destroyAbleArea, 1, 0) != null && direction == UP) {
				damageCollide(getObj(destroyAbleArea, 1, 0));
				getSound("collide").play();
			} else if (getObj(destroyAbleArea, 2, 1) != null && direction == RIGHT) {
				damageCollide(getObj(destroyAbleArea, 2, 1));
				getSound("collide").play();
			} else if (getObj(destroyAbleArea, 0, 1) != null && direction == LEFT) {
				damageCollide(getObj(destroyAbleArea, 0, 1));
				getSound("collide").play();
			}

			switch (type) {
			case TYPE_ROUND_MOVE:
				roundMove(area);
				break;
			case TYPE_UPDOWN_MOVE:
				updownMove(area);
				break;
			case TYPE_LEFTRIGHT_MOVE:
				leftRightMove(area);
				break;
			case TYPE_DOWN_DESTROY:
				moveDestroy(area, 1, 2, DOWN);
				break;
			case TYPE_UP_DESTROY:
				moveDestroy(area, 1, 0, UP);
				break;
			case TYPE_LEFT_DESTROY:
				moveDestroy(area, 0, 1, LEFT);
				break;
			case TYPE_RIGHT_DESTROY:
				moveDestroy(area, 2, 1, RIGHT);
				break;
			case TYPE_RANDOM_ROUND_MOVE:
				randomRoundMove(area);
				break;
			default:
				break;
			}

			if (direction == UNKNOWN) {
				return;
			}

			int x = trans.getX();
			int y = trans.getY();
			int newX = x, newY = y;

			if (direction == DOWN) {
				if (shiftAppear == true) {
					render.addPathStep(convertMove(SHIFT_DOWN));
				} else {
					render.addPathStep(convertMove(DOWN));
					newX = Scene.calcPositionX(x, convertShift(1, 2)[0]);
					newY = Scene.calcPositionY(y, convertShift(1, 2)[1]);
				}
			} else if (direction == UP) {
				if (shiftAppear == true) {
					render.addPathStep(convertMove(SHIFT_UP));
				} else {
					render.addPathStep(convertMove(UP));
					newX = Scene.calcPositionX(x, convertShift(1, 0)[0]);
					newY = Scene.calcPositionY(y, convertShift(1, 0)[1]);

				}
			} else if (direction == LEFT) {
				if (shiftAppear == true) {
					render.addPathStep(convertMove(SHIFT_LEFT));
				} else {
					render.addPathStep(convertMove(LEFT));
					newX = Scene.calcPositionX(x, convertShift(0, 1)[0]);
					newY = Scene.calcPositionY(y, convertShift(0, 1)[1]);
				}

			} else if (direction == RIGHT) {
				if (shiftAppear == true) {
					render.addPathStep(convertMove(SHIFT_RIGHT));
				} else {
					render.addPathStep(convertMove(RIGHT));
					newX = Scene.calcPositionX(x, convertShift(2, 1)[0]);
					newY = Scene.calcPositionY(y, convertShift(2, 1)[1]);
				}

			}
			if (shiftAppear == true) {
				shiftAppear = false;

			}
			trans.setXY(newX, newY, true);
		}
	}

	private void roundMove(GObject[][] area) {
		if (direction == DOWN) {
			if (getObj(area, 1, 2) != null) {

				if (getObj(area, 0, 1) == null) {
					direction = LEFT;
				} else if (getObj(area, 2, 1) == null) {
					direction = RIGHT;
				} else if (getObj(area, 1, 0) == null) {
					direction = UP;
				} else {
					direction = UNKNOWN;
				}
			}
		} else if (direction == UP) {
			if (getObj(area, 1, 0) != null) {
				if (getObj(area, 2, 1) == null) {
					direction = RIGHT;
				} else if (getObj(area, 0, 1) == null) {
					direction = LEFT;
				} else if (getObj(area, 1, 2) == null) {
					direction = DOWN;
				} else {
					direction = UNKNOWN;
				}
			}
		} else if (direction == LEFT) {
			if (getObj(area, 0, 1) != null) {
				if (getObj(area, 1, 0) == null) {
					direction = UP;
				} else if (getObj(area, 1, 2) == null) {
					direction = DOWN;
				} else if (getObj(area, 2, 1) == null) {
					direction = RIGHT;
				} else {
					direction = UNKNOWN;
				}
			}
		} else if (direction == RIGHT) {
			if (getObj(area, 2, 1) != null) {
				if (getObj(area, 1, 2) == null) {
					direction = DOWN;
				} else if (getObj(area, 1, 0) == null) {
					direction = UP;
				} else if (getObj(area, 0, 1) == null) {
					direction = LEFT;
				} else {
					direction = UNKNOWN;
				}
			}
		} else if (direction == UNKNOWN) {
			if (getObj(area, 2, 1) == null) {
				direction = RIGHT;
			} else if (getObj(area, 1, 2) == null) {
				direction = DOWN;
			} else if (getObj(area, 1, 0) == null) {
				direction = UP;
			} else if (getObj(area, 0, 1) == null) {
				direction = LEFT;
			}
		}
	}

	private void randomRoundMove(GObject[][] area) {
		int newDir = direction;
		ArrayList<Integer> dirList = new ArrayList<Integer>();

		if (direction == DOWN) {
			if (getObj(area, 1, 2) == null && forceMove == false) {
				newDir = DOWN;
			}

			else if (getObj(area, 1, 2) != null || (forceMove == true && getObj(area, 1, 2) == null)) {
				if (getObj(area, 0, 1) == null) {
					dirList.add(LEFT);
				}
				if (getObj(area, 2, 1) == null) {
					dirList.add(RIGHT);
				}
				if (dirList.size() > 0) {
					newDir = randomDir(dirList);
				} else {
					if (getObj(area, 2, 1) == null) {
						newDir = UP;
					} else {
						newDir = UNKNOWN;
					}
				}
			}
		} else if (direction == UP) {
			if (getObj(area, 1, 0) == null && forceMove == false) {
				newDir = UP;
			}

			if (getObj(area, 1, 0) != null || (forceMove == true && getObj(area, 1, 0) == null)) {
				if (getObj(area, 2, 1) == null) {
					dirList.add(RIGHT);
				}
				if (getObj(area, 0, 1) == null) {
					dirList.add(LEFT);
				}
				if (dirList.size() > 0) {
					newDir = randomDir(dirList);
				} else {
					if (getObj(area, 1, 2) == null) {
						newDir = DOWN;
					} else {
						newDir = UNKNOWN;
					}
				}
			}
		} else if (direction == LEFT) {
			if (getObj(area, 0, 1) == null && forceMove == false) {
				newDir = LEFT;
			}

			if (getObj(area, 0, 1) != null || (forceMove == true && getObj(area, 0, 1) == null)) {
				if (getObj(area, 1, 0) == null) {
					dirList.add(UP);
				}
				if (getObj(area, 1, 2) == null) {
					dirList.add(DOWN);
				}
				if (dirList.size() > 0) {
					newDir = randomDir(dirList);
				} else {
					if (getObj(area, 2, 1) == null) {
						newDir = RIGHT;
					} else {
						newDir = UNKNOWN;
					}
				}
			}
		} else if (direction == RIGHT) {
			if (getObj(area, 2, 1) == null && forceMove == false) {
				newDir = RIGHT;
			}

			if (getObj(area, 2, 1) != null || (forceMove == true && getObj(area, 2, 1) == null)) {
				if (getObj(area, 1, 2) == null) {
					dirList.add(DOWN);
				}
				if (getObj(area, 1, 0) == null) {
					dirList.add(UP);
				}
				if (dirList.size() > 0) {
					newDir = randomDir(dirList);
				} else {
					if (getObj(area, 0, 1) == null) {
						newDir = LEFT;
					} else {
						newDir = UNKNOWN;
					}
				}
			}
		} else if (direction == UNKNOWN) {
			if (getObj(area, 2, 1) == null) {
				dirList.add(RIGHT);
			}
			if (getObj(area, 1, 2) == null) {
				dirList.add(DOWN);
			}
			if (getObj(area, 1, 0) == null) {
				dirList.add(UP);
			}
			if (getObj(area, 0, 1) == null) {
				dirList.add(LEFT);
			}
			if (dirList.size() > 0) {
				newDir = randomDir(dirList);
			} else {
				newDir = UNKNOWN;
			}
			direction = newDir;

		}
		if (newDir != direction) {
			forceMove = false;
			direction = newDir;
		}
	}

	private int randomDir(ArrayList<Integer> dirList) {
		if (dirList.size() == 0) {
			return UNKNOWN;
		}

		int elem = (int) Math.floor(Math.random() * dirList.size());

		return dirList.get(elem);
	}

	private void updownMove(GObject[][] area) {
		if (direction == DOWN) {
			if (getObj(area, 1, 2) != null) {
				direction = UP;
			}
		} else if (direction == UP) {
			if (getObj(area, 1, 0) != null) {
				direction = DOWN;
			}
		} else if (direction == UNKNOWN || direction == LEFT || direction == RIGHT) {
			if (getObj(area, 1, 2) != null) {
				direction = UP;
			} else if (getObj(area, 1, 0) != null) {
				direction = DOWN;
			} else {
				direction = UNKNOWN;
			}

		}
	}

	private void leftRightMove(GObject[][] area) {
		if (direction == LEFT) {
			if (getObj(area, 0, 1) != null) {
				direction = RIGHT;
			}
		} else if (direction == RIGHT) {
			if (getObj(area, 2, 1) != null) {
				direction = LEFT;
			}
		} else if (direction == UNKNOWN || direction == UP || direction == DOWN) {
			if (getObj(area, 0, 1) != null) {
				direction = LEFT;
			} else if (getObj(area, 2, 1) != null) {
				direction = RIGHT;
			} else {
				direction = UNKNOWN;
			}

		}
	}

	private void moveDestroy(GObject[][] area, int x, int y, int dir) {
		if (getObj(area, x, y) == null) {
			direction = dir;
		} else if (destroyCollide == true) {
			direction = UNKNOWN;
			gobject.getScene().removeBufferList(gobject);
		}
	}

	@Override
	protected void damageCollide(GObject destroyAbleObj) {
		DestroyAbleComponent destroy = destroyAbleObj.getDestroyAbleComponent();
		DestroyCollideComponent destroyCol = gobject.getDestroyCollideComponent();
		if (destroy != null && destroyCol != null) {
			if (!destroyAbleObj.hasTag(destroyCol.getIgnoreTag()) && destroy.decreaseEnergy(destroyCol.getDamageEnergy()) == true) {
				for (Iterator<Component> iterator = destroyAbleObj.getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					ExplodeRound newExplode = (ExplodeRound) gobject.getScene().cloneGObject('G');
					TransformTileComponent transComp = newExplode.getTransformTileComponent();
					TransformTileComponent transCompPlay = gobject.getTransformTileComponent();
					transComp.setXY(transCompPlay.getX(), transCompPlay.getY(), false);
					comp.destroy();
					gobject.getScene().addBufferList(newExplode);

				}
				gobject.getScene().sendMessage(gobject, destroyAbleObj, null, destroyCol.getKey() + ".kill");
			}
		}
	}

	public int getDirection() {
		return direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean isDestroyCollide() {
		return destroyCollide;
	}

	public void setDestroyCollide(boolean destroyCollide) {
		this.destroyCollide = destroyCollide;
	}

	public boolean isShiftAppear() {
		return shiftAppear;
	}

	public void setShiftAppear(boolean shiftAppear) {
		this.shiftAppear = shiftAppear;
	}

}
