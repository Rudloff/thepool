package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class Brick extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9019043554493631698L;

	public Brick(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('a', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 114, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
