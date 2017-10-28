package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class BackTorch extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3645472604225507929L;

	public BackTorch(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('Q', true, Z_BACK2);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 8, true);
		LightComponent light = new LightComponent(true, 1, 2, 1, LightComponent.MODE_SINUS, GraphicsWrapper.getRGB(255, 40, 0, 0), GraphicsWrapper.getRGB(255, 228, 11, 0), 1, LightComponent.MODE_RANDOM);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(light);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
