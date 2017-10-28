package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class BackBrickBig extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4155808218102387865L;

	public BackBrickBig(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('A', true, Z_BACK2);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 7, true);
		addComponent(tiletransform);
		addComponent(rendertile);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
