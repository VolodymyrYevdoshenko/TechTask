package com.softserve.task;

public class Factory {

	public static Cache getCache(int capacity, long lifeTime, String strategy){
		
		switch(strategy){
		case "Replace_Oldest": 
			return new ReplaceOldestStrategyCache(capacity, lifeTime); 
		case "Replace_Least_Used":
			return new ReplaceLeastUsedStrategyCache(capacity, lifeTime);
		default: 
			return null;
		
		}
	}
}
