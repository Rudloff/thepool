package com.ad.thepool.components;

import java.util.HashMap;
import java.util.Iterator;

public class CollectorComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3141773976424350809L;

	public static final int COMP_NAME = 35;

	public HashMap<String, Integer> counters;

	public CollectorComponent(boolean isActive) {
		super(COMP_NAME, isActive);
		counters = new HashMap<String, Integer>();
	}

	public void incCounter(String counterKey) {
		if (counters.get(counterKey) == null) {
			counters.put(counterKey, 1);
		} else {
			counters.put(counterKey, counters.get(counterKey) + 1);
		}
	}

	public HashMap<String, Integer> getCounters() {
		return counters;
	}

	public void setCounters(HashMap<String, Integer> counters) {
		this.counters = counters;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		CollectorComponent coll = (CollectorComponent) super.clone();

		HashMap<String, Integer> newCounters = new HashMap<String, Integer>();

		for (Iterator<String> iterator = counters.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Integer val = counters.get(key);
			newCounters.put(key, new Integer(val));
		}
		coll.setCounters(newCounters);

		return coll;
	}

}
