package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.GravitySwitchComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class GravityPadDown extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4082028749006664775L;

	public GravityPadDown(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('2', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 44, true);
		GravitySwitchComponent gravswitch = new GravitySwitchComponent(true, Component.DOWN);
		tiletransform.setRot(TransformTileComponent.ROT_S);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(gravswitch);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
