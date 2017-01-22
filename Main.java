
package recursivesquares;
/*Name: Colby Parsons
Teacher's name: Ms. Hideg
Purpose: to simulate a game of dots and boxes
Date: June 1st 2016
*/
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static GameBoard g;
    public static int counter = 0;
    public static int width = 0;
    public static int height = 0;
    public static int num = 0;
    public static Color[] c = {StdDraw.BLUE, StdDraw.GREEN, StdDraw.MAGENTA, StdDraw.RED, StdDraw.ORANGE, StdDraw.GRAY};

    public static void main(String[] args) {
        start();
    }
    /*Pre: the program is run and height and width is entered
    Post: the grid is drawn
    Purpose: to draw the grid based on the width and height
    */
    public static void setCanvas(int w, int h) {
        StdDraw.setCanvasSize(w * 100, h * 100);
        StdDraw.setXscale(0, w + 1);
        StdDraw.setYscale(0, h + 1);
        StdDraw.setPenRadius(0.02);
        for (int i = 1; i < w + 1; i++) {//this loop draws the points
            for (int j = 1; j < h + 1; j++) {
                StdDraw.point(i, j);
            }
        }
        Font font = new Font("Arial", Font.BOLD, 22);
        StdDraw.setFont(font);
        for (int x = 0; x < h; x++) {//this loop adds the number labels
            StdDraw.text(0, x + 1, x + 1 + "");
        }
        String[] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int y = 0; y < w; y++) {//this loop adds the letter labels
            StdDraw.text(y + 1, h + 1, alpha[y]);
        }
    }
    /*Pre: The method is called when the progra starts
    Post: none
    Purpose: to run the game
    */
    public static void start() {
        Scanner input = new Scanner(System.in);
        System.out.println("What is the height of the grid? (Integer between 2 and 9)");
        while (height < 2 || height > 9) {//this evaluates until the user has entered a valid grid height
            try {
                height = input.nextInt();
            } catch (InputMismatchException e) {//this catch evaluates if the input entered is not an integer
                input.next();
            }
            if (height < 2 || height > 9) {//this checks if the input was valid and if not displays a message
                System.out.println("Please enter a valid value.");
                height = 0;
            }
        }
        System.out.println("What is the width of the grid? (Integer between 2 and 26)");
        while (width < 2 || width > 26) {//this evaluates until the user has entered a valid grid width
            try {//this try statement attempts to take the next integer entered
                width = input.nextInt();
            } catch (InputMismatchException e) {//this catch evaluates if the input entered is not an integer
                input.next();
            }
            if (width < 2 || width > 26) {//this checks if the input was valid and if not displays a message
                System.out.println("Please enter a valid value.");
                width = 0;
            }
        }
        g = new GameBoard(width, height);//this intializes the gameboard with the entered height and width
        setCanvas(width, height);
        addPlayers();
        String move;
        while (counter != g.getMax()) {//this evaluates until the number of completed squares equals the maximum number of squares
            Scanner input2 = new Scanner(System.in);
            System.out.println(g.getPlayerName() + " it is your turn, please enter your move.");
            move = "";
            while (!checkInput(move)) {//this runs until a valid move is entered
                move = input.next();
                if (!checkInput(move)) {//if the move is not valid this displays a message to user indicating that
                    System.out.println("Please enter a valid input.");
                }
            }
            addLine(move);
            drawLine(move);
            if (!square(move)) {//this evaluates if the user doesn't complete a square on their turn allowing users to go again after completing a square
                g.currentPlayer();
            }
        }
        gameOver();
    }
    /*Pre: The method is called
    Post: players are added to an array of player objects within the gameboard class
    Purpose: to add the players to the game
    */
    public static void addPlayers() {
        Scanner input = new Scanner(System.in);
        String[] players = new String[6];
        System.out.println("How many players are there? (Integer between 2 and 6)");
        while (num < 2 || num > 6) {//this runs until a valid number of players has been entered
            try {//this try statement attempts to take the next integer entered
                num = input.nextInt();
            } catch (InputMismatchException e) {//this catch evaluates if the input entered is not an integer
                input.next();
            }
            if (num < 2 || num > 6) {//this checks if the input was valid and if not displays a message
                System.out.println("Please enter a valid value.");
                num = 0;
            }
        }
        Scanner input2 = new Scanner(System.in);
        boolean check, check2 = false;
        for (int i = 0; i < num; i++) {//this runs until all the players are added
            System.out.println("Please enter the player's name.");
            String name = "";
            check = true;
            while (check) {
                name = input2.nextLine();
                for (String b : players) {//this iterate over the array of previously entered player names
                    if (name.toLowerCase().equals(b)) {//this checks if the name has already been used
                        check2 = true;
                    }
                }
                if (check2) {//this evaluates if the user did not enter a unique name
                    System.out.println("Please enter a unique name.");
                    check2 = false;
                } else {//this evaluates if the user enters a valid unique name
                    check = false;
                }
            }
            g.addPlayer(name);
            players[i] = name.toLowerCase();
        }
    }
    /*Pre: The user enters a move
    Post: the move is changed into numeric form
    Purpose: to interpret the input
    */
    public static int[] parseInput(String in) {
        in = in.toLowerCase();
        int first = (int) in.charAt(0) - 48;//these statements turn the input into 4 integers that reflect the two coordinates
        int second = (int) in.charAt(1) - 96;
        int third = (int) in.charAt(2) - 48;
        int fourth = (int) in.charAt(3) - 96;
        int[] inputs = {first, second, third, fourth};
        return inputs;
    }
    /*Pre: the user enters a move
    Post: a boolean is returned that states if the input is logically valid or not
    Purpose: to check for valid inputs
    */
    public static boolean checkInput(String in) {
        if (in.length() != 4) {//this checks that the inpit is appropriate length
            return false;
        }
        int[] inputs = parseInput(in);
        if (inputs[0] > height || inputs[0] < 0 || inputs[2] > height || inputs[2] < 0 || inputs[1] > width || inputs[1] < 0 || inputs[3] > width || inputs[3] < 0) {//this checks that the inputs are within the bounds of the grid
            return false;
        } else if ((inputs[0] == inputs[2] && (inputs[1] == inputs[3] + 1 || inputs[1] == inputs[3] - 1)) || (inputs[1] == inputs[3] && (inputs[0] == inputs[2] + 1 || inputs[0] == inputs[2] - 1))) {//this checks if the two specified coordinates are beside each other
            return !checkLine(in);
        }
        return false;
    }
    /*Pre: the user enters a move
    Post: the indicies of the line are returned
    Purpose: the move is translated into the notation used for the 2d array of lines and to return the indicies 
    */
    public static int[] getLine(String in) {
        int[] inputs = parseInput(in);
        int[] a = new int[2];
        if (inputs[0] == inputs[2]) {//this checks if the line is horizontal
            if (inputs[1] > inputs[3]) {//this checks which point is closer to the right of the grid
                a[0] = (inputs[0] - 1) * 2;
                a[1] = (inputs[3] - 1);
                return a;
            } else if (inputs[1] < inputs[3]) {//this checks which point is closer to the right of the grid
                a[0] = (inputs[0] - 1) * 2;
                a[1] = (inputs[1] - 1);
                return a;
            }
        } else if (inputs[1] == inputs[3]) {//this checks if the line is horizontal
            if (inputs[0] > inputs[2]) {//this checks which point is closer to the top of the grid
                a[0] = (inputs[2] * 2 - 1);
                a[1] = (inputs[1] - 1);
                return a;
            } else if (inputs[0] < inputs[2]) {//this checks which point is closer to the top of the grid
                a[0] = (inputs[0] * 2 - 1);
                a[1] = (inputs[1] - 1);
                return a;
            }
        }
        return a;
    }
    /*Pre: The user enters a move
    Post: the method returns a boolean stating whether that line already exists
    Purpose: to check if the line has already been drawn
    */
    public static boolean checkLine(String in) {
        int[] indices = getLine(in);
        return g.getLine(indices[0], indices[1]);
    }
    /*Pre: The user enters a move
    Post: the line is added to the array of lines
    Purpose: to add the line to the array
    */
    public static void addLine(String in) {
        int[] indices = getLine(in);
        g.addLine(indices[0], indices[1]);
    }
    /*Pre: The user enters a move
    Post: the line gets drawn on the grid
    Purpose: to draw the line
    */
    public static void drawLine(String in) {
        int[] inputs = parseInput(in);
        StdDraw.setPenColor(c[g.getCurrent()]);
        StdDraw.line(inputs[1], inputs[0], inputs[3], inputs[2]);
    }
    /*Pre: a line has been drawn
    Post: the square is drawn is one exists and the method returns true if a square was drawn or false if one wasn't
    Purpose: to determine if a square was completed and draw it
    */
    public static boolean square(String in) {
        int[] indices = getLine(in);
        int[] a = g.checkSquare(indices[0], indices[1]);
        if (a[0] != 0) {//this checks if the checksquare function returne a valid square
            if (a.length == 4) {//this evaluates if two squares need to be drawn
                StdDraw.filledSquare(a[1] - 0.5, a[0] - 0.5, 0.5);
                StdDraw.filledSquare(a[3] - 0.5, a[2] - 0.5, 0.5);
                counter += 2;
                g.addSquareCount();
                g.addSquareCount();
                return true;
            }
            StdDraw.filledSquare(a[1] - 0.5, a[0] - 0.5, 0.5);//this evaluates if one square is drawn
            counter++;
            g.addSquareCount();
            return true;
        }
        return false;
    }
    /*Pre: the method is called becuase the game is over
    Post: the program ends
    Purpose: to end the program and to display the winners
    */
    public static void gameOver() {
        int highest = 0;
        ArrayList<String> winners = new ArrayList<>();
        for (int i = 0; i < num; i++) {//This iterates over the users
            System.out.println(g.getPlayerName() + " you finished with " + g.squareCount() + " squares!");
            if (highest == g.squareCount()) {//this if else evaluates which players had the most squares and stores it in the winners arraylist 
                winners.add(g.getPlayerName());
            } else if (highest < g.squareCount()) {
                winners.clear();
                winners.add(g.getPlayerName());
                highest = g.squareCount();
            }
            g.currentPlayer();
        }
        if (winners.size() > 1) {//This evaluates if the game was a tie
            System.out.print("Congratulations ");
            for (String l : winners) {//this prints out the players who tied
                System.out.print(l + ", ");
            }
            System.out.println("you tied for the win!");
        } else {//this evaluates if there is one winner and prints out the winner
            System.out.println("Congratulations " + winners.get(0) + " you win!");
        }
        System.exit(0);

    }
}
