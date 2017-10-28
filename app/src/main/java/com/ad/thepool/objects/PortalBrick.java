package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.PortalActiveComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class PortalBrick extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7273663578710308058L;

	public PortalBrick(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('c', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 112, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		PortalActiveComponent portalA = new PortalActiveComponent(true);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(portalA);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
