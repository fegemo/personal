package com.guidetogalaxy.merchanteer.util;

/**
 * An immutable utility class that groups two objects together.
 * 
 * @author flávio coutinho
 *
 * @param <X> the first object type.
 * @param <Y> the second object type.
 */
public class Tuple<X, Y> {
	private final X x;
	private final Y y;
	
	/**
	 * Creates a tuple with the provided objects. They can be retrieved, but not set to
	 * point to other objects later.
	 * 
	 * @param x the first object.
	 * @param y the second object.
	 */
	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the first object.
	 * @return the first object.
	 */
	public X getX() {
		return x;
	}
	
	/**
	 * Returns the second object.
	 * @return the second object.
	 */
	public Y getY() {
		return y;
	}
}
