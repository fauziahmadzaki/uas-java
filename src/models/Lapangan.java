package models;

public class Lapangan {
    public int id;
    public String nama;
    public double harga; 
    public String kategori;

    public Lapangan(int id, String nama, double harga, String kategori){
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
    
    @Override
    public String toString() {
        return nama + " (" + kategori + ")"; 
    }
}