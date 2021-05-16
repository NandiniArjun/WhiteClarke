package uk.wc.model;

import lombok.Data;

@Data
public class Coordinate {
	private int posX;
    private int posY;
    
	public Coordinate(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}
	
	public void incrementX(int unit) {
		this.posX = posX + unit;
	}
	
	public void incrementY(int unit) {
		this.posY = posY + unit;
	}
}
