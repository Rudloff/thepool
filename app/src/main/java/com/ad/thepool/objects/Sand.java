package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.EraseAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class Sand extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3170000304296213330L;

	public Sand(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('s', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 90, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		EraseAbleComponent eraseable = new EraseAbleComponent(true);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(eraseable);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
