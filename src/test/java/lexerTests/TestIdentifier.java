package lexerTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIdentifier {

    private static GoLexer lexer = new GoLexer(null);
    private static GoParser parser = new GoParser(null);

    private final int token_id = lexer.IDENTIFIER;


    @Test
    public void test1() {
        String expr = "lol_kek_cheburek";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText(); // token text
        int type = tokens.get(0).getType(); // token type (int)

        assertEquals(tokens.size(), 2);
        assertEquals(text, "lol_kek_cheburek");
        assertEquals(type, token_id);
    }

    @Test
    public void test2() {
        String expr2 = "ident_with_number_2";
        lexer.setInputStream(CharStreams.fromString(expr2));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText(); // token text
        int type = tokens.get(0).getType(); // token type (int)

        assertEquals(tokens.size(), 2);
        assertEquals(text, "ident_with_number_2");
        assertEquals(type, token_id);
    }

    @Test
    public void test3() {
        String expr3 = "2_ident_with_number";
        lexer.setInputStream(CharStreams.fromString(expr3));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText(); // token text
        int type = tokens.get(0).getType(); // token type (int)

        assertEquals(tokens.size(), 3);
        assertEquals(text, "2");
    }

}
