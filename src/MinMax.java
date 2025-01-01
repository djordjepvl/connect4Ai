public class MinMax {

    public static int calculateBestMove(BoardState b) {
        int bestMove = 0;

        BoardState[] moves = new BoardState[7];
        int[] moveScores = new int[7];
        int[] moveColumns = new int[7];
        int cnt = 0;

        int[] order = new int[7];

        for (int i = 1; i<8; i++) {
            if (b.columnFull(i)) continue;
            BoardState newBoard = b.clone();
            newBoard.play(i);

            //if (newBoard.currentPlayerWon()) return i;
            moveScores[cnt] = newBoard.currentPlayerWinPosCount();

            newBoard.flip();
            moves[cnt] = newBoard;
            moveColumns[cnt] = i;

            order[cnt++] = i;
        }

        /*System.out.println();
        for (int i = 0; i<cnt; i++) {
            System.out.print(moveScores[i] + " ");
        }
        System.out.println();
        for (int i = 0; i<cnt; i++) {
            System.out.print(order[i] + " ");
        }
        System.out.println();

        for (int i = 1; i<cnt; i++) {
            int curr = order[i];

            for (int j = i-1; j>=0; j--) {
                if (curr > )
            }
        }

        for (int i = 0; i<cnt; i++) {
            System.out.print(order[i] + " ");
        } System.out.println();*/

        bestMove = order[0];
        return bestMove;
    }

    /*public static int calculateScore(BoardState b) {
        BoardState[] moves = new BoardState[7];
        int cnt = 0;
        for (int i = 1; i<8; i++) {
            if (b.columnFull(i)) continue;
            BoardState newBoard = b.clone();
            newBoard.play(i);
            newBoard.flip();
            moves[cnt++] = newBoard;
        }



        return 0;
    }*/
}
