package parserTests;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import static org.junit.Assert.assertTrue;

class Utils {
    static GoLexer lexer = new GoLexer(null);
    static GoParser parser = new GoParser(null);

    static void setInput(String input) {
        lexer.setInputStream(CharStreams.fromString(input));
        parser.setTokenStream(new CommonTokenStream(lexer));
    }

    static void assertTree(String expectedPattern, int expectedRule, ParseTree tree) throws AssertionError {
        ParseTreePattern ifPattern = Utils.parser.compileParseTreePattern(expectedPattern, expectedRule);
        ParseTreeMatch match = ifPattern.match(tree);
        assertTrue(match.succeeded());
    }
}
