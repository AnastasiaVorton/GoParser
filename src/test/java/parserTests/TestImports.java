package parserTests;

import antlr.GoParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static parserTests.Utils.assertTree;

public class TestImports {
    @Test
    public void simpleImport() {
        String statement = "import \"memes\"";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.importDecl();

        assertTree("import <STRING_LIT>", GoParser.RULE_importDecl, tree);
    }

    @Test
    public void dotImport() {
        String statement = "import . \"memes\"";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.importDecl();

        assertTree("import . <STRING_LIT>", GoParser.RULE_importDecl, tree);
    }

    @Test
    public void customImport() {
        String statement = "import best \"memes/loss\"";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.importDecl();

        assertTree("import <IDENTIFIER> <STRING_LIT>", GoParser.RULE_importDecl, tree);
    }

    @Test(expected = ParseCancellationException.class)
    public void invalidImport() {
        String statement = "import if os == os_win { \"fmt\" }";
        Utils.setInput(statement);
        ParseTree tree = Utils.parser.importDecl();

        assertTree("<importDecl>", GoParser.RULE_importDecl, tree);
    }
}
