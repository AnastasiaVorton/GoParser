package presentation;

import antlr.GoBaseListener;
import antlr.GoParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link ParseTree} listener, that maps the parse tree to a hash map, which is then converted to JSON.
 * NOTE: before accessing its getters, make sure you have walked the tree
 * with a {@link org.antlr.v4.runtime.tree.ParseTreeWalker}, passing this listener as an argument.
 */
public class ParseTreeJSONMapper extends GoBaseListener {
    private Boolean isPrettyPrint;
    private Map<String, Object> treeMapRepresentation;
    private String treeJSONStringRepresentation;

    public ParseTreeJSONMapper(Boolean isPrettyPrint) {
        this.isPrettyPrint = isPrettyPrint;
    }

    public Boolean getPrettyPrint() {
        return isPrettyPrint;
    }

    public Map<String, Object> getTreeMapRepresentation() {
        return treeMapRepresentation;
    }

    public String getTreeJSONStringRepresentation() {
        return treeJSONStringRepresentation;
    }

    private String convertMapToJSONString() {
        Gson gson;

        if (isPrettyPrint) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        } else {
            gson = new Gson();
        }

        return gson.toJson(treeMapRepresentation);
    }

    private static Map<String, Object> toMap(ParseTree tree) {
        Map<String, Object> map = new LinkedHashMap<>();
        traverse(tree, map);
        return map;
    }

    private static void traverse(ParseTree tree, Map<String, Object> map) {
        if (tree instanceof TerminalNodeImpl) {
            Token token = ((TerminalNodeImpl) tree).getSymbol();

            map.put("type", GoParser.VOCABULARY.getSymbolicName(token.getType()));
            map.put("lexeme", token.getText());
        } else {
            List<Map<String, Object>> children = new ArrayList<>();
            String name = tree.getClass().getSimpleName().replaceAll("Context$", "");
            map.put(Character.toLowerCase(name.charAt(0)) + name.substring(1), children);

            for (int i = 0; i < tree.getChildCount(); i++) {
                Map<String, Object> nested = new LinkedHashMap<>();
                children.add(nested);
                traverse(tree.getChild(i), nested);
            }
        }
    }

    @Override
    public void enterSourceFile(GoParser.SourceFileContext ctx) {
        treeMapRepresentation = toMap(ctx);
    }

    @Override
    public void exitSourceFile(GoParser.SourceFileContext ctx) {
        treeJSONStringRepresentation = convertMapToJSONString();
    }
}
