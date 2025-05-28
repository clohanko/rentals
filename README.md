# Back-end d'un portail de mise en relation pour de la location saisonnière

## Description
Ce projet constitue la partie back-end d'un portail de mise en relation pour la location saisonnière. Il est conçu pour fonctionner avec un front-end développé par Stéphanie.

L'API est entièrement documentée via Swagger pour en faciliter la découverte et l'utilisation.

## Fonctionnalités principales
- Authentification JWT
- Connexion (Login) et inscription (Register)
- Accès à son propre profil via `/me`
- Accès à la liste des locations via `/rentals`
- Détails d'une location individuelle
- Possibilité de laisser un message sur une location
- Upload d'image pour illustrer une location

## Technologies utilisées
- Java
- Spring Boot
- Spring Security (JWT)
- Maven
- Swagger/OpenAPI
- MySQL (avec MariaDB)

## Installation

### Prérequis
- JDK 17 ou version supérieure
- Maven 3.6 ou version supérieure
- Serveur de base de données MariaDB (ou MySQL)

### Configuration
Configurer les identifiants de connexion à la base de données dans `src/main/resources/application.properties` ou `application-dev.properties` :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nom_de_la_base
spring.datasource.username=utilisateur
spring.datasource.password=mot_de_passe
spring.jpa.hibernate.ddl-auto=update
```

### Lancement de l'application
```bash
mvn clean install
mvn spring-boot:run
```

L'application sera disponible sur `http://localhost:3001`

Accéder à l'interface Swagger : `http://localhost:3001/swagger-ui/index.html`

## Structure du projet
```
rentals2/
├── src/
│   ├── main/
│   │   ├── java/fr/openclassroom/rentals
│   │   │   ├── config/               # Configuration sécurité, JWT, Swagger
│   │   │   ├── controller/           # Contrôleurs REST (auth, utilisateurs, locations...)
│   │   │   ├── dto/                  # Objets de transfert de données
│   │   │   ├── exception/            # Gestion des exceptions personnalisées
│   │   │   ├── model/                # Entités JPA (User, Rental, Message)
│   │   │   ├── repository/           # Interfaces JPA
│   │   │   └── service/              # Logique métier et implémentations
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── schema.sql
│   │       ├── static/estate/        # Fichiers front-end statiques
│   │       └── templates/            # Modèles (si utilisés)
├── pom.xml                           # Configuration Maven
├── uploads/                          # Images uploadées
└── .git/                             # Dossier Git
```

## Exemples d'utilisation de l'API

### 1. Authentification
**POST** `/auth/login`
```json
{
  "username": "user@example.com",
  "password": "motdepasse"
}
```
*Retourne un JWT à utiliser dans les en-têtes Authorization.*

### 2. Inscription
**POST** `/auth/register`
```json
{
  "email": "newuser@example.com",
  "password": "motdepasse",
  "firstName": "Jean",
  "lastName": "Dupont"
}
```

### 3. Voir son profil
**GET** `/me`
*Requiert un JWT valide dans l'en-tête :*
```
Authorization: Bearer <token>
```

### 4. Liste des locations
**GET** `/rentals`

### 5. Détails d'une location
**GET** `/rentals/{id}`

### 6. Envoyer un message sur une location
**POST** `/messages`
```json
{
  "rentalId": 1,
  "content": "Bonjour, cette location est-elle disponible pour le mois prochain ?"
}
```

### 7. Upload d'une image pour une location
**POST** `/rentals/{id}/upload`
*Type : multipart/form-data*

## Contributeur
**Sébastien GERARD ChâTop**