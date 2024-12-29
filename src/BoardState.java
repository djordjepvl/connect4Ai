public class BoardState {
    private long board = 0;
    private long mask = 0;
    int moves = 0;

    public void play(int column, boolean red) {
        int shift = (column-1)*7;
        long newTokenPos = ( ((mask >> shift) & 0b1111111) +1 ) << shift;
        mask |= newTokenPos;
        board |= newTokenPos;

        if (newTokenPos >> shift+5 == 1) mask |= newTokenPos*2;

        moves++;
    }

    public boolean columnFull(int column) {
        return ((mask >> (column-1)*7 )&0b1000000)==0b1000000;
    }
    public boolean boardFull() {
        return mask > 0xFFFFFFFFFFFFL;
    }

    public boolean currentPlayerWon(boolean red) {
        long m = board & (board >> 7 );
        if (( m & (m >> 14) ) > 0) return true;
        m = board & (board >> 1);
        if (( m & (m >> 2) ) > 0) return true;
        m = board & (board >> 6);
        if (( m & (m >> 12)) > 0) return true;
        m = board & (board >> 8);
        return (( m & (m >> 16)) > 0);
    }

    public void print(boolean red) {
        for (int i = 5; i>=0; i--) {
            for (int j = 0; j<7; j++) {
                int pos = j*7 + i;
                boolean boardAtPos = ((board >> pos)&1)==1;
                boolean maskAtPos = ((mask >> pos)&1)==1;
                if (red) {
                    if (boardAtPos) System.out.print("r");
                    else if (maskAtPos) System.out.print("y");
                    else System.out.print("_");
                }
                else {
                    if (boardAtPos) System.out.print("y");
                    else if (maskAtPos) System.out.print("r");
                    else System.out.print("_");
                }
            }
            System.out.println();
        }
    }

    public boolean set(String boardString) {
        boolean red = false;
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
}
