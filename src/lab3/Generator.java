package lab3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generator {

    private static int generateElement() {
        return (int) (Math.random() * 5) * -1;
    }

    void generateAll() {

        // Перебор различных размерностей
        for (int n = 15; n < 1000; n += 50) {
            // Перебор точности числа double
            for (int k = 0; k < 7; k++) {
                double[][] matrix = new double[n][n];
                for (int i = 0; i < n; i++) {
                    int sum = 0;
                    for (int j = 0; j < n; j++) {
                        if (i != j) {
                            int element = generateElement();
                            sum += element;
                            matrix[i][j] = element;
                        }
                    }
                    if (i == 0) {
                        matrix[i][i] = -sum;
                        double pow = 1;
                        for (int p = 0; p < k; p++) {
                            pow *= 0.1;
                        }
                        matrix[i][i] -= pow * (n - 1);
                    } else {
                        matrix[i][i] = -sum;
                    }
                }
                Path path = Paths.get("src/lab3/matrices/k" + k + "_n" + n + ".txt");
                try {
                    Files.createFile(path);
                } catch (IOException ignored) {
                    // do nothing
                }
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            writer.write(matrix[i][j] + " ");
                        }
                        writer.write("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
