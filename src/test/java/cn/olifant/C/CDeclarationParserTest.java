package cn.olifant.C;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.nio.charset.Charset;

public class CDeclarationParserTest {
    public static void main(String[] args) throws Exception {
//        // 读取C语言源代码文件
//        CharStream input = CharStreams.fromFileName("src/test/java/cn/olifant/C/Test.c", Charset.forName("UTF-8"));
        // 获得输入
        String input = "int x[3];";
        // 解析input，获得parser
        CParser declarationParser = getParser(input);
        // 解析C语言代码，生成抽象语法树
        ParseTree declarationTree = declarationParser.declaration();
//        ParseTree declarationTree = declarationParser.declarator();

        // 打印抽象语法树
        System.out.println(declarationTree.toStringTree(declarationParser));
        // 提取声明类型信息并输出
        String type = extractDeclarationType(declarationTree);
        System.out.println(type);
    }

    // 从CharSteam中获得 CParser
    private static CParser getParser(String input) {
        // 转换格式
        CharStream charStream = CharStreams.fromString(input);
        // 创建C语言词法分析器
        CLexer lexer = new CLexer(charStream);
        // 创建词法符号流
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 创建C语言语法分析器
        return new CParser(tokens);
    }

    // 从抽象语法树中提取声明类型信息
    private static String extractDeclarationType(ParseTree tree) {
        String additionalType = "";
        String baseType = "int";
        if (tree.getChildCount() == 2) {
            // 基本类型声明
            baseType = tree.getChild(0).getChild(0).getText();
        } else {
            // 复合类型声明
            baseType = tree.getChild(0).getText();
            // 第二个节点的后缀
            String suffix = tree.getChild(1).getText();
            CParser declaratorParser = getParser(suffix);
            ParseTree declaratorTree = declaratorParser.declarator();
            // 如果存在*，则说明是指针
            if (suffix.startsWith("*")){
                // 指针声明
                additionalType = "pointer to " + additionalType;
            }
            // 如果存在[，则说明是数组
            if (suffix.indexOf("[") != -1) {
                // 数组声明
                int startIndex = suffix.indexOf("[");
                int endIndex = suffix.indexOf("]");
                String size = suffix.substring(startIndex+1, endIndex);
                additionalType = "size-" + size + " array of " + additionalType;
            }
            if (suffix.indexOf("(") != -1) {
                // 函数声明
                String args = extractFunctionArgs(tree.getChild(1));
                additionalType = "function that accepts " + args + " returning " + additionalType;
            }

        }
        return additionalType + baseType;
    }

    // 从函数声明中提取参数信息
    private static String extractFunctionArgs(ParseTree tree) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tree.getChildCount(); i++) {
            ParseTree child = tree.getChild(i);
            if (child instanceof CParser.ParameterDeclarationContext) {
                String type = child.getChild(0).getText();
                sb.append(type);
                if (i < tree.getChildCount() - 2) {
                    sb.append(" and ");
                }
            }
        }
        return sb.toString();
    }
}

