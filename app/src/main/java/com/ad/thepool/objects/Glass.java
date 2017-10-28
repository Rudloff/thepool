package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.MirrorComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class Glass extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4831325477336497637L;

	public Glass(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('g', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 33, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		MirrorComponent mirror = new MirrorComponent(true, MirrorComponent.TURN_THROUGH);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(mirror);

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
