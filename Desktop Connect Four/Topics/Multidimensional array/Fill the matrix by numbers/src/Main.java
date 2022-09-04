import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        var dimention = scanner.nextInt();
        int matrix[][] = new int[dimention][dimention];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else if (i > j) {
                    matrix[i][j] = i - j;
                } else {
                    matrix[i][j] = j - i;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}