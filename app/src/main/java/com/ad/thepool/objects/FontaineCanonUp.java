package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.FontaineComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;

public class FontaineCanonUp extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8045040153745363160L;

	public FontaineCanonUp(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent(']', true, Z_MAIN);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 14, true);
		CollideAbleComponent collideable = new CollideAbleComponent(true);
		FontaineComponent fontaine = new FontaineComponent(true, FontaineComponent.FONTAINE_UP, "std", "fontaine", 1);
		tiletransform.setRot(TransformTileComponent.ROT_N);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(collideable);
		addComponent(fontaine);

		tiletransform.setPriority(1);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
