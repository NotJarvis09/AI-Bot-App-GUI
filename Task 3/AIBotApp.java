import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class AIBotApp extends Application {
    private final ChatbotEngine botEngine = new ChatbotEngine();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AI Chatbot - JavaFX Edition");

        // UI Components
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        TextField inputField = new TextField();
        inputField.setPromptText("Type your message here (e.g., 'What are your working hours?')...");
        inputField.setStyle("-fx-font-size: 14px;");

        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2b579a; -fx-text-fill: white;");

        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setPadding(new Insets(12));
        HBox.setHgrow(inputField, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(inputBox);

        // Event Handlers
        sendButton.setOnAction(e -> processMessage(chatArea, inputField));
        inputField.setOnAction(e -> processMessage(chatArea, inputField));

        primaryStage.setScene(new Scene(root, 550, 450));
        primaryStage.show();
        
        // Initial bot greeting
        chatArea.appendText("Bot: Hello! I am your Java AI assistant. How can I help you today?\n\n");
    }

    private void processMessage(TextArea chatArea, TextField inputField) {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        chatArea.appendText("You: " + userInput + "\n");
        String response = botEngine.getResponse(userInput);
        chatArea.appendText("Bot: " + response + "\n\n");
        inputField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/**
 * Core Chatbot Engine combining NLP Tokenization and FAQ Intent Matching
 */
class ChatbotEngine {
    private final List<FAQItem> knowledgeBase = new ArrayList<>();

    public ChatbotEngine() {
        loadKnowledgeBase();
    }

    private void loadKnowledgeBase() {
        knowledgeBase.add(new FAQItem(
            Arrays.asList("hello", "hi", "hey", "greetings", "good morning"),
            "Hello! How can I assist you with your inquiries today?"
        ));
        knowledgeBase.add(new FAQItem(
            Arrays.asList("hours", "timing", "open", "schedule", "work"),
            "We are open Monday through Friday from 9:00 AM to 6:00 PM IST."
        ));
        knowledgeBase.add(new FAQItem(
            Arrays.asList("pricing", "cost", "price", "plans", "fees"),
            "We offer flexible pricing tiers starting at ₹999/month for individuals and custom enterprise packages."
        ));
        knowledgeBase.add(new FAQItem(
            Arrays.asList("help", "support", "contact", "issue"),
            "You can reach our support team 24/7 at support@aichatbot.in or via the live portal."
        ));
        knowledgeBase.add(new FAQItem(
            Arrays.asList("java", "nlp", "technology", "stack"),
            "This chatbot is built using Java, implementing custom NLP tokenization and a JavaFX graphical interface."
        ));
    }

    public String getResponse(String rawInput) {
        List<String> tokens = tokenize(rawInput);
        if (tokens.isEmpty()) {
            return "I didn't catch that. Could you please rephrase?";
        }

        FAQItem bestMatch = null;
        int maxScore = 0;

        for (FAQItem item : knowledgeBase) {
            int score = calculateMatchScore(tokens, item.patterns);
            if (score > maxScore) {
                maxScore = score;
                bestMatch = item;
            }
        }

        if (bestMatch != null && maxScore > 0) {
            return bestMatch.response;
        }

        return "I'm sorry, I don't have information on that yet. Try asking about our hours, pricing, or tech stack!";
    }

    private List<String> tokenize(String text) {
        String cleaned = text.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleaned.split("\\s+");
        
        Set<String> stopWords = new HashSet<>(Arrays.asList("is", "the", "a", "an", "and", "to", "in", "for", "of", "on", "you", "your"));
        
        return Arrays.stream(words)
                .filter(w -> !w.isEmpty() && !stopWords.contains(w))
                .collect(Collectors.toList());
    }

    private int calculateMatchScore(List<String> userTokens, List<String> patterns) {
        int score = 0;
        for (String token : userTokens) {
            for (String pattern : patterns) {
                if (token.contains(pattern) || pattern.contains(token)) {
                    score++;
                }
            }
        }
        return score;
    }

    private static class FAQItem {
        List<String> patterns;
        String response;

        public FAQItem(List<String> patterns, String response) {
            this.patterns = patterns;
            this.response = response;
        }
    }
}