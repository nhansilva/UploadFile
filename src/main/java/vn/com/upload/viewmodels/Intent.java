package vn.com.upload.viewmodels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author NhanVT3
 */
@Document(collection = "intent")
public class Intent {
    @Id
    String id;
    @Field(value = "name")
    String name;
    @Field(value = "responses")
    List<String> responses;
    @Field(value = "type")
    String type;

    @Field(value = "agent")
    String agent;
    @Field(value = "description")
    String description;

    public Intent() {
    }

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

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
