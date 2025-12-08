package views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.BookingService;
import models.Booking;

public class DataBookingPanel extends JPanel {
    private BookingService service;
    private DefaultTableModel tableModel;

    public DataBookingPanel(BookingService service) {
        this.service = service;
        setLayout(new BorderLayout());

        String[] cols = {"ID Booking", "ID Lapangan", "Pemesan", "Tanggal", "Durasi", "Total Harga"};
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        for (Booking b : service.getAll()) {
            tableModel.addRow(new Object[]{
                b.id, b.id_lapangan, b.nama_pemesan, b.tanggal, b.durasi + " Jam", b.totalHarga
            });
        }
    }
}