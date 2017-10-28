package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.DoorComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;

public class Door extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2044191882462826472L;

	public Door(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('X', true, Z_FRONT);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 29, true); // 68
		TriggerAbleComponent triggerAble = new TriggerAbleComponent(true);
		DoorComponent door = new DoorComponent(true, "std");
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(triggerAble);
		addComponent(door);
		addComponent(collideable);
		getTagList().add("door");
		door.setOpen(false);

		Animation animOpen = new Animation(29, 32, 10, Animation.TYPE_SEQ, true);
		Animation animClose = new Animation(29, 32, 10, Animation.TYPE_BACKSEQ, true);
		rendertile.getAnimationList().put("open", animOpen);
		rendertile.getAnimationList().put("close", animClose);

		rendertile.setActiveAnimation("close", true);
	}

}
