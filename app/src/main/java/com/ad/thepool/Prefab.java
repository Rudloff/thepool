package com.ad.thepool;

import java.io.Serializable;
import java.util.ArrayList;

public class Prefab implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5501101769020004150L;
	private ArrayList<GObject> repository = new ArrayList<GObject>();

	public ArrayList<GObject> getRepository() {
		return repository;
	}

	public void setRepository(ArrayList<GObject> repository) {
		this.repository = repository;
	}
}
