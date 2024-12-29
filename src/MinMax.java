public class MinMax {

    public static int calculateBestMove(BoardState b) {

        if (!b.columnFull(1)) return 1;
        if (!b.columnFull(2)) return 2;
        if (!b.columnFull(3)) return 3;
        if (!b.columnFull(4)) return 4;
        if (!b.columnFull(5)) return 5;
        if (!b.columnFull(6)) return 6;
        return 7;
    }
}
