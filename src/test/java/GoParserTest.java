import static org.junit.jupiter.api.Assertions.assertTrue;

import antlr.GoLexer;
import antlr.GoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class GoParserTest {


    String expr = "type Fund struct {\n" +
            "    // balance is unexported (private), because it's lowercase\n" +
            "    balance int\n" +
            "}";
    GoLexer lexer = new GoLexer(CharStreams.fromString(expr));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    GoParser parser = new GoParser(tokens);

    @Test
    public void oneLineCommentsShouldBeDetected() {
//        assertTrue(parser.getTokenStream().getText(), );
//        assertTrue(GoParser.match("// THIS //IS COMMENT //IN ONE LINE"), "// THIS //IS COMMENT //IN ONE LINE");
    }
//
//    @Test
//    public void multilineCommentsShouldBeDetected() {
//        assertTrue(GoParser.match("/* THIS IS FAKE MULTILINE COMMENT */"), "/* THIS IS FAKE MULTILINE COMMENT */");
//        assertTrue(GoParser.match("/* THIS IS\n FAKE\n MULTILINE\n COMMENT */"), "/* THIS IS\\n FAKE\\n MULTILINE\\n COMMENT */");
//    }

    @Test
    public void sharpCommentsShouldBeDetected() {
//        assertTrue(GoParser.match("# THIS IS SHARP COMMENT"), "# THIS IS SHARP COMMENT");
//        assertTrue(GoParser.match("# THIS IS # COMMENT"), "# THIS IS SHARP COMMENT");
    }


}
