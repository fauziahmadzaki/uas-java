package models;

public class Booking {
    public int id;
    public int id_lapangan;
    public String nama_pemesan;
    public String tanggal;
    public int durasi;
    public double totalHarga;
    public String status; 

    public Booking(int id, int id_lapangan, String nama_pemesan, String tanggal, int durasi, double totalHarga, String status) {
        this.id = id;
        this.id_lapangan = id_lapangan;
        this.nama_pemesan = nama_pemesan;
        this.tanggal = tanggal;
        this.durasi = durasi;
        this.totalHarga = totalHarga;
        this.status = status;
    }
}