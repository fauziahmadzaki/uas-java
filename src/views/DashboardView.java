package views;

import javax.swing.*;
import java.awt.*;
import services.*;
import views.frames.AppFrame;
import views.panels.*;
import views.components.Button;

public class DashboardView extends AppFrame {
    // Service dependencies
    private AuthService authService;
    private LapanganService lapanganService;
    private BookingService bookingService;
    private TransaksiService transaksiService;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Constructor diperbarui menerima TransaksiService
    public DashboardView(AuthService as, LapanganService ls, BookingService bs, TransaksiService ts) {
        super("Sistem Booking Futsal", 900, 600);
        this.authService = as;
        this.lapanganService = ls;
        this.bookingService = bs;
        this.transaksiService = ts;
        
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        // --- 1. Sidebar Menu ---
        JPanel sidebar = new JPanel(new GridLayout(10, 1, 5, 5));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setBackground(Color.LIGHT_GRAY);

        JLabel lblMenu = new JLabel("MENU UTAMA", SwingConstants.CENTER);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 16));
        sidebar.add(lblMenu);

        Button btnLapangan = new Button("Kelola Lapangan");
        Button btnBooking = new Button("Booking Aktif");
        Button btnTransaksi = new Button("Riwayat Transaksi"); // Menu Baru
        Button btnLogout = new Button("Logout");

        sidebar.add(btnLapangan);
        sidebar.add(btnBooking);
        sidebar.add(btnTransaksi);
        sidebar.add(btnLogout);
        
        add(sidebar, BorderLayout.WEST);

        // --- 2. Main Content (Card Layout) ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        LapanganPanel pnlLapangan = new LapanganPanel(lapanganService);
        BookingPanel pnlBooking = new BookingPanel(bookingService, lapanganService, transaksiService);
        TransaksiPanel pnlTransaksi = new TransaksiPanel(transaksiService); // Panel Baru

        mainPanel.add(pnlLapangan, "LAPANGAN");
        mainPanel.add(pnlBooking, "BOOKING");
        mainPanel.add(pnlTransaksi, "TRANSAKSI");

        add(mainPanel, BorderLayout.CENTER);

        // --- 3. Event Handling ---
        btnLapangan.addActionListener(e -> {
            pnlLapangan.refreshData();
            cardLayout.show(mainPanel, "LAPANGAN");
        });

        btnBooking.addActionListener(e -> {
            pnlBooking.refreshAll();
            cardLayout.show(mainPanel, "BOOKING");
        });

        btnTransaksi.addActionListener(e -> {
            pnlTransaksi.refreshData();
            cardLayout.show(mainPanel, "TRANSAKSI");
        });

        // --- PERBAIKAN BUG LOGOUT DISINI ---
        btnLogout.addActionListener(e -> {
            // 1. Proses Logout di Service
            authService.logout();
            
            // 2. Tutup Dashboard saat ini
            this.dispose();

            // 3. Buat Login View Baru
            LoginView loginView = new LoginView(authService);
            
            // 4. PENTING: Pasang kembali Logic "Apa yang terjadi jika login sukses"
            loginView.setOnSuccess(() -> {
                new DashboardView(authService, lapanganService, bookingService, transaksiService).setVisible(true);
            });

            // 5. Tampilkan Login
            loginView.setVisible(true);
        });
    }
}