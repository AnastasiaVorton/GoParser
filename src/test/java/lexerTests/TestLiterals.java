package lexerTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shared.Assert.assertToken;

public class TestLiterals {

    private static void assertIntLiteral(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, GoLexer.INT_LIT, expr);
    }

    private static void assertFloatLiteral(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, GoLexer.FLOAT_LIT, expr);
    }

    private static void assertImagLiteral(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, GoLexer.IMAGINARY_LIT, expr);
    }

    private static void assertRuneLiteral(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, GoLexer.RUNE_LIT, expr);
    }

    private static void assertStrLiteral(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, GoLexer.STRING_LIT, expr);
    }

    @Test
    public void intLiteralDecimal() {
        assertIntLiteral("132");
    }

    @Test
    public void intLiteralOctal() {
        assertIntLiteral("07");
    }

    @Test
    public void intLiteralHex() {
        assertIntLiteral("0xF4");
    }

    @Test
    public void floatLiteral() {
        assertFloatLiteral("132.51");
    }

    @Test
    public void imaginaryLiteral() {
        assertImagLiteral("5i");
    }

    @Test
    public void runeLiteral() {
        assertRuneLiteral("'a'");
    }

    @Test
    public void strLiteral() {
        assertStrLiteral("`a string literal`");
    }

}
