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
                "_y_____" +
                "_yyry_r" +
                "_ryyrrr" +
                "_yryryr" +
                "ryryryy"
                ;
        String board3 =
                "__yyrrr" +
                "_yrryyy" +
                "_ryyrrr" +
                "_yrryyy" +
                "_ryyrrr" +
                "_yrryyy"
                ;
        ConnectFourGame.start(board1);
    }
}