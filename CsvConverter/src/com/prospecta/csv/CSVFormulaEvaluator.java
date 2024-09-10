package com.prospecta.csv;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CSVFormulaEvaluator {

    // To store the CSV data
    private String[][] sheet;
    private Map<String, Integer> columnIndices;

    public CSVFormulaEvaluator(String filePath) throws IOException, FileNotFoundException {
        loadCSV(filePath);
    }

    // Method to load CSV into a 2D array
    private void loadCSV(String filePath) throws IOException, FileNotFoundException {
        List<String[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        try {
            // Read the CSV line by line and store it in 'sheet'
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (IOException e) {
            throw new IOException("Error reading the CSV file at: " + filePath, e);
        } finally {
            br.close();
        }

        // Initialize sheet and column indices
        sheet = rows.toArray(new String[0][0]);
        initializeColumnIndices();
    }

    // Method to initialize column letters (A, B, C, etc.) to indices (0, 1, 2, etc.)
    private void initializeColumnIndices() {
        columnIndices = new HashMap<>();
        for (int i = 0; i < sheet[0].length; i++) {
            columnIndices.put(Character.toString((char) ('A' + i)), i);
        }
    }

    // Method to evaluate the entire sheet
    public void evaluateSheet() {
        for (int row = 0; row < sheet.length; row++) {
            for (int col = 0; col < sheet[row].length; col++) {
                sheet[row][col] = evaluateCell(row, col);
            }
        }
    }

    // Method to evaluate a single cell
    private String evaluateCell(int row, int col) {
        String value = sheet[row][col].trim();

        // If it's a formula (starts with '='), evaluate it
        if (value.startsWith("=")) {
            try {
                return String.valueOf(evaluateFormula(value.substring(1), row, col));
            } catch (Exception e) {
                System.err.println("Error evaluating formula in cell " + getCellLabel(row, col) + ": " + e.getMessage());
                return "ERROR"; // Return "ERROR" if formula evaluation fails
            }
        }

        // Return the value if it's a number
        return value;
    }

    // Method to evaluate a formula
    private double evaluateFormula(String formula, int row, int col) throws ScriptException {
        // Regex to match cell references like A1, B2, etc.
        Pattern cellPattern = Pattern.compile("([A-Z])(\\d+)");
        Matcher matcher = cellPattern.matcher(formula);

        // Replace cell references with their evaluated values
        while (matcher.find()) {
            String column = matcher.group(1);
            int referencedRow = Integer.parseInt(matcher.group(2)) - 1;

            // Check if the referenced cell is valid
            if (!columnIndices.containsKey(column) || referencedRow >= sheet.length) {
                throw new IllegalArgumentException("Invalid cell reference: " + matcher.group(0));
            }

            int referencedCol = columnIndices.get(column);

            // Recursively evaluate the referenced cell
            String cellValue = evaluateCell(referencedRow, referencedCol);
            formula = formula.replace(matcher.group(0), cellValue);
        }

        // Evaluate the mathematical expression using evalExpression
        return evalExpression(formula);
    }

    // Method to evaluate a mathematical expression (basic arithmetic)
    private double evalExpression(String expression) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

        try {
            Object result = engine.eval(expression);  // Evaluate the expression
            return Double.parseDouble(result.toString());  // Convert result to Double
        } catch (ScriptException e) {
            throw new ScriptException("Error in formula: " + expression);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number in formula: " + expression);
        }
    }

    // Method to write the evaluated result to a new CSV file
    public void writeCSV(String outputFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

        try {
            for (String[] row : sheet) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error writing to CSV file at: " + outputFilePath, e);
        } finally {
            writer.close();
        }
    }

    // Helper method to get the cell label (e.g., A1, B2)
    private String getCellLabel(int row, int col) {
        return (char) ('A' + col) + String.valueOf(row + 1);
    }

    public static void main(String[] args) {
        try {
            // Replace with your actual file paths
            CSVFormulaEvaluator evaluator = new CSVFormulaEvaluator("input.csv");

            // Evaluate the entire sheet
            evaluator.evaluateSheet();

            // Write the evaluated results to a new output CSV file
            evaluator.writeCSV("output.csv");

            System.out.println("Evaluation completed successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
