package lexerTests;

import antlr.GoLexer;

import org.antlr.v4.runtime.Token;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static shared.Assert.assertToken;

/**
 * A class that contains test cases for Go identifiers.
 */
public class TestIdentifier {

    private static void assertIdentifier(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, GoLexer.IDENTIFIER, expr);
    }

    @Test
    public void simpleIdentifier() {
        assertIdentifier("cheburek");
    }

    @Test
    public void complexIdentifier() {
        assertIdentifier("ident_with_number_2");
    }

    @Test
    public void unicodeIdentifier() {
        assertIdentifier("спасибо_зуеву_за_курс");
    }

    @Test
    public void invalidIdentifier() {
        String expr = "2_ident_with_number";
        List<? extends Token> tokens = Utils.getTokens(expr);
        assertEquals(2, tokens.size());

        Token first = tokens.get(0);
        Token second = tokens.get(1);

        assertToken(first, GoLexer.INT_LIT, expr.substring(0, 1));
        assertToken(second, GoLexer.IDENTIFIER, expr.substring(1));
    }

}
