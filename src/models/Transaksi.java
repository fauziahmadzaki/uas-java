package models;

public class Transaksi {
    public int id;
    public int id_booking; 
    public double nominal; 

    public Transaksi(int id, int id_booking, double nominal) {
        this.id = id;
        this.id_booking = id_booking;
        this.nominal = nominal;
    }
}