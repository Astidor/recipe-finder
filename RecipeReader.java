import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeReader {
    private static final int BATCH_SIZE = 100;

    public int getBatchSize() {
        return BATCH_SIZE;
    }

    public List<Recipe> readRecipesFromCSV(String filePath, int startIndex) throws IOException, CsvException {
        List<Recipe> recipes = new ArrayList<>();
        int linesProcessed = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                linesProcessed++;
                if (linesProcessed <= startIndex) {
                    continue; // Skip lines until reaching the startIndex
                }
                //System.out.println("Successfully read line " + (linesProcessed) + " from the CSV file.");

                String[] nextLine = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split CSV line properly
                if (nextLine.length >= 7) {
                    Recipe recipe = parseRecipeFromCSVLine(nextLine);
                    recipes.add(recipe);
                } else {
                    System.err.println("Invalid CSV format: Line " + linesProcessed + " does not contain all expected fields");
                }

                if (recipes.size() >= BATCH_SIZE) {
                    break; // Stop reading if BATCH_SIZE is reached
                }
            }
        }

        return recipes;
    }

    private Recipe parseRecipeFromCSVLine(String[] line) {
        String title = line[1].trim(); // Remove leading/trailing spaces
        List<String> ingredients = parseListField(line[2]);
        List<String> directions = parseListField(line[3]);
        String link = line[4].trim(); // Remove leading/trailing spaces
        String source = line[5].trim(); // Remove leading/trailing spaces
        List<String> ner = parseListField(line[6]);
        return new Recipe(title, ingredients, directions, link, source, ner);
    }

    private List<String> parseListField(String field) {
        List<String> list = new ArrayList<>();
        String[] items = field.split(",");
        for (String item : items) {
            list.add(item.trim()); // Remove leading/trailing spaces
        }
        return list;
    }
}
