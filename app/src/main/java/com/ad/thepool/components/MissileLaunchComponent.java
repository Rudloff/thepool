package com.ad.thepool.components;

import com.ad.thepool.GObject;
import com.ad.thepool.Scene;
import com.ad.thepool.objects.Missile;

public class MissileLaunchComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7458533257095816842L;
	public static final int COMP_NAME = 16;
	public static final int TYPE_ONE_DIR = 0;
	public static final int TYPE_ROUND = 1;
	public static final int TYPE_REACT = 2;

	private int interval;
	private int type;
	private int direction;
	private int frame;
	private int duration;
	private int timer;
	private String anim;
	private boolean destroyCollide;

	public MissileLaunchComponent(boolean isActive, int interval, int type, int direction, String anim, boolean destroyCollide) {
		super(COMP_NAME, isActive);
		this.interval = interval;
		this.direction = direction;
		this.type = type;
		duration = 0;
		frame = -1;
		timer = 0;
		this.anim = anim;
		this.destroyCollide = destroyCollide;
	}

	public MissileLaunchComponent(boolean isActive, int interval, int type, int direction, int duration, String anim, boolean destroyCollide) {
		super(COMP_NAME, isActive);
		this.interval = interval;
		this.direction = direction;
		this.type = type;
		this.duration = duration;
		this.anim = anim;
		frame = -1;
		timer = 0;
		this.destroyCollide = destroyCollide;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		frame++;
		timer++;

		if (duration != 0 && timer > duration) {
			timer = duration;
			gobject.getScene().removeBufferList(gobject);
			return;
		}

		if ((frame % interval) == 0) {
			ColliderFrameComponent coll = gobject.getColliderFrameComponent();
			RenderTileComponent render = gobject.getRenderTileComponent();
			TransformTileComponent trans = gobject.getTransformTileComponent();
			// getSound("missile").play();
			if (coll == null || render == null || trans == null) {
				return;
			}

			GObject[][] area = coll.getColliderMatrix(trans.getZ(), CollideAbleComponent.COMP_NAME);

			switch (type) {
			case TYPE_ONE_DIR:
				fireMissileOneDir(area, trans.getX(), trans.getY(), trans.getZ());
				break;

			case TYPE_ROUND:
				direction = DOWN;
				fireMissileOneDir(area, trans.getX(), trans.getY(), trans.getZ());
				direction = UP;
				fireMissileOneDir(area, trans.getX(), trans.getY(), trans.getZ());
				direction = LEFT;
				fireMissileOneDir(area, trans.getX(), trans.getY(), trans.getZ());
				direction = RIGHT;
				fireMissileOneDir(area, trans.getX(), trans.getY(), trans.getZ());
				break;
			default:
				break;
			}
		}

		super.update();
	}

	public void resetTimer() {
		timer = 0;
	}

	private void fireMissileOneDir(GObject[][] area, int x, int y, int z) {
		int newX, newY;

		switch (direction) {
		case DOWN:
			if (getObj(area, 1, 2) == null) {
				Missile missile = (Missile) gobject.getScene().cloneGObject(';');
				MoveComponent move = missile.getMoveComponent();
				TransformTileComponent trans = missile.getTransformTileComponent();
				RenderTileComponent render = missile.getRenderTileComponent();
				move.setType(MoveComponent.TYPE_DOWN_DESTROY);
				move.setDestroyCollide(destroyCollide);
				move.setShiftAppear(true);
				trans.setRot(TransformTileComponent.ROT_S);
				render.setActiveAnimation(anim, true);
				newX = Scene.calcPositionX(x, convertShift(1, 2)[0]);
				newY = Scene.calcPositionY(y, convertShift(1, 2)[1]);
				trans.setXY(newX, newY, false);
				trans.setZ(z, false);
				// gobject.getScene().addGObject(missile);

				gobject.getScene().addBufferList(missile);
			}
			break;
		case UP:
			if (getObj(area, 1, 0) == null) {
				Missile missile = (Missile) gobject.getScene().cloneGObject(';');
				MoveComponent move = missile.getMoveComponent();
				TransformTileComponent trans = missile.getTransformTileComponent();
				RenderTileComponent render = missile.getRenderTileComponent();
				move.setType(MoveComponent.TYPE_UP_DESTROY);
				move.setDestroyCollide(destroyCollide);
				move.setShiftAppear(true);
				trans.setRot(TransformTileComponent.ROT_N);
				render.setActiveAnimation(anim, true);
				newX = Scene.calcPositionX(x, convertShift(1, 0)[0]);
				newY = Scene.calcPositionY(y, convertShift(1, 0)[1]);
				trans.setXY(newX, newY, false);
				trans.setZ(z, false);
				gobject.getScene().addBufferList(missile);
			}
			break;
		case LEFT:
			if (getObj(area, 0, 1) == null) {
				Missile missile = (Missile) gobject.getScene().cloneGObject(';');
				MoveComponent move = missile.getMoveComponent();
				TransformTileComponent trans = missile.getTransformTileComponent();
				RenderTileComponent render = missile.getRenderTileComponent();
				move.setType(MoveComponent.TYPE_LEFT_DESTROY);
				move.setDestroyCollide(destroyCollide);
				move.setShiftAppear(true);
				trans.setRot(TransformTileComponent.ROT_W);
				render.setActiveAnimation(anim, true);
				newX = Scene.calcPositionX(x, convertShift(0, 1)[0]);
				newY = Scene.calcPositionY(y, convertShift(0, 1)[1]);
				trans.setXY(newX, newY, false);
				trans.setZ(z, false);
				gobject.getScene().addBufferList(missile);
			}
			break;
		case RIGHT:
			if (getObj(area, 2, 1) == null) {
				Missile missile = (Missile) gobject.getScene().cloneGObject(';');
				MoveComponent move = missile.getMoveComponent();
				TransformTileComponent trans = missile.getTransformTileComponent();
				RenderTileComponent render = missile.getRenderTileComponent();
				move.setType(MoveComponent.TYPE_RIGHT_DESTROY);
				move.setDestroyCollide(destroyCollide);
				move.setShiftAppear(true);
				trans.setRot(TransformTileComponent.ROT_E);
				render.setActiveAnimation(anim, true);
				newX = Scene.calcPositionX(x, convertShift(2, 1)[0]);
				newY = Scene.calcPositionY(y, convertShift(2, 1)[1]);
				trans.setXY(newX, newY, false);
				trans.setZ(z, false);
				gobject.getScene().addBufferList(missile);

			}
			break;

		default:
			break;
		}
	}

}
