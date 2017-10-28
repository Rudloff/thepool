package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.FloaterComponent;
import com.ad.thepool.components.PushPullAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerFrameComponent;
import com.ad.thepool.components.WeightComponent;

public class PushAbleBox extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3201947262548215096L;

	public PushAbleBox(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('d', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 11, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		PushPullAbleComponent pushpullable = new PushPullAbleComponent(true, 5, true, "std");
		WeightComponent weight = new WeightComponent(true);
		TriggerFrameComponent triggerframe = new TriggerFrameComponent(true);
		FloaterComponent floater = new FloaterComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(pushpullable);
		addComponent(weight);
		addComponent(triggerframe);
		addComponent(floater);
		addComponent(collideframe);
		Animation pull = new Animation(22, 22, 0, Animation.TYPE_STILL, true);
		Animation push = new Animation(11, 11, 0, Animation.TYPE_STILL, true);

		rendertile.getAnimationList().put("pull", pull);
		rendertile.getAnimationList().put("push", push);
		rendertile.setActiveAnimation("push", true);

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
