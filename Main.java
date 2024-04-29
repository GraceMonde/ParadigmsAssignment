import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        // Parse the Java source file
        CompilationUnit cu = JavaParser.parse(new FileInputStream("StudentResults.java"));

        // Print the number of tokens
        System.out.println("Number of tokens: " + countTokens(cu));

        // Optionally, print the AST
        System.out.println("AST:");
        System.out.println(LexicalPreservingPrinter.print(cu));
    }

    // Method to count the number of tokens in the AST
    private static int countTokens(Node node) {
        // Get the source range of the node
        int begin = node.getBegin().isPresent() ? node.getBegin().get().line : -1;
        int end = node.getEnd().isPresent() ? node.getEnd().get().line : -1;

        // Count the tokens in the source range
        int tokenCount = 0;
        for (int i = begin; i <= end; i++) {
            tokenCount += node.getTokenRange().get().getTokens().get(i - 1).getText().length();
        }

        // Recursively count tokens in child nodes
        for (Node child : node.getChildNodes()) {
            tokenCount += countTokens(child);
        }

        return tokenCount;
    }
}
