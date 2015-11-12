package com.softserve.task;

import java.util.LinkedHashMap;

public class ReplaceOldestStrategyCache<K, V> extends Cache<K, V> {

	private final int MIN_CAPACITY = 0;
	private final long MIN_LIFE_TIME = 0;
	private final int DEFAULT_CAPACITY = 5;
	private final int DEFAULT_LIFE_TIME = 3000;

	public ReplaceOldestStrategyCache(int capacity, long lifeTime) {

		if (capacity > MIN_CAPACITY && lifeTime > MIN_LIFE_TIME) {
			setCapacity(capacity);
			setLifeTime(lifeTime);

		} else {
			setCapacity(DEFAULT_CAPACITY);
			setLifeTime(DEFAULT_LIFE_TIME);
		}
		cacheMap = new LinkedHashMap<K, V>(getCapacity());
		timeOfCreatingMap = new LinkedHashMap<K, Long>(getCapacity());

	}

	@Override
	public V get(K key) {
		synchronized (cacheMap) {
			V cache = cacheMap.get(key);
			if (cache == null) {
				return null;
			} else {
				return cache;
			}
		}
	}

	@Override
	public void put(K key, V value) {
		synchronized (cacheMap) {
			if (isCapacityNormal(getCapacity())) {
				cacheMap.put(key, value);
			} else {
				K oldestEntryKey = oldestEntry((LinkedHashMap<K, V>) cacheMap);
				cacheMap.remove(oldestEntryKey);
				timeOfCreatingMap.remove(oldestEntryKey);
				cacheMap.put(key, value);
			}
			timeOfCreatingMap.put(key, currentTime());
		}
	}

	@Override
	public void remove(K key) {
		synchronized (cacheMap) {
			cacheMap.remove(key);
			timeOfCreatingMap.remove(key);
		}
	}

	  /**
     * Method return key of the oldest entry
     */
	private K oldestEntry(LinkedHashMap<K, V> cacheMap) {
		K key = null;

		for (K k : cacheMap.keySet()) {
			key = k;
			break;
		}

		return key;
	}

}
