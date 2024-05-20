package Socket;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ClientProxy implements VirtualView{
    final PrintWriter output;

    public ClientProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }


    public void showUpdate(Integer number) {
        output.println("update");
        output.println(number);
        output.flush();
    }


    public void reportError(String details) {
        output.println("error");
        output.println(details);
        output.flush();
    }
}
