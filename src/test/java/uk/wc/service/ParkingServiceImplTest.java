package uk.wc.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import uk.wc.model.Coordinate;

public class ParkingServiceImplTest {

	 private ParkingService parkingService;

	@Before
	public void setup() {
		parkingService = new ParkingServiceImpl();
	}
	
	@Test
	public void test_parking_commands_in_a_valid_format_and_happy_path() {
		Coordinate coordinate = parkingService.findParkingSlot("5,5:FLFLFFRFFF");
		assertNotNull(coordinate);
		assertEquals(coordinate.getPosX(), 4);
		assertEquals(coordinate.getPosY(), 1);
	}
	
	@Test
	public void test_parking_commands_in_a_invalid_format() {
		
		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			parkingService.findParkingSlot("5,5");
	    });

	    String expectedMessage = "An Invalid input is passed. Input should be in the format -> 5,5:FLLR.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void test_parking_commands_exception_when_coordinates_exceeds_limit() {
		
		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			parkingService.findParkingSlot("5,5:FFFFFFFFFFFFFFFFFFFFFFFFFF");
	    });

	    String expectedMessage = "Parking slot has crossed the limit.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
}
