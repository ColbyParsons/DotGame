
package recursivesquares;

import java.util.ArrayList;


public class GameBoard {

    private boolean[][] lines;
    private ArrayList<Player> players = new ArrayList<>(6);
    private int current = 0;
    private int maxsquares;
    
    public GameBoard(int w, int h) {//initializes the gameboard object
        lines = new boolean[h * 2 - 1][w];
        maxsquares = (h - 1) * (w - 1);
    }
    /*Pre: a player's name is passed to the method
    Post: a player object is created and added to the arraylist of players
    Purpose: to add a player to the game
    */
    public void addPlayer(String n) {
        Player p = new Player(n);
        players.add(p);
    }
    /*Pre: the player's turn is over
    Post: the turn is passed to the next player
    Purpose: to pass the turn to the next player
    */
    public void currentPlayer() {
        if (current < players.size() - 1) {//this checks if the counter has not reached the total number of players
            current++;
        } else {//this evaluates when the last player has their turn and sets it back to the first player
            current = 0;
        }
    }

    public int getCurrent() {
        return current;
    }
    
    public void addLine(int h, int w) {
        lines[h][w] = true;
    }

    public boolean getLine(int h, int w) {
        return lines[h][w];
    }

    public String getPlayerName() {
        return players.get(current).getName();
    }
    /*Pre: a square has been completed
    Post: a square is added to the player's count
    Purpose: to add a square to a player's count
    */
    public void addSquareCount() {
        players.get(current).addSquare();
    }
    /*Pre: none
    Post: returns the number of squares that a player has
    Purpose: to allow access to the number of squares a player has
    */
    public int squareCount() {
        return players.get(current).getSquare();
    }
    /*Pre: a line is placed
    Post: an array of integers is  returned providing information on what squares have been completed or not
    Purpose: to provide the coordinates of a square after checking if one has been completed
    */
    public int[] checkSquare(int h, int w) {
        int[] a = new int[2];
        if ((h + 1) % 2 == 0 && w != lines[0].length - 1 && w != 0) {//this checks if the line is vertical and isn't on the edges of the grid
            if (lines[h][w] && lines[h - 1][w] && lines[h + 1][w] && lines[h][w + 1] && lines[h][w] && lines[h - 1][w - 1] && lines[h + 1][w - 1] && lines[h][w - 1]) {//This checks if both squares on either side of the line are completed
                a = new int[4];
                a[0] = (h + 1) / 2 + 1;
                a[1] = w + 2;
                a[2] = (h + 1) / 2 + 1;
                a[3] = w + 1;
                return a;
            }
        } else if (h % 2 == 0 && h != 0 && h != lines.length - 1) {//this checks if the line is horizontal and isn't on the edges of the grid
            if (lines[h][w] && lines[h - 1][w] && lines[h - 2][w] && lines[h - 1][w + 1] && lines[h][w] && lines[h + 1][w] && lines[h + 2][w] && lines[h + 1][w + 1]) {//This checks if both squares on either side of the line are completed
                a = new int[4];
                a[0] = h / 2 + 1;
                a[1] = w + 2;
                a[2] = h / 2 + 2;
                a[3] = w + 2;
                return a;
            }
        }
        if ((h + 1) % 2 == 0) {//this checks if the line is vertical
            if (w != lines[0].length - 1) {//this checks if the line is not on the right edge of the grid
                if (lines[h][w] && lines[h - 1][w] && lines[h + 1][w] && lines[h][w + 1]) {//this checks if a square is completed to the right of the line 
                    a[0] = (h + 1) / 2 + 1;
                    a[1] = w + 2;
                    return a;
                }
            }
            if (w != 0) {//this checks if the line is not on the left edge of the grid
                if (lines[h][w] && lines[h - 1][w - 1] && lines[h + 1][w - 1] && lines[h][w - 1]) {//this checks if a square is completed to the left of the line
                    a[0] = (h + 1) / 2 + 1;
                    a[1] = w + 1;
                    return a;
                }
            }
        } else if (h % 2 == 0) {//this checks if the line is vertical
            if (h != 0) {//this checks if the line is not on the bottom edge of the grid
                if (lines[h][w] && lines[h - 1][w] && lines[h - 2][w] && lines[h - 1][w + 1]) {//this checks if a square is completed below the line
                    a[0] = h / 2 + 1;
                    a[1] = w + 2;
                    return a;
                }
            }
            if (h != lines.length - 1) {//this checks if the line is not on the top edge of the grid
                if (lines[h][w] && lines[h + 1][w] && lines[h + 2][w] && lines[h + 1][w + 1]) {//this checks if a square is completed above the line
                    a[0] = h / 2 + 2;
                    a[1] = w + 2;
                    return a;
                }
            }
        }
        a[0] = 0;
        a[1] = 0;
        return a;
    }

    public int getMax() {
        return maxsquares;
    }
}
