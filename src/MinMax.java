import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;

public class MinMax {

    static Long2IntOpenHashMap table = new Long2IntOpenHashMap(80000000);
    static {
        table.defaultReturnValue(42);
    }

    public static int calculateBestMove(BoardState b) {
        // create and sort list of possible moves
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
        //check if opponent has winning moves
        int opp = b.opponentWinningPos();
        if (opp != 0) {
            return opp;
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
        int bestMove = moveColumns[order[0]];

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

        //check if move is already in the table and if alpha>beta
        int maxPossibleScore = (43-b.moves)/2;
        int transPositionTableScore = table.get(b.getKey());
        if (transPositionTableScore != 42) {
            maxPossibleScore = transPositionTableScore;
        }
        if (beta > maxPossibleScore) {
            beta = maxPossibleScore;
            if (alpha >= beta) return beta;
        }

        //create and sort list of possible moves
        BoardState[] moves = new BoardState[7];
        int[] moveScores = new int[7];
        int[] moveColumns = new int[7];
        int cnt = 0;
        int[] order = new int[7];
        for (int i = 1; i < 8; i++) {
            if (b.columnFull(i)) continue;
            BoardState newBoard = b.clone();
            newBoard.play(i);

            if (newBoard.currentPlayerWon()) {
                //System.out.println(newBoard.moves);
                return (44 - newBoard.moves) / 2;
            }
            if (newBoard.boardFull()) return 0;


            moveScores[cnt] = newBoard.currentPlayerWinPosCount();

            newBoard.flip();
            moves[cnt] = newBoard;
            moveColumns[cnt] = i;

            order[cnt] = cnt++;
        }

        // check if opponent has winning moves
        int opp = b.opponentWinningPos();
        if (opp != 0) {
            b.play(opp);
            b.flip();
            int moveScore = -calculateScore(b, -beta, -alpha);
            if (moveScore > alpha) return moveScore;
            return alpha;
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

        //get best score
        for (int i = 0; i<cnt; i++) {
            int moveScore = -calculateScore(moves[order[i]], -beta, -alpha);
            //System.out.println(-moveScore);
            if (moveScore > alpha) {
                alpha = moveScore;
                if (alpha >= beta) return alpha;
            }
        }
        table.put(b.getKey(), alpha);
        return alpha;
    }
}
