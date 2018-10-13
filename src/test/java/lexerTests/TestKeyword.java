package lexerTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestKeyword {

    private static GoLexer lexer = new GoLexer(null);
    private static GoParser parser = new GoParser(null);

    private final int token_identifier = lexer.IDENTIFIER;
    private final int token_keyword = 32;

    @Test
    public void test1() {
        String expr = "break";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText(); // token text
        int type = tokens.get(0).getType(); // token type (int)

        assertEquals(tokens.size(), 2);
        assertEquals(text, "break");
        assertEquals(type, token_keyword);
    }

    @Test
    public void test2() {
        String expr2 = "ifif";
        lexer.setInputStream(CharStreams.fromString(expr2));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText(); // token text
        int type = tokens.get(0).getType(); // token type (int)

        assertEquals(tokens.size(), 2);
        assertEquals(text, "ifif");
        assertEquals(type, token_identifier);
    }

}
