package ch.bbcag.javaee;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class MessageHandler implements Serializable {

    private List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void clearMessages() {
        messages.clear();
    }

}
