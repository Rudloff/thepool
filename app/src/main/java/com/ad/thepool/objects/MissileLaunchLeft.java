package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.DestroyCollideComponent;
import com.ad.thepool.components.MissileLaunchComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class MissileLaunchLeft extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5127047122422165336L;

	public MissileLaunchLeft(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('D', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 104, true); // 6
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		DestroyCollideComponent destroyCollide = new DestroyCollideComponent(true, 5, "std", "missile");
		ColliderFrameComponent collideFrame = new ColliderFrameComponent(true);
		MissileLaunchComponent missileLaunch = new MissileLaunchComponent(true, 8, MissileLaunchComponent.TYPE_ONE_DIR, Component.LEFT, "blow", true);
		tiletransform.setRot(TransformTileComponent.ROT_W);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(destroyCollide);
		addComponent(collideFrame);
		addComponent(missileLaunch);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
