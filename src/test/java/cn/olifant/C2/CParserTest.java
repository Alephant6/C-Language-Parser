package cn.olifant.C2;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class CParserTest {
    public static void main(String[] args) {
//        String input = "int x;";
//        String input = "int x[2];";
//        String input = "int *x[2];";
//        String input = "int (*x)[2];";
        String input = "int *(x[2]);";
//        String input = "int **x[2];";
//        String input = "int f(int x, float *y);";
//        String input = "int (*f)(int x, float *);";
//        String input = "int (*f[2])(int x, float *);";
        CharStream stream = CharStreams.fromString(input);
        CLexer lexer = new CLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ParseTree tree = parser.declaration();
        MyCListener listener = new MyCListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
    }
}
