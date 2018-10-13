package shared;

import org.antlr.v4.runtime.Token;
import org.junit.ComparisonFailure;

public class Assert {
    public static void assertToken(Token given, int expectedType, String expectedText) throws AssertionError {
        if (!given.getText().equals(expectedText) || given.getType() != expectedType)
            throw new ComparisonFailure("Token type or value doesn't match.",
                    "type: " + expectedType + ", text: " + expectedText,
                    "type: " + given.getType() + "text: " + given.getText());
    }
}
