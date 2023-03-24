package cn.olifant.C;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class SimpCDeclParserTest {
    public static void main(String[] args) throws Exception {
        // input
        String input = "int x;";
//        String input = "void f(int x);";
//        String input = "int x[2];";
//        String input = "int *x[2];";
//        String input = "*x[2];";
//        String input = "int f(int x, float *y);";
//        String input = "int (*f)(int x, float *);";
//        String input = "(*f)(int x, float *);";
//        String input = "int (*f[2])(int x, float y);";
        // 转换格式
        CharStream charStream = CharStreams.fromString(input);
        // 创建C语言词法分析器
        CLexer lexer = new CLexer(charStream);
        // 创建词法符号流
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 创建C语言语法分析器
        CParser parser = new CParser(tokens);
        // 解析C语言代码，生成抽象语法树
        ParseTree tree = parser.declarator();
//        ParseTree tree = parser.pointer();
//        ParseTree tree = parser.declaration();
        //打印抽象语法树
        System.out.println(tree.toStringTree(parser));
        // 从抽象语法树中提取声明类型信息
        extractDeclarationType(tree);

    }

    private static void extractDeclarationType(ParseTree tree){
        String baseType = tree.getChild(0).getText();
        if (tree.getChild(1).getChildCount() == 1 ){
            System.out.println(baseType);
        }else{

        }

    }

}
