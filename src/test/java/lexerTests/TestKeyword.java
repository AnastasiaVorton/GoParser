package lexerTests;

import antlr.GoLexer;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static shared.Assert.assertToken;

public class TestKeyword {

    @Test
    public void allKeywords() {
        String allKeywords = "break "
                + "default "
                + "func "
                + "interface "
                + "select "
                + "case "
                + "defer "
                + "go "
                + "map "
                + "struct "
                + "chan "
                + "else "
                + "goto "
                + "package "
                + "switch "
                + "fallthrough "
                + "const "
                + "if "
                + "range "
                + "type "
                + "continue "
                + "for "
                + "import "
                + "return "
                + "var";
        
        String[] lexemes = allKeywords.split(" ");
        List<? extends Token> tokens = Utils.getTokens(allKeywords).stream()
                .filter(t -> !t.getText().equals(" ")).collect(Collectors.toList());

        assertEquals(lexemes.length, tokens.size());

        for (int i = 0; i < lexemes.length; i++) {
            String lexeme = lexemes[i];
            Token token = tokens.get(i);

            // only keywords have their types translated to their exact names, but surrounded with '
            assertEquals("'" + lexeme + "'", GoLexer.VOCABULARY.getDisplayName(token.getType()));
            assertEquals(lexeme, token.getText());
        }
    }

    @Test
    public void invalidKeyword() {
        String expr = "ifif";
        List<? extends Token> tokens = Utils.getTokens(expr);
        assertEquals(1, tokens.size());

        Token first = tokens.get(0);

        // expect the token to be matched as an identifier
        assertToken(first, GoLexer.IDENTIFIER, expr.substring(0, 4));
    }

}
