package com.example.springdatatransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookingService {

    private final static Logger log = LoggerFactory.getLogger(BookingService.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void book(String... persons) {
        for (String person : persons) {
            log.info("Booking person " + person + " in a seat");
            jdbcTemplate.update("insert into bookings(first_name) values(?)", person);
        }
    }

    public List<String> listAllBookings() {
        return jdbcTemplate.query("select first_name from bookings",
                (rs, rownumber) -> rs.getString("first_name"));
    }
}
