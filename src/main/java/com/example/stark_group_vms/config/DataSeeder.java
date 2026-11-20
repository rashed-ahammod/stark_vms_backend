package com.example.stark_group_vms.config;

import com.example.stark_group_vms.entity.Vehicle;
import com.example.stark_group_vms.enums.FuelType;
import com.example.stark_group_vms.enums.VehicleStatus;
import com.example.stark_group_vms.enums.VehicleType;
import com.example.stark_group_vms.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedVehicles(VehicleRepository vehicleRepository) {
        return args -> {
            if (vehicleRepository.count() > 0) {
                return;
            }

            List<Vehicle> mockVehicles = List.of(
                    build("DHA-METRO-GA-11-1001", VehicleType.CAR, "Toyota", "Corolla Altis", 2022, "Silver", FuelType.OCTANE, 5, "Rahim Uddin", "+8801711000001", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-GA-11-1002", VehicleType.CAR, "Honda", "Civic", 2023, "White", FuelType.PETROL, 5, "Karim Ahmed", "+8801711000002", VehicleStatus.IN_USE),
                    build("DHA-METRO-GHA-13-2001", VehicleType.SUV, "Toyota", "Land Cruiser Prado", 2021, "Black", FuelType.DIESEL, 7, "Jamal Hossain", "+8801711000003", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-GHA-13-2002", VehicleType.SUV, "Mitsubishi", "Pajero Sport", 2020, "Pearl White", FuelType.DIESEL, 7, "Kamal Mia", "+8801711000004", VehicleStatus.MAINTENANCE),
                    build("DHA-METRO-CHA-15-3001", VehicleType.MICROBUS, "Toyota", "HiAce", 2022, "Silver", FuelType.CNG, 12, "Rafiqul Islam", "+8801711000005", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-CHA-15-3002", VehicleType.MICROBUS, "Nissan", "Urvan", 2019, "White", FuelType.DIESEL, 14, "Shafiqul Alam", "+8801711000006", VehicleStatus.IN_USE),
                    build("DHA-METRO-MA-17-4001", VehicleType.VAN, "Suzuki", "Every", 2021, "White", FuelType.LPG, 7, "bellal Hossain", "+8801711000007", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-TA-19-5001", VehicleType.TRUCK, "Tata", "LPT 1615", 2018, "Blue", FuelType.DIESEL, 3, "Monirul Islam", "+8801711000008", VehicleStatus.IN_USE),
                    build("DHA-METRO-TA-19-5002", VehicleType.TRUCK, "Isuzu", "NKR", 2020, "White", FuelType.DIESEL, 3, "Habibur Rahman", "+8801711000009", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-NA-21-6001", VehicleType.PICKUP, "Mahindra", "Bolero Pik-Up", 2021, "White", FuelType.DIESEL, 2, "Sohrab Ali", "+8801711000010", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-NA-21-6002", VehicleType.PICKUP, "Toyota", "Hilux", 2023, "Grey", FuelType.DIESEL, 5, "Anwar Hossain", "+8801711000011", VehicleStatus.IN_USE),
                    build("DHA-METRO-BA-23-7001", VehicleType.BUS, "Hino", "1J Plus", 2019, "Green", FuelType.DIESEL, 40, "Delwar Hossain", "+8801711000012", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-BA-23-7002", VehicleType.BUS, "Ashok Leyland", "Viking", 2018, "Blue", FuelType.CNG, 45, "Mizanur Rahman", "+8801711000013", VehicleStatus.MAINTENANCE),
                    build("DHA-METRO-LA-25-8001", VehicleType.MOTORCYCLE, "Yamaha", "FZ-S V3", 2023, "Matte Black", FuelType.OCTANE, 2, "Tanvir Hasan", "+8801711000014", VehicleStatus.AVAILABLE),
                    build("DHA-METRO-LA-25-8002", VehicleType.MOTORCYCLE, "Bajaj", "Pulsar 150", 2022, "Red", FuelType.PETROL, 2, "Sajid Khan", "+8801711000015", VehicleStatus.IN_USE),
                    build("CTG-METRO-GA-11-9001", VehicleType.CAR, "Toyota", "Axio Hybrid", 2021, "Pearl", FuelType.HYBRID, 5, "Faruk Ahmed", "+8801711000016", VehicleStatus.AVAILABLE),
                    build("CTG-METRO-GA-11-9002", VehicleType.CAR, "Toyota", "Premio", 2020, "Red Wine", FuelType.OCTANE, 5, "Nasir Uddin", "+8801711000017", VehicleStatus.AVAILABLE),
                    build("CTG-METRO-GHA-13-9003", VehicleType.SUV, "Hyundai", "Tucson", 2023, "Dark Grey", FuelType.HYBRID, 5, "Imran Hossain", "+8801711000018", VehicleStatus.AVAILABLE),
                    build("SYL-METRO-GA-11-9004", VehicleType.CAR, "BYD", "Atto 3", 2024, "Blue", FuelType.ELECTRIC, 5, "Zahidul Islam", "+8801711000019", VehicleStatus.AVAILABLE),
                    build("RAJ-METRO-CHA-15-9005", VehicleType.MICROBUS, "Toyota", "Noah", 2021, "Black", FuelType.HYBRID, 8, "Alamin Mia", "+8801711000020", VehicleStatus.INACTIVE)
            );

            vehicleRepository.saveAll(mockVehicles);
        };
    }

    private Vehicle build(
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
            VehicleStatus status) {
        Vehicle v = new Vehicle();
        v.setVehicleNumber(vehicleNumber);
        v.setVehicleType(vehicleType);
        v.setBrand(brand);
        v.setModel(model);
        v.setManufacturingYear(manufacturingYear);
        v.setColor(color);
        v.setFuelType(fuelType);
        v.setSeatingCapacity(seatingCapacity);
        v.setDriverName(driverName);
        v.setDriverPhone(driverPhone);
        v.setStatus(status);
        return v;
    }
}

