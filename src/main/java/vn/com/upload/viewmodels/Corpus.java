package vn.com.upload.viewmodels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author NhanVT3
 */
@Document(collection = "corpus")
public class Corpus {
    @Id
    String id;
    @Field(value = "intent")
    String intent;
    @Field(value = "phrase")
    String phrase;

    public Corpus() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
