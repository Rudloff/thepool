package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.DestroyAbleComponent;
import com.ad.thepool.components.DestroyCollideComponent;
import com.ad.thepool.components.FloaterComponent;
import com.ad.thepool.components.MoveComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class BlowyLeftRightMovingTrap extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2262261906915743392L;

	public BlowyLeftRightMovingTrap(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('M', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 77, true); // 6
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		DestroyCollideComponent destroyCollide = new DestroyCollideComponent(true, 5, "std", "trap");
		ColliderFrameComponent collideFrame = new ColliderFrameComponent(true);
		DestroyAbleComponent destroy = new DestroyAbleComponent(true, 50, false, 0, "std");
		MoveComponent move = new MoveComponent(true, 4, MoveComponent.TYPE_LEFTRIGHT_MOVE);
		FloaterComponent floater = new FloaterComponent(true);
		ColliderFrameComponent collideframe = new ColliderFrameComponent(true);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(destroyCollide);
		addComponent(collideFrame);
		addComponent(move);
		addComponent(destroy);
		addComponent(floater);
		addComponent(collideframe);

		Animation animRot = new Animation(77, 82, 16, Animation.TYPE_LOOP, true);
		rendertile.getAnimationList().put("rotate", animRot);
		rendertile.setActiveAnimation("rotate", true);

		getTagList().add("trap");
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
