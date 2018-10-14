package parserTests;

import antlr.GoParser;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.junit.Test;

import static parserTests.Utils.assertTree;

public class TestIfStatements {
    @Test
    public void simpleIfStatement() {
        String statement =
                "if a < b {\n"
                + "    return a\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.ifStmt();

        assertTree("if <expression> <block>", GoParser.RULE_ifStmt, tree);
    }

    @Test(expected = NoViableAltException.class)
    public void invalidIfStatement() {
        String statement =
                "if a < b {\n"
                + "    return a\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.ifStmt();

        assertTree("if <expression> <expression>", GoParser.RULE_ifStmt, tree);
    }

    @Test
    public void fullIfStatement() {
        String statement =
                "if meme == \"loss\" {\n"
                + "    fmt.Println(`| ||\n|| |_`)\n"
                + "} else {\n"
                + "    fmt.Println(\"pls get better memes\")\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.ifStmt();

        assertTree("if <expression> <block> else <block>", GoParser.RULE_ifStmt, tree);
    }

    @Test
    public void elseIfStatement() {
        String statement =
                "if meme == \"loss\" {\n"
                + "    fmt.Println(\"nice meme!\")\n"
                + "} else if meme.origin() == \"dezinfo\" {\n"
                + "    fmt.Println(\"ok those are also decent\")\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.ifStmt();

        assertTree("if <expression> <block> else if <expression> <block>", GoParser.RULE_ifStmt, tree);
    }
}
