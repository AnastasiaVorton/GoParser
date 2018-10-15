import antlr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import presentation.ParseTreeJSONMapper;
import presentation.RecognitionErrorListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));

            GoLexer lexer = new GoLexer(CharStreams.fromFileName("in.txt"));
            GoParser parser = new GoParser(new CommonTokenStream(lexer));

            RecognitionErrorListener listener = new RecognitionErrorListener();
            parser.addErrorListener(listener);

            ParseTreeJSONMapper mapper = new ParseTreeJSONMapper(true);
            ParseTreeWalker walker = new ParseTreeWalker();
            GoParser.SourceFileContext tree = parser.sourceFile();

            walker.walk(mapper, tree);
            String jsonTree = mapper.getTreeJSONStringRepresentation();
            writer.write(jsonTree);

            for (RecognitionException e : listener.getErrors()) {
                writer.write("\n" + e.getMessage());
            }

            writer.close();
        } catch (IOException e) {
            // I/O exceptions
            e.printStackTrace();
        }
    }
}
