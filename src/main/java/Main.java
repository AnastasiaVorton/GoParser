import antlr.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static String toJson(ParseTree tree) {
        Gson prettyPrint = new GsonBuilder().setPrettyPrinting().create();
        return prettyPrint.toJson(toMap(tree));
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
            map.put("text", token.getText());
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

    public static void main(String[] args) {
        try {
            GoLexer lexer = new GoLexer(CharStreams.fromFileName("in.txt"));
            GoParser parser = new GoParser(new CommonTokenStream(lexer));
            System.out.println(toJson(parser.sourceFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
