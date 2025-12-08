import javax.swing.SwingUtilities;
import services.*;
import views.LoginView;
import views.DashboardView;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            // Instansiasi semua service
            AuthService authService = new AuthService();
            LapanganService lapanganService = new LapanganService();
            BookingService bookingService = new BookingService();
            TransaksiService transaksiService = new TransaksiService();

            
            LoginView loginView = new LoginView(authService);
            
            
            loginView.setOnSuccess(() -> {
                DashboardView dashboard = new DashboardView(
                    authService, 
                    lapanganService, 
                    bookingService, 
                    transaksiService
                );
                dashboard.setVisible(true);
            });

            loginView.setVisible(true);
        });
    }
}