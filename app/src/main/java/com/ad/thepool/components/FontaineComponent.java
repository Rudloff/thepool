package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;
import com.ad.thepool.objects.Portal;
import com.ad.thepool.objects.Water;

public class FontaineComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8002025035868862712L;

	public static final int COMP_NAME = 37;

	private int fontaineDir;
	private int fontaineType;
	public static final int FONTAINE_NO = -1;
	public static final int FONTAINE_LEFT = 0;
	public static final int FONTAINE_RIGHT = 1;
	public static final int FONTAINE_UP = 2;
	public static final int FONTAINE_DOWN = 3;
	public static final int MAX_FONTAINE = 50;

	int pushPullEnergy = 1;
	public String destroyKey;
	public String switchKey;

	private boolean reverseSwitch;

	private ArrayList<GObject> fontaineList;

	public FontaineComponent(boolean isActive, int fontaineType, String destroyKey, String switchKey, int pushPullEnergy) {
		super(COMP_NAME, isActive);
		fontaineList = new ArrayList<GObject>();
		this.pushPullEnergy = pushPullEnergy;
		this.fontaineType = fontaineType;
		this.destroyKey = destroyKey;
		this.switchKey = switchKey;
	}

	@Override
	public void update() {
		fontaineDir = FONTAINE_NO;

		TransformTileComponent transComp = gobject.getTransformTileComponent();
		RenderTileComponent renderComp = gobject.getRenderTileComponent();

		if (transComp == null || renderComp == null) {
			return;
		}

		int xpos = transComp.getX();
		int ypos = transComp.getY();

		clearAllWater();
		fontaineList.clear();

		if (fontaineType == FONTAINE_LEFT) {
			if (fontaineDir != FONTAINE_LEFT) {
				fireFontaine(xpos, ypos, -1, 0);
				fontaineDir = FONTAINE_LEFT;
			}
		} else if (fontaineType == FONTAINE_RIGHT) {
			if (fontaineDir != FONTAINE_RIGHT) {
				fireFontaine(xpos, ypos, 1, 0);
				fontaineDir = FONTAINE_RIGHT;
			}
		} else if (fontaineType == FONTAINE_UP) {
			if (fontaineDir != FONTAINE_UP || fontaineType == FONTAINE_UP) {
				fireFontaine(xpos, ypos, 0, -1);
				fontaineDir = FONTAINE_UP;
			}
		} else if (fontaineType == FONTAINE_DOWN) {
			if (fontaineDir != FONTAINE_DOWN) {
				fireFontaine(xpos, ypos, 0, 1);
				fontaineDir = FONTAINE_DOWN;
			}
		}
	}

	private void fireFontaine(int xpos, int ypos, int xdir, int ydir) {
		boolean collide;
		int fontainex, fontainey;
		Water newWater;
		TransformTileComponent fontaineTransComp;
		WaterComponent waterComp;
		int countFontaine = 0;
		GObject obj = null, backObj = null;
		int turn = MirrorComponent.TURN_THROUGH;

		collide = false;
		fontainex = Scene.calcPositionX(xpos, xdir);
		fontainey = Scene.calcPositionY(ypos, ydir);

		while (collide == false && countFontaine < MAX_FONTAINE) {
			obj = gobject.getScene().searchGObjectByPosition(fontainex, fontainey, GObject.Z_MAIN);
			backObj = gobject.getScene().searchGObjectByPosition(fontainex, fontainey, GObject.Z_BACK2);
			if (obj != null && ((obj.getCollideAbleComponent() != null) && obj.getMirrorComponent() == null && obj.getFloaterComponent() == null)) {
				collide = true;
				break;
			} else {
				if (backObj == null) {
					newWater = (Water) gobject.getScene().cloneGObject('~');
					fontaineTransComp = newWater.getTransformTileComponent();
					waterComp = newWater.getWaterComponent();
					fontaineTransComp.setXY(fontainex, fontainey, false);
					if (xdir < 0 && ydir == 0) {
						fontaineTransComp.setRot(TransformTileComponent.ROT_S);
						waterComp.setFloaterDir(LEFT);
					} else if (xdir > 0 && ydir == 0) {
						fontaineTransComp.setRot(TransformTileComponent.ROT_N);
						waterComp.setFloaterDir(RIGHT);
					} else if (xdir == 0 && ydir < 0) {
						fontaineTransComp.setRot(TransformTileComponent.ROT_W);
						waterComp.setFloaterDir(UP);
					} else if (xdir == 0 && ydir > 0) {
						fontaineTransComp.setRot(TransformTileComponent.ROT_E);
						waterComp.setFloaterDir(DOWN);
					}
					newWater.getRenderTileComponent().setActiveAnimation("float", true);

					fontaineList.add(newWater);

					gobject.getScene().addBufferList(newWater);
				}
			}
			turn = -1;
			MirrorComponent mirror = null;
			if (obj != null) {
				mirror = obj.getMirrorComponent();
			}
			if (mirror != null) {
				turn = mirror.getTurn();
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
								fontainex = oppPortalTrans.getX();
								fontainey = oppPortalTrans.getY();
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
			fontainex = Scene.calcPositionX(fontainex, xdir);
			fontainey = Scene.calcPositionY(fontainey, ydir);
			countFontaine++;
		}
		if (collide == true && obj != null) {
			// If object can be destroyed, do it
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
						fontainex = Scene.calcPositionX(fontainex, xdir * dir);
						fontainey = Scene.calcPositionY(fontainey, ydir * dir);
						GObject moveobj = gobject.getScene().searchGObjectByPosition(fontainex, fontainey, GObject.Z_MAIN);
						if (moveobj == null || moveobj.hasTag("laser")) {
							render.addPathStep(RenderTileComponent.calcDir(xdir * dir, ydir * dir));
							trans.setXY(fontainex, fontainey, true);
						}
					}
				}
			}

		}
	}

	private void clearAllWater() {
		Scene scene;

		scene = gobject.getScene();

		for (Iterator<GObject> iterator = fontaineList.iterator(); iterator.hasNext();) {
			Water water = (Water) iterator.next();
			scene.removeBufferList(water);
		}
	}

	@Override
	public void initRestore() {
		// TODO Auto-generated method stub
		super.initRestore();
		clearAllWater();
		fontaineList.clear();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		FontaineComponent comp = (FontaineComponent) super.clone();
		fontaineList = new ArrayList<GObject>();
		return comp;
	}

	public int getFontaineDir() {
		return fontaineDir;
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {

		if (reverseSwitch == true) {
			if (sendkey.equals(switchKey + ".on")) {
				setActive(true);
			}
			if (sendkey.equals(switchKey + ".off")) {
				setActive(false);
			}
		} else {
			if (sendkey.equals(switchKey + ".on")) {
				setActive(false);
			}
			if (sendkey.equals(switchKey + ".off")) {
				setActive(true);
			}

		}
	}

	public String getDestroyKey() {
		return destroyKey;
	}

	public void setDestroyKey(String destroyKey) {
		this.destroyKey = destroyKey;
	}

	public String getSwitchKey() {
		return switchKey;
	}

	public void setSwitchKey(String switchKey) {
		this.switchKey = switchKey;
	}

	public boolean isReverseSwitch() {
		return reverseSwitch;
	}

	public void setReverseSwitch(boolean reverseSwitch) {
		this.reverseSwitch = reverseSwitch;
	}

	@Override
	public void setActive(boolean isActive) {
		// TODO Auto-generated method stub
		super.setActive(isActive);
		if (isActive == false) {
			clearAllWater();
		}
	}

}
