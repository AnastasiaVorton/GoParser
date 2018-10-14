package lexerTests;

import antlr.GoLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.junit.ComparisonFailure;

import java.util.List;

/**
 * Package-private class that contains common utility methods useful for lexer testing.
 */
class Utils {
    private static GoLexer lexer = new GoLexer(null);

    /**
     * Returns all tokens contained in a string found by {@link GoLexer}.
     * @param expr Expression to retrieve tokens from.
     * @return List of objects, each of some unknown type that conforms to {@link Token}.
     */
    static List<? extends Token> getTokens(String expr) {
        lexer.setInputStream(CharStreams.fromString(expr));
        return lexer.getAllTokens();
    }

    /**
     * Checks if the token is the desired one.
     * @param given token to check.
     * @param expectedType Expected type of the token, types can be found as static variables of Lexer and Parser.
     * @param expectedText Expected lexeme of the token.
     * @throws AssertionError in case the token doesn't match.
     */
    static void assertToken(Token given, int expectedType, String expectedText) throws AssertionError {
        if (!given.getText().equals(expectedText) || given.getType() != expectedType)
            throw new ComparisonFailure("Token type or value doesn't match.",
                    "type: " + expectedType + ", text: " + expectedText,
                    "type: " + given.getType() + "text: " + given.getText());
    }
}
