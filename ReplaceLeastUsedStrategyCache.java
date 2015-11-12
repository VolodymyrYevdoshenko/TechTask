package com.softserve.task;

import java.util.LinkedHashMap;

public class ReplaceLeastUsedStrategyCache<K, V> extends Cache<K, V> {

	private final int MIN_CAPACITY = 0;
	private final long MIN_LIFE_TIME = 0;
	private final int DEFAULT_CAPACITY = 5;
	private final int DEFAULT_LIFE_TIME = 5000;
	private final int STEP = 1;

	public ReplaceLeastUsedStrategyCache(int capacity, long lifeTime) {

		if (capacity > MIN_CAPACITY && lifeTime > MIN_LIFE_TIME) {
		setCapacity(capacity);
		setLifeTime(lifeTime);
		
		} else {
			setCapacity(DEFAULT_CAPACITY);
			setLifeTime(DEFAULT_LIFE_TIME);
		}
		cacheMap = new LinkedHashMap<K, V>(getCapacity());
		timeOfCreatingMap = new LinkedHashMap<K, Long>(getCapacity());
		counterGetAccess = new LinkedHashMap<K, Integer>(getCapacity());


	}

	@Override
	public V get(K key) {
		synchronized (cacheMap) {
			V cache = cacheMap.get(key);
			int value = counterGetAccess.get(key) + STEP;
			counterGetAccess.put(key, value);
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
				K leastUsedEntryKey = null;
				leastUsedEntryKey = leastUsedEntry((LinkedHashMap<K, Integer>) counterGetAccess);
				cacheMap.remove(leastUsedEntryKey);
				cacheMap.put(key, value);
				counterGetAccess.remove(leastUsedEntryKey);
				timeOfCreatingMap.remove(leastUsedEntryKey);
			}
			timeOfCreatingMap.put(key, currentTime());
			counterGetAccess.put(key, 0);
		}
	}

	  /**
     * Method return key of the least used entry 
     */
	private K leastUsedEntry(LinkedHashMap<K, Integer> cacheMap) {
		K key = null;
		int leastUsedValue = 0;
		K leastUsedKey = null;

		for (K k : cacheMap.keySet()) {
			key = k;
			break;
		}

		leastUsedKey = key;
		leastUsedValue = cacheMap.get(key);

		for (K k : cacheMap.keySet()) {
			key = k;

			if (leastUsedValue > cacheMap.get(key)) {
				leastUsedValue = cacheMap.get(key);
				leastUsedKey = key;
			}
		}

		return leastUsedKey;

	}

	@Override
	public void remove(K key) {
		synchronized (cacheMap) {
			cacheMap.remove(key);
			counterGetAccess.remove(key);
			timeOfCreatingMap.remove(key);
		}
	}

}
