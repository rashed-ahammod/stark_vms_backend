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

`PORT` is injected by Render automatically and must not be set manually.

Render supplies its connection string as `postgres://user:pass@host/db`, which is
not a JDBC URL. Convert it: use the host and database name in `DB_URL` as shown
above, and pass the user and password separately. Prefer the **Internal** Database
URL — it keeps traffic inside Render's network and needs no SSL configuration.

### 6.2 Free tier limitation

Render's free PostgreSQL instances **expire 30 days after creation**, followed by a
14-day grace period, after which the database and all its data are deleted. Storage
is capped at 1 GB and only one free database can be active per workspace. For
anything that must outlive a demo, upgrade the database to a paid plan or host it
elsewhere.
