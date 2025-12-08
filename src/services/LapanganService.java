package services;

import java.util.ArrayList;
import models.Lapangan;

public class LapanganService {
    private ArrayList<Lapangan> data;

    public LapanganService(){
        this.data = new ArrayList<>();
       // Seeding Data
        tambahLapangan(new Lapangan(0, "Lapangan A (Sintetis)", 100000, "Sintetis"));
        tambahLapangan(new Lapangan(0, "Lapangan B (Matras)", 80000, "Matras"));
    }

    public void tambahLapangan(Lapangan l) {
        l.id = data.isEmpty() ? 1 : data.get(data.size()-1).id + 1; 
        data.add(l);
    }

    public void updateLapangan(int id, Lapangan lBaru) {
        for(int i=0; i<data.size(); i++) {
            if(data.get(i).id == id) {
                lBaru.id = id; 
                data.set(i, lBaru);
                return;
            }
        }
    }

    public boolean hapusLapangan(int id) {
        return data.removeIf(l -> l.id == id);
    }

    public ArrayList<Lapangan> getAll(){ return this.data; }

    public Lapangan getLapangan(int id) {
        for (Lapangan l : data) {
            if (l.id == id) return l;
        }
        return null;
    }
}