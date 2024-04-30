package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.printer.YamlPrinter;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import java.io.FileInputStream;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        // Parse the Java source file
        FileInputStream in = new FileInputStream("C:\\Users\\GRACE\\Desktop\\ParadigmsAssignment\\StudentResults.java");
        CompilationUnit cu = new JavaParser().parse(in).getResult().get();
        String ouputDirAST = "./AbstractSyntaxTree.txt";
        String ouputDirSTAT = "./TokenCount.txt";


        // Traverse the AST to extract class names
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classNode ->
                System.out.println("Class Name: " + classNode.getName()));

        // Print the number of tokens
        int counts = countTokens(cu);
        System.out.println("Number of tokens: " + counts);

        // Optionally, print the AST
        System.out.println("AST:");
        String ast = new YamlPrinter(true).output(cu);
        FileWriter ast_wr = new FileWriter(ouputDirAST);
        ast_wr.append(ast);
        ast_wr.close();

        FileWriter stat_wr = new FileWriter(ouputDirSTAT);
        stat_wr.append("Number of tokens: " + counts);
        stat_wr.close();
        //System.out.println(LexicalPreservingPrinter.print(cu));
    }


    // Method to count the number of tokens in the AST
    private static int countTokens(Node node) {
        // Get the source range of the node
        int begin = node.getBegin().isPresent() ? node.getBegin().get().line : -1;
        int end = node.getEnd().isPresent() ? node.getEnd().get().line : -1;

        // Count the tokens in the source range
        int tokenCount = 0;
        for (int i = begin; i <= end; i++) {
            tokenCount += node.getTokenRange().get().toRange().get().getLineCount();
        }

        // Recursively count tokens in child nodes
        for (Node child : node.getChildNodes()) {
            tokenCount += countTokens(child);
        }

        return tokenCount;
    }
}
