import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;


public class project1 {

    // get the type of operation to be executed on the B plus tree
    private static int getOperationType(String operation) {
        if (operation.contains("Insert")) {
            return 1;
        } else if (operation.contains("Search") && operation.contains(",")){
            return 2;
        } else {
            return 3;
        }
    }

    private static double parseInsertKey(String line) {
        String keyStr = line.substring(line.indexOf('(') + 1, line.indexOf(',')).trim();
        return Double.parseDouble(keyStr);
    }

    private static String parseInsertValue(String line) {
        return line.substring(line.indexOf("Value") + 5, line.indexOf(')')).trim();
    }

    public static void main(String[] args) throws IOException {
        // check if the input argument is valid
        if (args.length != 0) {
            System.err.println("Invalid input, please enter:java treesearch file_name");
        } else {
            // get the input file name
            Scanner scanner = new Scanner(System.in);
            String inputFile = scanner.nextLine();
            System.out.println("Start reading from:" + inputFile);

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile + ".txt")));

            } catch (FileNotFoundException e) {
                System.err.println("The specified input file is not found");
            }

            // get the order of the B plus tree
            int order = Integer.parseInt(input.readLine().trim());
            BPlusTree tree = new BPlusTree(order);

            // execute the operations from input file and
            BufferedWriter outputFile = new BufferedWriter(new FileWriter(new File("out_put.txt")));
            do {
                String newLine = input.readLine().trim();
                switch(getOperationType(newLine)) {
                    case 1: // insert operation
                        double insertionKey = parseInsertKey(newLine);
                        String insertionValue = parseInsertValue(newLine);
                        tree.insertion(insertionKey, insertionValue);
                        break;
                    case 2: // search by key range operation
                        double low = Double.parseDouble(newLine.substring(newLine.indexOf('(') + 1, newLine.indexOf(',')).trim());
                        double high = Double.parseDouble(newLine.substring(newLine.indexOf(',') + 1, newLine.indexOf(')')).trim());
                        //searchRange(low, high);
                        break;
                    case 3: // search by key operation
                        double searchingKey = Double.parseDouble(newLine.substring(newLine.indexOf('(') + 1, newLine.indexOf(')')).trim());
                        //searchValue(searchingKey);
                        break;
                }
                System.out.println("operations performed: " + newLine);
            } while (input.ready());
            outputFile.close();
        }
    }
}
