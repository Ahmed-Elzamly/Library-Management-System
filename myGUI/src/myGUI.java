import javax.swing.* ;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class myGUI extends JFrame {
    public class DBConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/library";
        private static final String USER = "root";
        private static final String PASSWORD = "987a654h321med";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
    JPanel north , left , l2txt , l2lab  , l4lab , l4txt , right , l5txt , l5lab ,  l7txt , l7lab , south ;
    JButton b1 ;
    JLabel l1 , l2 , l4 , l5 ,  l7;
    JTextField t1  , t3 , t4 , t5 ;
    myGUI(){
        this.setLayout(new BorderLayout(30 , 30));
        Font f = new Font("Arial", Font.BOLD,24) ;
        this.setTitle("DB project");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700 , 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        north = new JPanel() ;
        this.add(north , BorderLayout.NORTH) ;
        north.setLayout(new FlowLayout());
        l1 = new JLabel("New User Register") ;
        l1.setFont(f);
        north.add(l1) ;

        left = new JPanel() ;
        this.add(left , BorderLayout.WEST) ;
        left.setLayout(new GridLayout(2,2));

        l2lab = new JPanel() ;
        l2lab.setLayout(new FlowLayout());
        left.add(l2lab);
        l2 = new JLabel("First name : ") ;
        l2lab.add(l2) ;
        l2txt = new JPanel() ;
        l2txt.setLayout(new FlowLayout());
        left.add(l2txt) ;
        t1 = new JTextField(15) ;
        l2txt.add(t1) ;

        l4lab = new JPanel() ;
        l4lab.setLayout(new FlowLayout());
        left.add(l4lab);
        l4 = new JLabel("Last name : ") ;
        l4lab.add(l4) ;
        l4txt = new JPanel() ;
        l4txt.setLayout(new FlowLayout());
        left.add(l4txt) ;
        t3 = new JTextField(15) ;
        l4txt.add(t3) ;

        right = new JPanel() ;
        this.add(right , BorderLayout.EAST) ;
        right.setLayout(new GridLayout(2,2));

        l5lab = new JPanel() ;
        l5lab.setLayout(new FlowLayout());
        right.add(l5lab);
        l5 = new JLabel("Email address : ") ;
        l5lab.add(l5) ;
        l5txt = new JPanel() ;
        l5txt.setLayout(new FlowLayout());
        right.add(l5txt) ;
        t4 = new JTextField(15) ;
        l5txt.add(t4) ;

        l7lab = new JPanel() ;
        l7lab.setLayout(new FlowLayout());
        right.add(l7lab);
        l7 = new JLabel("Mobile number : ") ;
        l7lab.add(l7) ;
        l7txt = new JPanel() ;
        l7txt.setLayout(new FlowLayout());
        right.add(l7txt) ;
        t5 = new JTextField(15) ;
        l7txt.add(t5) ;

        south = new JPanel() ;
        this.add(south , BorderLayout.SOUTH) ;
        south.setLayout(new FlowLayout());
        b1 = new JButton("Register") ;
        south.add(b1) ;
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = t1.getText();
                String email = t4.getText();
                String lastName = t3.getText();
                String phone = t5.getText();
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO members (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setString(3, email);
                    stmt.setString(4, phone);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error while connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                t1.setText("");
                t3.setText("");
                t4.setText("");
                t5.setText("");
            }
        });
        this.setVisible(true);

    }
    public static void main(String[] args){
    new myGUI() ;
    }
}