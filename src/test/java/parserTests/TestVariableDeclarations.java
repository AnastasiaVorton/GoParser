package parserTests;

import antlr.GoParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static parserTests.Utils.assertTree;

public class TestVariableDeclarations {
    @Test
    public void simpleVariableDeclaration() {
        String statement = "var a int = 5";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.varDecl();

        assertTree("var <IDENTIFIER> <type> = <basicLit>", GoParser.RULE_varDecl, tree);
    }

    @Test
    public void tupleVariableDeclaration() {
        String statement = "var r, i = complexSqrt(-1)";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.varDecl();

        assertTree("var <identifierList> = <expression>", GoParser.RULE_varDecl, tree);
    }

    @Test(expected = ParseCancellationException.class)
    public void invalidVariableDeclaration() {
        String statement = "var break = 5";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.varDecl();

        assertTree("<varDecl>", GoParser.RULE_varDecl, tree);
    }
}
