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

public class PullAbleBox extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 316612699461777191L;

	public PullAbleBox(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('e', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 22, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		PushPullAbleComponent pushpullable = new PushPullAbleComponent(true, 5, false, "std");
		WeightComponent weight = new WeightComponent(true);
		TriggerFrameComponent triggerframe = new TriggerFrameComponent(true);
		FloaterComponent floater = new FloaterComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);
		addComponent(collideframe);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(pushpullable);
		addComponent(weight);
		addComponent(triggerframe);
		addComponent(floater);

		Animation pull = new Animation(22, 22, 0, Animation.TYPE_STILL, true);
		Animation push = new Animation(11, 11, 0, Animation.TYPE_STILL, true);

		rendertile.getAnimationList().put("pull", pull);
		rendertile.getAnimationList().put("push", push);
		rendertile.setActiveAnimation("pull", true);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
