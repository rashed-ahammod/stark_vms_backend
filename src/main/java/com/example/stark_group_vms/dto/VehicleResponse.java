package com.example.stark_group_vms.dto;

import com.example.stark_group_vms.entity.Vehicle;
import com.example.stark_group_vms.enums.FuelType;
import com.example.stark_group_vms.enums.VehicleStatus;
import com.example.stark_group_vms.enums.VehicleType;

import java.time.LocalDateTime;

public record VehicleResponse(
        Long id,
        String vehicleNumber,
        VehicleType vehicleType,
        String brand,
        String model,
        Integer manufacturingYear,
        String color,
        FuelType fuelType,
        Integer seatingCapacity,
        String driverName,
        String driverPhone,
        VehicleStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static VehicleResponse from(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getVehicleNumber(),
                vehicle.getVehicleType(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getManufacturingYear(),
                vehicle.getColor(),
                vehicle.getFuelType(),
                vehicle.getSeatingCapacity(),
                vehicle.getDriverName(),
                vehicle.getDriverPhone(),
                vehicle.getStatus(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}

