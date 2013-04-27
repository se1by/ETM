package com.se1by.ETM.util;

import org.newdawn.slick.geom.Vector2f;

public class Vector2i {
	
	Vector2f vec;
	public Vector2i(){
		vec = new Vector2f();
	}
	public Vector2i(int x, int y){
		vec = new Vector2f(x, y);
	}
	
	public int getX(){
		return (int) vec.getX();
	}
	
	public int getY(){
		return (int) vec.getY();
	}
	
	public void addX(int x){
		vec.x += x;
	}
	
	public void addY(int y){
		vec.y += y;
	}
	
	public Vector2i multiply(int x, int y){
		vec.x *= x;
		vec.y *= y;
		return this;
	}
	
	public Vector2i add(Vector2i vec2){
		vec.add(vec2.asVector2f());
		return this;
	}
	
	public Vector2f asVector2f(){
		return vec;
	}
	

}
