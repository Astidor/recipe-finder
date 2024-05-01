## Recipe Finder

The Recipe Finder is a Java application designed to help users find recipes based on available ingredients. It utilizes a database of recipes obtained from [RecipeNLG](https://recipenlg.cs.put.poznan.pl/), a repository of natural language generation for recipes.

### Features:

- **Ingredient-Based Search**: Users can input a list of available ingredients, and the application will find recipes that can be prepared using those ingredients.
- **Pagination**: Recipes are displayed in pages, with a maximum of 10 recipes per page.
- **Navigation**: Users can navigate through pages of recipes, as well as move to the previous or next page.
- **Recipe Details**: The application provides details for each recipe, including its title, ingredients, directions, link, source, and named entities recognition (NER).
- **Flexible Input**: Users can specify the filename of the CSV database file containing recipes, as well as the list of available ingredients, making the application adaptable to different datasets and user preferences.

### How to Use:

1. **Clone the Repository**: Clone this repository to your local machine using the following command:
   ```
   git clone <repository-url>
   ```

2. **Compile and Run**: Compile and run the `main.java` file to launch the Recipe Finder application.

3. **Input Ingredients**: When prompted, input the list of available ingredients separated by commas.

4. **Navigate Pages**: Use the options to navigate through pages of recipes. You can select a recipe to view its details or move to the next or previous page.

5. **Exit**: To exit the application, simply enter the appropriate command when prompted.

### Requirements:

- Java Development Kit (JDK)
- CSV database file containing recipes (e.g., `recipes.csv`)

### Credits:

- **RecipeNLG Database**: The recipe database used in this application is sourced from [RecipeNLG](https://recipenlg.cs.put.poznan.pl/), a project providing natural language generation for recipes.
