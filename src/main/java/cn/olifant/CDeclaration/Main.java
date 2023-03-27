package cn.olifant.CDeclaration;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        // 输入.c源文件的文件名，以UTF-8编码标准
        String fileName = "src/main/java/cn/olifant/CDeclaration/samples.c";
        // 读入文件
        ArrayList<String> fileContext = readCFile(fileName);

        // 遍历文件内容，输出声明的类
        for(int i=0; i<fileContext.size(); i++) {
            // 获取一个声明
            String input = fileContext.get(i);
            // 定义对输出格式
            System.out.print("Input "+(i+1)+": "+input+"\nOutput "+(i+1)+": ");
            // 对input分析后输出结果
            printType(input);
            // 换行
            System.out.println("\n");

        }

    }


    // 将输入的声明类型以自然语言的形式打印出来
    public static void printType(String input) {
        // 参考3.4
        // 新建一个CharStream，从input中获取
        CharStream stream = CharStreams.fromString(input);
        // 新建一个词法分析器，处理输入的CharStream
        CLexer lexer = new CLexer(stream);
        // 新建一个词法符号的缓冲区，用于存储词法分析器，将生成此法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 新建一个语法分析器，处理词法符号缓冲区中的内容
        CParser parser = new CParser(tokens);
        // 针对declaration规则，开始语法分析
        ParseTree tree = parser.declaration();
        // 设置一个监听器
        CDeclarationListener listener = new CDeclarationListener();
        // 新建一个walker用于触发上面的监听器
        ParseTreeWalker walker = new ParseTreeWalker();
        // 使用walker遍历语法树
        walker.walk(listener, tree);

    }


    // 读取C语言源文件 .c 并返回一个按行存贮字符串的数组
    public static ArrayList<String> readCFile(String fileName) {
        // 初始化字符串列表
        ArrayList<String> lines = new ArrayList<String>();

        try{
            // 按UTF-8编码格数读取文件
            FileInputStream fileInputStream = new FileInputStream(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            // 将每一行字符串添加到列表中
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;

    }



}
