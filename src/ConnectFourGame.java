import java.util.Scanner;

public class ConnectFourGame {
    static boolean red;
    private static BoardState currentBoard = new BoardState();

    public static void start(String initialBoard) {
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
