package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Scene;
import com.ad.thepool.objects.Laser;
import com.ad.thepool.objects.LaserGlow;
import com.ad.thepool.objects.Portal;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class LaserComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 720713232895888721L;
	public static final int COMP_NAME = 13;
	public static final int GLOW_STEP = 4;
	public static final int LASER_STEP = 4;

	private boolean pressLeft = false;
	private boolean pressRight = false;
	private boolean pressUp = false;
	private boolean pressDown = false;
	private int laserDir;
	private int lastLaserDir;
	private int laserType;
	public static final int LASER_NO = -1;
	public static final int LASER_USER = -1;
	public static final int LASER_LEFT = 0;
	public static final int LASER_RIGHT = 1;
	public static final int LASER_UP = 2;
	public static final int LASER_DOWN = 3;
	public static final int LASER_CHARACTER = 4;
	public static final int GLOWTIMER = 1;
	public static final int MAX_LASER = 50;
	private int laserColor;
	private boolean waitRelease;
	private boolean release;
	private boolean playLoop;

	public static final int LASERW = Game.raster / 4;

	private int currentLaser = 0;
	int laserEnergy = 10;
	int pushPullEnergy = 1;
	// public String destroyKey;
	public String switchKey;
	int glowStart;
	int glowCounter;
	LaserTransportAbleComponent lastTransportAble;

	private ArrayList<GObject> laserList;
	private ArrayList<GObject> glowList;

	private boolean isSwitchReverse;

	public LaserComponent(boolean isActive, int laserType, String switchKey, boolean isSwitchReverse, int laserEnergy, int pushPullEnergy) {
		super(COMP_NAME, isActive);
		laserList = new ArrayList<GObject>();
		glowList = new ArrayList<GObject>();
		this.laserEnergy = laserEnergy;
		this.pushPullEnergy = pushPullEnergy;
		this.laserType = laserType;
		// this.destroyKey = destroyKey;
		this.switchKey = switchKey;
		this.isSwitchReverse = isSwitchReverse;
		this.glowCounter = 0;
		lastTransportAble = null;
		lastLaserDir = LASER_NO;
		if (laserType == LASER_USER || laserType == LASER_CHARACTER) {
			laserColor = GraphicsWrapper.COLOR_WHITE;
		} else {
			laserColor = GraphicsWrapper.COLOR_RED;
		}
		waitRelease = false;
	}

	@Override
	public void update() {
		laserDir = LASER_NO;
		boolean swimming = false;

		TransformTileComponent transComp = gobject.getTransformTileComponent();
		RenderTileComponent renderComp = gobject.getRenderTileComponent();
		PhysicsComponent physics = gobject.getPhysicsComponent();
		ControlComponent control = gobject.getControlComponent();

		if (transComp == null || renderComp == null) {
			return;
		}

		if (control != null && control.getState() == ControlComponent.STATE_SWIM) {
			swimming = true;
		}

		int xpos = transComp.getX();
		int ypos = transComp.getY();

		clearAllLaser(true);
		laserList.clear();
		glowList.clear();

		// Wait for released keys in case of transporter or portal
		if (waitRelease == true) {
			if (release == true) {
				waitRelease = false;
			} else {
				if ((laserType == LASER_USER || laserType == LASER_CHARACTER) && pressLeft == false && pressRight == false && pressUp == false && pressDown == false) {
					release = true;
				}
				return;
			}
		}

		int gravity = DOWN;

		if (physics != null) {
			gravity = physics.getGravity();
		}

		if (pressLeft == true || laserType == LASER_LEFT) {
			if (lastLaserDir != LASER_LEFT) {
				currentLaser = 0;
			}

			if (laserDir != LASER_LEFT) {
				if (pressLeft == true) {

					switch (gravity) {
					case DOWN:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootL", true);
						} else {
							renderComp.setActiveAnimation("swimL", true);
						}
						break;
					case UP:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootR", true);
						} else {
							renderComp.setActiveAnimation("swimR", true);
						}
						break;
					case LEFT:
						renderComp.setActiveAnimation("shootD", true);
						break;
					case RIGHT:
						renderComp.setActiveAnimation("shootU", true);
						break;
					default:
						break;
					}
				}

				incCurrentLaser();
				fireLaser(xpos, ypos, -1, 0);
				laserDir = LASER_LEFT;
				lastLaserDir = laserDir;
			}
		} else if (pressRight == true || laserType == LASER_RIGHT) {
			if (lastLaserDir != LASER_RIGHT) {
				currentLaser = 0;
			}

			if (laserDir != LASER_RIGHT) {
				if (pressRight == true) {
					switch (gravity) {
					case DOWN:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootR", true);
						} else {
							renderComp.setActiveAnimation("swimR", true);
						}
						break;
					case UP:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootL", true);
						} else {
							renderComp.setActiveAnimation("swimL", true);
						}
						break;
					case LEFT:
						renderComp.setActiveAnimation("shootU", true);
						break;
					case RIGHT:
						renderComp.setActiveAnimation("shootD", true);
						break;
					default:
						break;
					}
				}
				incCurrentLaser();
				fireLaser(xpos, ypos, 1, 0);
				laserDir = LASER_RIGHT;
				lastLaserDir = laserDir;
			}
		} else if (pressUp == true || laserType == LASER_UP) {
			if (lastLaserDir != LASER_UP) {
				currentLaser = 0;
			}
			if (laserDir != LASER_UP || laserType == LASER_UP) {
				if (pressUp == true) {
					switch (gravity) {
					case DOWN:
						renderComp.setActiveAnimation("shootU", true);
						break;
					case UP:
						renderComp.setActiveAnimation("shootD", true);
						break;
					case LEFT:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootL", true);
						} else {
							renderComp.setActiveAnimation("swimL", true);
						}
						break;
					case RIGHT:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootR", true);
						} else {
							renderComp.setActiveAnimation("swimR", true);
						}
						break;
					default:
						break;
					}
				}
				incCurrentLaser();
				fireLaser(xpos, ypos, 0, -1);
				laserDir = LASER_UP;
				lastLaserDir = laserDir;
			}
		} else if (pressDown == true || laserType == LASER_DOWN) {
			if (lastLaserDir != LASER_DOWN) {
				currentLaser = 0;
			}
			if (laserDir != LASER_DOWN) {
				if (pressDown == true) {
					switch (gravity) {
					case DOWN:
						renderComp.setActiveAnimation("shootD", true);
						break;
					case UP:
						renderComp.setActiveAnimation("shootU", true);
						break;
					case LEFT:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootR", true);
						} else {
							renderComp.setActiveAnimation("swimR", true);
						}
						break;
					case RIGHT:
						if (swimming == false) {
							renderComp.setActiveAnimation("shootL", true);
						} else {
							renderComp.setActiveAnimation("swimL", true);
						}
						break;
					default:
						break;
					}
				}
				incCurrentLaser();
				fireLaser(xpos, ypos, 0, 1);
				laserDir = LASER_DOWN;
				lastLaserDir = laserDir;
			}
		}
		if (laserDir == LASER_NO) {
			currentLaser = 0;
		}
		if ((laserType == LASER_USER || laserType == LASER_CHARACTER) && currentLaser == LASER_STEP && (pressLeft == true || pressRight == true || pressUp == true || pressDown == true)) {
			getSound("laser").loop();
			playLoop = true;
		}

		if ((laserType == LASER_USER || laserType == LASER_CHARACTER) && pressLeft == false && pressRight == false && pressUp == false && pressDown == false) {
			release = true;
			if(playLoop == true)
			{
				getSound("laser").stop();
				playLoop = false;
			}

		}

	}

	@Override
	public void paintOverlay(GraphicsWrapper g) {
		int fromX = -1, fromY = -1;
		double fromRot = 0D;
		double toRot = 0D;
		int toX, toY;
		boolean skip;
		boolean first = true;
		int colorGlow;

		int screenx = Scene.DIMX * Game.raster;
		int screeny = Scene.DIMY * Game.raster;

		if (gobject.getScene().getGame().getQuality() <= Game.QUAL_LOW) {
			colorGlow = GraphicsWrapper.NO_COLOR;
		} else {
			colorGlow = GraphicsWrapper.COLOR_ORANGE;
		}
		if (laserList.size() == 1) {
			Laser laser = (Laser) laserList.get(0);
			TransformTileComponent trans = laser.getTransformTileComponent();

			fromX = trans.getX();
			fromY = trans.getY();
			fromRot = trans.getRot();

			drawStartLine(g, fromX, fromY, fromRot, colorGlow);
			drawEndLine(g, fromX, fromY, fromRot, colorGlow);
			return;
		}
		fromX = -1;
		fromY = -1;

		for (Iterator<GObject> iterator = laserList.iterator(); iterator.hasNext();) {
			Laser laser = (Laser) iterator.next();
			TransformTileComponent trans = laser.getTransformTileComponent();
			skip = false;
			if (trans != null) {
				if (fromX == -1) {
					fromX = trans.getX();
					fromY = trans.getY();
					fromRot = trans.getRot();
				} else {
					toX = trans.getX();
					toY = trans.getY();
					toRot = trans.getRot();
					GObject backObj = gobject.getScene().searchGObjectByPosition(fromX, fromY, GObject.Z_BACK);
					if (backObj != null) {
						PortalComponent portalComp = backObj.getPortalComponent();
						if (portalComp != null) {
							skip = true;
						}
					}
					if (first == true) {
						first = false;
						drawStartLine(g, fromX, fromY, fromRot, colorGlow);
					}
					if (skip == false) {
						if (fromX == 0 && toX == Scene.DIMX - 1) {
							g.drawLine(0, fromY * Game.raster + (Game.raster / 2), fromX * Game.raster + (Game.raster / 2), fromY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
							g.drawLine(screenx, toY * Game.raster + (Game.raster / 2), toX * Game.raster + (Game.raster / 2), toY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
						} else if (fromX == Scene.DIMX - 1 && toX == 0) {
							g.drawLine(screenx, fromY * Game.raster + (Game.raster / 2), fromX * Game.raster + (Game.raster / 2), fromY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
							g.drawLine(0, toY * Game.raster + (Game.raster / 2), toX * Game.raster + (Game.raster / 2), toY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
						} else if (fromY == 0 && toY == Scene.DIMY - 1) {
							g.drawLine(fromX * Game.raster + (Game.raster / 2), 0, fromX * Game.raster + (Game.raster / 2), fromY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
							g.drawLine(toX * Game.raster + (Game.raster / 2), screeny, toX * Game.raster + (Game.raster / 2), toY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
						} else if (fromY == Scene.DIMY - 1 && toY == 0) {
							g.drawLine(fromX * Game.raster + (Game.raster / 2), screeny, fromX * Game.raster + (Game.raster / 2), fromY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
							g.drawLine(toX * Game.raster + (Game.raster / 2), 0, toX * Game.raster + (Game.raster / 2), toY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
						} else {
							g.drawLine(fromX * Game.raster + (Game.raster / 2), fromY * Game.raster + (Game.raster / 2), toX * Game.raster + (Game.raster / 2), toY * Game.raster + (Game.raster / 2), laserColor, colorGlow, LASERW);
						}
						if (iterator.hasNext() == false) {
							drawEndLine(g, toX, toY, toRot, colorGlow);
						}
					} else {
						drawEndLine(g, fromX, fromY, fromRot, colorGlow);
						drawStartLine(g, toX, toY, toRot, colorGlow);
					}
					fromX = toX;
					fromY = toY;
					fromRot = toRot;
				}
			}
		}
	}

	private void drawStartLine(GraphicsWrapper g, int x, int y, double rot, int colorGlow) {
		int startx = x * Game.raster + (Game.raster / 2);
		int starty = y * Game.raster + (Game.raster / 2);

		if (Double.compare(rot, TransformTileComponent.ROT_S) == 0) {
			g.drawLine(startx, starty, startx, starty - (Game.raster / 2), laserColor, colorGlow, LASERW);
		} else if (Double.compare(rot, TransformTileComponent.ROT_N) == 0) {
			g.drawLine(startx, starty, startx, starty + (Game.raster / 2), laserColor, colorGlow, LASERW);
		} else if (Double.compare(rot, TransformTileComponent.ROT_E) == 0) {
			g.drawLine(startx, starty, startx + (Game.raster / 2), starty, laserColor, colorGlow, LASERW);
		} else if (Double.compare(rot, TransformTileComponent.ROT_W) == 0) {
			g.drawLine(startx, starty, startx - (Game.raster / 2), starty, laserColor, colorGlow, LASERW);
		}
	}

	private void drawEndLine(GraphicsWrapper g, int x, int y, double rot, int colorGlow) {
		int startx = x * Game.raster + (Game.raster / 2);
		int starty = y * Game.raster + (Game.raster / 2);

		if (Double.compare(rot, TransformTileComponent.ROT_S) == 0) {
			g.drawLine(startx, starty, startx, starty + (Game.raster / 2), laserColor, colorGlow, LASERW);
		} else if (Double.compare(rot, TransformTileComponent.ROT_N) == 0) {
			g.drawLine(startx, starty, startx, starty - (Game.raster / 2), laserColor, colorGlow, LASERW);
		} else if (Double.compare(rot, TransformTileComponent.ROT_E) == 0) {
			g.drawLine(startx, starty, startx - (Game.raster / 2), starty, laserColor, colorGlow, LASERW);
		} else if (Double.compare(rot, TransformTileComponent.ROT_W) == 0) {
			g.drawLine(startx, starty, startx + (Game.raster / 2), starty, laserColor, colorGlow, LASERW);
		}
	}

	private void fireLaser(int xpos, int ypos, int xdir, int ydir) {
		boolean collide;
		int laserx, lasery;
		int lastx, lasty;
		Laser newLaser;
		TransformTileComponent laserTransComp;
		DestroyCollideComponent laserDestroyCollideComp;
		int countLaser = 0;
		GObject obj = null, backObj = null;
		int turn = MirrorComponent.TURN_THROUGH;

		collide = false;
		lastx = xpos;
		lasty = ypos;
		laserx = Scene.calcPositionX(xpos, xdir);
		lasery = Scene.calcPositionY(ypos, ydir);

		// if(glowCounter == 0)
		// {
		if (glowStart > 0) {
			glowStart--;
		} else {
			glowStart = 4;

		}
		// glowCounter = GLOWTIMER;
		// }
		// else
		// {
		// glowCounter--;
		// }

		while (collide == false && countLaser < currentLaser) {
			obj = gobject.getScene().searchGObjectByPosition(laserx, lasery, GObject.Z_MAIN);
			backObj = gobject.getScene().searchGObjectByPosition(laserx, lasery, GObject.Z_BACK);
			if (obj != null && obj.getCollideAbleComponent() != null && obj.getMirrorComponent() == null && obj.getDestroyAbleComponent() == null) {
				collide = true;
				break;
			} else {
				newLaser = (Laser) gobject.getScene().cloneGObject('l');
				laserTransComp = newLaser.getTransformTileComponent();
				laserDestroyCollideComp = newLaser.getDestroyCollideComponent();

				if (laserType == LASER_USER) {
					laserDestroyCollideComp.setIgnoreTag("player");
				}

				laserTransComp.setXY(laserx, lasery, false);
				laserDestroyCollideComp.setDamageEnergy(laserEnergy);
				if (xdir < 0 && ydir == 0) {
					laserTransComp.setRot(TransformTileComponent.ROT_E);
				} else if (xdir > 0 && ydir == 0) {
					laserTransComp.setRot(TransformTileComponent.ROT_W);
				} else if (xdir == 0 && ydir < 0) {
					laserTransComp.setRot(TransformTileComponent.ROT_N);
				} else if (xdir == 0 && ydir > 0) {
					laserTransComp.setRot(TransformTileComponent.ROT_S);
				}

				laserList.add(newLaser);

				// Glow paint
				if (gobject.getScene().getGame().getQuality() > Game.QUAL_LOW) {
					if ((countLaser + glowStart) % GLOW_STEP == 0) {
						LaserGlow newGlow = (LaserGlow) gobject.getScene().cloneGObject('*');
						TransformTileComponent glowTransComp = newGlow.getTransformTileComponent();
						// RenderTileComponent render = (RenderTileComponent)
						// newGlow.getRenderTileComponent();
						glowTransComp.setXY(laserx, lasery, false);
						glowTransComp.setRot(laserTransComp.getRot());
						glowList.add(newGlow);
						// if(xdir < 0 && ydir == 0)
						// {
						// render.addPathStep(convertMove(LEFT));
						// }
						// else if(xdir > 0 && ydir == 0)
						// {
						// render.addPathStep(convertMove(RIGHT));
						// }
						// else if(xdir == 0 && ydir < 0)
						// {
						// render.addPathStep(convertMove(UP));
						// int newX = Scene.calcPosition(laserx, lasery,
						// convertShift(1, 0)[0], convertShift(1, 0)[1])[0];
						// int newY = Scene.calcPosition(laserx, lasery,
						// convertShift(1, 0)[0], convertShift(1, 0)[1])[1];
						// glowTransComp.setX(newX);
						// glowTransComp.setY(newY);
						// }
						// else if(xdir == 0 && ydir > 0)
						// {
						// render.addPathStep(convertMove(DOWN));
						// }
						gobject.getScene().addGObject(newGlow);
						// g.drawRect(fromX * Game.RASTER + (Game.RASTER / 2) -
						// 4,
						// fromY * Game.RASTER + (Game.RASTER / 2) - 4, 7, 7);
					}
				}

				GObject existLaser = gobject.getScene().searchGObjectByPosition(laserx, lasery, GObject.Z_FRONT);
				if (existLaser == null) {
					gobject.getScene().addBufferList(newLaser);
				}
			}
			turn = -1;
			MirrorComponent mirror = null;
			if (obj != null) {
				mirror = obj.getMirrorComponent();
			}
			if (mirror != null) {
				turn = mirror.getTurn();
				// TODO: add laser front
				// laser left
				if (xdir < 0 && ydir == 0) {
					if (turn == MirrorComponent.TURN_RIGHT) {
						xdir = 0;
						ydir = -1;
					} else if (turn == MirrorComponent.TURN_LEFT) {
						xdir = 0;
						ydir = 1;
					} else if (turn == MirrorComponent.TURN_THROUGH) {
						// laserx = Scene.calcPosition(laserx, lasery, xdir,
						// ydir)[0];
						// lasery = Scene.calcPosition(laserx, lasery, xdir,
						// ydir)[1];
					}
				}
				// laser right
				else if (xdir > 0 && ydir == 0) {
					if (turn == MirrorComponent.TURN_RIGHT) {
						xdir = 0;
						ydir = 1;
					} else if (turn == MirrorComponent.TURN_LEFT) {
						xdir = 0;
						ydir = -1;
					} else if (turn == MirrorComponent.TURN_THROUGH) {
						// laserx = Scene.calcPosition(laserx, lasery, xdir,
						// ydir)[0];
						// lasery = Scene.calcPosition(laserx, lasery, xdir,
						// ydir)[1];
					}
				}
				// laser up
				else if (xdir == 0 && ydir < 0) {
					if (turn == MirrorComponent.TURN_RIGHT) {
						xdir = 1;
						ydir = 0;
					} else if (turn == MirrorComponent.TURN_LEFT) {
						xdir = -1;
						ydir = 0;
					} else if (turn == MirrorComponent.TURN_THROUGH) {
						// laserx = Scene.calcPosition(laserx, lasery,
						// xdir,ydir)[0];
						// lasery = Scene.calcPosition(laserx, lasery, xdir,
						// ydir)[1];
					}
				}
				// laser down
				else if (xdir == 0 && ydir > 0) {
					if (turn == MirrorComponent.TURN_RIGHT) {
						xdir = -1;
						ydir = 0;
					} else if (turn == MirrorComponent.TURN_LEFT) {
						xdir = 1;
						ydir = 0;
					} else if (turn == MirrorComponent.TURN_THROUGH) {
						// laserx = Scene.calcPosition(laserx, lasery, xdir,
						// ydir)[0];
						// lasery = Scene.calcPosition(laserx, lasery, xdir,
						// ydir)[1];
					}
				}
			}
			// Fire to portal
			if (backObj != null) {
				PortalComponent portalComp = backObj.getPortalComponent();
				if (portalComp != null) {
					ArrayList<GObject> portalList = gobject.getScene().searchGObjectByTag("portal");
					int psize = portalList.size();
					if (psize == 2) {
						int order = portalComp.getOrder();
						for (Iterator<GObject> iterator = portalList.iterator(); iterator.hasNext();) {
							Portal portal = (Portal) iterator.next();
							PortalComponent oppPortalComp = portal.getPortalComponent();
							if (oppPortalComp.getOrder() != order) {
								TransformTileComponent oppPortalTrans = portal.getTransformTileComponent();
								laserx = oppPortalTrans.getX();
								lasery = oppPortalTrans.getY();
								int fireExit = oppPortalComp.getFireExitDir();
								switch (fireExit) {
								case UP:
									xdir = 0;
									ydir = -1;
									break;
								case DOWN:
									xdir = 0;
									ydir = 1;
									break;
								case LEFT:
									xdir = -1;
									ydir = 0;
									break;
								case RIGHT:
									xdir = 1;
									ydir = 0;
									break;
								default:
									break;
								}
							}
						}
					}

				}
			}
			lastx = laserx;
			lasty = lasery;
			laserx = Scene.calcPositionX(laserx, xdir);
			lasery = Scene.calcPositionY(lasery, ydir);
			countLaser++;
		}

		if (collide == true && obj != null) {
			// PushPull Element
			PushPullAbleComponent pushPull = obj.getPushPullAbleComponent();
			if (pushPull != null) {
				int dir;
				if (pushPull.isPush()) {
					dir = 1;
				} else {
					dir = -1;
				}
				if (pushPull.decreaseEnergy(pushPullEnergy) == true) {
					TransformTileComponent trans = obj.getTransformTileComponent();
					RenderTileComponent render = obj.getRenderTileComponent();
					if (trans != null && render != null) {
						laserx = Scene.calcPositionX(laserx, xdir * dir);
						lasery = Scene.calcPositionY(lasery, ydir * dir);
						GObject moveobj = gobject.getScene().searchGObjectByPosition(laserx, lasery, GObject.Z_MAIN);
						if (moveobj == null || moveobj.hasTag("laser")) {
							getSound("push").play();
							render.addPathStep(RenderTileComponent.calcDir(xdir * dir, ydir * dir));
							trans.setXY(laserx, lasery, true);
						}
					}
				}
			}

			// Switch position
			LaserTransportAbleComponent lasertrans = obj.getLaserTransportAbleComponent();
			if (lasertrans != null && lasertrans.isDoneStep() == false) {
				if (lastTransportAble == null || !(lasertrans.equals(lastTransportAble))) {
					lasertrans.resetTimer();
					lastTransportAble = lasertrans;
				} else if (lasertrans.isTimerFinish() == true) {
					lastTransportAble = null;
					TransformTileComponent transObj = obj.getTransformTileComponent();
					RenderTileComponent render = obj.getRenderTileComponent();
					if (transObj != null && render != null) {
						getSound("transport").play();

						TransformTileComponent transPly = gobject.getTransformTileComponent();
						int tempx, tempy;

						tempx = transPly.getX();
						tempy = transPly.getY();
						int tempz = transPly.getZ();

						transPly.setXY(transObj.getX(), transObj.getY(), false);

						transObj.setXY(tempx, tempy, false);
						gobject.getScene().swapBufferObject(transObj.getX(), transObj.getY(), transObj.getZ(), tempx, tempy, tempz);

						lasertrans.setDoneStep();
						waitRelease = true;
						release = false;
					}
				}
			}
			// PortalActivate
			PortalActiveComponent portalact = obj.getPortalActiveComponent();
			if (portalact != null && (laserType == LASER_USER || laserType == LASER_CHARACTER)) {
				GObject backobj = gobject.getScene().searchGObjectByPosition(lastx, lasty, GObject.Z_BACK);
				GObject mainobj = gobject.getScene().searchGObjectByPosition(lastx, lasty, GObject.Z_MAIN);
				if (backobj == null && mainobj == null) {
					ArrayList<GObject> portalList = gobject.getScene().searchGObjectByTag("portal");
					int psize = portalList.size();
					getSound("activate_portal").play();
					if (psize <= 1) {
						Portal newPortal = (Portal) gobject.getScene().cloneGObject('o');
						TransformTileComponent transComp = newPortal.getTransformTileComponent();
						transComp.setXY(lastx, lasty, true);
						PortalComponent portalcomp = newPortal.getPortalComponent();
						portalcomp.setOrder(psize);
						portalcomp.setFireExitDir(calcFireExitDir(xdir, ydir));

						gobject.getScene().addGObject(newPortal);
						waitRelease = true;
						release = false;

					}
					// TODO: Set fir exit dir
					else if (psize > 1) {
						for (Iterator<GObject> iterator = portalList.iterator(); iterator.hasNext();) {
							Portal portal = (Portal) iterator.next();
							PortalComponent portalcomp = portal.getPortalComponent();
							if (portalcomp.getOrder() == 0) {
								gobject.getScene().removeBufferList(portal);
							} else {
								portalcomp.setOrder(0);
								portalcomp.setFireExitDir(calcFireExitDir(xdir, ydir));
							}
						}
						Portal newPortal = (Portal) gobject.getScene().cloneGObject('o');
						TransformTileComponent transComp = newPortal.getTransformTileComponent();
						transComp.setXY(lastx, lasty, true);
						PortalComponent portalcomp = newPortal.getPortalComponent();
						portalcomp.setOrder(1);
						portalcomp.setFireExitDir(calcFireExitDir(xdir, ydir));
						gobject.getScene().addGObject(newPortal);
						waitRelease = true;
						release = false;

					}

				}
			}
		}
	}

	private int calcFireExitDir(int xdir, int ydir) {
		if (xdir == -1 && ydir == 0) {
			return RIGHT;
		} else if (xdir == 1 && ydir == 0) {
			return LEFT;
		} else if (xdir == 0 && ydir == -1) {
			return DOWN;
		} else if (xdir == 0 && ydir == 1) {
			return UP;
		} else {
			return UNKNOWN;
		}
	}

	private void clearAllLaser(boolean withGlow) {
		Scene scene;

		scene = gobject.getScene();

		for (Iterator<GObject> iterator = laserList.iterator(); iterator.hasNext();) {
			Laser laser = (Laser) iterator.next();
			scene.removeBufferList(laser);
			// if (withGlow == true) {
			// TransformTileComponent laserTrans = (TransformTileComponent)
			// laser.getTransformTileComponent();
			// GObject glowObj =
			// scene.searchGObjectByPosition(laserTrans.getX(),
			// laserTrans.getY(), GObject.Z_FRONT, true);
			//
			// if (glowObj != null && glowObj.hasTag("glow")) {
			// scene.getBufferList().remove(glowObj);
			// }
			// }
		}
		if (withGlow == true) {
			for (Iterator<GObject> iterator = glowList.iterator(); iterator.hasNext();) {
				LaserGlow laserg = (LaserGlow) iterator.next();
				scene.removeBufferList(laserg);
			}

		}
	}

	@Override
	public void keyDown(int key) {
		if (laserType == LASER_USER) {
			if (key == KeyEventWrapper.KEY_LASER_LEFT) {
				pressLeft = true;
			}
			if (key == KeyEventWrapper.KEY_LASER_RIGHT) {
				pressRight = true;
			}
			if (key == KeyEventWrapper.KEY_LASER_UP) {
				pressUp = true;
			}
			if (key == KeyEventWrapper.KEY_LASER_DOWN) {
				pressDown = true;
			}
		}
	}

	@Override
	public void keyUp(int key) {
		if (laserType == LASER_USER) {
			if (key == KeyEventWrapper.KEY_LASER_LEFT) {
				pressLeft = false;
			}
			if (key == KeyEventWrapper.KEY_LASER_RIGHT) {
				pressRight = false;
			}
			if (key == KeyEventWrapper.KEY_LASER_UP) {
				pressUp = false;
			}
			if (key == KeyEventWrapper.KEY_LASER_DOWN) {
				pressDown = false;
			}
		}
	}

	@Override
	public boolean mouseMove(int x, int y) {
		// TODO Auto-generated method stub
		if (laserType == LASER_USER) {
			ControlComponent control = gobject.getControlComponent();
			if (control != null && control.getMouseState() == ControlComponent.MSTATE_RELEASE) {
				int area = mouseArea(x, y);
				if (area == AREA_DOWN) {
					pressDown = true;
					drawCursor(x, y);
				} else if (area == AREA_UP) {
					pressUp = true;
					drawCursor(x, y);
				} else if (area == AREA_LEFT) {
					pressLeft = true;
					drawCursor(x, y);
				} else if (area == AREA_RIGHT) {
					pressRight = true;
					drawCursor(x, y);
				}
			}
		}
		return true;
	}

	@Override
	public boolean mouseUp(int x, int y) {
		// TODO Auto-generated method stub
		if (laserType == LASER_USER) {
			pressLeft = false;
			pressRight = false;
			pressUp = false;
			pressDown = false;
			clearCursor();
		}
		return true;
	}

	private void drawCursor(int x, int y) {
		ArrayList<GObject> cursors = gobject.getScene().searchGObjectByTag("cursor");

		for (Iterator<GObject> iterator = cursors.iterator(); iterator.hasNext();) {
			GObject elem = iterator.next();

			MouseCursorComponent comp = elem.getMouseCursorComponent();

			if (comp != null) {
				comp.setxStart(x);
				comp.setyStart(y);
				comp.setLaser(true);
			}
		}
	}

	private void clearCursor() {
		ArrayList<GObject> cursors = gobject.getScene().searchGObjectByTag("cursor");

		for (Iterator<GObject> iterator = cursors.iterator(); iterator.hasNext();) {
			GObject elem = iterator.next();

			MouseCursorComponent comp = elem.getMouseCursorComponent();

			if (comp != null) {
				comp.setxStart(-1);
				comp.setyStart(-1);
			}
		}
	}

	private void incCurrentLaser() {
		if (currentLaser < MAX_LASER) {
			currentLaser += LASER_STEP;
		}
	}

	@Override
	public void initRestore() {
		// TODO Auto-generated method stub
		super.initRestore();
		clearAllLaser(true);
		laserList.clear();
		glowList.clear();
		glowCounter = 0;
		lastLaserDir = LASER_NO;
		pressDown = false;
		pressUp = false;
		pressLeft = false;
		pressRight = false;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		LaserComponent comp = (LaserComponent) super.clone();
		laserList = new ArrayList<GObject>();
		return comp;
	}

	public int getLaserDir() {
		return laserDir;
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		if (sendkey.equals(switchKey + ".on")) {
			if (isSwitchReverse == true) {
				clearAllLaser(true);
				setActive(false);
			} else {
				setActive(true);
			}
		}
		if (sendkey.equals(switchKey + ".off")) {
			if (isSwitchReverse == true) {
				setActive(true);
			} else {
				clearAllLaser(true);
				setActive(false);
			}
		}
		if (tag != null && gobject.hasTag(tag)) {

			if (sendkey.equals("laser_left.down")) {
				pressLeft = true;
			}
			if (sendkey.equals("laser_right.down")) {
				pressRight = true;
			}
			if (sendkey.equals("laser_up.down")) {
				pressUp = true;
			}
			if (sendkey.equals("laser_down.down")) {
				pressDown = true;
			}
			if (sendkey.equals("laser_left.up")) {
				pressLeft = false;
			}
			if (sendkey.equals("laser_right.up")) {
				pressRight = false;
			}
			if (sendkey.equals("laser_up.up")) {
				pressUp = false;
			}
			if (sendkey.equals("laser_down.up")) {
				pressDown = false;
			}

			if (sendkey.equals("release")) {
				pressLeft = false;
				pressRight = false;
				pressDown = false;
				pressUp = false;
			}

		}

	}

	// public String getDestroyKey() {
	// return destroyKey;
	// }
	//
	// public void setDestroyKey(String destroyKey) {
	// this.destroyKey = destroyKey;
	// }

	public String getSwitchKey() {
		return switchKey;
	}

	public void setSwitchKey(String switchKey) {
		this.switchKey = switchKey;
	}

	public boolean isSwitchReverse() {
		return isSwitchReverse;
	}

	public void setSwitchReverse(boolean isSwitchReverse) {
		this.isSwitchReverse = isSwitchReverse;
	}

}
