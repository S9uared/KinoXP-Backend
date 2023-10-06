package dat3.kinoxp.api_facade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
class TextBody {
    private String text;
    public TextBody(String text) {
        this.text = text;
    }
}

@Getter
@Setter
@NoArgsConstructor
class TranslationResponse {
    private List<TranslationItem> translations;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TranslationItem {
        private String text;
        private String to;
    }
}

@Service
public class AzureTranslate {

    RestTemplate restTemplate;

    public AzureTranslate() {
        restTemplate = new RestTemplate();
    }

    @Value("${app.azure-translate-key}")
    String SUBSCRIPTION_KEY;
    String REGION = "northeurope";

    String AZURE_TRANSLATE_URL = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=da";

    public String translate(String txt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
        headers.set("Ocp-Apim-Subscription-Region", REGION);

        TextBody t = new TextBody(txt);
        List<TextBody> body = Collections.singletonList(t);
        HttpEntity<List<TextBody>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<TranslationResponse[]> response = restTemplate.exchange(AZURE_TRANSLATE_URL, HttpMethod.POST, entity, TranslationResponse[].class);
        System.out.println(response.getBody());

        return response.getBody()[0].getTranslations().get(0).getText();
    }

    public static void main(String[] args) {
        String result = new AzureTranslate().translate("hello World");
        System.out.println(result);
    }
}