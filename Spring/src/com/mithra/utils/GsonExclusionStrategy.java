package com.mithra.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


public class GsonExclusionStrategy implements ExclusionStrategy {

	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	public boolean shouldSkipField(FieldAttributes f) {
		return (f.getName().equalsIgnoreCase("book") || f.getName().equalsIgnoreCase("jdoDetachedState"));
	}

}
