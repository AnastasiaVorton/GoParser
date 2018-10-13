import antlr.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import presentation.ParseTreeJSONMapper;

public class Main {
    public static void main(String[] args) {
        try {
            GoLexer lexer = new GoLexer(CharStreams.fromFileName("in.txt"));
            GoParser parser = new GoParser(new CommonTokenStream(lexer));

            ParseTreeJSONMapper mapper = new ParseTreeJSONMapper(true);
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(mapper, parser.sourceFile());

            System.out.println(mapper.getTreeJSONStringRepresentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
