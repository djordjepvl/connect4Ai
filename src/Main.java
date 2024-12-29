//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String defaultBoard =
                "____r__" +
                "____r__" +
                "____y__" +
                "____ry_" +
                "____yr_" +
                "r___ryy"
                ;

        ConnectFourGame.start(defaultBoard);
    }
}