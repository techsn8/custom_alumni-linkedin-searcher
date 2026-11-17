**# Alumni LinkedIn Profile Searcher

Java 17 / Spring Boot REST backend for the supplied assignment. Default mode is `mock`, so you can run and demonstrate the API without PhantomBuster credentials. The `phantombuster` profile activates the configurable external integration later.

## Prerequisites
- JDK 17+
- Maven 3.8+
- PostgreSQL 14+ (or Docker Desktop)

## Start database
Run `docker compose up -d`, or create a PostgreSQL database named `alumni_db`. Defaults are user `postgres`, password `postgres`; override using `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.

## Run in mock mode (default)
```bash
mvn clean test
mvn spring-boot:run
```
Mock data is fabricated demonstration data, not real LinkedIn data.

## Postman requests
POST `http://localhost:8080/api/alumni/search`
```json
{
  "university": "University of XYZ",
  "designation": "Software Engineer",
  "passoutYear": 2020
}
```
The `passoutYear` field is optional; omit it or send null.

GET `http://localhost:8080/api/alumni/all`

GET `http://localhost:8080/api/alumni/1`

DELETE `http://localhost:8080/api/alumni/1` (returns 204).

## Activate PhantomBuster later
Create and configure a Phantom in your PhantomBuster account first. Then set `APP_PROFILE=phantombuster`, `PHANTOMBUSTER_API_KEY`, and `PHANTOMBUSTER_AGENT_ID`.

Windows PowerShell:
```powershell
$env:APP_PROFILE="phantombuster"
$env:PHANTOMBUSTER_API_KEY="your_key"
$env:PHANTOMBUSTER_AGENT_ID="your_agent_id"
mvn spring-boot:run
```
Linux/macOS:
```bash
export APP_PROFILE=phantombuster
export PHANTOMBUSTER_API_KEY=your_key
export PHANTOMBUSTER_AGENT_ID=your_agent_id
mvn spring-boot:run
```

The integration sends `university`, `designation`, and `passoutYear` as Phantom launch arguments. Configure your selected Phantom to accept those exact argument names; otherwise adapt `PhantomBusterSearchClient`. PhantomBuster executions may take longer than an immediate launch/fetch cycle. For production, implement asynchronous execution and status polling. Inspect the actual JSON produced by your Phantom and adapt the `rows()` method and field mapping if necessary: different Phantoms can return different output shapes.

## Layers
Controller (REST) -> Service (business logic) -> AlumniSearchClient (mock or PhantomBuster) -> Repository (PostgreSQL). DTO validation and centralized exception handling are included.

Keep credentials out of source control. Use third-party data access only in accordance with LinkedIn/PhantomBuster terms and applicable privacy requirements.**

# by S
# Swagger: http://localhost:8080/swagger-ui/index.html