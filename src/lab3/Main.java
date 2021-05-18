package lab3;

public class Main {
    public static void main(String[] args) {
        ProfileMatrix matrix = new ProfileMatrix();
        matrix.readFromFile(15, 1);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.println();
        }
        /*if (args == null || args.length != 2) {
            System.err.println("Format: program input.file output.file");
            System.exit(1);
        }
        try (Scanner sc = new Scanner(Files.newBufferedReader(Paths.get(args[0])))) {
            try (BufferedWriter out = Files.newBufferedWriter(Paths.get(args[1]))) {
                int n = sc.nextInt();
                double[][] doubles = new double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        doubles[i][j] = sc.nextDouble();
                    }
                }

                double[] b = new double[n];
                for (int i = 0; i < n; i++) {
                    b[i] = sc.nextDouble();
                }

                ProfileMatrix matrix = new ProfileMatrix(doubles);
                double[] res = matrix.solveByLU(b);

                for (int i = 0; i < n; i++) {
                    out.write(res[i] + " ");
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }*/
    }
}
