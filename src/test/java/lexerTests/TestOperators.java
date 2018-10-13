package lexerTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestOperators {

    private static GoLexer lexer = new GoLexer(null);
    private static GoParser parser = new GoParser(null);

    private final int token_1 = 53;
    private final int token_2 = 55;


    @Test
    public void test1() {
        String expr = "a || b";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(2).getText();
        int type = tokens.get(2).getType();

        assertEquals(tokens.size(), 6);
        assertEquals(text, "||");
        assertEquals(type, token_1);
    }

    @Test
    public void test2() {
        String expr = "a == b";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(2).getText();
        int type = tokens.get(2).getType();

        assertEquals(tokens.size(), 6);
        assertEquals(text, "==");
        assertEquals(type, token_2);
    }

}
