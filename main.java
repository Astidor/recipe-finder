import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {
    private static final int MAX_RECIPES_PER_PAGE = 10;
    private static int startIndex = 0; // Declaring startIndex as a global variable
    private static List<Integer> startIndexList = new ArrayList<>(); // List to store start indexes for each page
    private static int currentPage = 1; // Variable to track the current page number



    public static void main(String[] args) {
        RecipeReader recipeReader = new RecipeReader();
        try {
            List<Recipe> allRecipes = new ArrayList<>();
            List<Recipe> matchingRecipes = new ArrayList<>();

            // Prompt the user to enter available ingredients
            Scanner scanner1 = new Scanner(System.in);
            System.out.print("Enter available ingredients (comma-separated): ");
            String ingredientsInput = scanner1.nextLine();
            List<String> availableIngredients = Arrays.asList(ingredientsInput.split(","));

            // Prompt the user to enter the CSV file path
            System.out.print("Enter the CSV file name (it should be in the same folder): ");
            String fileName  = scanner1.nextLine();
            String filePath = "./" + fileName;

      

            // Create a RecipeFinder instance
            RecipeFinder recipeFinder = new RecipeFinder(allRecipes);
            startIndexList.add(startIndex); // Add the new start index to the list

            // Read and process CSV file in batches until enough recipes are found
            startIndex = processCsvBatches(recipeReader, recipeFinder, allRecipes, matchingRecipes, availableIngredients, filePath);
            startIndexList.add(startIndex); // Add the new start index to the list

            //System.out.println(startIndex);

            // Display matching recipes
            displayRecipes(matchingRecipes, availableIngredients);

            try (// User interaction loop
			Scanner scanner = new Scanner(System.in)) {
				while (!matchingRecipes.isEmpty()) {
					System.out.println("--------------------------------------");

					System.out.println("Page : " + currentPage);
				    System.out.print("Choose a recipe by entering its number, or enter 'n' for the next page. If you want to exit, type \"exit\": ");
				    String userInput = scanner.nextLine();
				    if (userInput.equalsIgnoreCase("n")) {
				        // Move to the next page
				    	matchingRecipes.clear();
                        currentPage++;

				    	startIndex = processCsvBatches(recipeReader, recipeFinder, allRecipes, matchingRecipes, availableIngredients,filePath);
                        startIndexList.add(startIndex); // Add the new start index to the list

				    	displayRecipes(matchingRecipes, availableIngredients);
                        //System.out.println("Start Index List: " + startIndexList); // Output the list of start indexes
                        //System.out.println("Start Index : " + startIndex); // Output the list of start indexes

                    } else if (userInput.equalsIgnoreCase("p")) {
                        // Move to the previous page
                        if (startIndexList.size() > 2) {
                            matchingRecipes.clear();
                            currentPage--;

                            startIndexList.remove(startIndexList.size() - 1); // Remove the current start index from the list
                            startIndex = startIndexList.get(startIndexList.size() - 2); // Get the previous start index
                            startIndex = processCsvBatches(recipeReader, recipeFinder, allRecipes, matchingRecipes, availableIngredients,filePath);
                            displayRecipes(matchingRecipes, availableIngredients);
                            //System.out.println("Start Index List: " + startIndexList); // Output the list of start indexes
                            //System.out.println("Start Index : " + startIndex); // Output the list of start indexes

                        } else {
                            System.out.println("Already at the first page.");
                        }
                    } else if (userInput.equalsIgnoreCase("exit")) {
                        // Exit the program
                        break;

				        
				    } else {
				        try {
				            int recipeIndex = Integer.parseInt(userInput) - 1;
				            if (recipeIndex >= 0 && recipeIndex < matchingRecipes.size()) {
				                Recipe chosenRecipe = matchingRecipes.get(recipeIndex);
				                // Print details of the chosen recipe
				                printRecipeDetails(chosenRecipe);
				            } else {
				                System.out.println("Invalid recipe number. Please try again.");
				            }
				        } catch (NumberFormatException e) {
				            System.out.println("Invalid input. Please enter a valid number or 'n' for the next page.");
				        }
				    }
				}
			}

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
    
    private static int processCsvBatches(RecipeReader recipeReader, RecipeFinder recipeFinder, List<Recipe> allRecipes, List<Recipe> matchingRecipes, List<String> availableIngredients,String filePath) throws IOException, CsvException {
        int localStartIndex = startIndex; // Local variable to track the startIndex

    	while (matchingRecipes.size() < MAX_RECIPES_PER_PAGE) {
            List<Recipe> batchRecipes = recipeReader.readRecipesFromCSV(filePath, localStartIndex);
            if (batchRecipes.isEmpty()) {
                // No more recipes to read
                break;
            }
            allRecipes.addAll(batchRecipes);
            List<Recipe> batchMatches = recipeFinder.findRecipesByIngredientsInBatch(batchRecipes, availableIngredients);
            matchingRecipes.addAll(batchMatches);
            
            localStartIndex += recipeReader.getBatchSize();


        }
        return localStartIndex; // Return the updated startIndex

    }

    private static void displayRecipes(List<Recipe> recipes, List<String> availableIngredients) {
        System.out.println("Matching recipes based on available ingredients:");
        if (recipes.isEmpty()) {
            System.out.println("No matching recipes found.");
            return;
        }

        for (int i = 0; i < Math.min(recipes.size(), MAX_RECIPES_PER_PAGE); i++) {
            Recipe recipe = recipes.get(i);
            System.out.print((i + 1) + ". " + recipe.getTitle() + ". ");
            System.out.println("!!Ingredients needed:!! " + recipe.getNer().toString().replaceAll("[\\[\\]\"]", ""));
        }
    }


    private static void printRecipeDetails(Recipe recipe) {
        System.out.println("");

        System.out.println("Recipe: " + recipe.getTitle());
        System.out.println("");

        System.out.println("Ingredients: " + recipe.getIngredients().toString().replaceAll("[\\[\\]\"]", ""));
        System.out.println("Directions: " + recipe.getDirections().toString().replaceAll("[\\[\\]\"]", ""));
        System.out.println("Link: " + recipe.getLink());
        System.out.println("Source: " + recipe.getSource());
        System.out.println("NER: " + recipe.getNer().toString().replaceAll("[\\[\\]\"]", ""));
    }
}
