package Socket;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ServerProxy {
    final PrintWriter output;

    public ServerProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }

    public void connect(VirtualView client){
        output.println("connect");
        output.flush();
    }
    public void add(Integer number){
        output.println("add");
        output.println(number);
        output.flush();
    }

    public void reset(){
        output.println("reset");
        output.flush();
    }

}
