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

