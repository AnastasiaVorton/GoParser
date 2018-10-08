import antlr.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {
    public static void main(String[] args) {
        GoLexer lexer = new GoLexer(CharStreams.fromString("hello world"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GoParser parser = new GoParser(tokens);
    }
}
