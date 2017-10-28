package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;

public class PathMoveComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8528985595319647813L;

	public static final int COMP_NAME = 18;

	private int startx;
	private int starty;
	private boolean doMove;
	private int activeElement;
	private boolean removeAfterMove;
	private int timer;

	private ArrayList<Pathelement> path;

	public PathMoveComponent(boolean isActive, boolean removeAfterMove) {
		super(COMP_NAME, isActive);
		doMove = false;
		path = new ArrayList<Pathelement>();
		activeElement = -1;
		this.removeAfterMove = removeAfterMove;
	}

	public void reset() {
		doMove = false;
		activeElement = 0;
		Pathelement elem = path.get(0);
		if (elem.getDuration() != -1) {
			timer = elem.getDuration();
		}
		// TODO: reset position
	}

	public int getStartx() {
		return startx;
	}

	public void setStartx(int startx) {
		this.startx = startx;
	}

	public int getStarty() {
		return starty;
	}

	public void setStarty(int starty) {
		this.starty = starty;
	}

	public ArrayList<Pathelement> getPath() {
		return path;
	}

	public void setPath(ArrayList<Pathelement> path) {
		this.path = path;
	}

	public void addPath(String tag, int nextOrderId, String message, int duration, boolean stopAfterStep) {
		Pathelement elem = new Pathelement(tag, nextOrderId, message, duration, stopAfterStep);
		path.add(elem);
	}

	public void addPath(String tag, int nextOrderId, String message, boolean stopAfterStep) {
		Pathelement elem = new Pathelement(tag, nextOrderId, message, -1, stopAfterStep);
		path.add(elem);
	}

	public boolean isDoMove() {
		return doMove;
	}

	public void setDoMove(boolean doMove) {
		this.doMove = doMove;
	}

	public int getActiveElement() {
		return activeElement;
	}

	public boolean isRemoveAfterMove() {
		return removeAfterMove;
	}

	public void setRemoveAfterMove(boolean removeAfterMove) {
		this.removeAfterMove = removeAfterMove;
	}

	@Override
	public void update() {
		Pathelement elem = null;
		if (doMove == true && activeElement >= 0) {
			if (activeElement < path.size()) {
				elem = path.get(activeElement);

				if (elem.getDuration() != -1) {
					if (timer > 0) {
						timer--;
						if (gobject.hasTag(elem.getTag())) {
							gobject.getScene().sendMessage(gobject, null, elem.getTag(), "release");
							gobject.getScene().sendMessage(gobject, null, elem.getTag(), elem.getMessage());
						}
					} else {
						nextElement();
					}
				}

				if (gobject.hasTag(elem.getTag())) {
					gobject.getScene().sendMessage(gobject, null, elem.getTag(), "release");
					gobject.getScene().sendMessage(gobject, null, elem.getTag(), elem.getMessage());
				}
			}
		}

	}

	private void nextElement() {
		Pathelement elem = path.get(activeElement);
		if (elem.isStopAfterStep() == true) {
			setDoMove(false);
		}
		activeElement++;
		if (activeElement == path.size()) {
			if (removeAfterMove == true) {
				gobject.getScene().removeBufferList(gobject);
			} else {
				gobject.getScene().sendMessage(gobject, null, elem.getTag(), "release");
			}
		} else {
			elem = path.get(activeElement);

			gobject.getScene().sendMessage(gobject, null, elem.getTag(), "step." + activeElement);

			if (elem.getDuration() != -1) {
				timer = elem.getDuration();
			} else {
				timer = 0;
			}
		}

	}

	@Override
	public void onTriggerEnter(GObject collidedObject, int x, int y) {
		if (doMove == true && activeElement >= 0) {
			if (activeElement < path.size()) {
				if (collidedObject.hasTag("waypoint")) {
					WaypointComponent wayPointComp = collidedObject.getWaypointComponent();
					if (gobject.hasTag(wayPointComp.getTag())) {
						Pathelement elem = path.get(activeElement);
						if (wayPointComp.getOrder() == elem.getNextOrderId() && elem.getDuration() == -1) {
							nextElement();
						}

					}
				}
			}
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		PathMoveComponent compClone = (PathMoveComponent) super.clone();
		compClone.path = new ArrayList<Pathelement>();

		for (Iterator<Pathelement> iterator = path.iterator(); iterator.hasNext();) {
			Pathelement elem = (Pathelement) iterator.next();
			Pathelement elemClone = (Pathelement) elem.clone();
			compClone.path.add(elemClone);
		}

		return compClone;

	}

}
