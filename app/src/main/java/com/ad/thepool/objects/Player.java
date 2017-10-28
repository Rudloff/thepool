package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.ActivateComponent;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.CollectorComponent;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.ControlComponent;
import com.ad.thepool.components.DestroyAbleComponent;
import com.ad.thepool.components.FloaterComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.PhysicsComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerFrameComponent;
import com.ad.thepool.components.WeightComponent;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class Player extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4349958816307155282L;

	public Player(int id, TileMap tilemap) {
		super(id, true);
		DestroyAbleComponent destroyAble = new DestroyAbleComponent(true, 100, true, 50, "std");
		TransformTileComponent tiletransform = new TransformTileComponent('@', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 55, true);
		CameraComponent camera = new CameraComponent(true, CameraComponent.TYPE_FOLLOW_XY);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);
		PhysicsComponent physics = new PhysicsComponent(true, false, Component.DOWN);
		ControlComponent control = new ControlComponent(true, true);
		LaserComponent laser = new LaserComponent(true, LaserComponent.LASER_USER, "laser", false, 20, 2);
		TriggerFrameComponent triggerframe = new TriggerFrameComponent(true);
		ActivateComponent activate = new ActivateComponent(true);
		WeightComponent weight = new WeightComponent(true);
		CollectorComponent collect = new CollectorComponent(true);
		FloaterComponent floater = new FloaterComponent(true);
		LightComponent light = new LightComponent(true, 0, 4, 5, LightComponent.MODE_SINUS_UP, GraphicsWrapper.getRGB(0, 0, 0, 0), GraphicsWrapper.getRGB(255, 255, 255, 0), 4, LightComponent.MODE_SINUS_UP);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(camera);
		addComponent(collideable);
		addComponent(collideframe);
		addComponent(activate);
		addComponent(control);
		addComponent(physics);
		addComponent(laser);
		addComponent(triggerframe);
		addComponent(destroyAble);
		addComponent(weight);
		addComponent(collect);
		addComponent(floater);
		addComponent(light);

		getTagList().add("player");

		Animation animStand = new Animation(55, 55, 0, Animation.TYPE_STILL, true);
		Animation animWalkR = new Animation(58, 61, 4, Animation.TYPE_SEQ, true);
		Animation animWalkL = new Animation(48, 51, 4, Animation.TYPE_SEQ, true);
		Animation animShootU = new Animation(57, 57, 0, Animation.TYPE_STILL, true);
		Animation animShootD = new Animation(55, 55, 0, Animation.TYPE_STILL, true);
		Animation animShootL = new Animation(47, 47, 0, Animation.TYPE_STILL, true);
		Animation animShootR = new Animation(56, 56, 0, Animation.TYPE_STILL, true);
		Animation animClimb = new Animation(62, 63, 4, Animation.TYPE_SEQ, true);
		Animation animRobe = new Animation(64, 65, 4, Animation.TYPE_SEQ, true);
		Animation animStepR = new Animation(69, 72, 4, Animation.TYPE_SEQ, true);
		Animation animStepL = new Animation(73, 76, 4, Animation.TYPE_SEQ, true);
		Animation animJump = new Animation(64, 64, 4, Animation.TYPE_STILL, true);
		Animation animSwimR = new Animation(107, 108, 4, Animation.TYPE_SEQ, true);
		Animation animSwimL = new Animation(116, 117, 4, Animation.TYPE_SEQ, true);
		Animation animClimbStand = new Animation(62, 62, 4, Animation.TYPE_STILL, true);
		Animation animRobeStand = new Animation(64, 64, 4, Animation.TYPE_STILL, true);

		rendertile.getAnimationList().put("stand", animStand);
		rendertile.getAnimationList().put("walkR", animWalkR);
		rendertile.getAnimationList().put("walkL", animWalkL);
		rendertile.getAnimationList().put("shootU", animShootU);
		rendertile.getAnimationList().put("shootD", animShootD);
		rendertile.getAnimationList().put("shootL", animShootL);
		rendertile.getAnimationList().put("shootR", animShootR);
		rendertile.getAnimationList().put("climb", animClimb);
		rendertile.getAnimationList().put("robe", animRobe);
		rendertile.getAnimationList().put("stepR", animStepR);
		rendertile.getAnimationList().put("stepL", animStepL);
		rendertile.getAnimationList().put("jump", animJump);
		rendertile.getAnimationList().put("swimR", animSwimR);
		rendertile.getAnimationList().put("swimL", animSwimL);
		rendertile.getAnimationList().put("climbStand", animClimbStand);
		rendertile.getAnimationList().put("robeStand", animRobeStand);
		rendertile.setActiveAnimation("stand", true);

		tiletransform.setPriority(1);

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
