
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Given a url to a web page, extract a suitable image from that page. This will
 * attempt to follow a method similar to Google+, as described <a href=
 * "http://webmasters.stackexchange.com/questions/25581/how-does-google-plus-select-an-image-from-a-shared-link"
 * >here</a>
 * 
 */
public class ImageExtractor {

    // TODO: Add junit test case for this. (Construct Document from string, extract, check)
    public static String extractImageUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        String imageUrl = null;

        imageUrl = getImageFromSchema(document);
        if (imageUrl != null) {
            return imageUrl;
        }

        imageUrl = getImageFromOpenGraph(document);
        if (imageUrl != null) {
            return imageUrl;
        }

        imageUrl = getImageFromTwitterCard(document);
        if (imageUrl != null) {
            return imageUrl;
        }

        imageUrl = getImageFromLinkRel(document);
        if (imageUrl != null) {
            return imageUrl;
        }

        imageUrl = getImageFromGuess(document);
        if (imageUrl != null) {
            return imageUrl;
        }

        return imageUrl;
    }

    private static String getImageFromGuess(Document document) {
        // TODO
        return null;
    }

    private static String getImageFromLinkRel(Document document) {
        Element link = document.select("link[rel=image_src]").first();
        if (link != null) {
            return link.attr("abs:href");
        }
        return null;
    }

    private static String getImageFromTwitterCard(Document document) {
        Element meta = document.select("meta[name=twitter:card][content=photo]").first();
        if (meta == null) {
            return null;
        }
        Element image = document.select("meta[name=twitter:image]").first();
        return image.attr("abs:content");
    }

    private static String getImageFromOpenGraph(Document document) {
        Element image = document.select("meta[property=og:image]").first();
        if (image != null) {
            return image.attr("abs:content");
        }
        Element secureImage = document.select("meta[property=og:image:secure]").first();
        if (secureImage != null) {
            return secureImage.attr("abs:content");
        }
        return null;
    }

    private static String getImageFromSchema(Document document) {
        Element container =
            document.select("*[itemscope][itemtype=http://schema.org/ImageObject]").first();
        if (container == null) {
            return null;
        }

        Element image = container.select("img[itemprop=contentUrl]").first();
        if (image == null) {
            return null;
        }
        return image.absUrl("src");
    }
}