import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LanguageContentExtractor implements ContentExtractor{

    @Override
    public List<Content> extractsContent(String json) {
        JsonParser parser = new JsonParser();
        List<Map<String, String>> attributesList = parser.parse(json);

        List<Content> contents = new ArrayList<>();

        for (Map<String, String> attribute : attributesList) {
            var title = attribute.get("title");
            var imageUrl = attribute.get("image");
            Content content = new Content(title, imageUrl);
            contents.add(content);
        }
        return contents;
    }
}
