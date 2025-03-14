package com.myapp.Parkease.config;

import com.myapp.Parkease.entity.ParkingSlot;
import com.myapp.Parkease.entity.User;
import com.myapp.Parkease.repository.ParkingSlotRepository;
import com.myapp.Parkease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ParkingSlotRepository parkingSlotRepository, UserRepository userRepository) {
        return args -> {
            // Create parking slots if none exist
            if (parkingSlotRepository.count() == 0) {
                // Create some sample parking slots
                for (int i = 1; i <= 10; i++) {
                    ParkingSlot slot = new ParkingSlot();
                    slot.setLocation("Level 1");
                    slot.setSlotNumber(i);
                    slot.setStatus("available");
                    parkingSlotRepository.save(slot);
                }
                
                for (int i = 1; i <= 10; i++) {
                    ParkingSlot slot = new ParkingSlot();
                    slot.setLocation("Level 2");
                    slot.setSlotNumber(i);
                    slot.setStatus("available");
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