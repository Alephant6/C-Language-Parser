package cn.olifant.CDeclaration;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        // Option 1
        // Enter the file name of the .c source file, encoded in UTF-8
//        String fileName = "src/main/java/cn/olifant/CDeclaration/samples.c";
//        String fileName = "src/main/java/cn/olifant/CDeclaration/moreComplexSamples.c";
//        printCFileAllType(fileName);

        // Option 2
        // You can also directly pass in a "valid C-language declaration" string, and output its type
        String CDecl = "int *(*f)[3](int y);";
        // Call pringType method output its type
        printType(CDecl);

    }

    // print all declared types in a C file
    public static void printCFileAllType(String fileName) {
        // Read file
        ArrayList<String> fileContext = readCFile(fileName);

        // Traverse the contents of the file and output the declared class
        for(int i=0; i<fileContext.size(); i++) {
            // Get a declaration
            String input = fileContext.get(i);
            // Define the output format
            System.out.print("Input "+(i+1)+": "+input+"\nOutput "+(i+1)+": ");
            // Output the result after analyzing the input
            printType(input);
            // New lines
            System.out.println("\n");

        }

    }


    // Print out the input declaration type in natural language
    public static void printType(String input) {
        // Refer to 3.4
        // Create a new CharStream and get it from input
        CharStream stream = CharStreams.fromString(input);
        // Create a new lexical analyzer to process the input CharStream
        CLexer lexer = new CLexer(stream);
        // Create a buffer of lexical symbols to store the lexical analyzer, which will generate this lexical symbol
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Create a new syntax analyzer to process the contents of the lexical symbol buffer
        CParser parser = new CParser(tokens);
        // For declaration rules, start syntax analysis
        ParseTree tree = parser.declaration();
        // Set up a listener
        CDeclarationListener listener = new CDeclarationListener();
        // Create a new walker to trigger the above listener
        ParseTreeWalker walker = new ParseTreeWalker();
        // Use walker to traverse the syntax tree
        walker.walk(listener, tree);

    }


    // Read the C language source file .c and return an array of strings stored line by line
    public static ArrayList<String> readCFile(String fileName) {
        // list of strings
        ArrayList<String> lines = new ArrayList<String>();

        try{
            // Read files according to the number of UTF-8 encoded grids
            FileInputStream fileInputStream = new FileInputStream(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            // Append each line of strings to the list
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
