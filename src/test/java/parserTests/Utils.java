package parserTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

class Utils {
    static GoLexer lexer = new GoLexer(null);
    static GoParser parser = new GoParser(null);

    static void setInput(String input) {
        lexer.setInputStream(CharStreams.fromString(input));
        parser.setTokenStream(new CommonTokenStream(lexer));
    }
}
