package views;

import javax.swing.*;
import java.awt.*;
import services.*;
import views.frames.AppFrame;
import views.panels.*;
import views.components.Button;

public class DashboardView extends AppFrame {
    private AuthService authService;
    private LapanganService lapanganService;
    private BookingService bookingService;
    private TransaksiService transaksiService;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    
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

        
        JPanel sidebar = new JPanel(new GridLayout(10, 1, 5, 5));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setBackground(Color.LIGHT_GRAY);

        JLabel lblMenu = new JLabel("MENU UTAMA", SwingConstants.CENTER);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 16));
        sidebar.add(lblMenu);

        Button btnLapangan = new Button("Kelola Lapangan");
        Button btnBooking = new Button("Booking Aktif");
        Button btnTransaksi = new Button("Riwayat Transaksi");
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

        btnLogout.addActionListener(e -> {
           
            authService.logout();
            
           
            this.dispose();

           
            LoginView loginView = new LoginView(authService);
            
           
            loginView.setOnSuccess(() -> {
                new DashboardView(authService, lapanganService, bookingService, transaksiService).setVisible(true);
            });

           
            loginView.setVisible(true);
        });
    }
}