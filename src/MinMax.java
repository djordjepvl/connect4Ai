import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;

public class MinMax {

    static Long2IntOpenHashMap table = new Long2IntOpenHashMap(8500000);
    static {
        table.defaultReturnValue(42);
    }

    public static int calculateBestMove(BoardState b) {
        int bestMove;

        BoardState[] moves = new BoardState[7];
        int[] moveScores = new int[7];
        int[] moveColumns = new int[7];
        int cnt = 0;

        int[] order = new int[7];

        for (int i = 1; i<8; i++) {
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
            int curr = order[i];

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
        bestMove = moveColumns[order[0]];

        int alpha = -42;
        int beta = 42;

        for (int i = 0; i<cnt; i++) {
            int moveScore = -calculateScore(moves[order[i]],-beta, -alpha);
            System.out.println(-moveScore);
            if (moveScore > alpha) {
                alpha = moveScore;
                bestMove = moveColumns[order[i]];
                if (alpha >= beta) break;
            }
        }
        System.out.println(alpha);
        return bestMove;
    }

    public static int calculateScore(BoardState b, int alpha, int beta) {

        BoardState[] moves = new BoardState[7];
        int[] moveScores = new int[7];
        int[] moveColumns = new int[7];
        int cnt = 0;

        int[] order = new int[7];

        for (int i = 1; i<8; i++) {
            if (b.columnFull(i)) continue;
            BoardState newBoard = b.clone();
            newBoard.play(i);

            if (newBoard.currentPlayerWon()) {
                //System.out.println(newBoard.moves);
                return (44 - newBoard.moves)/2;
            }
            if (newBoard.boardFull()) return 0;

            moveScores[cnt] = newBoard.currentPlayerWinPosCount();

            newBoard.flip();
            moves[cnt] = newBoard;
            moveColumns[cnt] = i;

            order[cnt] = cnt++;
        }

        int maxPossibleScore = (43-b.moves)/2;
        Integer transPositionTableScore = table.get(b.board);
        if (transPositionTableScore != 42) maxPossibleScore = transPositionTableScore;
        if (beta > maxPossibleScore) {
            beta = maxPossibleScore;
            if (alpha >= beta) return beta;
        }


        // change order based on heuristic using insertion sort
        for (int i = 1; i<cnt; i++) {
            int curr = order[i];

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


        for (int i = 0; i<cnt; i++) {
            int moveScore = -calculateScore(moves[order[i]], -beta, -alpha);
            //System.out.println(-moveScore);
            if (moveScore > alpha) {
                alpha = moveScore;
                if (alpha >= beta) return alpha;
            }
        }
        table.put(b.board, alpha);
        return alpha;
    }
}
