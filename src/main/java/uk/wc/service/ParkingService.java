package uk.wc.service;

import uk.wc.model.Coordinate;

public interface ParkingService {

	/**
     * @param pInput the instructions for car movement
     * @return the coordinate of the car park slot
     */
    Coordinate findParkingSlot(String pInput);
}
