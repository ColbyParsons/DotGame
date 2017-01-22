
package recursivesquares;


public class Player {

    private int squares;
    private String name;

    public Player(String n) {// initializes the player class
        squares = 0;
        name = n;
    }
    /*Pre: a square has been completed
    Post: increases the player's square count
    Purpose:  adds a square to the number of squares that the player has completed
    */
    public void addSquare() {
        squares++;
    }
    /*Pre: non
    Post: returns the number of squares
    Purpose: to provide access to the number of squares
    */
    public int getSquare() { 
        return squares;
    }
    /*Pre: none
    Post: returns the player's name
    Purpose:  to provide access to the player's name
    */
    public String getName() {
        return name;
    }
}
