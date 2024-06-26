# CargoFlow

Comprehensive transportation management application tailored for companies handling diverse 
cargo operations. It efficiently organizes employees into administrative roles and drivers, 
with detailed profiles, including personal information and driving qualifications. The system 
seamlessly tracks vehicle specifications, route details, and cargo logistics, offering insightful 
reports like monthly route summaries and mileage analysis for both employees and vehicles.

## Prerequisites
- [Git](https://git-scm.com/downloads)
- [Docker](https://docs.docker.com/get-docker/)

## Installation
- Clone the Repository.
```bash
git clone https://github.com/KsaweryZietara/cargoflow.git
cd cargoflow
```

- Use Docker Compose to set up and run the application along with its dependencies.
```bash
docker compose up -d
```

- To stop and remove the containers created by Docker Compose, you can use the following command.
```bash
docker compose down
```

## React application
The React application is available on port 3000. You can access it by navigating to:
```
http://localhost:3000
```

#### Default Admin Credentials
- Username: `admin`
- Password: `admin`

## Documentation
For comprehensive documentation of API endpoints and functionalities, please refer to Swagger documentation.
Swagger provides an interactive interface where you can explore endpoints, parameters, request/response payloads, and even test API 
requests directly within your browser.
```
http://localhost:8080/docs
```
