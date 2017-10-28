package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;

public class FloaterComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7378496809992350617L;

	public static final int COMP_NAME = 38;

	private int floatStep;
	private int frame;
	private int moveDir;

	public static final int FLOAT_ACTIVE = 3;

	public FloaterComponent(boolean isActive) {
		super(COMP_NAME, isActive);
		floatStep = UNKNOWN;
		frame = 0;
	}

	@Override
	public void update() {
		ColliderFrameComponent coll = gobject.getColliderFrameComponent();
		TransformTileComponent trans = gobject.getTransformTileComponent();
		RenderTileComponent render = gobject.getRenderTileComponent();

		if (coll == null || trans == null || render == null) {
			return;
		}

		moveDir = UNKNOWN;

		frame++;
		if ((frame % FLOAT_ACTIVE) == 0) {
			GObject[][] area = coll.getColliderMatrix(GObject.Z_MAIN, CollideAbleComponent.COMP_NAME);
			GObject[][] colarea = coll.getColliderMatrix(GObject.Z_BACK, CollectAbleComponent.COMP_NAME);
			GObject[][] eraseArea = coll.getColliderMatrix(GObject.Z_MAIN, EraseAbleComponent.COMP_NAME);
			GObject[][] destroyCollideArea = coll.getColliderMatrix(GObject.Z_MAIN, DestroyCollideComponent.COMP_NAME);

			GObject water = gobject.getScene().searchGObjectByPosition(trans.getX(), trans.getY(), GObject.Z_BACK2);
			if (water == null) {
				return;
			}
			WaterComponent waterComp = water.getWaterComponent();
			if (waterComp != null && waterComp.getFloaterDir() != UNKNOWN) {
				// floatStep = convertMove(waterComp.getFloaterDir());
				floatStep = waterComp.getFloaterDir();
			}
			GObject colObj;
			colObj = getObjNorm(destroyCollideArea, 0, 1);
			if (colObj != null && floatStep == LEFT) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObjNorm(destroyCollideArea, 2, 1);
			if (colObj != null && floatStep == RIGHT) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObjNorm(destroyCollideArea, 1, 0);
			if (colObj != null && floatStep == UP) {
				damageCollide(colObj);
				getSound("collide").play();
			}
			colObj = getObjNorm(destroyCollideArea, 1, 2);
			if (colObj != null && floatStep == DOWN) {
				damageCollide(colObj);
				getSound("collide").play();
			}

			if ((getObjNorm(area, 0, 1) == null || getObjNorm(eraseArea, 0, 1) != null) && getObjNorm(colarea, 0, 1) == null && floatStep == LEFT) {
				render.addPathStep(LEFT);
				render.setActiveAnimation("swimL", true);
				getSound("swim").play();
				moveDir = LEFT;
			} else if ((getObjNorm(area, 2, 1) == null || getObjNorm(eraseArea, 2, 1) != null) && getObjNorm(colarea, 2, 1) == null && floatStep == RIGHT) {
				render.addPathStep(RIGHT);
				render.setActiveAnimation("swimR", true);
				moveDir = RIGHT;
				getSound("swim").play();
			} else if ((getObjNorm(area, 1, 0) == null || getObjNorm(eraseArea, 1, 0) != null) && getObjNorm(colarea, 1, 0) == null && floatStep == UP) {
				render.addPathStep(UP);
				render.setActiveAnimation("swimL", true);
				getSound("swim").play();
				moveDir = UP;
			} else if ((getObjNorm(area, 1, 2) == null || getObjNorm(eraseArea, 1, 2) != null) && getObjNorm(colarea, 1, 2) == null && floatStep == DOWN) {
				render.addPathStep(DOWN);
				render.setActiveAnimation("swimR", true);
				getSound("swim").play();
				moveDir = DOWN;
			}

			// Do moves

			int x = trans.getX();
			int y = trans.getY();

			if (moveDir == LEFT) {
				// 0 1, -1, 0
				int newX = Scene.calcPositionX(x, convertShiftNorm(0, 1)[0]);
				int newY = Scene.calcPositionY(y, convertShiftNorm(0, 1)[1]);
				GObject eraseObj = getObjNorm(eraseArea, 0, 1);
				if (eraseObj != null) {
					gobject.getScene().removeBufferList(eraseObj);
				}
				trans.setXY(newX, newY, true);
			} else if (moveDir == RIGHT) {
				// 2 1, 1 0
				int newX = Scene.calcPositionX(x, convertShiftNorm(2, 1)[0]);
				int newY = Scene.calcPositionY(y, convertShiftNorm(2, 1)[1]);
				GObject eraseObj = getObjNorm(eraseArea, 2, 1);
				if (eraseObj != null) {
					gobject.getScene().removeBufferList(eraseObj);
				}
				trans.setXY(newX, newY, true);

			} else if (moveDir == UP) {
				// 1 0
				int newX = Scene.calcPositionX(x, convertShiftNorm(1, 0)[0]);
				int newY = Scene.calcPositionY(y, convertShiftNorm(1, 0)[1]);
				GObject eraseObj = getObjNorm(eraseArea, 1, 0);
				if (eraseObj != null) {
					gobject.getScene().removeBufferList(eraseObj);
				}
				trans.setXY(newX, newY, true);
			} else if (moveDir == DOWN) {
				// 1 2
				int newX = Scene.calcPositionX(x, convertShiftNorm(1, 2)[0]);
				int newY = Scene.calcPositionY(y, convertShiftNorm(1, 2)[1]);
				GObject eraseObj = getObjNorm(eraseArea, 1, 2);
				if (eraseObj != null) {
					gobject.getScene().removeBufferList(eraseObj);
				}
				trans.setXY(newX, newY, true);
			}

		} else {
			floatStep = UNKNOWN;
		}

	}

}
