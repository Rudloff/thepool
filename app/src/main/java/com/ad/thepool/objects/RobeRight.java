package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.RobeAbleComponent;
import com.ad.thepool.components.TransformTileComponent;

public class RobeRight extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3118054094787789876L;

	public RobeRight(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('|', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 46, true);
		RobeAbleComponent robeable = new RobeAbleComponent(true, Component.RIGHT);
		tiletransform.setRot(TransformTileComponent.ROT_W);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(robeable);
	}
}
