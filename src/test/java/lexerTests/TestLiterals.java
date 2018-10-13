package lexerTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLiterals {

    private static GoLexer lexer = new GoLexer(null);
    private static GoParser parser = new GoParser(null);

    int token_1 = lexer.INT_LIT;
    int token_2 = lexer.FLOAT_LIT;
    int token_3 = lexer.IMAGINARY_LIT;
    int token_4 = lexer.RUNE_LIT;
    int token_5 = lexer.STRING_LIT;

    @Test
    public void testInt1() {
        String expr = "132";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();

        assertEquals(tokens.size(), 2);
        assertEquals(text, "132");
        assertEquals(type, token_1);
    }

    @Test
    public void testInt2() {
        String expr = "07";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();

        assertEquals(tokens.size(), 2);
        assertEquals(text, "07");
        assertEquals(type, token_1);
    }

    @Test
    public void testInt3() {
        String expr = "0xF4";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();

        assertEquals(tokens.size(), 2);
        assertEquals(text, "0xF4");
        assertEquals(type, token_1);
    }

    @Test
    public void testFloat1() {
        String expr = "132.51";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();

        assertEquals(tokens.size(), 2);
        assertEquals(text, "132.51");
        assertEquals(type, token_2);
    }

    @Test
    public void testImaginary1() {
        String expr = "5i";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();

        assertEquals(tokens.size(), 2);
        assertEquals(text, "5i");
        assertEquals(type, token_3);
    }

    @Test
    public void testRune1() {
        String expr = "'a'";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();

        assertEquals(tokens.size(), 2);
        assertEquals(text, "'a'");
        assertEquals(type, token_4);
    }

    @Test
    public void testString1() {
        String expr = "`a string literal`";
        lexer.setInputStream(CharStreams.fromString(expr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setTokenStream(tokens);
        ParseTree tree = parser.sourceFile();

        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();

        assertEquals(tokens.size(), 2);
        assertEquals(text, "`a string literal`");
        assertEquals(type, token_5);
    }
}
