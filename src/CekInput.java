import java.util.Scanner;

public class CekInput {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int m = scan.nextInt();
        int n = scan.nextInt();
        int f = scan.nextInt();
        int h = scan.nextInt();
        int t = scan.nextInt();
        int[][] grid = new int[m][n];
        for (int i = 0; i < h; i++) {
            int x = scan.nextInt() - 1;
            int y = scan.nextInt() - 1;
            grid[x][y] = 1;
        }
        for (int i = 0; i < t; i++) {
            int x = scan.nextInt() - 1;
            int y = scan.nextInt() - 1;
            grid[x][y] = 2;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    System.out.print(". ");
                }
                else if (grid[i][j] == 1) {
                    System.out.print("H ");
                }
                else {
                    System.out.print("T ");
                }
            }
            System.out.println();
        }
    }
}
