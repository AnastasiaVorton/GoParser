package parserTests;

import antlr.GoParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static parserTests.Utils.assertTree;

public class TestCycles {
    @Test
    public void simpleForStatement() {
        String statement =
                "for loss.isAlive() {\n"
                + "    break // gotta break manually because loss is never dead\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.forStmt();

        assertTree("for <expression> <block>", GoParser.RULE_forStmt, tree);
    }

    @Test
    public void usualForStatement() {
        String statement =
                "for i := 0; i < TAs.count(); i++ {\n"
                + "    if checkingTA == TAs[i] { sayHi() }\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.forStmt();

        assertTree("for <forClause> <block>", GoParser.RULE_forStmt, tree);
    }

    @Test
    public void rangeForStatement() {
        String statement =
                "for i, ta := range TAs {\n"
                + "    if checkingTA == ta { sayHi() }\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.forStmt();

        assertTree("for <rangeClause> <block>", GoParser.RULE_forStmt, tree);
    }

    @Test(expected = ParseCancellationException.class)
    public void illegalForStatement() {
        String statement =
                "for loss.isAlive()\n"
                + "    Println(\"in some PLs this is possible, but not in Go\")";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.forStmt();

        assertTree("<forStmt>", GoParser.RULE_forStmt, tree);
    }
}
