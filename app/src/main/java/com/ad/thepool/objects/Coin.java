package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollectAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.FloaterComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;

public class Coin extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3536814370221642056L;

	public Coin(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('#', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 12, true);
		TriggerAbleComponent triggerAble = new TriggerAbleComponent(true);
		CollectAbleComponent collable = new CollectAbleComponent(true, "std");
		FloaterComponent floater = new FloaterComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(triggerAble);
		addComponent(collable);
		addComponent(floater);
		addComponent(collideframe);

		getTagList().add("coin");
	}

}
