package lab01;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) {
        OutputStream out = new BufferedOutputStream(System.out);
        try {
            out.write(("Hello, Egor & Yaroslav!").getBytes());
        } catch (IOException ignored) {
            // do nothing
        }
    }
}
