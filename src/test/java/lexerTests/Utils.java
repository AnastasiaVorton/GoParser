package lexerTests;

import antlr.GoLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

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
}
