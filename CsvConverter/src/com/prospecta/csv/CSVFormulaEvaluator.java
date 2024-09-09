package com.prospecta.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class CSVFormulaEvaluator {

    // To store the CSV data
    private String[][] sheet;
    private Map<String, Integer> columnIndices;

    public CSVFormulaEvaluator(String filePath) throws IOException {
        loadCSV(filePath);
    }

    // Method to load CSV into a 2D array
    private void loadCSV(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        // Read the CSV line by line and store it in 'sheet'
        while ((line = br.readLine()) != null) {
            rows.add(line.split(","));
        }
        br.close();

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
        for (int r = 0; r < sheet.length; r++) {
            for (int c = 0; c < sheet[r].length; c++) {
                sheet[r][c] = evaluateCell(r, c);
            }
        }
    }

 
    private String evaluateCell(int r, int c) {
        String value = sheet[r][c].trim();
        if (value.startsWith("=")) {
            return String.valueOf(evaluateFormula(value.substring(1), r, c));
        }

        return value;
    }

    private double evaluateFormula(String formula, int r, int c) {
        Pattern cellPattern = Pattern.compile("([A-Z])(\\d+)");
        Matcher matcher = cellPattern.matcher(formula);

        while (matcher.find()) {
            String column = matcher.group(1);
            int referencedRow = Integer.parseInt(matcher.group(2)) - 1;
            int referencedCol = columnIndices.get(column);

            String cellValue = evaluateCell(referencedRow, referencedCol);
            formula = formula.replace(matcher.group(0), cellValue);
        }

        // Evaluate the mathematical expression using the eval() method
        return evalExpression(formula);
    }

    // Method to evaluate a mathematical expression (basic arithmetic)
    private double evalExpression(String expression) {
        try {
            // Use the JavaScript engine to evaluate arithmetic expressions
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            Object result = engine.eval(expression);  
            return Double.parseDouble(result.toString());  
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating expression: " + expression, e);
        }
    }

    // Method to write the evaluated result to a new CSV file
    public void writeCSV(String outputFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

        for (String[] row : sheet) {
            writer.write(String.join(",", row));
            writer.newLine();
        }

        writer.close();
    }

    public static void main(String[] args) {
        try {
            CSVFormulaEvaluator evaluator = new CSVFormulaEvaluator("C:\\Users\\ACCESS INFO\\Desktop\\CSV\\exampleInput.csv");

            evaluator.evaluateSheet();

            // Write the evaluated results to a new output CSV file
            evaluator.writeCSV("C:\\Users\\ACCESS INFO\\Desktop\\CSV\\exampleOutput.csv");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
