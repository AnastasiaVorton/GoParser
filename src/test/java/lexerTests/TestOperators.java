package lexerTests;

import org.antlr.v4.runtime.Token;
import org.junit.Test;

import static lexerTests.Utils.assertToken;


public class TestOperators {

    private static void assertOrOperand(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, 53, expr);
    }

    private static void assertEqOperand(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);
        assertToken(token, 55, expr);
    }

    @Test
    public void orOperator() {
        assertOrOperand("||");
    }

    @Test
    public void equalOperator() {
        assertEqOperand("==");
    }

}