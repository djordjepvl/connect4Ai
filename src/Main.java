//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String board1 =
                "____r__" +
                "____r__" +
                "____y__" +
                "____ry_" +
                "____yr_" +
                "r___ryy"
                ;
        String board2 =
                "_______" +
                "__y____" +
                "_ryyy__" +
                "_ryryyr" +
                "_rrryrr" +
                "yyrrryy"
                ;
        ConnectFourGame.start(board2);
    }
}