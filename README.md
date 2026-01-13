# SpringBoot LLM: Hotel AI Assistant

**SpringBoot LLM** is a sophisticated Spring Boot application that leverages **Spring AI** and **Google Gemini** to provide an intelligent, multi-lingual chatbot assistant designed for hotel concierge and front desk operations.

Built with **Java 25**, this project demonstrates how to integrate Large Language Models (LLMs) into enterprise-grade applications with context awareness, tool calling (function calling), and robust error handling.

---

## 🌟 Key Features

- **🤖 Google Gemini Integration**: Uses Google's generative AI models (configured for `gemini-2.5-flash`) for fast and accurate responses.
- **🌍 Multi-lingual Support**: Automatically detects and responds in **English**, **Thai**, and **Burmese (Myanmar)**.
- **🧠 Conversational Memory**: Maintains context across multiple interactions using Spring AI's `ChatMemory`.
- **🛠️ Intelligent Tool Calling**:
  - `HotelFaqTool`: Provides authoritative answers on hotel services (Check-in/out, WiFi, Parking, etc.) using a mock data repository.
  - `RoomPriceTool`: Delivers real-time pricing information for hotel rooms.
- **🔍 Smart Scope Management**: Handles questions ranging from specific hotel policies to local attractions and general travel tips.
- **📊 Token Usage Tracking**: Logs prompt, completion, and total tokens for every interaction.
- **🛡️ Robust Error Handling**: Includes custom handling for API quota exhaustion (`RESOURCE_EXHAUSTED`) and fallback messaging.
- **⚙️ Monitoring**: Includes **Spring Boot Actuator** for application health and monitoring.

---

## 🛠️ Tech Stack

- **Languge**: Java 25
- **Framework**: Spring Boot 4.0.1
- **AI Integration**: Spring AI 1.1.2
- **Model**: Google GenAI (Gemini)
- **Utilities**: Lombok, Spring Boot Actuator
- **Build Tool**: Maven

---

## 🚀 Getting Started

### Prerequisites

- **Java 25** or higher.
- **Maven 3.9+**.
- A **Google Gemini API Key** (Google AI Studio).

### Configuration

1. Create a file named `src/main/resources/env.yml` (if it doesn't exist).
2. Add your Google API Key:

```yaml
GOOLE_GENAI_API_KEY: your_api_key_here
```

*Note: The application is configured to look for this environment variable in `application-local.yml`.*

### Running the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080` (default Spring Boot port).

---

## 🔌 API Documentation

### Ask a Question

**Endpoint**: `POST /api/ai/question`

**Parameters**:

- `X-Conversation-Id` (Query Param, Optional): Unique ID to maintain conversation context.
- `X-Hotel-Id` (Query Param, Optional): The ID of the hotel the assistant is serving.

**Request Body** (`text/plain`):

```text
"What time is check-out and how much is a deluxe room?"
```

**Response** (`application/json`):

```json
{
  "answer": "Check-out time is 12:00 PM. A deluxe room is priced at $150 per night.",
  "tokenUsage": {
    "promptTokens": 145,
    "completionTokens": 25,
    "totalTokens": 170
  }
}
```

---

## 📁 Project Structure

```text
src/main/java/com/zay/springbootllm/
├── chat/
│   ├── hotel/        # AI Tools and Intent definitions
│   ├── service/      # Gemini AI logic and prompt engineering
│   ├── web/          # REST Controllers
│   ├── data/         # Mock repositories for FAQ and Pricing
│   └── util/         # Language detection and DTOs
├── config/           # Spring AI and Global Exception handling
└── exceptions/       # Custom business exceptions
```

---

## 📝 License

This project is for demo purposes. Feel free to use and modify it for your own AI-powered Spring Boot applications!
