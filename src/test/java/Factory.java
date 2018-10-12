import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;

public class Factory {


    public Lexer createLexer(String s) {
        GoLexer lexer = new GoLexer(CharStreams.fromString(s));
        return lexer;
    }

    public Parser createParser(Lexer lexer) {
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GoParser parser = new GoParser(tokens);
        return parser;
    }

    public ParseTree createTreeParser(Parser parser) {
        return null;
    }
}
