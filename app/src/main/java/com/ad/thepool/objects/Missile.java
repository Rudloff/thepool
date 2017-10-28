package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.DestroyCollideComponent;
import com.ad.thepool.components.FloaterComponent;
import com.ad.thepool.components.MoveComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class Missile extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8880062764970961690L;

	public Missile(int id, TileMap tilemap) {
		super(id, false);
		TransformTileComponent tiletransform = new TransformTileComponent(';', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 93, true); // 6
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		DestroyCollideComponent destroyCollide = new DestroyCollideComponent(true, 20, "std", "missile");
		ColliderFrameComponent collideFrame = new ColliderFrameComponent(true);
		MoveComponent move = new MoveComponent(true, 1, MoveComponent.TYPE_DOWN_DESTROY, 5);
		FloaterComponent floater = new FloaterComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(move);
		addComponent(collideable);
		addComponent(destroyCollide);
		addComponent(collideFrame);
		addComponent(floater);
		addComponent(collideframe);

		getTagList().add("missile");

		Animation animRot = new Animation(93, 97, 20, Animation.TYPE_LOOP, true);
		rendertile.getAnimationList().put("blow", animRot);
		Animation animRotExp = new Animation(83, 87, 10, Animation.TYPE_SEQ, true);
		rendertile.getAnimationList().put("explode", animRotExp);
		rendertile.setActiveAnimation("blow", true);

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
