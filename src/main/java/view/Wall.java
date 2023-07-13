package view;

/**
 * Many choices for user to paint the wall(at least 8 option)
 * 
 * @author Huy Nhat Giang
 * 
 */

public class Wall {
    
    /**
     * private Object instance (apply Singleton pattern)
     */
    private static Wall instance;

	/**
	 * wall option in num type
	 */
	private int a;

	/**
	 * private constructor of class(apply Singleton pattern)
	 */
	private Wall() {
		super();
	}
	
	/**
	 * other class can access to Object instance
	 * @return instance of Object
	 */
	public static Wall getInstance(){
        if(instance == null){
            synchronized(Wall.class){
                if(instance == null){
                   instance = new Wall();
                }
            }
        }
        return instance;
     }

	/**
	 * getColor method to get the input choice of wall color
	 * @param color wall color 
	 */
     public void getColor(int color) {
		  int cl = 0;
		  cl= color;
		  setInt(cl);  
   }
     
     /**
      * Setter for wall color
     * @param a wall option
     */
    public void setInt(int a) {
 		 this.a = a;
      }
     
     /**
      * Getter for wall color
     * @return wall option
     */
    public int getInt() {
		return a;  	 
     }
   
}
