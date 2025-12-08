package services;

import java.util.ArrayList;
import models.Booking;

public class BookingService {
    private ArrayList<Booking> data = new ArrayList<>();

    public void tambahBooking(Booking b) {
        b.id = data.isEmpty() ? 1 : data.get(data.size()-1).id + 1;
        data.add(b);
    }

    public void updateBooking(int id, Booking bBaru) {
        for(int i=0; i<data.size(); i++) {
            if(data.get(i).id == id) {
                bBaru.id = id;
                data.set(i, bBaru);
                return;
            }
        }
    }

    public boolean hapusBooking(int id) {
        return data.removeIf(b -> b.id == id);
    }

    public ArrayList<Booking> getAll() { return data; }
    
    public Booking getBooking(int id) {
        for(Booking b : data) {
            if(b.id == id) return b;
        }
        return null;
    }
    
    public void updateStatus(int id, String statusBaru) {
        Booking b = getBooking(id);
        if(b != null) b.status = statusBaru;
    }
}