package lexerTests;

import antlr.GoLexer;
import org.antlr.v4.runtime.Token;
import org.junit.ComparisonFailure;
import org.junit.Test;

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

    private static void assertImaginaryLiteral(String expr) throws AssertionError {
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
    public void floatLiteralShort() {
        assertFloatLiteral("132.");
    }

    @Test
    public void floatLiteralExp() {
        assertFloatLiteral("132.51e+5");
    }

    @Test
    public void floatLiteralShortLeft() {
        assertFloatLiteral(".51");
    }

    @Test
    public void imaginaryLiteral() {
        assertImaginaryLiteral("5i");
    }

    @Test
    public void imaginaryFloatLiteral() {
        assertImaginaryLiteral(".25i");
    }

    @Test
    public void runeLiteral() {
        assertRuneLiteral("'a'");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void illegalBacklashRuneLiteral() {
        assertRuneLiteral("'\\d'");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void illegalManyCharactersRuneLiteral() {
        assertRuneLiteral("'aa'");
    }

    @Test
    public void strLiteral() {
        assertStrLiteral("`a string literal`");
    }

    @Test
    public void multilineRawStrLiteral() {
        assertStrLiteral("`sweet dreams\n are made of memes`");
    }

    @Test(expected = ComparisonFailure.class)
    public void illegalMultilineInterpretedStrLiteral() { assertStrLiteral("\"who am I\nto disagree\""); }
}
