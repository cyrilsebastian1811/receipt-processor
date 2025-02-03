# Receipt Processor

### Prerequisites

- Clone the repo
```shell
git clone https://github.com/cyrilsebastian1811/receipt-processor.git
cd receipt-processor
git checkout master
```
- Docker

### Spin up container

```shell
# replace <project_directory> with your location
cd <project_directory>
docker-compose up --build -d
```

### Test APIs

Please use the following Swagger API to test endpoints: http://localhost:8080/swagger-ui.html

### Database access

The application makes use of in-memory H2 Database. Use this to access it: http://localhost:8080/h2-console

You'll be asked to login. Find the login information below

```markdown
JDBC URL=jdbc:h2:mem:receiptdb
User Name=sa
Password=password
```

### Tear down container

```shell
# replace <project_directory> with your location
cd <project_directory>
docker-compose down -v
./gradlew clean
```
