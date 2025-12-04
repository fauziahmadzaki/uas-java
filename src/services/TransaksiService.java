package services;

import java.util.ArrayList;

import models.Transaksi;

public class TransaksiService {
    private ArrayList<Transaksi> data = new ArrayList<>();

    public void tambah(Transaksi t){
        data.add(t);
    }

    public void update(int index, Transaksi t){
        if(index >= 0 && index < data.size()){
            data.set(index, t);
        }
    }

    public Transaksi get(int index){
        return data.get(index);
    }

    public ArrayList<Transaksi> getAll(){
        return data;
    }

    public float getTotalHarga(){
        float total = 0;
        for(Transaksi t : data){
            total += t.nominal;
        }
        return total;
    }
}
