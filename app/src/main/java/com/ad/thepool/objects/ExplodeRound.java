package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.DestroyCollideComponent;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.MissileLaunchComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class ExplodeRound extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5523958604591688656L;

	public ExplodeRound(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('G', true, Z_FRONT2);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 23, true); // 6
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		DestroyCollideComponent destroyCollide = new DestroyCollideComponent(true, 1, "std", null);
		ColliderFrameComponent collideFrame = new ColliderFrameComponent(true);
		MissileLaunchComponent missileLaunch = new MissileLaunchComponent(true, 2, MissileLaunchComponent.TYPE_ROUND, Component.UP, 5, "explode", true);
		LightComponent light = new LightComponent(true, 0, 10, 30, LightComponent.MODE_SINUS_UP, GraphicsWrapper.getRGB(255, 0, 0, 0), GraphicsWrapper.getRGB(0, 0, 0, 0), 30, LightComponent.MODE_SINUS_UP);

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(destroyCollide);
		addComponent(collideFrame);
		addComponent(missileLaunch);
		addComponent(light);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
