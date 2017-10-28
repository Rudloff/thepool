package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.WaterComponent;

public class WaterDown extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7475960998587387987L;

	public WaterDown(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('&', true, Z_BACK2);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 17, true);
		WaterComponent water = new WaterComponent(true, Component.DOWN);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(water);
		tiletransform.setRot(TransformTileComponent.ROT_E);
		getTagList().add("water");

		Animation animRot = new Animation(17, 20, 2, Animation.TYPE_LOOP, true);
		rendertile.getAnimationList().put("float", animRot);
		rendertile.setActiveAnimation("float", true);

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
