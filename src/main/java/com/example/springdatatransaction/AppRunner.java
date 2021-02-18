package com.example.springdatatransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AppRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AppRunner.class);

    private BookingService bookingService;

    public AppRunner(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void run(String... args) throws Exception {

        bookingService.book("Alice", "Bob", "Carol");

        Assert.isTrue(bookingService.listAllBookings().size() == 3,
                "booking should work");

        log.info("Alice, Bob, Carol have been booked");

        try {
            bookingService.book("Cris", "Samuel");
        } catch (RuntimeException e) {
            log.info("The following exception is expected because Samuel is too big for db");
            log.error(e.getMessage());
        }

        try {
            bookingService.book("Budy", null);
        } catch (RuntimeException e) {
            log.info("The following exception is expected because null is not valid");
            log.error(e.getMessage());
        }

        for (String person : bookingService.listAllBookings()) {
            log.info(person + " is booked");
        }
        log.info("End of booking, Budy should not be on the list");

        Assert.isTrue(bookingService.listAllBookings().size() == 3,
                "All bookings shoud be 3");
    }
}
