# Indie Game Discovery

A personalized game recommendation engine built with Spring Boot, Spring AI, and MongoDB Atlas. Companion project for the Foojay article.

## Prerequisites

- Java 21+
- MongoDB Atlas cluster (free tier works)
- OpenAI API key

## Setup

1. Clone the repository:

```bash
git clone https://github.com/parvezmrobin/indie-game-discovery.git
cd indie-game-discovery
```

2. Set environment variables:

```bash
export MONGODB_URI="mongodb+srv://<username>:<password>@<cluster>.mongodb.net/indie-game-discovery?appName=devrel-tutorial-indie-game-discovery"
export OPENAI_API_KEY="sk-..."
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

The application seeds 25 indie games into your database on startup and generates vector embeddings for each one.

## API Endpoints

### Create a user profile

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "player1",
    "preferences": {
      "genres": {"roguelike": 0.9, "platformer": 0.7},
      "tags": {"pixel-art": 0.8, "challenging": 0.9},
      "mechanics": {"permadeath": 0.6, "procedural-generation": 0.8}
    }
  }'
```

### Get a user profile

```bash
curl http://localhost:8080/api/users/{userId}
```

### Get recommendations

```bash
curl http://localhost:8080/api/recommendations/{userId}
```

### Rate a game

```bash
curl -X POST http://localhost:8080/api/users/{userId}/ratings \
  -H "Content-Type: application/json" \
  -d '{"gameId": "<gameId>", "score": 5}'
```

Rating a game updates the user's preference weights, which influences future recommendations.

## Article

Read the full tutorial on [Foojay](https://foojay.io/placeholder).
