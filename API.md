# Stark VMS — Vehicle Management API Documentation

**Base URL:** `http://localhost:9001/api`

---

## 1. Vehicle Fields & Schema

| Field | Type | Required | Constraints / Default | Example |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `Long` | Read-only | Auto-generated primary key | `1` |
| `vehicleNumber` | `String` | **Yes** | Unique, non-blank | `"DHA-METRO-GA-12-3456"` |
| `vehicleType` | `Enum` (`VehicleType`) | **Yes** | Non-null (`CAR`, `SUV`, `VAN`, `MICROBUS`, `BUS`, `TRUCK`, `PICKUP`, `MOTORCYCLE`) | `"CAR"` |
| `brand` | `String` | **Yes** | Non-blank | `"Toyota"` |
| `model` | `String` | **Yes** | Non-blank | `"Corolla"` |
| `manufacturingYear` | `Integer` | No | Nullable | `2023` |
| `color` | `String` | No | Nullable | `"Silver"` |
| `fuelType` | `Enum` (`FuelType`) | No | Nullable (`PETROL`, `DIESEL`, `OCTANE`, `CNG`, `LPG`, `HYBRID`, `ELECTRIC`) | `"HYBRID"` |
| `seatingCapacity` | `Integer` | No | Nullable | `5` |
| `driverName` | `String` | No | Nullable | `"Rahim Uddin"` |
| `driverPhone` | `String` | No | Nullable | `"+8801711000000"` |
| `status` | `Enum` (`VehicleStatus`) | No | Defaults to `"AVAILABLE"` (`AVAILABLE`, `IN_USE`, `MAINTENANCE`, `INACTIVE`) | `"AVAILABLE"` |
| `createdAt` | `LocalDateTime` | Read-only | Auto-set on creation | `"2026-11-12T09:00:00"` |
| `updatedAt` | `LocalDateTime` | Read-only | Auto-updated on save | `"2026-11-12T09:00:00"` |

---

## 2. API Endpoints

All `/api/vehicles` endpoints require authentication. Send the JWT from
`POST /api/auth/login` as `Authorization: Bearer <token>`; without it every
request returns `401`. See section 7.

### 2.1 Get Paginated Vehicles
* **Method:** `GET`
* **Endpoint:** `/api/vehicles?page=0&size=10`
* **Query Parameters:**
  * `page` (optional, default `0`) — Zero-based page index
  * `size` (optional, default `10`) — Number of items per page

#### Success Response (`200 OK`)
```json
{
  "content": [
    {
      "id": 1,
      "vehicleNumber": "DHA-METRO-GA-12-3456",
      "vehicleType": "CAR",
      "brand": "Toyota",
      "model": "Corolla",
      "manufacturingYear": 2023,
      "color": "Silver",
      "fuelType": "HYBRID",
      "seatingCapacity": 5,
      "driverName": "Rahim Uddin",
      "driverPhone": "+8801711000000",
      "status": "AVAILABLE",
      "createdAt": "2026-11-12T09:00:00",
      "updatedAt": "2026-11-12T09:00:00"
    }
  ],
  "hasNext": false,
  "totalElements": 1
}
```

---

### 2.2 Get Single Vehicle by ID
* **Method:** `GET`
* **Endpoint:** `/api/vehicles/{id}`

#### Success Response (`200 OK`)
```json
{
  "id": 1,
  "vehicleNumber": "DHA-METRO-GA-12-3456",
  "vehicleType": "CAR",
  "brand": "Toyota",
  "model": "Corolla",
  "manufacturingYear": 2023,
  "color": "Silver",
  "fuelType": "HYBRID",
  "seatingCapacity": 5,
  "driverName": "Rahim Uddin",
  "driverPhone": "+8801711000000",
  "status": "AVAILABLE",
  "createdAt": "2026-11-12T09:00:00",
  "updatedAt": "2026-11-12T09:00:00"
}
```

#### Not Found Response (`404 Not Found`)
```json
{
  "timestamp": "2026-11-12T09:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Vehicle not found with id: 99"
}
```

---

### 2.3 Create Vehicle
* **Method:** `POST`
* **Endpoint:** `/api/vehicles`
* **Headers:** `Content-Type: application/json`

#### Example Request Body
```json
{
  "vehicleNumber": "DHA-METRO-GA-12-3456",
  "vehicleType": "CAR",
  "brand": "Toyota",
  "model": "Corolla",
  "manufacturingYear": 2023,
  "color": "Silver",
  "fuelType": "HYBRID",
  "seatingCapacity": 5,
  "driverName": "Rahim Uddin",
  "driverPhone": "+8801711000000",
  "status": "AVAILABLE"
}
```

#### Success Response (`201 Created`)
```json
{
  "id": 1,
  "vehicleNumber": "DHA-METRO-GA-12-3456",
  "vehicleType": "CAR",
  "brand": "Toyota",
  "model": "Corolla",
  "manufacturingYear": 2023,
  "color": "Silver",
  "fuelType": "HYBRID",
  "seatingCapacity": 5,
  "driverName": "Rahim Uddin",
  "driverPhone": "+8801711000000",
  "status": "AVAILABLE",
  "createdAt": "2026-11-12T09:00:00",
  "updatedAt": "2026-11-12T09:00:00"
}
```

---

### 2.4 Update Vehicle
* **Method:** `PUT`
* **Endpoint:** `/api/vehicles/{id}`
* **Headers:** `Content-Type: application/json`

#### Example Request Body
```json
{
  "vehicleNumber": "DHA-METRO-GA-12-3456",
  "vehicleType": "CAR",
  "brand": "Toyota",
  "model": "Corolla",
  "manufacturingYear": 2023,
  "color": "Black",
  "fuelType": "HYBRID",
  "seatingCapacity": 5,
  "driverName": "Karim Ahmed",
  "driverPhone": "+8801811000000",
  "status": "IN_USE"
}
```

#### Success Response (`200 OK`)
```json
{
  "id": 1,
  "vehicleNumber": "DHA-METRO-GA-12-3456",
  "vehicleType": "CAR",
  "brand": "Toyota",
  "model": "Corolla",
  "manufacturingYear": 2023,
  "color": "Black",
  "fuelType": "HYBRID",
  "seatingCapacity": 5,
  "driverName": "Karim Ahmed",
  "driverPhone": "+8801811000000",
  "status": "IN_USE",
  "createdAt": "2026-11-12T09:00:00",
  "updatedAt": "2026-11-12T09:05:00"
}
```

---

### 2.5 Delete Vehicle
* **Method:** `DELETE`
* **Endpoint:** `/api/vehicles/{id}`

#### Success Response (`204 No Content`)
Empty response body.

---

## 3. Error Responses

### 3.1 Duplicate Vehicle Number (`409 Conflict`)
Returned when creating or updating a vehicle with a `vehicleNumber` that already exists.
```json
{
  "timestamp": "2026-11-12T09:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Vehicle with number 'DHA-METRO-GA-12-3456' already exists"
}
```

### 3.2 Validation Error (`400 Bad Request`)
Returned when required fields are missing/blank or an invalid enum/payload is sent.
```json
{
  "timestamp": "2026-11-12T09:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Vehicle number is required; Vehicle type is required; Brand is required; Model is required",
  "errors": {
    "vehicleNumber": "Vehicle number is required",
    "vehicleType": "Vehicle type is required",
    "brand": "Brand is required",
    "model": "Model is required"
  }
}
```



### 3.3 Invalid Enum Value (`400 Bad Request`)
Returned when an enum field carries a value outside its allowed set. The offending
field appears in `errors`, and the message lists the accepted values.
```json
{
  "timestamp": "2026-11-21T20:29:25.3569484",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid value 'Car' for vehicleType. Allowed values: CAR, SUV, VAN, MICROBUS, BUS, TRUCK, PICKUP, MOTORCYCLE",
  "errors": {
    "vehicleType": "Invalid value 'Car' for vehicleType. Allowed values: CAR, SUV, VAN, MICROBUS, BUS, TRUCK, PICKUP, MOTORCYCLE"
  }
}
```

Malformed JSON and unparseable path or query parameters still return the generic
`"Invalid request payload or parameters"` message with no `errors` map.

### 3.4 Unauthenticated (`401 Unauthorized`)
Returned when the `Authorization` header is missing, malformed, expired or signed
with the wrong key.
```json
{
  "timestamp": "2026-11-21T20:55:38.868196100",
  "status": 401,
  "error": "Unauthorized",
  "message": "Authentication required. Send a valid Bearer token."
}
```

A failed login returns the same status with a different message:
```json
{
  "timestamp": "2026-11-21T20:53:09.3869992",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password"
}
```
The message is identical for an unknown username and a wrong password, so the
response cannot be used to discover which usernames exist.

---

## 4. Configuration Profiles

All vehicle data is real, database-backed data — the application ships with no
seed or mock records. The database starts empty and is populated through the
`POST /api/vehicles` endpoint.

The database is **PostgreSQL**. Configuration is split across three files in
`src/main/resources`:

| File | Purpose |
| :--- | :--- |
| `application.properties` | Shared settings; binds to `${PORT:9001}` and activates `dev` by default |
| `application-dev.properties` | Local PostgreSQL datasource, `ddl-auto=update`, SQL logging on |
| `application-prod.properties` | Env-supplied credentials, `ddl-auto=validate`, SQL logging off, tuned pool |

| Setting | `dev` | `prod` |
| :--- | :--- | :--- |
| `spring.jpa.hibernate.ddl-auto` | `update` | `validate` |
| Datasource | hardcoded localhost | `${DB_URL}` / `${DB_USER}` / `${DB_PASSWORD}` |
| `spring.jpa.show-sql` | `true` | `false` |

Activate production with `SPRING_PROFILES_ACTIVE=prod`.

---

## 5. Database Setup

### 5.1 Local development

The `dev` profile expects PostgreSQL on `localhost:5432`. Credentials are read
from the `DB_USER` and `DB_PASSWORD` environment variables so that no real
password is ever committed.

Create a dedicated role and database once, as the `postgres` superuser. The role
owns the database, so it needs no further grants:

```powershell
$psql = "C:\Program Files\PostgreSQL\18\bin\psql.exe"
& $psql -U postgres -h 127.0.0.1 -c "CREATE USER stark_vms WITH PASSWORD 'choose-a-password';"
& $psql -U postgres -h 127.0.0.1 -c "CREATE DATABASE stark_group_vms OWNER stark_vms;"
```

Then store the credentials in the environment (new terminals pick these up; an
already-running editor must be restarted):

```powershell
setx DB_USER "stark_vms"
setx DB_PASSWORD "choose-a-password"
```

Run `./gradlew bootRun`. With `ddl-auto=update`, Hibernate creates the `vehicles`
table on first start, and the API is available at `http://localhost:9001/api`.

If table creation fails with `permission denied for schema public`, grant it
explicitly: `GRANT ALL ON SCHEMA public TO stark_vms;`

### 5.2 Case-insensitive uniqueness

`vehicleNumber` is compared case-insensitively by the service layer, so
`POST`ing `abc-123` when `ABC-123` exists returns `409 Conflict`. PostgreSQL
comparisons are case-sensitive by default, so the plain `UNIQUE` constraint
Hibernate generates does not enforce that rule at the database level. Add a
functional unique index once per database to enforce it there as well:

```sql
CREATE UNIQUE INDEX IF NOT EXISTS ux_vehicles_vehicle_number_lower
  ON vehicles (LOWER(vehicle_number));
```

Hibernate's `validate` does not inspect indexes, so this index is safe to add by
hand and will not cause schema validation to fail.

### 5.3 Production schema

The `prod` profile uses `ddl-auto=validate`: Hibernate never alters the schema, it
only verifies that the schema matches the entities. Apply the schema below to the
production database once, before the first deploy.

This DDL was exported from a schema Hibernate generated itself, so the column
types match exactly what `validate` expects. It already includes the
case-insensitive index from section 5.2.

```sql
CREATE TABLE vehicles (
    id                 bigint                          NOT NULL,
    vehicle_number     character varying(255)          NOT NULL,
    vehicle_type       character varying(255)          NOT NULL,
    brand              character varying(255)          NOT NULL,
    model              character varying(255)          NOT NULL,
    manufacturing_year integer,
    color              character varying(255),
    fuel_type          character varying(255),
    seating_capacity   integer,
    driver_name        character varying(255),
    driver_phone       character varying(255),
    status             character varying(255)          NOT NULL,
    created_at         timestamp(6) without time zone  NOT NULL,
    updated_at         timestamp(6) without time zone  NOT NULL,
    CONSTRAINT vehicles_vehicle_type_check CHECK (vehicle_type::text = ANY (ARRAY['CAR','SUV','VAN','MICROBUS','BUS','TRUCK','PICKUP','MOTORCYCLE']::text[])),
    CONSTRAINT vehicles_fuel_type_check    CHECK (fuel_type::text    = ANY (ARRAY['PETROL','DIESEL','OCTANE','CNG','LPG','HYBRID','ELECTRIC']::text[])),
    CONSTRAINT vehicles_status_check       CHECK (status::text       = ANY (ARRAY['AVAILABLE','IN_USE','MAINTENANCE','INACTIVE']::text[]))
);

ALTER TABLE vehicles ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME vehicles_id_seq START WITH 1 INCREMENT BY 1 CACHE 1
);

ALTER TABLE vehicles ADD CONSTRAINT vehicles_pkey PRIMARY KEY (id);
ALTER TABLE vehicles ADD CONSTRAINT uk_vehicles_vehicle_number UNIQUE (vehicle_number);

CREATE UNIQUE INDEX ux_vehicles_vehicle_number_lower
    ON vehicles (LOWER(vehicle_number));
```

The `users` table holds login accounts. Passwords are stored only as BCrypt
hashes, never in plain text.

```sql
CREATE TABLE users (
    id         bigint                          NOT NULL,
    username   character varying(255)          NOT NULL,
    password   character varying(255)          NOT NULL,
    full_name  character varying(255)          NOT NULL,
    created_at timestamp(6) without time zone  NOT NULL
);

ALTER TABLE users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME users_id_seq START WITH 1 INCREMENT BY 1 CACHE 1
);

ALTER TABLE users ADD CONSTRAINT users_pkey PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT uk_users_username UNIQUE (username);
```

To regenerate it after an entity change, let Hibernate build the table locally with
the `dev` profile and dump the structure again:

```powershell
& "C:\Program Files\PostgreSQL\18\bin\pg_dump.exe" -U stark_vms --schema-only `
    --no-owner --no-privileges -t vehicles stark_group_vms
```

---

## 6. Deployment (Render)

Render has no native Java runtime, so the service is deployed as a Docker container.
The included `Dockerfile` builds with the Gradle wrapper and runs the jar on a slim
JRE image.

Create a **Web Service** on Render pointing at this repository, with runtime
**Docker**. Render detects the `Dockerfile` automatically; no build or start command
is required. Add a **PostgreSQL** instance in the same Render workspace so the
service and database share a region.

### 6.1 Required environment variables

| Variable | Value |
| :--- | :--- |
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `DB_URL` | `jdbc:postgresql://<host>/<database>` — from Render's Internal Database URL |
| `DB_USER` | database username |
| `DB_PASSWORD` | database password |
| `JWT_SECRET` | HS256 signing key, at least 32 characters (startup fails without it) |
| `ADMIN_USERNAME` | bootstrap administrator, created on first start |
| `ADMIN_PASSWORD` | that account's password, stored only as a BCrypt hash |
| `CORS_ALLOWED_ORIGINS` | the Flutter Web origin, e.g. `https://stark-vms-web.onrender.com` |

`PORT` is injected by Render automatically and must not be set manually.

`CORS_ALLOWED_ORIGINS` takes a comma-separated list and defaults to `*`, which is
fine locally but should name the real origin in production. A request from any
other origin is rejected with `403` at the pre-flight.

Render supplies its connection string as `postgres://user:pass@host/db`, which is
not a JDBC URL. Convert it: use the host and database name in `DB_URL` as shown
above, and pass the user and password separately. Prefer the **Internal** Database
URL — it keeps traffic inside Render's network and needs no SSL configuration.

### 6.2 Health check

Set the service's **Health Check Path** to `/actuator/health`. It is the only
actuator endpoint exposed and the only path besides `/api/auth/login` reachable
without a token; it returns `200` with `{"status":"UP"}`.

This matters: every other path requires authentication, so leaving the health
check on the default `/` makes Render read the `401` as an unhealthy instance and
restart the service in a loop.

### 6.3 Deployment order

1. Create the PostgreSQL instance and apply the schema from section 5.3 — both
   the `vehicles` and the `users` table. With `ddl-auto=validate` the application
   refuses to start against a missing or mismatched schema.
2. Create the Web Service with runtime **Docker**, set the environment variables
   above and the health check path.
3. Deploy. On first start the administrator account is created from
   `ADMIN_USERNAME` / `ADMIN_PASSWORD`.
4. Log in once to confirm, then set `CORS_ALLOWED_ORIGINS` to the deployed
   frontend origin.

### 6.4 Free tier limitation

Render's free PostgreSQL instances **expire 30 days after creation**, followed by a
14-day grace period, after which the database and all its data are deleted. Storage
is capped at 1 GB and only one free database can be active per workspace. For
anything that must outlive a demo, upgrade the database to a paid plan or host it
elsewhere.

---

## 7. Authentication

Vehicle endpoints are protected with a stateless JWT bearer token. There is no
sign-up endpoint: accounts are created by the server operator, and the first
administrator is bootstrapped from environment variables on startup.

### 7.1 Log in
* **Method:** `POST`
* **Endpoint:** `/api/auth/login`
* **Headers:** `Content-Type: application/json`
* **Auth:** none — this is the only open endpoint

#### Request
```json
{ "username": "admin", "password": "your-password" }
```

#### Success Response (`200 OK`)
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400,
  "user": { "id": 1, "username": "admin", "fullName": "Administrator" }
}
```

`expiresIn` is in seconds. Blank fields return `400` with an `errors` map, and
wrong credentials return `401` (see section 3.4).

### 7.2 Current user
* **Method:** `GET`
* **Endpoint:** `/api/auth/me`
* **Auth:** required

Returns the account the token belongs to. Use it to validate a stored token on
app start.

```json
{ "id": 1, "username": "admin", "fullName": "Administrator" }
```

### 7.3 Calling protected endpoints

Send the token on every `/api/vehicles` request:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

The token is signed with HS256 and carries `sub` (username), `uid`, `name`,
`iat` and `exp`. It is stateless, so a server restart does not log users out —
provided `JWT_SECRET` is set to a fixed value.

### 7.4 Required environment variables

| Variable | Required | Purpose |
| :--- | :--- | :--- |
| `JWT_SECRET` | **yes in prod** | HS256 signing key, minimum 32 characters |
| `JWT_EXPIRY_SECONDS` | no | Token lifetime, default `86400` (24 hours) |
| `ADMIN_USERNAME` | first run | Username of the bootstrap administrator |
| `ADMIN_PASSWORD` | first run | Password for that account, hashed before storage |
| `ADMIN_FULL_NAME` | no | Display name, default `Administrator` |

The admin account is created only when `ADMIN_USERNAME` does not already exist,
so restarting never overwrites a changed password. If `JWT_SECRET` is missing the
`prod` profile fails to start; the `dev` profile generates a random secret and
logs a warning, which means tokens issued before a restart stop working.

Set them locally the same way as the database credentials:

```powershell
setx JWT_SECRET "a-long-random-string-of-at-least-32-characters"
setx ADMIN_USERNAME "admin"
setx ADMIN_PASSWORD "choose-a-password"
```

No password or secret belongs in a committed file. CORS is configured in
`SecurityConfig`, not with `@CrossOrigin`: once Spring Security is on the
classpath the filter chain rejects the pre-flight `OPTIONS` request before it
reaches a controller, so annotation-based CORS silently stops working.
