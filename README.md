# Web Store

## Prerequisites

Before you begin, ensure that you have the following installed on your system:

- Docker: [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: [Install Docker Compose](https://docs.docker.com/compose/install/)

## Docker Secrets Setup

For security purposes, the JWT encryption key is stored in a Docker Secret. To deploy this application, you need to create a secret called `jwt_secret` with your desired JWT secret key.

### Steps to Create Docker Secret

1. Initialize Docker Swarm (if not already initialized):
    ```bash
    docker swarm init
    ```

2. Create the Docker Secret with your JWT secret key:
    ```bash
    docker secret create jwt_secret jwt-secret-key.txt
    ```

    Replace `jwt-secret-key.txt` with the path to a file containing your JWT secret key. This file should contain only the secret key, with no extra whitespace or new lines.

3. Verify that the secret has been created:
    ```bash
    docker secret ls
    ```

    You should see `jwt_secret` in the list of secrets.

## Running the Application with Docker Compose

Once the secret is set up, you can run the application using Docker Compose.

### Steps to Run

1. Build and start the application using Docker Compose:
    ```bash
    docker-compose up --build
    ```

2. To stop the application:
    ```bash
    docker-compose down
    ```