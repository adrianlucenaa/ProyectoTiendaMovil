package org.example.Model.Domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PhoneTest {
    @Test
    public void testGetFullName() {
        // Arrange
        Phone phone = new Phone();
        phone.setBrand("Samsung");
        phone.setName("Galaxy S21");

        // Act
        String fullName = phone.getName();

        // Assert
        assertEquals("Samsung Galaxy S21", fullName);
    }

    @Test
    public void testCalculateDiscountedPrice() {
        // Arrange
        Phone phone = new Phone();
        phone.setBrand("Samsung");
        phone.setName("Galaxy S21");
        phone.setPrice(1000);
        double discountPercentage = 0.1;

        // Act
        double discountedPrice = phone.calculatePrice(discountPercentage);

        // Assert
        assertEquals(900, discountedPrice);
    }
}