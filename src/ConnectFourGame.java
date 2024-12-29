import java.util.Scanner;

public class ConnectFourGame {
    static boolean red;
    private static BoardState currentBoard = new BoardState();

    public static void start(String initialBoard) {
        red = currentBoard.set(initialBoard);
        red = !red;
        while (true) {
            currentBoard.print();
            if (currentBoard.currentPlayerWon(red)) {
                if (red) System.out.print("Red won!");
                else System.out.print("Yellow won!");
                break;
            }

            red = !red;

            if (red) System.out.print("Red's move:");
            else System.out.print("Yellow's move:");

            consolePlay();

        }
    }
    public static void start() {
        start("");
    }

    private static void consolePlay() {
        try {
            int column = Integer.parseInt((new Scanner(System.in)).nextLine());
            if (column<1 || column>7) throw new Exception();
            playAt(column);
        }
        catch (Exception e) {System.out.println("Invalid move!");}
    }

    private static void playAt(int column) {
        if (currentBoard.columnFull(column)) {
            System.out.println("Invalid move!");
            return;
        }
        currentBoard.play(column, red);
    }
}
