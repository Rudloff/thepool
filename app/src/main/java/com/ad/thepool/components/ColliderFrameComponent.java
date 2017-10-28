package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;

public class ColliderFrameComponent extends Component implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1724815740441203188L;

	public static final int COMP_NAME = 5;

	// private GObject collideAreaPrev[][] = new GObject[3][3];
	// private GObject collideArea[][] = new GObject[3][3];

	public int frameModel;

	public ColliderFrameComponent(boolean isActive) {
		super(COMP_NAME, isActive);
		// for (int i = 0; i < 3; i++) {
		// for (int j = 0; j < 3; j++) {
		// collideArea[i][j] = null;
		// collideAreaPrev[i][j] = null;
		// }
		// }
	}

	// public void updateA() {
	// TransformTileComponent trans = gobject.getTransformTileComponent();
	//
	// if (trans == null)
	// return;
	//
	// // Enter in collision matrix
	// for (int i = -1; i < 2; i++) {
	// for (int j = -1; j < 2; j++) {
	// int pos[] = Scene.calcPosition(trans.getX(), trans.getY(), i, j);
	//
	// GObject coll = gobject.getScene().searchGObjectByPosition(pos[0], pos[1],
	// trans.getZ());
	// if (coll == null) {
	// collideArea[i + 1][j + 1] = null;
	// } else {
	// if (coll != gobject && coll.getCollideAbleComponent() != null) {
	// collideArea[i + 1][j + 1] = coll;
	//
	// } else {
	// collideArea[i + 1][j + 1] = null;
	// }
	// }
	// }
	// }
	// // Check for changed and stay values
	// for (int i = 0; i < 3; i++) {
	// for (int j = 0; j < 3; j++) {
	// GObject currColl = collideArea[i][j];
	// GObject prevColl = collideAreaPrev[i][j];
	// if (currColl != null) {
	// if (currColl != prevColl) {
	// // Send collision message to source
	// for (Iterator<Component> iterator =
	// currColl.getComponentList().iterator(); iterator.hasNext();) {
	// Component comp = (Component) iterator.next();
	// // gobject.getScene().debugDrawTile(i+4, j+4, '2');
	//
	// comp.onCollisionEnter(gobject, i , j);
	// }
	// // Send collision message to dest
	// for (Iterator<Component> iterator =
	// gobject.getComponentList().iterator(); iterator.hasNext();) {
	// Component comp = (Component) iterator.next();
	// comp.onCollisionEnter(currColl, i , j );
	// }
	// }
	// if (currColl == prevColl) {
	// for (Iterator<Component> iterator =
	// currColl.getComponentList().iterator(); iterator.hasNext();) {
	// Component comp = (Component) iterator.next();
	//
	// // gobject.getScene().debugDrawTile(i + 4, j + 4,
	// // '2');
	//
	// comp.onCollisionStay(gobject, i , j );
	// }
	// for (Iterator<Component> iterator =
	// gobject.getComponentList().iterator(); iterator.hasNext();) {
	// Component comp = (Component) iterator.next();
	// comp.onCollisionStay(currColl, i , j );
	// }
	// }
	// }
	// if(currColl == null || prevColl != currColl)
	// {
	// if (prevColl != null) {
	// for (Iterator<Component> iterator =
	// prevColl.getComponentList().iterator(); iterator.hasNext();) {
	// Component comp = (Component) iterator.next();
	// comp.onCollisionLeave(gobject, i , j );
	// }
	// for (Iterator<Component> iterator =
	// gobject.getComponentList().iterator(); iterator.hasNext();) {
	// Component comp = (Component) iterator.next();
	// comp.onCollisionLeave(prevColl, i , j );
	// }
	// }
	// }
	// collideAreaPrev[i][j] = collideArea[i][j];
	// }
	// }
	// }

	public void postUpdate() {

	}

	public GObject[][] getColliderMatrix(int z) {
		GObject area[][] = new GObject[3][3];

		TransformTileComponent trans = gobject.getTransformTileComponent();

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int pos[] = Scene.calcPosition(trans.getX(), trans.getY(), i, j);

				GObject coll = gobject.getScene().searchGObjectByPosition(pos[0], pos[1], z);
				if (coll == null) {
					area[i + 1][j + 1] = null;
				} else {
					if (coll != gobject) {
						area[i + 1][j + 1] = coll;

					} else {
						area[i + 1][j + 1] = null;
					}
				}
			}
		}
		return area;
	}

	public GObject[][] getColliderMatrix(int z, int compId) {
		GObject area[][] = new GObject[3][3];

		TransformTileComponent trans = gobject.getTransformTileComponent();

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int pos[] = Scene.calcPosition(trans.getX(), trans.getY(), i, j);

				GObject coll = gobject.getScene().searchGObjectByPosition(pos[0], pos[1], z);
				if (coll == null) {
					area[i + 1][j + 1] = null;
				} else {
					if (coll != gobject && coll.findComponent(compId) != null) {
						area[i + 1][j + 1] = coll;

					} else {
						area[i + 1][j + 1] = null;
					}
				}
			}
		}
		return area;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ColliderFrameComponent comp = (ColliderFrameComponent) super.clone();

		// comp.collideArea = new GObject[3][3];
		// comp.collideAreaPrev = new GObject[3][3];

		return comp;
	}

}
