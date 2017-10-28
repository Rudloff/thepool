package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.DestroyCollideComponent;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class Laser extends GObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5376046392172663052L;

	public Laser(int id, TileMap tilemap) {
		super(id, false);
		TransformTileComponent tiletransform = new TransformTileComponent('l', true, Z_FRONT);
		// RenderTileComponent rendertile = new RenderTileComponent(tilemap,3,
		// true);
		ColliderFrameComponent collframe = new ColliderFrameComponent(true);
		DestroyCollideComponent destroyCollide = new DestroyCollideComponent(true, 100, "std", "laser");
		LightComponent light = new LightComponent(true, 3, GraphicsWrapper.getRGB(128, 100, 0, 32));

		addComponent(light);
		addComponent(tiletransform);
		// addComponent(rendertile);
		addComponent(destroyCollide);
		addComponent(collframe);
		getTagList().add("laser");
		// setPaintOverlay(true);
	}
}
