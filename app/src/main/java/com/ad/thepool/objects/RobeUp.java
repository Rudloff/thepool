package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.RobeAbleComponent;
import com.ad.thepool.components.TransformTileComponent;

public class RobeUp extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4768641027745812344L;

	public RobeUp(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('_', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 46, true);
		RobeAbleComponent robeable = new RobeAbleComponent(true, Component.UP);
		tiletransform.setRot(TransformTileComponent.ROT_S);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(robeable);
	}
}
