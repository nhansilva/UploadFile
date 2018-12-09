package vn.com.upload.viewmodels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author NhanVT3
 */
@Document(collection = "agent")
public class Agent {
    @Id
    private String id;
    @Field(value = "name")
    private String name;
    @Field(value = "phrases")
    private List<String> Phrases;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhrases() {
        return Phrases;
    }

    public void setPhrases(List<String> phrases) {
        Phrases = phrases;
    }

    public Agent() {
    }

    public Agent(String name, List<String> phrases) {
        this.name = name;
        Phrases = phrases;
    }
}
