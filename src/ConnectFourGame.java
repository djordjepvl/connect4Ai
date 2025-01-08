import java.util.Scanner;

public class ConnectFourGame {
    static boolean red;
    private static BoardState currentBoard;

    public static String y = "\033[33mO\033[0m";
    public static String r = "\033[31mO\033[0m";
    public static String e = "\033[90m□\033[0m";

    public static void start(String initialBoard) {
        currentBoard = new BoardState();
        red = currentBoard.set(initialBoard);
        while (true) {
            currentBoard.print(red);

            if (red) {
                System.out.print("Red's move:");
                if (!consolePlay()) continue;
            }
            else {
                System.out.print("Yellow's move:");
                if (!AIPlay()) continue;
            }


            if (currentBoard.currentPlayerWon()) {
                currentBoard.print(red);
                if (red) System.out.println("Red wins!");
                else System.out.println("Yellow wins!");
                break;
            }
            else if (currentBoard.boardFull()) {
                currentBoard.print(red);
                System.out.println("Draw!");
                break;
            }

            red = !red;
            currentBoard.flip();
        }
        System.out.println("Play again?");
        System.out.print("y/n: ");
        String nl = (new Scanner(System.in)).nextLine();
        if (nl.equals("y")) start();
    }
    public static void start() {
        start("");
    }

    private static boolean consolePlay() {
        try {
            int column = Integer.parseInt((new Scanner(System.in)).nextLine());
            return playAt(column);
        }
        catch (Exception e) {
            System.out.println("Invalid move!");
            return false;
        }
    }

    private static boolean AIPlay() {
        int column = MinMax.calculateBestMove(currentBoard);
        System.out.println(column);
        return playAt(column);
    }

    private static boolean playAt(int column) {
        if (currentBoard.columnFull(column) || column<1 || column>7) {
            System.out.println("Invalid move!");
            return false;
        }
        currentBoard.play(column);
        return true;
    }
}
