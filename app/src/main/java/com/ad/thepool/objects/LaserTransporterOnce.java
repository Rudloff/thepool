package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.LaserTransportAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.WeightComponent;

public class LaserTransporterOnce extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 548355017197383461L;

	public LaserTransporterOnce(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('u', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 42, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		LaserTransportAbleComponent lasertrans = new LaserTransportAbleComponent(true, true);
		WeightComponent weight = new WeightComponent(true);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(lasertrans);
		addComponent(weight);
		Animation animOn = new Animation(42, 42, 0, Animation.TYPE_STILL, true);
		Animation animOff = new Animation(114, 114, 0, Animation.TYPE_STILL, true);
		rendertile.getAnimationList().put("on", animOn);
		rendertile.getAnimationList().put("off", animOff);
		rendertile.setActiveAnimation("on", true);

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
