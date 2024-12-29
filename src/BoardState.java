public class BoardState {
    private long board = 0;
    private long mask = 0;

    public void play(int column, boolean red) {
        int shift = (column-1)*7;
        long newTokenPos = ( ((mask >> shift) & 0b1111111) +1 ) << shift;
        mask |= newTokenPos;
        if (red) board |= newTokenPos;

        if (newTokenPos >> shift+5 == 1) mask |= newTokenPos*2;
    }

    public boolean columnFull(int column) {
        return ((mask >> (column-1)*7 )&0b1000000)==0b1000000;
    }

    public boolean currentPlayerWon(boolean red) {
        long pos;
        if (red) pos = board;
        else pos = board^(mask&0xFDFBF7EFDFBFL);

        long m = pos & (pos >> 7 );
        if (( m & (m >> 14) ) > 0) return true;
        m = pos & (pos >> 1);
        if (( m & (m >> 2) ) > 0) return true;
        m = pos & (pos >> 6);
        if (( m & (m >> 12)) > 0) return true;
        m = pos & (pos >> 8);
        return (( m & (m >> 16)) > 0);

    }

    public void print() {
        for (int i = 5; i>=0; i--) {
            for (int j = 0; j<7; j++) {
                int pos = j*7 + i;
                boolean boardAtPos = ((board >> pos)&1)==1;
                boolean maskAtPos = ((mask >> pos)&1)==1;
                if (boardAtPos) System.out.print("r"); //red
                else if (maskAtPos) System.out.print("y"); //yellow
                else System.out.print("_");
            }
            System.out.println();
        }
    }

    public boolean set(String boardString) {
        boolean red = true;
        board = 0;
        mask = 0;
        if (boardString.length() !=42) return red;

        for (int i = 0; i < 42; i++) {
            char c = boardString.charAt(i);
            long val = (long) Math.pow(2, i%7*7 +5 - i/7);

            if (boardString.charAt(i) == 'r') {
                board += val;
                mask += val;
                red = !red;
                if (i<7) mask += val*2;
            }
            else if (boardString.charAt(i) == 'y') {
                mask += val;
                red = !red;
                if (i<7) mask += val*2;
            }
        }
        return red;
    }
}
