import java.util.List;

public class Recipe {
    private String title;
    private final List<String> ingredients;
    private final List<String> directions;
    private String link;
    private String source;
    private final List<String> ner;

    public Recipe(String title, List<String> ingredients, List<String> directions, String link, String source, List<String> ner) {
        this.title = title;
        this.ingredients = ingredients;
        this.directions = directions;
        this.link = link;
        this.source = source;
        this.ner = ner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getNer() {
        return ner;
    }
}
