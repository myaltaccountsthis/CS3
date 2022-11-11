import java.util.Arrays;

public class BoggleAdvanced {
    private static final String[] BOGGLE_1992 = {
            "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
            "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
            "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
            "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
    };

    private final BoggleSolver solver = new BoggleSolver("./data/dictionary-yawl.txt");

    public int getPoints(BoggleBoard board) {
        int points = 0;
        for (String s : solver.getAllValidWords(board))
            points += solver.scoreOf(s);
        return points;
    }

    public int recursiveBoard(char[][] chars, int i) {
        // TODO does not work, takes too long
        if (i == 16)
            return getPoints(new BoggleBoard(chars));
        int r = i / 4, c = i % 4;
        char temp = chars[r][c];
        char[] letters = BOGGLE_1992[i].toCharArray();
        int highest = 0;
        for (int j = 0; j < 6; j++) {
            chars[r][c] = letters[j];
            int points = recursiveBoard(chars, i + 1);
            if (points > highest) {
                highest = points;
            }
        }
        chars[r][c] = temp;
        return highest;
    }

    public static void main(String[] args) {
        BoggleAdvanced advanced = new BoggleAdvanced();

        char[][] chars = new char[4][4];
        System.out.println(advanced.recursiveBoard(chars, 0));
    }
}
