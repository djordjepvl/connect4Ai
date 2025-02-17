public class BoardState {
    private long board = 0;
    private long mask = 0;
    int moves = 0;


    public void play(int column) {
        int shift = (column-1)*7;
        long newTokenPos = ( ((mask >> shift) & 0b1111111) +1 ) << shift;
        mask |= newTokenPos;
        board |= newTokenPos;

        if (newTokenPos >> shift+5 == 1) mask |= newTokenPos*2;

        moves++;
    }

    public int opponentWinningPos() {
        flip();
        long a = (mask&0xFDFBF7EFDFBFL) + 0x40810204081L;

        int winMove = 0;

        if (currentPlayerWon(a & 0x3FL)) winMove += 1;
        if (currentPlayerWon(a & 0xFC0000000000L)) {
            winMove += 7;
            if (winMove > 7) return winMove;
        }
        if (currentPlayerWon(a & 0x1F80L)) {
            winMove += 2;
            if (winMove > 7) return winMove;
        }
        if (currentPlayerWon(a & 0x1F800000000L)) {
            winMove += 6;
            if (winMove > 7) return winMove;
        }
        if (currentPlayerWon(a & 0xFC000L)) {
            winMove += 3;
            if (winMove > 7) return winMove;
        }
        if (currentPlayerWon(a & 0x3F0000000L)) {
            winMove += 5;
            if (winMove > 7) return winMove;
        }
        if (currentPlayerWon(a & 0x7E00000L)) {
            winMove += 4;
            if (winMove > 7) return winMove;
        }
        flip();
        return winMove;
    }
    public boolean currentPlayerWon() {
        //horizontal
        long a = board & (board >> 7 );
        if (( a & (a >> 14) ) > 0) return true;

        // vertical
        a = board & (board >> 1);
        if (( a & (a >> 2) ) > 0) return true;

        // diagonal \
        a = board & (board >> 6);
        if (( a & (a >> 12)) > 0) return true;

        // diagonal /
        a = board & (board >> 8);
        return (( a & (a >> 16)) > 0);
    }
    public boolean currentPlayerWon(long additionalMoves) {
        long board = this.board | additionalMoves;
        //horizontal
        long a = board & (board >> 7 );
        if (( a & (a >> 14) ) > 0) return true;

        // vertical
        a = board & (board >> 1);
        if (( a & (a >> 2) ) > 0) return true;

        // diagonal \
        a = board & (board >> 6);
        if (( a & (a >> 12)) > 0) return true;

        // diagonal /
        a = board & (board >> 8);
        return (( a & (a >> 16)) > 0);
    }
    // not 100% accurate, used for heuristic
    public int currentPlayerWinPosCount() {
        long m = ~mask;

        //horizontal
        long a = board&(board << 7)&(board >> 7);
        long b = m&((a <<14)|(a >> 14));

        //vertical
        a = board&(board << 1)&(board >> 1);
        b |= m&((a <<2)|(a >> 2));

        // diagonal /
        a = board&(board << 8)&(board >> 8);
        b |= m&((a <<16)|(a >> 16));

        // diagonal \
        a = board&(board << 6)&(board >> 6);
        b |= m&((a <<12)|(a >> 12));

        b &= 0xFDFBF7EFDFBFL;

        int cnt;
        for (cnt = 0; b>0; cnt++) b &= b-1;
        return cnt;
    }

    public boolean columnFull(int column) {
        return ((mask >> (column-1)*7 )&0b1000000)==0b1000000;
    }
    public boolean boardFull() {
        return moves > 41;
    }

    public void print(boolean red) {
        String r = ConnectFourGame.r;
        String y = ConnectFourGame.y;
        String e = ConnectFourGame.e;

        System.out.println();
        for (int i = 5; i>=0; i--) {
            for (int j = 0; j<7; j++) {
                int pos = j*7 + i;
                boolean boardAtPos = ((board >> pos)&1)==1;
                boolean maskAtPos = ((mask >> pos)&1)==1;
                if (red) {
                    if (boardAtPos) System.out.print(r);
                    else if (maskAtPos) System.out.print(y);
                    else System.out.print(e);
                }
                else {
                    if (boardAtPos) System.out.print(y);
                    else if (maskAtPos) System.out.print(r);
                    else System.out.print(e);
                }
            }
            System.out.println();
        }
    }
    public boolean set(String boardString) {
        boolean red = true;
        board = 0;
        mask = 0;
        if (boardString.length() !=42) return true;

        for (int i = 0; i < 42; i++) {
            char c = boardString.charAt(i);
            long val = (long) Math.pow(2, i%7*7 +5 - i/7);

            if (boardString.charAt(i) == 'r') {
                board += val;
                mask += val;
                if (i<7) mask += val*2;
                moves++;
            }
            else if (boardString.charAt(i) == 'y') {
                mask += val;
                if (i<7) mask += val*2;
                moves++;
            }
        }
        if (moves%2==1) {
            red = !red;
            flip();
        }
        return red;
    }

    public void flip() {
        board = board^(mask&0xFDFBF7EFDFBFL);
    }

    public BoardState clone() {
        BoardState b = new BoardState();
        b.board = this.board;
        b.mask = this.mask;
        b.moves = this.moves;
        return b;
    }

    public void printBits() {
        printBits(board);
    }
    private void printBits(long a) {
        System.out.println();
        for (int i = 6; i>=0; i--) {
            for (int j = 0; j<7; j++) {
                int pos = j*7 + i;
                boolean b = ((a >> pos)&1)==1;
                System.out.print(b? 1:0);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public long getKey() {
        long reverseBoard = ((board & 0x3FL) << 42) | ((board & 0x1F80L) << 28) | ((board & 0xFC000L) << 14) | (board & 0x7E00000L)
                | ((board & 0x3F0000000L) >> 14) | ((board & 0x1F800000000L) >> 28) | ((board & 0xFC0000000000L) >> 42);

        if (reverseBoard > board) return reverseBoard;
        return board;
    }

    public long getBoard() {
        return board;
    }

    public long getMask() {
        return mask;
    }
}
