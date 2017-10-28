package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;

public class Exit extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7643408976081577364L;

	public Exit(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('x', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 66, true); // 68
		TriggerAbleComponent triggerAble = new TriggerAbleComponent(true);
		ExitComponent exit = new ExitComponent(true, "std", true);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(triggerAble);
		addComponent(exit);
		getTagList().add("exit");

		Animation animOpen = new Animation(66, 68, 10, Animation.TYPE_BACKSEQ, true);
		Animation animClose = new Animation(66, 68, 10, Animation.TYPE_SEQ, true);
		rendertile.getAnimationList().put("open", animOpen);
		rendertile.getAnimationList().put("close", animClose);

		rendertile.setActiveAnimation("open", true);
	}

}
