package parserTests;

import antlr.GoParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.junit.Test;

public class TestIfStatements {
    @Test
    public void simpleIfStatement() {
        String statement =
                "if a < b {\n"
                + "    return a\n"
                + "}";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.ifStmt();

        ParseTreePattern ifPattern = Utils.parser.compileParseTreePattern("if <expression> <block>",
                GoParser.RULE_ifStmt);
        ParseTreeMatch match = ifPattern.match(tree);


        System.out.println(match.succeeded());
    }
}
