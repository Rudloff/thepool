package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.PortalComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class Portal extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1768305070466237846L;

	public Portal(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('o', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 38, true);
		PortalComponent portal = new PortalComponent(true, 0);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(portal);
		getTagList().add("portal");

		Animation animRot = new Animation(38, 41, 5, Animation.TYPE_LOOP, true);
		rendertile.getAnimationList().put("rotate", animRot);
		rendertile.setActiveAnimation("rotate", true);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
