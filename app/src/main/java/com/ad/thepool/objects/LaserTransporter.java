package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.LaserTransportAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerFrameComponent;
import com.ad.thepool.components.WeightComponent;

public class LaserTransporter extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 819299766954582339L;

	public LaserTransporter(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('t', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 37, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		LaserTransportAbleComponent lasertrans = new LaserTransportAbleComponent(true, false);
		WeightComponent weight = new WeightComponent(true);
		TriggerFrameComponent triggerframe = new TriggerFrameComponent(true);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(lasertrans);
		addComponent(weight);
		addComponent(triggerframe);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
