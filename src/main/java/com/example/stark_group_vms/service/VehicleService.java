package com.example.stark_group_vms.service;

import com.example.stark_group_vms.dto.VehicleResponse;
import com.example.stark_group_vms.entity.Vehicle;
import com.example.stark_group_vms.enums.VehicleStatus;
import com.example.stark_group_vms.exception.DuplicateResourceException;
import com.example.stark_group_vms.exception.ResourceNotFoundException;
import com.example.stark_group_vms.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public Page<VehicleResponse> getVehicles(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return vehicleRepository.findAll(pageable).map(VehicleResponse::from);
    }

    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle = findEntityById(id);
        return VehicleResponse.from(vehicle);
    }

    @Transactional
    public VehicleResponse createVehicle(Vehicle vehicle) {
        String normalizedNumber = vehicle.getVehicleNumber().trim();
        if (vehicleRepository.existsByVehicleNumberIgnoreCase(normalizedNumber)) {
            throw new DuplicateResourceException("Vehicle with number '" + normalizedNumber + "' already exists");
        }
        vehicle.setId(null);
        vehicle.setVehicleNumber(normalizedNumber);
        if (vehicle.getStatus() == null) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
        }
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleResponse.from(saved);
    }

    @Transactional
    public VehicleResponse updateVehicle(Long id, Vehicle updatedDetails) {
        Vehicle existing = findEntityById(id);

        String normalizedNumber = updatedDetails.getVehicleNumber().trim();
        if (vehicleRepository.existsByVehicleNumberIgnoreCaseAndIdNot(normalizedNumber, id)) {
            throw new DuplicateResourceException("Vehicle with number '" + normalizedNumber + "' already exists");
        }

        existing.setVehicleNumber(normalizedNumber);
        existing.setVehicleType(updatedDetails.getVehicleType());
        existing.setBrand(updatedDetails.getBrand());
        existing.setModel(updatedDetails.getModel());
        existing.setManufacturingYear(updatedDetails.getManufacturingYear());
        existing.setColor(updatedDetails.getColor());
        existing.setFuelType(updatedDetails.getFuelType());
        existing.setSeatingCapacity(updatedDetails.getSeatingCapacity());
        existing.setDriverName(updatedDetails.getDriverName());
        existing.setDriverPhone(updatedDetails.getDriverPhone());
        if (updatedDetails.getStatus() != null) {
            existing.setStatus(updatedDetails.getStatus());
        }

        Vehicle saved = vehicleRepository.save(existing);
        return VehicleResponse.from(saved);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        Vehicle existing = findEntityById(id);
        vehicleRepository.delete(existing);
    }

    private Vehicle findEntityById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }
}
