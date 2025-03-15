package com.myapp.Parkease.config;

import com.myapp.Parkease.entity.ParkingOwner;
import com.myapp.Parkease.entity.ParkingSlot;
import com.myapp.Parkease.entity.User;
import com.myapp.Parkease.entity.Location;
import com.myapp.Parkease.repository.ParkingOwnerRepository;
import com.myapp.Parkease.repository.ParkingSlotRepository;
import com.myapp.Parkease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.myapp.Parkease.repository.LocationRepository;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(
            ParkingSlotRepository parkingSlotRepository, 
            UserRepository userRepository,
            ParkingOwnerRepository parkingOwnerRepository,
            LocationRepository locationRepository) {
        return args -> {
            // Create sample parking owner
            ParkingOwner owner = null;
            if (parkingOwnerRepository.count() == 0) {
                owner = new ParkingOwner();
                owner.setName("Sample Parking Owner");
                owner.setContact("123-456-7890");
                owner.setEmail("owner@example.com");
                owner.setAddress("123 Main St");
                parkingOwnerRepository.save(owner);
            } else {
                owner = parkingOwnerRepository.findAll().get(0);
            }

            // Create sample locations
            Location location1 = null;
            Location location2 = null;
            if (locationRepository.count() == 0) {
                location1 = new Location();
                location1.setName("Main Building");
                location1.setLatitude(14.6091);
                location1.setLongitude(121.0223);
                locationRepository.save(location1);
                
                location2 = new Location();
                location2.setName("Annex Building");
                location2.setLatitude(14.6093);
                location2.setLongitude(121.0225);
                locationRepository.save(location2);
            } else {
                location1 = locationRepository.findAll().get(0);
                if (locationRepository.count() > 1) {
                    location2 = locationRepository.findAll().get(1);
                } else {
                    location2 = location1;
                }
            }
            

            // Create parking slots if none exist
            if (parkingSlotRepository.count() == 0) {
                // Create some sample parking slots
                for (int i = 1; i <= 10; i++) {
                    ParkingSlot slot = new ParkingSlot();
                    slot.setLocation(location1);
                    slot.setSlotNumber(i);
                    slot.setStatus("available");
                    slot.setOwner(owner); // Set the owner
                    parkingSlotRepository.save(slot);
                }
                
                for (int i = 1; i <= 10; i++) {
                    ParkingSlot slot = new ParkingSlot();
                    slot.setLocation(location2);
                    slot.setSlotNumber(i);
                    slot.setStatus("available");
                    slot.setOwner(owner); // Set the owner
                    parkingSlotRepository.save(slot);
                }
            }
            
            // Create a test user if none exist
            if (userRepository.count() == 0) {
                User user = new User();
                user.setName("Test User");
                user.setEmail("test@example.com");
                user.setPassword("password");
                userRepository.save(user);
            }
        };
    }
}