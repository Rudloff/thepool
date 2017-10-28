package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.objects.Portal;

public class ActivateComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7213692233980311679L;

	public static final int COMP_NAME = 1;
	
	private boolean permanentSwim;

	private GObject lastPortal;

	public ActivateComponent(boolean isActive) {
		super(COMP_NAME, isActive);
		lastPortal = null;
		permanentSwim = false;
	}

	public void processBackObj() {
		TransformTileComponent trans = gobject.getTransformTileComponent();
		ControlComponent control = gobject.getControlComponent();
		PhysicsComponent physics = gobject.getPhysicsComponent();
		RenderTileComponent render = gobject.getRenderTileComponent();

		if (trans == null || control == null) {
			return;
		}

		int currentState = control.getState();

		GObject backObj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_BACK);
		GObject back2Obj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_BACK2);
		// GObject frontObj =
		// gobject.getScene().searchGObjectByPosition(trans.getX(),
		// trans.getY(), GObject.Z_FRONT);

		if (backObj == null || backObj.getPortalComponent() == null) {
			lastPortal = null;
		}
		int gravity = DOWN;
		boolean defWalk = true;

		if (physics != null) {
			gravity = physics.getGravity();
		}
		// if(frontObj != null && frontObj.hasTag("laser"))
		// {
		// damageCollide(frontObj);
		// }
		if ((back2Obj != null && back2Obj.getWaterComponent() != null) || permanentSwim == true) {
			control.setJump(true);
			if (render != null && currentState != ControlComponent.STATE_SWIM) {
				render.setActiveAnimation("swimL", true);
			}
			control.setState(ControlComponent.STATE_SWIM);
			defWalk = false;
		} else if (backObj != null && backObj.getClimbAbleComponent() != null) {
			control.setJump(true);
			if (render != null && currentState != ControlComponent.STATE_CLIMB) {
				render.setActiveAnimation("climbStand", true);
			}
			control.setState(ControlComponent.STATE_CLIMB);
			defWalk = false;
		}
		// climb a robe
		else if (backObj != null && backObj.getRobeAbleComponent() != null) {
			RobeAbleComponent robeAble = backObj.getRobeAbleComponent();

			if (robeAble.getForGravity() == gravity) {
				control.setJump(true);
				if (render != null && currentState != ControlComponent.STATE_ROBE) {
					render.setActiveAnimation("robeStand", true);
				}
				control.setState(ControlComponent.STATE_ROBE);
				defWalk = false;
			} else {
				control.setJump(false);

				if (render != null && currentState != ControlComponent.STATE_WALK) {
					render.setActiveAnimation("stand", true);
				}
				defWalk = false;
				control.setState(ControlComponent.STATE_WALK);
			}
		}
		// Enter a portal
		if (backObj != null && backObj.getPortalComponent() != null && backObj != lastPortal) {
			ArrayList<GObject> portalList = gobject.getScene().searchGObjectByTag("portal");
			int psize = portalList.size();
			int newx = -1, newy = -1;
			if (psize == 2) {
				PortalComponent portalComp = backObj.getPortalComponent();
				int order = portalComp.getOrder();
				for (Iterator<GObject> iterator = portalList.iterator(); iterator.hasNext();) {
					Portal portal = (Portal) iterator.next();
					PortalComponent oppPortalComp = portal.getPortalComponent();

					if (oppPortalComp.getOrder() != order) {

						TransformTileComponent oppPortalTrans = portal.getTransformTileComponent();
						newx = oppPortalTrans.getX();
						newy = oppPortalTrans.getY();
						lastPortal = portal;
						getSound("portal").play();
					}
				}
			}
			if (newx != -1) {
				trans.setXY(newx, newy, true);
			}
		}
		// Gravity Switch
		else if (backObj != null && backObj.getGravitySwitchComponent() != null) {
			GravitySwitchComponent gravSwitch = backObj.getGravitySwitchComponent();
			PhysicsComponent physicsComp = gobject.getPhysicsComponent();
			int oldGrav = physicsComp.getGravity();
			int newGrav = gravSwitch.getToGrav();
			physicsComp.setGravity(newGrav);

			if (oldGrav != newGrav) {
				if (newGrav == DOWN) {
					trans.setRot(TransformTileComponent.ROT_N);
					getSound("gravity").play();
				} else if (newGrav == UP) {
					trans.setRot(TransformTileComponent.ROT_S);
					getSound("gravity").play();
				} else if (newGrav == LEFT) {
					trans.setRot(TransformTileComponent.ROT_E);
					getSound("gravity").play();
				} else if (newGrav == RIGHT) {
					trans.setRot(TransformTileComponent.ROT_W);
					getSound("gravity").play();
				}
			}

			if (control != null) {
				control.releaseKeys();
			}
		}
		// Coin
		else if (backObj != null && backObj.getCollectAbleComponent() != null) {
			gobject.getScene().removeBufferList(backObj);
			getSound("collect").play();
			CollectorComponent coll = gobject.getCollectorComponent();
			CollectAbleComponent collAble = backObj.getCollectAbleComponent();
			if (coll != null && collAble != null) {
				String countKey = collAble.getCounterKey();
				coll.incCounter(countKey);
				gobject.getScene().sendMessage(gobject, backObj, "coin", "counter." + countKey);
				// Check if remaining coins existing
				boolean found = false;
				Iterator<GObject> it = gobject.getScene().getBufferListIterator();
				while (it.hasNext() && found == false) {
					GObject obj = it.next();
					if (obj.hasTag("coin")) {
						CollectAbleComponent collAbleInt = obj.getCollectAbleComponent();
						if (collAbleInt.getCounterKey().equals(countKey)) {
							found = true;
						}
					}
				}
				if (found == false) {
					gobject.getScene().sendMessage(gobject, backObj, "coin", "coin.empty." + countKey);
				}
			}

			if (control != null) {
				control.releaseKeys();
			}
		}

		else if (defWalk == true) {
			if (render != null && currentState != ControlComponent.STATE_WALK) {
				render.setActiveAnimation("stand", true);
			}
			control.setState(ControlComponent.STATE_WALK);
		}
	}
	
	public void setPermanentSwim(boolean permanentSwim) {
		this.permanentSwim = permanentSwim;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
