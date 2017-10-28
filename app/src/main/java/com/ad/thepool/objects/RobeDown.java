package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.RobeAbleComponent;
import com.ad.thepool.components.TransformTileComponent;

public class RobeDown extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6727335155798278331L;

	public RobeDown(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('-', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 46, true);
		RobeAbleComponent robeable = new RobeAbleComponent(true, Component.DOWN);
		tiletransform.setRot(TransformTileComponent.ROT_N);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(robeable);
	}
}
