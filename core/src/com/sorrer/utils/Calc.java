package com.sorrer.utils;

public class Calc {
	/**
	 * Value between target -+ dis
	 * @param target
	 * @param value
	 * @param dis
	 * @return
	 */
	public static boolean within(float target, float value, float dis){
		if(value < dis + target && value > target - dis){
			return true;
		}
		return false;
	}
	/**
	 * Value between min max;
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean withinM(float value, float min, float max){
		if(value < min && value> max ) return true;
		return false; 
	}
}
