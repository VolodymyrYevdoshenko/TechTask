package com.softserve.task;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public abstract class Cache<K, V> {

	private int capacity;
	private long lifeTime;
	protected Map<K, V> cacheMap; //main map
	protected Map<K, Long> timeOfCreatingMap; //map for saving creation time of entries
	protected Map<K, Integer> counterGetAccess; //map for counting times of accessing through method get(..)

	public Cache() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					cleanup();
				}
			}
		});

		thread.setDaemon(true);
		thread.start();

	}

	  /**
     * Removing from map entries, which lifetime is over
     */
	private void cleanup() {
		ArrayList<K> deleteKey = null;

		synchronized (cacheMap) {
			deleteKey = new ArrayList<K>();
			K key = null;

			for (K k : cacheMap.keySet()) {
				key = k;
				if (currentTime() > (lifeTime + timeOfCreatingMap.get(key))) {
					deleteKey.add(key);
				}
			}
		}

		for (K key : deleteKey) {
			synchronized (cacheMap) {
				cacheMap.remove(key);
				timeOfCreatingMap.remove(key);
				counterGetAccess.remove(key);
			}

			Thread.yield();
		}
	}

	protected Long currentTime() {
		return System.currentTimeMillis();
	}

	public abstract V get(K key);

	public abstract void put(K key, V value);

	public abstract void remove(K key);

	public Set<Map.Entry<K, V>> entrySet() {
		synchronized (cacheMap) {
			return cacheMap.entrySet();
		}
	}

	public int size() {
		synchronized (cacheMap) {
			return cacheMap.size();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	protected boolean isCapacityNormal(int capacity) {
		return capacity > cacheMap.size();
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public long getLifeTime() {
		return lifeTime;
	}

	protected boolean isLifeTimeNormal(long lifeTime) {
		return lifeTime > 0;
	}

	public void setLifeTime(long lifeTime) {
		if (isLifeTimeNormal(lifeTime)) {
			this.lifeTime = lifeTime;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cacheMap == null) ? 0 : cacheMap.hashCode());
		result = prime * result + capacity;
		result = prime * result + (int) (lifeTime ^ (lifeTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cache other = (Cache) obj;
		if (cacheMap == null) {
			if (other.cacheMap != null)
				return false;
		} else if (!cacheMap.equals(other.cacheMap))
			return false;
		if (capacity != other.capacity)
			return false;
		if (lifeTime != other.lifeTime)
			return false;
		return true;
	}

}
