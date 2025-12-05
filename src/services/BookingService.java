package services;

import java.util.ArrayList;

import models.Booking;



public class BookingService {
    ArrayList<Booking> data;

    public BookingService() {
        data = new ArrayList<>();
    }

    public void tambahBooking(Booking b) {

        b.id = data.size() + 1;
        data.add(b);
        // TODO Auto-generated method stub
    }

    public void update(int index, Booking b) {
        if (index >= 0 && index < data.size()) {
            data.set(index, b);
        }
    }

    public void hapusBooking(int index) {
        if (index >= 0 && index < data.size()) {
        data.remove(index);
    }
        // TODO Auto-generated method stub
    }

    public ArrayList<Booking> getAll(){
        return data;
    }

    public Booking getLapangan(int id) {
         for (Booking b : data) {
            if (b.id == id) {
                return b;
            }
        }
        return null;
    }
}
