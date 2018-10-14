package lexerTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static shared.Assert.assertToken;

public class TestKeyword {

    private static void assertKeyword(String expr) throws AssertionError {
        Token token = Utils.getTokens(expr).get(0);

    }

    @Test
    public void breakKeyword() {
        assertKeyword("break");
    }

    @Test
    public void invalidKeyword() {
        String expr = "ifif";
        List<? extends Token> tokens = Utils.getTokens(expr);
        assertEquals(1, tokens.size());

        Token first = tokens.get(0);

        assertToken(first, GoLexer.IDENTIFIER, expr.substring(0, 4));
    }

}
