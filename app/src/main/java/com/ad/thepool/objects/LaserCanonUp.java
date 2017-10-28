package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class LaserCanonUp extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5086890511791288140L;

	public LaserCanonUp(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('9', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 15, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		LaserComponent laser = new LaserComponent(true, LaserComponent.LASER_UP, "laser", false, 100, 1);
		tiletransform.setRot(TransformTileComponent.ROT_N);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(laser);

		Animation animRot = new Animation(15, 16, 10, Animation.TYPE_LOOP, true);
		rendertile.getAnimationList().put("blink", animRot);
		rendertile.setActiveAnimation("blink", true);
		tiletransform.setPriority(1);

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
