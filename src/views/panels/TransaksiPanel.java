package views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import services.TransaksiService;
import models.Transaksi;

public class TransaksiPanel extends JPanel {
    private TransaksiService transaksiService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;

    public TransaksiPanel(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Judul di Atas
        JLabel title = new JLabel("Laporan Keuangan & Transaksi", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // 2. Tabel Data di Tengah
        String[] columns = {"ID Transaksi", "ID Booking", "Nominal (Rp)"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        
        // Bungkus tabel dengan ScrollPane agar bisa discroll
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 3. Total Pendapatan di Bawah
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total Pendapatan: Rp 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(0, 100, 0)); // Warna hijau gelap
        
        footerPanel.add(lblTotal);
        add(footerPanel, BorderLayout.SOUTH);

        // Load data awal
        refreshData();
    }

    // Method untuk me-refresh tampilan tabel saat data berubah
    public void refreshData() {
        // Hapus data lama di tabel
        tableModel.setRowCount(0);
        
        // Format mata uang ke Rupiah
        NumberFormat kursIndonesia = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // Isi ulang baris tabel dari Service
        for (Transaksi t : transaksiService.getAll()) {
            tableModel.addRow(new Object[]{
                t.id,
                t.id_booking,
                kursIndonesia.format(t.nominal)
            });
        }

        // Update Label Total
        double total = transaksiService.getTotalPendapatan();
        lblTotal.setText("Total Pendapatan: " + kursIndonesia.format(total));
    }
}