
/**
* Bird - This class creates functionality for a bird to fly and to have collision detection with pipes.
* 
* @Author Micah Stairs, William Fiset
* March 13, 2014
**/

import acm.graphics.*;
import acm.program.*;

public class Bird extends FlappyBird{

	GRectangle birdRect;

	protected int downwardSpeed = 0, hoverCounter = 0, x, y;
	private int animationCounter = 0;
	boolean hoverDirectionUp = true;

	public Bird(int startingX, int startingY){
		
		// Creates a thin invisible rectangle on top of the bird as it flys for collision detection
		if (isNight){
			birdRect = new GRectangle(x, y , (int) Data.birdFlatDay.getWidth(), (int) Data.birdFlatDay.getHeight());
		}
		else{
			birdRect = new GRectangle(x, y , (int) Data.birdFlatNight.getWidth(), (int) Data.birdFlatNight.getHeight());
		}

		this.x = startingX;
		this.y = startingY;

	}

	/** Checks for collision between the pipes and the bird **/
	public boolean pipeCollision(){

		for (GImage pipeImage : Data.pipeTopDay)
			if(birdRect.intersects( new GRectangle(pipeImage.getBounds())))
				return true;

		for (GImage pipeImage : Data.pipeBottomDay)
			if(birdRect.intersects( new GRectangle(pipeImage.getBounds())))
				return true;

		return false;
	}

	/** Draw bird on screen **/
	public void draw(GraphicsProgram window){
		
		// Resets the location for all bird images
		//Day
		Data.birdDownDay.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		Data.birdFlatDay.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		Data.birdUpDay.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);		
		//Night
		Data.birdDownNight.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		Data.birdFlatNight.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		Data.birdUpNight.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);

		//scale bird size based on Y location
		//day
		Data.birdDownDay.setSize((getY() / 10) * 1.5, (getY() / 10));
		Data.birdFlatDay.setSize((getY() / 10)* 1.5, (getY() / 10));
		Data.birdUpDay.setSize((getY() / 10) * 1.5, (getY() / 10));
		//night
		Data.birdDownNight.setSize((getY() / 10) * 1.5, (getY() / 10));
		Data.birdFlatNight.setSize((getY() / 10)* 1.5, (getY() / 10));
		Data.birdUpNight.setSize((getY() / 10) * 1.5, (getY() / 10));
		
		if(FlappyBird.currentMode != 2){
			
			// Proceeds to the next image in the animation
			if(animationCounter % 2 == 0)
				animateBird(animationCounter/2, window);
	
			animationCounter = (animationCounter + 1) % 8;
			
		}
		
	}

	/** Makes the bird move downwards **/
	public void fly(){

		hoverBird();

		// Move Flappy Bird
		downwardSpeed -= 1*FlappyBird.currentMode;
		this.setY( this.getY() - downwardSpeed );

		// Set the new location of the invisible rectangle under the bird, used for collision detection
		birdRect.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		
 	}

	/** Makes sure that the bird doesn't go off screen **/
	public void capHeight(){
		
		if(getY() > 50)
			downwardSpeed = 10;

	}

	/** Animates the Flappy bird **/
	protected void animateBird(int index, GraphicsProgram window){
		
		if(index == 0){
			//Day
			window.add(Data.birdFlatDay);
			window.remove(Data.birdUpDay);
			//Night
			window.add(Data.birdFlatNight);
			window.remove(Data.birdUpNight);
		}
		else if(index == 1){
			//Day
			window.add(Data.birdDownDay);
			window.remove(Data.birdFlatDay);
			//Night
			window.add(Data.birdDownNight);
			window.remove(Data.birdFlatNight);

		}
		else if(index == 2){
			//Day
			window.add(Data.birdFlatDay);
			window.remove(Data.birdDownDay);
			//Night
			window.add(Data.birdFlatNight);
			window.remove(Data.birdDownNight);
		}
		else{
			//Day
			window.add(Data.birdUpDay);
			window.remove(Data.birdFlatDay);
			//Night
			window.add(Data.birdUpNight);
			window.remove(Data.birdFlatNight);
		}
		
	}

	/** Makes the bird appear to hover up and down **/
	protected void hoverBird(){

		if(hoverDirectionUp){
			hoverCounter--;
			if(hoverCounter == -1)
				hoverDirectionUp = false;
		}
		else{
			hoverCounter++;
			if(hoverCounter == 1)
				hoverDirectionUp = true;
		}

	}

	public void setY(int y){ this.y = y; }
	public void setX(int x){ this.x = x; }
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }

    public void scale(double d) {
    }

}
