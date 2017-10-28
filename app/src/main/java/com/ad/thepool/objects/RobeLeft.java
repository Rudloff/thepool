package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.RobeAbleComponent;
import com.ad.thepool.components.TransformTileComponent;

public class RobeLeft extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -474231872720277873L;

	public RobeLeft(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('!', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 46, true);
		RobeAbleComponent robeable = new RobeAbleComponent(true, Component.LEFT);
		tiletransform.setRot(TransformTileComponent.ROT_E);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(robeable);
	}
}
