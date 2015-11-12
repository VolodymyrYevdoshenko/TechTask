package com.softserve.task;

import java.util.Map;

public class Main {

	public static void main(String[] args) {

		Cache<Integer, String> cache;
		
		System.out.println("--- REPLACE_LEAST_USED displacement strategy ---");

		cache = Factory.getCache(5, 5000, "Replace_Least_Used");

		System.out.println("Put into cache 5 items, capacity is 5: ");

		cache.put(1, "one");
		cache.put(2, "two");
		cache.put(3, "three");
		cache.put(4, "four");
		cache.put(5, "five");
		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry);
		}
		
		System.out.println("Put into cache 6th item, capacity is 5: ");
		cache.put(6, "six");

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry);
		}

		System.out.println("Get second element .");
		int key = 2;
		String value = cache.get(2);

		System.out.println("   Key = " + key + ", value = " + value);

		System.out.println("Remove sixth element.");

		key = 6;

		cache.remove(key);

		System.out.println(" > Removed item: ");
		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry);
		}

		System.out.print("Size of map is: ");
		System.out.println(cache.size());
		
		System.out.println("EntrySet:");
		System.out.println(cache.entrySet());

		System.out.println("Removing after life time is over.");

		long timelife = cache.getLifeTime();
		System.out.println("Set timelife: " + timelife + "ms");

		System.out.println(" Our entries: ");

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}

		System.out.println(" Sleep for 3000 ms. add new items: ");
		cache.setCapacity(7);
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		cache.put(7, "seven");
		cache.put(8, "eight");
		cache.put(9, "nine");

		System.out.println(" Get all entries after sleep 3000ms: ");
		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}

		System.out.println("Sleep 3000ms, older elements should be removed");
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}

		System.out.println("Sleep 5000ms, all elements should be removed");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}
		
		
		System.out.println("--- REPLACE_OLDEST displacement strategy ---");

		cache = Factory.getCache(5, 5000, "Replace_Least_Used");

		System.out.println("Put into cache 5 items, capacity is 5: ");

		cache.put(1, "one");
		cache.put(2, "two");
		cache.put(3, "three");
		cache.put(4, "four");
		cache.put(5, "five");
		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry);
		}
		
		System.out.println("Put into cache 6th item, capacity is 5: ");
		cache.put(6, "six");

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry);
		}

		System.out.println("Get second element .");
		key = 2;
		value = cache.get(2);

		System.out.println("   Key = " + key + ", value = " + value);

		System.out.println("Remove sixth element.");

		key = 6;

		cache.remove(key);

		System.out.println(" > Removed item: ");
		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry);
		}

		System.out.print("Size of map is: ");
		System.out.println(cache.size());
		
		System.out.println("EntrySet:");
		System.out.println(cache.entrySet());

		System.out.println("Removing after life time is over.");

		timelife = cache.getLifeTime();
		System.out.println("Set timelife: " + timelife + "ms");

		System.out.println(" Our entries: ");

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}

		System.out.println(" Sleep for 3000 ms. add new items: ");
		cache.setCapacity(7);
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		cache.put(7, "seven");
		cache.put(8, "eight");
		cache.put(9, "nine");

		System.out.println(" Get all entries after sleep 3000ms: ");
		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}

		System.out.println("Sleep 3000ms, older elements should be removed");
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}

		System.out.println("Sleep 5000ms, all elements should be removed");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Map.Entry entry : cache.entrySet()) {
			System.out.println("   " + entry.getKey());
		}

	}

}
