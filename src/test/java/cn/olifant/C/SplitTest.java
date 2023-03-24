package cn.olifant.C;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class SplitTest {
    public static void main(String[] args) {
        String input = "int (*f[2])(int x, float *);";
        CLexer lexer = new CLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        DeclaratorListener listener = new DeclaratorListener();
        parser.addParseListener(listener);
        parser.declaration();
        List<String> elements = listener.getElements();
        System.out.println(elements);
    }

    private static class DeclaratorListener extends CBaseListener {
        private final List<String> elements = new ArrayList<String>();

        @Override
        public void enterDeclarator(CParser.DeclaratorContext ctx) {
            String name = ctx.getText();
            String type = getType(ctx);
            elements.add(type + " " + name);
        }

        private String getType(CParser.DeclaratorContext ctx) {
            if (ctx.pointer() != null) {
                return "pointer to " + getType(ctx.getChild(CParser.DeclaratorContext.class, 0));
            } else if (ctx.getChildCount() == 1) {
                return ctx.getText();
            } else {
                return getType(ctx.getChild(CParser.DeclaratorContext.class, 0));
            }
        }

        public List<String> getElements() {
            return elements;
        }
    }
}
