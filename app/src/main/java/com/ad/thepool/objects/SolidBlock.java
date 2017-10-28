package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.ActivateComponent;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.PhysicsComponent;
import com.ad.thepool.components.PushPullAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerFrameComponent;
import com.ad.thepool.components.WeightComponent;

public class SolidBlock extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2770033532905079066L;

	public SolidBlock(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('f', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 91, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);
		PhysicsComponent physics = new PhysicsComponent(true, true, Component.DOWN);
		PushPullAbleComponent pushable = new PushPullAbleComponent(true, 5, true, "std");
		ActivateComponent activate = new ActivateComponent(true);
		WeightComponent weight = new WeightComponent(true);
		TriggerFrameComponent triggerframe = new TriggerFrameComponent(true);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(collideframe);
		addComponent(physics);
		addComponent(pushable);
		addComponent(activate);
		addComponent(weight);
		addComponent(triggerframe);

		physics.setRollLeftRight(false);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
