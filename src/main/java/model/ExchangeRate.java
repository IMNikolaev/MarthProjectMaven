package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class ExchangeRate {
    private String name;
    private double rate;
    private double ratio;
    private Map<LocalDate, LocalTime> date;
}
