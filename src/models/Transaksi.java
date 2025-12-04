package models;

public class Transaksi {
    public int id;
    public int id_lapangan;
    public float nominal;

    public Transaksi(int id, int id_lapangan, float nominal) {
        this.id = id;
        this.id_lapangan = id_lapangan;
        this.nominal = nominal;
    }


}
