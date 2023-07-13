package view;


import controller.GameFlow;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Object painting of game
 * @author Huy Nhat Giang-modified
 * add option for color on wall
 *
 */

public class GraphicObject extends Rectangle {
	
    /**
     * color option chose by number
     */
    private int color;
    
    /**
     * Setter of color option
     * @param color color option user chose
     */
    public void setColor(int color) {
    	this.color = color;
    }
    
    /**
     * Getter of color option
     * @return color option
     */
    public int getColor() {
    	return color;
    }
    
    public GraphicObject() {}
    /**
     * Painting all objects of game
     * @param obj Object of game
     */
    public GraphicObject(GameObject obj) {
        Color color = null;
        int cl;
        switch (obj) {
            case WALL: //paint Wall          	
            	Wall wall = Wall.getInstance();
            	cl= wall.getInt();
            	if(cl == 1) {
                   color = Color.BLACK;
                  }
            	else if(cl == 2) {
            		color = Color.RED;
            	  }
            	else if(cl == 3) {
            		color = Color.BLUE;
            	  }
            	else if(cl == 4) {
            		color = Color.GREEN;
            	  }
            	else if(cl == 5) {
            		color = Color.YELLOW;
            	  }
            	else if(cl == 6) {
            		color = Color.ORANGE;
            	  }
            	else if(cl == 7) {
            		color = Color.CYAN;
            	  }
            	else if(cl == 8) {
            		color = Color.GRAY;
            	  }
                break;

            case CRATE: //paint Create
                color = Color.ORANGE;
                break;

            case DIAMOND: //paint Diamond
                color = Color.DEEPSKYBLUE;

                // TODO: fix memory leak.
                if (GameFlow.isDebugActive()) {
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.2);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                    ft.play();
                }

                break;
            
            case KEEPER: //paint Keeper
            	color = Color.RED;
                break;

            case FLOOR:// paint Floor
                color = Color.WHITE;	
                break;

            case CRATE_ON_DIAMOND: //paint Create Goal
                color = Color.DARKCYAN;
                break;

            default:
                String message = "Error in Level constructor. Object not recognized.";
                GameFlow.logger.severe(message);
                throw new AssertionError(message);
        }
        
        this.setFill(color);
        this.setHeight(30);
        this.setWidth(30);

        if (obj != GameObject.WALL) {
            this.setArcHeight(50);
            this.setArcWidth(50);
        }

        if (GameFlow.isDebugActive()) {
            this.setStroke(Color.RED);
            this.setStrokeWidth(0.25);
        }
    }
    

}
