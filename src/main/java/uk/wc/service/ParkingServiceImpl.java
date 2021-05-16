package uk.wc.service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import uk.wc.model.Coordinate;
import uk.wc.utils.Directions;

@Service
public class ParkingServiceImpl implements ParkingService {
	
	Directions direction = Directions.NORTH;
	
	String pInputFormat = "([0-9]+),([0-9]+):([FLR]+)";
	
	Pattern pattern = Pattern.compile(pInputFormat);

	@Override
    public Coordinate findParkingSlot(String pInput) {
		Coordinate coordinate;
		
    	if(isValidParkingInput(pInput)) {
    		
    		coordinate = getInitialCoordinates(pInput);
    		List<Character> movements = getMovementInstructions(pInput);
    		
    		for(Character move : movements) {
    			if(move.equals('F')) {
    				coordinate = moveForward(coordinate, direction);
    			} else if(move.equals('L')) {
    				direction = changeDirection(direction, -1);
    			} else {// 'R' as only F|L|R should be given
    				direction = changeDirection(direction, 1);
    			}
    		}
    		
    		if(coordinate.getPosX() > Directions.MAX_GRID_SIZE || coordinate.getPosY() > Directions.MAX_GRID_SIZE)
    			throw new IllegalArgumentException("Parking slot has crossed the limit."); 
    	} else {
    		throw new IllegalArgumentException("An Invalid input is passed. Input should be in the format -> 5,5:FLLR.");
    	}
    	
		return coordinate;
	}	
	
    private boolean isValidParkingInput(String pInput) {
		Matcher matcher = pattern.matcher(pInput);
		return matcher.matches();
	}
    
    private Coordinate getInitialCoordinates(String pInput) {
    	Matcher matcher = pattern.matcher(pInput);
    	int posX = 0, posY = 0;
    	
    	if(matcher.matches()) {
    		posX = Integer.parseInt(matcher.group(1));
    		posY = Integer.parseInt(matcher.group(2));
    	}
    	return new Coordinate(posX, posY);
    	
    }
    
    private List<Character> getMovementInstructions(String pInput) {
    	Matcher matcher = pattern.matcher(pInput);
    	
    	if(matcher.matches()) {
    		return (matcher.group(3)).chars()
    	  			  .mapToObj(move -> (char)move)
    	  			  .collect(Collectors.toList());
    	}
    	return Collections.emptyList();   	
    }
    
    private Coordinate moveForward(Coordinate coordinate, Directions direction) {
    	
    	if(direction.equals(Directions.NORTH)) {
    		coordinate.incrementX(1);    		
    	} else if(direction.equals(Directions.SOUTH)) {
    		coordinate.incrementX(-1);
    	} else if(direction.equals(Directions.WEST)) {
    		coordinate.incrementY(-1);
    	} else if(direction.equals(Directions.EAST)) {
    		coordinate.incrementY(1);
    	} else {
    		throw new IllegalArgumentException("Invalid direction found.");
    	}
    	return coordinate;
    }
    
    private Directions changeDirection(Directions direction, int turn) {
    	
    	switch (direction) {
        	case NORTH:
        		return turn > 0 ? Directions.EAST : Directions.WEST;
        	case EAST:
        		return turn > 0 ? Directions.SOUTH : Directions.NORTH;
        	case SOUTH:
        		return turn > 0 ? Directions.WEST : Directions.EAST;
        	case WEST:
        		return turn > 0 ? Directions.NORTH : Directions.SOUTH;
        	default:
        		throw new IllegalArgumentException("Invalid turn.");
    	}
    }
}
