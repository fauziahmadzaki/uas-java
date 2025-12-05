package services;

import java.util.ArrayList;
import models.Lapangan;

public class LapanganService {
    ArrayList<Lapangan> data;

    public LapanganService(){
        this.data = new ArrayList<>();
    }
    public void tambahLapangan(Lapangan lapangan) {
        if (getLapangan(lapangan.id) !=null) {
            System.out.println("Error lapangan " + lapangan.id + " sudah ada");
            return;
        }
        this.data.add(lapangan);
        System.out.println("Lapangan "+ lapangan.id +" ditambahkan ");
    }

    public void editLapangan(int id, Lapangan lapanganBaru) {
        int index = -1;
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).id == id){
                index = i;
                break;
            }
        }
        if (index != -1){
            this.data.set(index, lapanganBaru);
            System.out.println("Lapangan dengan "+ id +" telah di ubah");
        }else{
            System.out.println("Lapangan dengan "+ id +" tidak ditemukan");
        }
    }

    public void hapusLapangan(int id) {
        Lapangan hapusLapangan = getLapangan(id);
        
        if (hapusLapangan != null){
            this.data.remove(hapusLapangan);
            System.out.println("Lapangan dengan "+ id+ " telah dihapus");
        }else{
            System.out.println("Lapangan denga "+ id+ " tidak ditemukan");
        }
    }

    public ArrayList<Lapangan> getAll(){
        return this.data;
    }

    public Lapangan getLapangan(int id) {
        for (Lapangan lapangan : this.data) {
            if (lapangan.id == id){
                return lapangan;
            }
        }
        return null;
    }
}
