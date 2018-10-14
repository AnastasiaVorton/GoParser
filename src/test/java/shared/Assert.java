package shared;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.ComparisonFailure;

public class Assert {
    /**
     * Checks if the token is the desired one.
     * @param given token to check.
     * @param expectedType Expected type of the token, types can be found as static variables of Lexer and Parser.
     * @param expectedText Expected lexeme of the token.
     * @throws AssertionError in case the token doesn't match.
     */
    public static void assertToken(Token given, int expectedType, String expectedText) throws AssertionError {
        if (!given.getText().equals(expectedText) || given.getType() != expectedType)
            throw new ComparisonFailure("Token type or value doesn't match.",
                    "type: " + expectedType + ", text: " + expectedText,
                    "type: " + given.getType() + "text: " + given.getText());
    }
}
