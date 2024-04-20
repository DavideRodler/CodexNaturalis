package View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class InputReaderTask implements Callable<String> {

    private BufferedReader buffer;

    public InputReaderTask() {
        this.buffer = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws Exception {
        String input;
        // wait until there is data to complete a readLine()
        while (!buffer.ready()) {
            Thread.sleep(200);
        }
        input = buffer.readLine();
        return input;
    }
}
