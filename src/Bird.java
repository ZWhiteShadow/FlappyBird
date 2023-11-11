
/**
* Bird - This class creates functionality for a bird to fly and to have collision detection with pipes.
* 
* @Author Micah Stairs, William Fiset
* March 13, 2014
**/

import acm.graphics.*;
import acm.program.*;

public class Bird extends FlappyBird{

	GRectangle birdRect, birdRect2;

	protected int downwardSpeed = 0, x, y;
	private int animationCounter = 0;
	boolean hoverDirectionUp = true;

	public Bird(int startingX, int startingY){

		this.x = startingX;
		this.y = startingY;
		// Creates a thin invisible rectangle on top of the bird as it flys for collision detection
		birdRect = new GRectangle(x, y, 25, 30);
		birdRect2 = new GRectangle(x - 35, y, 25, 30);

	}

	/** Checks for collision between the pipes and the bird **/
	public boolean pipeCollision(){

		for (GImage pipeImage : Data.pipeTopDay)
			if(birdRect.intersects( new GRectangle(pipeImage.getBounds())))
				return true;

		for (GImage pipeImage : Data.pipeBottomDay)
			if(birdRect.intersects( new GRectangle(pipeImage.getBounds())))
				return true;
		
		for (GImage pipeImage : Data.pipeMiddleDay)
			if(birdRect.intersects( new GRectangle(pipeImage.getBounds())))
				return true;

		return false;
	}

	public void updateBirdRect(int player) {
		double newWidth = this.birdSize(player)[player-1] * 1.5;
		double newHeight = this.birdSize(player)[player-1];
		birdRect.setSize(newWidth, newHeight);
		birdRect.setLocation(this.getX(), this.getY());
	}

	public double[] birdSize(int player){
		
		//scale bird size based on Y location
		double birdWidth1 = 0, birdHeight1 = 0, birdWidth2 = 0, birdHeight2 = 0;
		double scalingFactor = 7; // Adjust this value to make the bird scale slower
		double maxSize = 20.0; // Adjust this value to set the maximum size
		
		if (player == 1){
			birdHeight1 = Math.min((this.getY() / scalingFactor), maxSize);
			birdWidth1 = birdHeight1 * 1.5; // Maintain the original proportions
		} else{
			birdHeight2 = Math.min((this.getY() / scalingFactor), maxSize);
			birdWidth2 = birdHeight2 * 1.5; // Maintain the original proportions
		}
		
		// player 1
		Data.player1Down.setSize(birdWidth1, birdHeight1);
		Data.player1Flat.setSize(birdWidth1, birdHeight1);
		Data.player1Up.setSize(birdWidth1, birdHeight1);
		// player 2
		Data.player2Down.setSize(birdWidth2, birdHeight2);
		Data.player2Flat.setSize(birdWidth2, birdHeight2);
		Data.player2Up.setSize(birdWidth2, birdHeight2);

		double[] birdHeight = {birdHeight1, birdHeight2};
		return birdHeight;
	}

	/** Draw bird on screen **/
	public void draw(GraphicsProgram window, int player){
		
		// Resets the location for all bird images
		// Player 1
			Data.player1Down.setLocation(FlappyBird.BIRD_X_START, this.getY());
			Data.player1Flat.setLocation(FlappyBird.BIRD_X_START, this.getY());
			Data.player1Up.setLocation(FlappyBird.BIRD_X_START, this.getY());		
			updateBirdRect(1);
			birdSize(player);
		//Player 2
			Data.player2Down.setLocation(FlappyBird.BIRD_X_START - 35, this.getY());
			Data.player2Flat.setLocation(FlappyBird.BIRD_X_START - 35, this.getY());
			Data.player2Up.setLocation(FlappyBird.BIRD_X_START - 35, this.getY());
			updateBirdRect(2);
			birdSize(player);




		if(FlappyBird.currentMode != 2){
			
			// Proceeds to the next image in the animation
			if(this.animationCounter % 2 == 0)
				this.animateBird(this.animationCounter/2, window);
	
			this.animationCounter = (this.animationCounter + 1) % 8;
			
		}
		
	}

	/** Makes the bird move downwards **/
	public void fly(){

		// Move Flappy Bird
		this.downwardSpeed -= 1;
		this.setY(this.getY() - this.downwardSpeed );
		
 	}

	/** Makes sure that the bird doesn't go off screen **/
	public void capHeight(){
		
		// cap at top of screen
		if(this.getY() > 20)
			this.downwardSpeed = 10;

	}

	/** Animates the Flappy bird **/
	protected void animateBird(int index, GraphicsProgram window){
		
		if(index == 0){
			// Player 1
			window.add(Data.player1Flat);
			window.remove(Data.player1Up);
			// Player 2
			window.add(Data.player2Flat);
			window.remove(Data.player2Up);
		}
		else if(index == 1){
			// Player 1
			window.add(Data.player1Down);
			window.remove(Data.player1Flat);
			// Player 2
			window.add(Data.player2Down);
			window.remove(Data.player2Flat);

		}
		else if(index == 2){
			// Player 1
			window.add(Data.player1Flat);
			window.remove(Data.player1Down);
			// Player 2
			window.add(Data.player2Flat);
			window.remove(Data.player2Down);
		}
		else{
			// Player 1
			window.add(Data.player1Up);
			window.remove(Data.player1Flat);
			// Player 2
			window.add(Data.player2Up);
			window.remove(Data.player2Flat);
		}
		
	}

	public void setY(int y){ this.y = y; }
	public void setX(int x){ this.x = x; }
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }

    public void scale(double d) {
    }

}
