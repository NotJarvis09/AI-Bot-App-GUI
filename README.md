# AI Bot App GUI

An intelligent Java desktop AI bot application equipped with an interactive Graphical User Interface (GUI). Designed to simulate automated chat responses, this application allows users to interact with a chatbot engine powered by FAQ data structures and custom conversational logic.

## Features
- **Interactive Chat Interface:** Built using Java to provide a user-friendly desktop chat window.
- **Chatbot Engine:** Processes user queries and retrieves matching responses dynamically through dedicated classes like `ChatbotEngine` and `ChatbotEngine$FAQItem`.
- **FAQ Data Integration:** Utilizes structured JSON data files (`faq.json`) to manage and look up predefined question-answer pairs seamlessly.

## Project Structure
- `Task 3/AIBotApp.java`: Main application entry point and GUI controller.
- `Task 3/ChatbotEngine.java`: Core chatbot logic and processing engine.
- `Task 3/ChatbotEngine$FAQItem.java`: Handles individual FAQ data objects and items.
- `Task 3/faq.json`: JSON data storage for chatbot question-answer pairs.

## Prerequisites
- Java Development Kit (JDK) installed on your system.

## How to Run
1. Clone the repository:
```bash
git clone https://github.com/NotJarvis09/AI-Bot-App.git
```
2. Navigate to the project directory.
```bash
cd "Task 3"
```
3. Compile the Java source file
```bash
javac *.java
```
4. Run the application:
```bash
java Task 3.AIBotApp
```
