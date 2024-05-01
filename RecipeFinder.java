import java.util.ArrayList;
import java.util.List;

public class RecipeFinder {
    private List<Recipe> allRecipes;
    private static final int REQUIRED_AVAILABLE_INGREDIENTS = 2;

    private static final int MAX_OTHER_INGREDIENTS = 2;

    public RecipeFinder(List<Recipe> allRecipes) {
        this.allRecipes = allRecipes;
    }

    public List<Recipe> findRecipesByIngredientsInBatch(List<Recipe> batch, List<String> availableIngredients) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : batch) {
            if (hasAllIngredients(recipe, availableIngredients)) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }

    public boolean hasAllIngredients(Recipe recipe, List<String> availableIngredients) {
        int availableIngredientsCount = 0;
        int otherIngredientsCount = 0;

        for (String recipeIngredient : recipe.getIngredients()) {
            boolean found = false;
            for (String availableIngredient : availableIngredients) {
                if (recipeIngredient.contains(availableIngredient)) {
                    found = true;
                    availableIngredientsCount++;
                    break;
                }
            }
            if (!found) {
                otherIngredientsCount++;
                if (otherIngredientsCount > MAX_OTHER_INGREDIENTS) {
                    return false; // If more than 3 other ingredients are found, return false
                }
            }
        }
        if (availableIngredients.size() < REQUIRED_AVAILABLE_INGREDIENTS) {
        	return availableIngredientsCount >= 1;
        }else {return availableIngredientsCount >= REQUIRED_AVAILABLE_INGREDIENTS;}
        
    }
}
