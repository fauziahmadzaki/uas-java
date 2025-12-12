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

        JLabel title = new JLabel("Laporan Keuangan & Transaksi", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID Transaksi", "ID Booking", "Nominal (Rp)"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total Pendapatan: Rp 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(0, 100, 0)); 
        
        footerPanel.add(lblTotal);
        add(footerPanel, BorderLayout.SOUTH);

        refreshData();
    }

    
    public void refreshData() {
    
        tableModel.setRowCount(0);
        
    
        NumberFormat kursIndonesia = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (Transaksi t : transaksiService.getAll()) {
            tableModel.addRow(new Object[]{
                t.id,
                t.id_booking,
                kursIndonesia.format(t.nominal)
            });
        }
        double total = transaksiService.getTotalPendapatan();
        lblTotal.setText("Total Pendapatan: " + kursIndonesia.format(total));
    }
}