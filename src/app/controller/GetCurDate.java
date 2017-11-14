package app.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Created by rail on 10/29/17.
 */
public class GetCurDate {
    private LocalDate localDate,tanggal;

    public GetCurDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date , formatter);
        localDate=ld;

        date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ld = LocalDate.parse(date , formatter);
        tanggal=ld;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
