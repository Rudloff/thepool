package com.ad.thepool.components;

import java.util.HashMap;
import java.util.Iterator;

import com.ad.thepool.GObject;

public class TriggerFrameComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1325547692413275573L;

	public static final int COMP_NAME = 31;

	private HashMap<Integer, GObject> triggerPrev;
	private HashMap<Integer, GObject> trigger;

	public int frameModel;

	public TriggerFrameComponent(boolean isActive) {
		super(COMP_NAME, isActive);
		triggerPrev = new HashMap<Integer, GObject>();
		trigger = new HashMap<Integer, GObject>();
	}

	@Override
	public void update() {
		TransformTileComponent trans = gobject.getTransformTileComponent();

		if (trans == null) {
			return;
		}

		// ArrayList<GObject> objList;

		// objList = gobject.getScene().searchGObjectByPosition(trans.getX(),
		// trans.getY());
		//
		// for (Iterator iterator = objList.iterator(); iterator.hasNext();) {
		// GObject gObject = (GObject) iterator.next();
		// triggerAction(gObject,gObject.getTransformTileComponent().getZ());
		//
		// }

		GObject obj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_BACK);
		triggerAction(obj, GObject.Z_BACK);
		obj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_BACK2);
		triggerAction(obj, GObject.Z_BACK2);
		obj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_FRONT);
		triggerAction(obj, GObject.Z_FRONT);
		obj = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_FRONT2);
		triggerAction(obj, GObject.Z_FRONT2);

	}

	private void triggerAction(GObject obj, int z) {
		TransformTileComponent trans = gobject.getTransformTileComponent();

		trigger.put(z, null);
		if (obj != null && obj != gobject && obj.getTriggerAbleComponent() != null) {
			trigger.put(z, obj);
		}
		if (trigger.get(z) != null) {
			if (trigger.get(z) != triggerPrev.get(z)) {
				for (Iterator<Component> iterator = trigger.get(z).getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					comp.onTriggerEnter(gobject, trans.getX(), trans.getY());
				}
				for (Iterator<Component> iterator = gobject.getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					comp.onTriggerEnter(trigger.get(z), trans.getX(), trans.getY());
				}
				gobject.getScene().onTriggerEnter(gobject, trigger.get(z), trans.getX(), trans.getY());
			} else if (trigger.get(z) == triggerPrev.get(z)) {
				for (Iterator<Component> iterator = trigger.get(z).getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					comp.onTriggerStay(gobject, trans.getX(), trans.getY());
				}
				for (Iterator<Component> iterator = gobject.getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					comp.onTriggerStay(trigger.get(z), trans.getX(), trans.getY());
				}
				gobject.getScene().onTriggerStay(gobject, trigger.get(z), trans.getX(), trans.getY());
			}
		}
		if (trigger.get(z) == null || trigger.get(z) != triggerPrev.get(z)) {
			if (triggerPrev.get(z) != null) {
				for (Iterator<Component> iterator = triggerPrev.get(z).getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					comp.onTriggerLeave(gobject, trans.getX(), trans.getY());
				}
				for (Iterator<Component> iterator = gobject.getComponentList().iterator(); iterator.hasNext();) {
					Component comp = iterator.next();
					comp.onTriggerLeave(triggerPrev.get(z), trans.getX(), trans.getY());
				}
				gobject.getScene().onTriggerLeave(gobject, trigger.get(z), trans.getX(), trans.getY());
			}
		}
		triggerPrev.put(z, trigger.get(z));
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		TriggerFrameComponent comp = (TriggerFrameComponent) super.clone();

		HashMap<Integer, GObject> newTriggerPrev = new HashMap<Integer, GObject>();
		HashMap<Integer, GObject> newTrigger = new HashMap<Integer, GObject>();

		comp.triggerPrev = newTriggerPrev;
		comp.trigger = newTrigger;

		return comp;
	}

}
