public class MinMax {

    public static byte calculateBestMove(BoardState b) {
        byte bestMove;

        BoardState[] moves = new BoardState[7];
        byte[] moveScores = new byte[7];
        byte[] moveColumns = new byte[7];
        byte cnt = 0;

        byte[] order = new byte[7];

        for (byte i = 1; i<8; i++) {
            if (b.columnFull(i)) continue;
            BoardState newBoard = b.clone();
            newBoard.play(i);

            if (newBoard.boardFull() || newBoard.currentPlayerWon()) return i;
            moveScores[cnt] = newBoard.currentPlayerWinPosCount();

            newBoard.flip();
            moves[cnt] = newBoard;
            moveColumns[cnt] = i;

            order[cnt] = cnt++;
        }

        // change order based on heuristic using insertion sort
        for (int i = 1; i<cnt; i++) {
            byte curr = order[i];

            int j = i-1;
            while (j>=0) {
                if (moveScores[curr] < moveScores[order[j]] ) break;
                if (moveScores[curr] == moveScores[order[j]] &&
                        Math.abs(3 - moveColumns[curr]) > Math.abs(4 - moveColumns[order[j]])) break;
                order[j+1] = order[j];
                j--;
            }
            order[j+1] = curr;
        }
        bestMove = order[0];


        int max = 0;
        for (byte i = 0; i<cnt; i++) {
            int moveScore = calculateScore(moves[order[i]]);
            if (moveScore > max) max = moveScore;
        }
        return bestMove;
    }

    public static byte calculateScore(BoardState b) {




        return 0;
    }
}
