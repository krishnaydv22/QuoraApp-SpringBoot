## 📘 Quora App - Social Q&A Platform

A Quora-like social Q&A platform built using a reactive, event-driven microservice-inspired architecture. 
The system supports asking and answering questions, full-text search, user relationships (followers/following), and real-time view tracking with Kafka.

## 🚀 Tech Stack

**Backend Framework:** Spring Boot, Spring WebFlux (Reactive APIs)

**Databases:**

**MongoDB (Reactive) →** Stores questions & answers

**MySQL** → Manages user relationships (followers/following)

**Event Streaming:** Apache Kafka (asynchronous processing for view counts)

**Search Engine:** Elasticsearch (full-text search on questions & answers)

**Security:** (Planned) JWT Authentication, Spring Security

## ✨ Features

**✅ Reactive APIs with Spring WebFlux –** handle high concurrency with minimal resources

**✅ Asynchronous event processing with Kafka –** question view counts handled efficiently

**✅ Full-text search with Elasticsearch –** supports keyword & multi-word queries

**✅ Followers & Following system –** personalized feeds based on followed users’ posts

**✅ Hybrid Database Architecture –** MongoDB for documents, MySQL for relationships

**✅ Scalability & Low Latency –** designed with reactive + event-driven patterns

## 📂 Project Structure

QuoraApp-SpringBoot <br>
 ┣ 📂 src <br>
 ┃ ┣ 📂 main <br>
 ┃ ┃ ┣ 📂 java/com/algo/QuoraApp <br>
 ┃ ┃ ┃ ┣ 📂 adapter       # Converters between entities & DTOs <br>
 ┃ ┃ ┃ ┣ 📂 config        # Spring & application configurations <br>
 ┃ ┃ ┃ ┣ 📂 consumer      # Kafka consumers (e.g., view count events) <br>
 ┃ ┃ ┃ ┣ 📂 controller    # REST controllers (Reactive endpoints) <br>
 ┃ ┃ ┃ ┣ 📂 dto           # Data Transfer Objects <br>
 ┃ ┃ ┃ ┣ 📂 events        # Domain & Kafka events <br>
 ┃ ┃ ┃ ┣ 📂 exception     # Custom exception handling <br>
 ┃ ┃ ┃ ┣ 📂 model         # Entities (User, Question, Answer, Follow, etc.) <br>
 ┃ ┃ ┃ ┣ 📂 producer      # Kafka producers (publishing events) <br>
 ┃ ┃ ┃ ┣ 📂 repository    # MongoDB, MySQL, Elasticsearch repositories <br>
 ┃ ┃ ┃ ┣ 📂 services      # Business logic & reactive pipelines <br>
 ┃ ┃ ┃ ┣ 📂 utils         # Utility/helper classes <br>
 ┃ ┃ ┗ 📂 resources <br>
 ┃ ┃   ┣ application.yml  # Configurations (MongoDB, MySQL, Kafka, Elasticsearch) <br>
 ┣ 📄 build.gradle         # Dependencies & build configs <br>
 ┣ 📄 README.md            # Project documentation <br>

## ⚡ Getting Started
**✅ Prerequisites**

**Java 17+**

**Gradle**

**Docker (for MongoDB, MySQL, Kafka, Elasticsearch)**

**▶️ Run Required Services in Docker**

**Start MongoDB**
docker run -d --name mongo -p 27017:27017 mongo

**Start MySQL**
docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=quora -p 3306:3306 mysql:8

**Start Kafka**
docker run -d --name kafka -p 9092:9092 apache/kafka:4.0.0

**Start Elasticsearch**
docker run -d --name elasticsearch -p 9200:9200 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  docker.elastic.co/elasticsearch/elasticsearch:8.15.0

## 📡 API Endpoints
**📝 Question APIs**

POST /api/questions → Create a new question

GET /api/questions/{id} → Get question by ID

GET /api/questions/elasticsearch?query=keyword → Search questions (via Elasticsearch)

**💬 Answer APIs**

POST /api/answers → Post an answer to a question

**👥 Follow APIs**

POST /api/follow/{followerId}/{followeeId} → Follow a user

GET /api/follow/{userId}/followers → Get list of followers

GET /api/follow/{userId}/following → Get list of following

**🔮 Future Enhancements**

🔐 JWT-based authentication & role-based access (User/Admin)

🔔 Notification service for new followers & posts

⚡ Caching with Redis for faster feed retrieval

☸️ Deployment with Docker Compose & Kubernetes



