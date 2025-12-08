package views.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import services.LapanganService;
import models.Lapangan;
import views.components.Button;
import views.components.LabeledTextInput;

public class LapanganPanel extends JPanel {
    private LapanganService service;
    private JTable table;
    private DefaultTableModel tableModel;
    private LabeledTextInput inputNama, inputHarga, inputKategori;
    private int selectedId = -1;
    private Button btnSimpan, btnHapus, btnBatal;
    private JPanel formPanel;

    public LapanganPanel(LapanganService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Input (GridBagLayout)
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Input Data Lapangan"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        inputNama = new LabeledTextInput("Nama Lapangan:");
        inputHarga = new LabeledTextInput("Harga per Jam (Rp):");
        inputKategori = new LabeledTextInput("Kategori:");

        gbc.gridy = 0; formPanel.add(inputNama, gbc);
        gbc.gridy = 1; formPanel.add(inputHarga, gbc);
        gbc.gridy = 2; formPanel.add(inputKategori, gbc);

        // Tombol
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSimpan = new Button("Simpan");
        btnHapus = new Button("Hapus");
        btnBatal = new Button("Batal / Baru");

        btnSimpan.setBackground(new Color(50, 150, 250));
        btnHapus.setBackground(Color.RED);
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setEnabled(false);

        btnSimpan.addActionListener(e -> simpanData());
        btnHapus.addActionListener(e -> hapusData());
        btnBatal.addActionListener(e -> resetForm());

        btnPanel.add(btnHapus);
        btnPanel.add(btnBatal);
        btnPanel.add(btnSimpan);

        gbc.gridy = 3; formPanel.add(btnPanel, gbc);
        
        add(formPanel, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"ID", "Nama", "Harga/Jam", "Kategori"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row != -1) {
                    selectedId = (int) table.getValueAt(row, 0);
                    inputNama.setText(table.getValueAt(row, 1).toString());
                
                    String hargaText = table.getValueAt(row, 2).toString().replaceAll("[^0-9]", "");
                    inputHarga.setText(hargaText);
                    inputKategori.setText(table.getValueAt(row, 3).toString());
                    
                    btnSimpan.setText("Update Data");
                    btnSimpan.setBackground(new Color(255, 140, 0));
                    formPanel.setBorder(BorderFactory.createTitledBorder("Edit Lapangan (ID: " + selectedId + ")"));
                    btnHapus.setEnabled(true);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        refreshData();
    }

    private void simpanData() {
        String nama = inputNama.getText().trim();
        String hargaStr = inputHarga.getText().trim();
        String kategori = inputKategori.getText().trim();

        // 1. Cek Kosong
        if (nama.isEmpty() || hargaStr.isEmpty() || kategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validasi Harga (Angka & Positif)
        double harga = 0;
        try {
            harga = Double.parseDouble(hargaStr);
            if(harga < 0) {
                JOptionPane.showMessageDialog(this, "Harga tidak boleh negatif!", "Validasi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(harga == 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Harga 0 (Gratis). Lanjutkan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.NO_OPTION) return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka valid!", "Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Lapangan l = new Lapangan(0, nama, harga, kategori);

        if (selectedId == -1) {
            service.tambahLapangan(l);
            JOptionPane.showMessageDialog(this, "Berhasil menambah lapangan!");
        } else {
            service.updateLapangan(selectedId, l);
            JOptionPane.showMessageDialog(this, "Berhasil update lapangan!");
        }
        
        resetForm();
        refreshData();
    }

    private void hapusData() {
        if(selectedId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                service.hapusLapangan(selectedId);
                resetForm();
                refreshData();
            }
        }
    }

    private void resetForm() {
        inputNama.setText("");
        inputHarga.setText("");
        inputKategori.setText("");
        selectedId = -1; 
        
        btnSimpan.setText("Simpan");
        btnSimpan.setBackground(new Color(50, 150, 250));
        btnHapus.setEnabled(false);
        formPanel.setBorder(BorderFactory.createTitledBorder("Input Data Lapangan"));
        table.clearSelection();
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        for (Lapangan l : service.getAll()) {
            // Format angka agar rapi (tanpa .0 jika bulat)
            String hargaFmt = (l.harga % 1 == 0) ? String.format("Rp %.0f", l.harga) : String.format("Rp %.2f", l.harga);
            tableModel.addRow(new Object[]{l.id, l.nama, hargaFmt, l.kategori});
        }
    }
}