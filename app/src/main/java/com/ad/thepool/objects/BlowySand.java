package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.DestroyAbleComponent;
import com.ad.thepool.components.EraseAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class BlowySand extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3900558882198605914L;

	public BlowySand(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('w', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 102, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		EraseAbleComponent eraseable = new EraseAbleComponent(true);
		DestroyAbleComponent destroy = new DestroyAbleComponent(true, 20, false, 0, "std");
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(eraseable);
		addComponent(destroy);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
