package cn.olifant.C;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class CTest {
    public static void main(String[] args) throws Exception {
        String input = "int x; ";
        CharStream stream = CharStreams.fromString(input);
        CLexer lexer = new CLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ParseTree tree = parser.declaration();
        MyCListener listener = new MyCListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        String type = listener.getType();
        String name = listener.getName();
        String size = listener.getSize();
        if (type.startsWith("int") || type.startsWith("char") || type.startsWith("float") || type.startsWith("double") || type.startsWith("void") || type.startsWith("struct") || type.startsWith("union") || type.startsWith("enum")) {
            System.out.print("a ");
        }
        if (size != null) {
            System.out.print("array of " + size + " ");
        }
        System.out.print(name);
        if (type.endsWith("*")) {
            System.out.print(" pointer to");
            type = type.substring(0, type.length() - 1);
        }
        System.out.println(" " + type);
    }
}
