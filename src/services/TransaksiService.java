package services;

import java.util.ArrayList;
import models.Transaksi;

public class TransaksiService {
    private ArrayList<Transaksi> data = new ArrayList<>();

   
    public void tambah(Transaksi t){
   
        if (t.id == 0) {
            t.id = data.size() + 1;
        }
        data.add(t);
    }

    
    public ArrayList<Transaksi> getAll(){
        return data;
    }

    
    public double getTotalPendapatan(){
        double total = 0;
        for(Transaksi t : data){
            total += t.nominal;
        }
        return total;
    }
}