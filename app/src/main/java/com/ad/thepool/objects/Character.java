package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.ActivateComponent;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CollectorComponent;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.ControlComponent;
import com.ad.thepool.components.DestroyAbleComponent;
import com.ad.thepool.components.FloaterComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.PathMoveComponent;
import com.ad.thepool.components.PhysicsComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerFrameComponent;
import com.ad.thepool.components.WeightComponent;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class Character extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9169668333979493411L;

	public Character(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('q', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 55, true); // as
																						// player
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);
		PhysicsComponent physics = new PhysicsComponent(true, false, Component.DOWN);
		ControlComponent control = new ControlComponent(true, false);
		TriggerFrameComponent triggerframe = new TriggerFrameComponent(true);
		PathMoveComponent pathmove = new PathMoveComponent(true, true);
		ActivateComponent activate = new ActivateComponent(true);
		WeightComponent weight = new WeightComponent(true);
		CollectorComponent collect = new CollectorComponent(true);
		FloaterComponent floater = new FloaterComponent(true);
		LaserComponent laser = new LaserComponent(true, LaserComponent.LASER_CHARACTER, "laser", false, 20, 2);
		DestroyAbleComponent destroyAble = new DestroyAbleComponent(true, 100, true, 50, "std");
		LightComponent light = new LightComponent(true, 3, GraphicsWrapper.getRGB(255, 255, 255, 0));

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(collideframe);
		addComponent(control);
		addComponent(physics);
		addComponent(triggerframe);
		addComponent(pathmove);
		addComponent(activate);
		addComponent(weight);
		addComponent(floater);
		addComponent(collect);
		addComponent(laser);
		addComponent(destroyAble);
		addComponent(light);
		getTagList().add("character");

		Animation animStand = new Animation(132, 132, 0, Animation.TYPE_STILL, true);
		Animation animWalkR = new Animation(135, 138, 4, Animation.TYPE_SEQ, true);
		Animation animWalkL = new Animation(125, 128, 4, Animation.TYPE_SEQ, true);
		Animation animShootU = new Animation(134, 134, 0, Animation.TYPE_STILL, true);
		Animation animShootD = new Animation(132, 132, 0, Animation.TYPE_STILL, true);
		Animation animShootL = new Animation(124, 124, 0, Animation.TYPE_STILL, true);
		Animation animShootR = new Animation(133, 133, 0, Animation.TYPE_STILL, true);
		Animation animClimb = new Animation(139, 140, 4, Animation.TYPE_SEQ, true);
		Animation animRobe = new Animation(141, 142, 4, Animation.TYPE_SEQ, true);
		Animation animStepR = new Animation(146, 149, 4, Animation.TYPE_SEQ, true);
		Animation animStepL = new Animation(150, 153, 4, Animation.TYPE_SEQ, true);
		Animation animJump = new Animation(141, 141, 4, Animation.TYPE_STILL, true);
		Animation animSwimR = new Animation(129, 130, 4, Animation.TYPE_SEQ, true);
		Animation animSwimL = new Animation(122, 123, 4, Animation.TYPE_SEQ, true);
		Animation animClimbStand = new Animation(139, 139, 4, Animation.TYPE_STILL, true);
		Animation animRobeStand = new Animation(141, 141, 4, Animation.TYPE_STILL, true);

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
