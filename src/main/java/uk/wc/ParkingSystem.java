package uk.wc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import uk.wc.model.Coordinate;
import uk.wc.service.ParkingService;

/**
 * Spring Boot application to derive the parking slot
 *
 */
@Slf4j
@SpringBootApplication
public class ParkingSystem implements ApplicationRunner
{
	@Value("${parking.input}")
    private String pInput;
	
	@Autowired
	private ParkingService parkingService;
	
    public static void main( String[] args )
    {
    	SpringApplication.run(ParkingSystem.class, args);
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception
    {
    	Coordinate parkingPosition;
    	try {
    		parkingPosition = parkingService.findParkingSlot(pInput);
    		log.info("Parking Slot, X: {}, Y: {}", parkingPosition.getPosX(), parkingPosition.getPosY());
    	} catch(IllegalArgumentException iae) {
    		log.error(iae.getMessage());
    	}
    }
}
