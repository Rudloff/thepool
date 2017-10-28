package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class LaserGlow extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5206743693065918106L;

	public LaserGlow(int id, TileMap tilemap) {
		super(id, false);
		TransformTileComponent tiletransform = new TransformTileComponent('*', true, Z_FRONT2);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 0, true);
		LightComponent light = new LightComponent(true, 3, GraphicsWrapper.getRGB(170, 100, 0, 32));

		addComponent(light);
		addComponent(tiletransform);
		addComponent(rendertile);
		getTagList().add("glow");
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
