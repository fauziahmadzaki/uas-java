package views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import services.*;
import models.*;
import views.components.Button;
import views.components.LabeledTextInput;

public class BookingPanel extends JPanel {
    private BookingService bookingService;
    private LapanganService lapanganService;
    private TransaksiService transaksiService;

    private JComboBox<Lapangan> comboLapangan;
    private LabeledTextInput inputNama, inputDurasi, inputTanggal, inputNoTelp;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private int selectedBookingId = -1;
    private Button btnSimpan, btnHapus, btnBatal, btnSelesai;
    private JPanel formPanel;

    public BookingPanel(BookingService bs, LapanganService ls, TransaksiService ts) {
        this.bookingService = bs;
        this.lapanganService = ls;
        this.transaksiService = ts;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel Kiri: Form Input ---
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(320, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Booking Baru"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Inisialisasi Komponen
        comboLapangan = new JComboBox<>();
        inputNama = new LabeledTextInput("Nama Pemesan:");
        inputNoTelp = new LabeledTextInput("No. Telepon (Contoh: 0812...)");
        inputTanggal = new LabeledTextInput("Tanggal (DD/MM/YYYY):");
        inputDurasi = new LabeledTextInput("Durasi (Jam):");

        // Layout Komponen
        gbc.gridy = 0; formPanel.add(new JLabel("Pilih Lapangan:"), gbc);
        gbc.gridy = 1; formPanel.add(comboLapangan, gbc);
        gbc.gridy = 2; formPanel.add(inputNama, gbc);
        gbc.gridy = 3; formPanel.add(inputNoTelp, gbc);
        gbc.gridy = 4; formPanel.add(inputTanggal, gbc);
        gbc.gridy = 5; formPanel.add(inputDurasi, gbc);

        // --- Panel Tombol ---
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        btnSimpan = new Button("Booking");
        btnHapus = new Button("Hapus");
        btnBatal = new Button("Reset");
        
        btnSimpan.setBackground(new Color(50, 150, 250));
        btnHapus.setBackground(Color.RED);
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setEnabled(false);

        btnSimpan.addActionListener(e -> simpanBooking());
        btnHapus.addActionListener(e -> hapusBooking());
        btnBatal.addActionListener(e -> resetForm());

        btnPanel.add(btnSimpan);
        btnPanel.add(btnHapus);
        btnPanel.add(btnBatal);

        gbc.gridy = 6;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(btnPanel, gbc);
        
        // Spacer
        gbc.gridy = 7; gbc.weighty = 1.0;
        formPanel.add(new JLabel(), gbc);

        add(formPanel, BorderLayout.WEST);

        // --- Panel Tengah: Tabel ---
        JPanel centerPanel = new JPanel(new BorderLayout(5,5));
        String[] cols = {"ID", "Pemesan", "Telp", "Lapangan", "Durasi", "Total", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row != -1) {
                    String status = table.getValueAt(row, 6).toString();
                    if("Selesai".equals(status)) {
                        resetForm();
                        JOptionPane.showMessageDialog(null, "Booking selesai tidak bisa diedit!");
                        return;
                    }

                    selectedBookingId = (int) table.getValueAt(row, 0);
                    inputNama.setText(table.getValueAt(row, 1).toString());
                    inputNoTelp.setText(table.getValueAt(row, 2).toString());
                    
                    // Set Combo
                    String namaLap = table.getValueAt(row, 3).toString();
                    for(int i=0; i<comboLapangan.getItemCount(); i++){
                        if(comboLapangan.getItemAt(i).nama.equals(namaLap)){
                            comboLapangan.setSelectedIndex(i);
                            break;
                        }
                    }

                    String durasiStr = table.getValueAt(row, 4).toString().replace(" Jam", "");
                    inputDurasi.setText(durasiStr);
                    
                    btnSimpan.setText("Update");
                    btnSimpan.setBackground(new Color(255, 140, 0));
                    formPanel.setBorder(BorderFactory.createTitledBorder("Edit Booking ID: " + selectedBookingId));
                    btnHapus.setEnabled(true);
                }
            }
        });

        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        btnSelesai = new Button("Tandai Selesai & Bayar");
        btnSelesai.setBackground(new Color(46, 204, 113));
        btnSelesai.addActionListener(e -> prosesSelesai());
        
        JPanel bottomAction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomAction.add(btnSelesai);
        centerPanel.add(bottomAction, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        
        refreshAll();
    }

    private void simpanBooking() {
        // --- VALIDASI START ---
        Lapangan l = (Lapangan) comboLapangan.getSelectedItem();
        if(l == null) {
            JOptionPane.showMessageDialog(this, "Pilih lapangan terlebih dahulu!");
            return;
        }

        String nama = inputNama.getText().trim();
        String hp = inputNoTelp.getText().trim();
        String durasiStr = inputDurasi.getText().trim();
        String tanggal = inputTanggal.getText().trim();

        // 1. Cek Kosong
        if (nama.isEmpty() || hp.isEmpty() || durasiStr.isEmpty() || tanggal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua data harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validasi No HP (Angka, 10-13 digit)
        if (!hp.matches("\\d{10,13}")) {
            JOptionPane.showMessageDialog(this, "Nomor Telepon tidak valid!\nHarus berupa angka 10-13 digit.", "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validasi Durasi (Harus Angka & Positif)
        int durasi = 0;
        try {
            durasi = Integer.parseInt(durasiStr);
            if (durasi <= 0) throw new NumberFormatException();
            if (durasi > 24) { // Contoh validasi bisnis: max booking 24 jam
                 JOptionPane.showMessageDialog(this, "Durasi maksimal 24 jam!", "Error Validasi", JOptionPane.WARNING_MESSAGE);
                 return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Durasi harus berupa angka positif!", "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // --- VALIDASI END ---

        double total = l.harga * durasi;

        // Proses Simpan
        // Pastikan Anda sudah update Booking.java agar punya field noHp
        // Untuk contoh ini, saya simpan noHp di objek Booking jika konstruktor mendukung
        // Booking b = new Booking(0, l.id, nama, hp, tanggal, durasi, total, "Booked");
        
        // Menggunakan konstruktor asumsi (sesuaikan dengan Booking.java Anda)
        Booking b = new Booking(0, l.id, nama, tanggal, durasi, total, "Booked");
        // Jika ada field noHp manual: b.noHp = hp;

        if (selectedBookingId == -1) {
            bookingService.tambahBooking(b);
            JOptionPane.showMessageDialog(this, "Booking Berhasil Disimpan!");
        } else {
            bookingService.updateBooking(selectedBookingId, b);
            JOptionPane.showMessageDialog(this, "Booking Berhasil Diupdate!");
        }
        
        resetForm();
        refreshTable();
    }
    
    // ... (Sisa method hapusBooking, resetForm, dll sama seperti kode sebelumnya) ...
    // Pastikan copy method resetForm, hapusBooking, prosesSelesai, refreshTable, refreshForm dari kode sebelumnya.

    private void hapusBooking() {
        if(selectedBookingId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Hapus booking ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                bookingService.hapusBooking(selectedBookingId);
                resetForm();
                refreshTable();
            }
        }
    }

    private void resetForm() {
        inputNama.setText("");
        inputNoTelp.setText("");
        inputDurasi.setText("");
        inputTanggal.setText("");
        if(comboLapangan.getItemCount() > 0) comboLapangan.setSelectedIndex(0);
        selectedBookingId = -1;
        btnSimpan.setText("Booking");
        btnSimpan.setBackground(new Color(50, 150, 250));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Booking Baru"));
        btnHapus.setEnabled(false);
        table.clearSelection();
    }

    private void prosesSelesai() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih booking dulu!");
            return;
        }
        int idBooking = (int) table.getValueAt(row, 0);
        String currentStatus = table.getValueAt(row, 6).toString();

        if("Selesai".equals(currentStatus)) {
            JOptionPane.showMessageDialog(this, "Booking sudah selesai!");
            return;
        }

        Booking b = bookingService.getBooking(idBooking);
        Transaksi t = new Transaksi(0, b.id, b.totalHarga);
        transaksiService.tambah(t);
        bookingService.updateStatus(idBooking, "Selesai");
        
        JOptionPane.showMessageDialog(this, "Transaksi Selesai & Data Terekap!");
        refreshTable();
    }

    public void refreshForm() {
        comboLapangan.removeAllItems();
        for(Lapangan l : lapanganService.getAll()){
            comboLapangan.addItem(l);
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for(Booking b : bookingService.getAll()){
            Lapangan lap = lapanganService.getLapangan(b.id_lapangan);
            String namaLap = (lap != null) ? lap.nama : "?";
            // String hp = b.noHp; // Gunakan ini jika field noHp sudah ada di model
            String hp = "-"; // Dummy text jika belum update model

            tableModel.addRow(new Object[]{
                b.id, b.nama_pemesan, hp, namaLap, b.durasi + " Jam", b.totalHarga, b.status
            });
        }
    }
    
    public void refreshAll() { refreshForm(); refreshTable(); }
}