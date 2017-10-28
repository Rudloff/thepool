package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.ClimbAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class Ladder extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451249722162648211L;

	public Ladder(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('h', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 45, true);
		ClimbAbleComponent physicsSwitch = new ClimbAbleComponent(true, true);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(physicsSwitch);
	}

}
