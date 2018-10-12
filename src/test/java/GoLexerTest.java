import static org.junit.jupiter.api.Assertions.assertEquals;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

public class GoLexerTest {

    @Test
    public void testIdentifier() {
        String expr = "lol_kek_cheburek";
        String expr2 = "ident_with_number_2";
        String expr3 = "2_ident_with_number";
        GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
        GoLexer lexer2 = new GoLexer(CharStreams.fromString(expr2));
        GoLexer lexer3 = new GoLexer(CharStreams.fromString(expr3)); // doesnt suppose to be identified as identifier
        int token_id = lexer.IDENTIFIER;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
        CommonTokenStream tokens3 = new CommonTokenStream(lexer3);
        GoParser parser = new GoParser(tokens);
        GoParser parser2 = new GoParser(tokens2);
        GoParser parser3 = new GoParser(tokens3);
        ParseTree tree = parser.sourceFile();
        ParseTree tree2 = parser2.sourceFile();
        ParseTree tree3 = parser3.sourceFile();
        assertEquals(tokens.size(), 2);
        assertEquals(tokens2.size(), 2);
        assertEquals(tokens3.size(), 3);
        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();
        String text2 = tokens2.get(0).getText();
        int type2 = tokens2.get(0).getType();
        String text3 = tokens3.get(0).getText();
        int type3 = tokens3.get(0).getType();
        assertEquals(text, "lol_kek_cheburek");
        assertEquals(type, token_id);
        assertEquals(text2, "ident_with_number_2");
        assertEquals(type2, token_id);
        assertEquals(text3, "2");
    }

    @Test
    public void testKeyword() {
        String expr = "break";
        String expr2 = "ifif";
        GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
        GoLexer lexer2 = new GoLexer(CharStreams.fromString(expr2));
        int token_identifier = lexer.IDENTIFIER;
        int token_keyword = 32;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
        GoParser parser = new GoParser(tokens);
        GoParser parser2 = new GoParser(tokens2);
        ParseTree tree = parser.sourceFile();
        ParseTree tree2 = parser2.sourceFile();
        assertEquals(tokens.size(), 2);
        assertEquals(tokens2.size(), 2);
        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();
        String text2 = tokens2.get(0).getText();
        int type2 = tokens2.get(0).getType();
        assertEquals(text, "break");
        assertEquals(type, token_keyword);
        assertEquals(text2, "ifif");
        assertEquals(type2, token_identifier);
    }

    @Test
    public void testOperators() {
        String expr = "a || b";
        String expr2 = "a == b";
        GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
        GoLexer lexer2 = new GoLexer(CharStreams.fromString(expr2));
        int token_1 = 53;
        int token_2 = 55;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
        GoParser parser = new GoParser(tokens);
        GoParser parser2 = new GoParser(tokens2);
        ParseTree tree = parser.sourceFile();
        ParseTree tree2 = parser2.sourceFile();
        assertEquals(tokens.size(), 6);
        assertEquals(tokens2.size(), 6);
        String text = tokens.get(2).getText();
        int type = tokens.get(2).getType();
        String text2 = tokens2.get(2).getText();
        int type2 = tokens2.get(2).getType();
        assertEquals(text, "||");
        assertEquals(type, token_1);
        assertEquals(text2, "==");
        assertEquals(type2, token_2);
    }

    @Test
    public void testIntLiterals() {
        String expr = "132";
        String expr2 = "07";
        String expr3 = "0xF4";
        GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
        GoLexer lexer2 = new GoLexer(CharStreams.fromString(expr2));
        GoLexer lexer3 = new GoLexer(CharStreams.fromString(expr3));
        int token_1 = lexer.INT_LIT;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
        CommonTokenStream tokens3 = new CommonTokenStream(lexer3);
        GoParser parser = new GoParser(tokens);
        GoParser parser2 = new GoParser(tokens2);
        GoParser parser3 = new GoParser(tokens3);
        ParseTree tree = parser.sourceFile();
        ParseTree tree2 = parser2.sourceFile();
        ParseTree tree3 = parser3.sourceFile();
        assertEquals(tokens.size(), 2);
        assertEquals(tokens2.size(), 2);
        assertEquals(tokens3.size(), 2);
        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();
        String text2 = tokens2.get(0).getText();
        int type2 = tokens2.get(0).getType();
        String text3 = tokens3.get(0).getText();
        int type3 = tokens3.get(0).getType();
        assertEquals(text, "132");
        assertEquals(type, token_1);
        assertEquals(text2, "07");
        assertEquals(type2, token_1);
        assertEquals(text3, "0xF4");
        assertEquals(type3, token_1);
    }

    @Test
    public void testFloatLiterals() {
        String expr = "132.51";
        String expr2 = "e+15";
        String expr3 = "5i";
        GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
        GoLexer lexer2 = new GoLexer(CharStreams.fromString(expr2));
        GoLexer lexer3 = new GoLexer(CharStreams.fromString(expr3));
        int token_1 = lexer.FLOAT_LIT;
        int token_2 = lexer.IMAGINARY_LIT;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
        CommonTokenStream tokens3 = new CommonTokenStream(lexer3);
        GoParser parser = new GoParser(tokens);
        GoParser parser2 = new GoParser(tokens2);
        GoParser parser3 = new GoParser(tokens3);
        ParseTree tree = parser.sourceFile();
        ParseTree tree2 = parser2.sourceFile();
        ParseTree tree3 = parser3.sourceFile();
        assertEquals(tokens.size(), 2);
//        assertEquals(tokens2.size(), 2);
        assertEquals(tokens3.size(), 2);
        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();
        String text2 = tokens2.get(0).getText();
        int type2 = tokens2.get(0).getType();
        String text3 = tokens3.get(0).getText();
        int type3 = tokens3.get(0).getType();
        assertEquals(text, "132.51");
        assertEquals(type, token_1);
//        assertEquals(text2, "e-15");
//        assertEquals(type2, token_1);
        assertEquals(text3, "5i");
        assertEquals(type3, token_2);
    }

    @Test
    public void testRuneLiterals() {
        String expr = "'a'";
        GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
        int token_1 = lexer.RUNE_LIT;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GoParser parser = new GoParser(tokens);
        ParseTree tree = parser.sourceFile();
        assertEquals(tokens.size(), 2);
        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();
        assertEquals(text, "'a'");
        assertEquals(type, token_1);
    }

    @Test
    public void testStringLiterals() {
        String expr = "`a string literal`";
        GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
        int token_1 = lexer.STRING_LIT;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GoParser parser = new GoParser(tokens);
        ParseTree tree = parser.sourceFile();
        assertEquals(tokens.size(), 2);
        String text = tokens.get(0).getText();
        int type = tokens.get(0).getType();
        assertEquals(text, "`a string literal`");
        assertEquals(type, token_1);
    }

}

