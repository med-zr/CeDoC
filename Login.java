package cedocpackage;


    import java.awt.EventQueue;
    import java.awt.*;
    import javax.swing.*;
    import javax.swing.JFrame;
    import javax.swing.JOptionPane;
    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import java.awt.BorderLayout;
    import java.awt.Color;
    import java.awt.Font;
    import java.awt.Image;
    import java.awt.event.ActionListener;
    import java.awt.event.ActionEvent;
    import javax.swing.JTextPane;
    import javax.swing.JLabel;
    import javax.swing.JTextField;
    import javax.swing.JPanel;
    import java.sql.*;

    public class Login {
        public JFrame frame;
        private JTextField textField1;
        private JButton btnNewButton_2;
        private JButton btnNewButton;
        private JPasswordField passwordField;
        Connection connection=null;
	/**
	 * Launch the application.
	 */
        public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
                                    Login window = new Login();
                                    window.frame.setVisible(true);
				} catch (Exception e) {
                                    e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection=sqlConnection.dbconnection();
	}
        
	/**
	 * Initialize the contents of the frame.
	 */
        public void authentification(String password1,String email) {
		
		try {
			String sql= "select * from info_etudiant where email=? ";
			PreparedStatement st=connection.prepareStatement(sql);
			st.setString(1,email);
			ResultSet rst=st.executeQuery();
			while(rst.next()) {
				if(password1.equalsIgnoreCase(rst.getString(3))) {
					JOptionPane.showMessageDialog(null, "logged in");
					frame.dispose();
					Acceuil home = new Acceuil(rst.getInt(1));
					home.setVisible(true);
					break;
				}
				else {
					JOptionPane.showMessageDialog(null, "wrong password or username");
					passwordField.setText("");
				}
			}
			rst.close();
			st.close();
		}catch(Exception e1){
			System.out.println(e1);
		}
		
	}
	static int count=0;
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 0, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
                //Image icon = setIcon(new ImageIcon("img/Ensiaslogo.png"));
                frame.setIconImage(new ImageIcon("img/graduated.png").getImage());
                frame.setTitle("CeDoc");
		
		textField1 = new JTextField();
		textField1.setBounds(365, 278, 320, 46);
		textField1.setForeground(Color.BLACK);
		textField1.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
		frame.getContentPane().add(textField1);
		textField1.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("Email :");
		lblNewLabel.setBounds(473, 221, 97, 46);
		lblNewLabel.setFont(new Font("Vladimir Script", Font.BOLD, 29));
		lblNewLabel.setForeground(Color.WHITE);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password :");
		lblNewLabel_1.setBounds(473, 326, 112, 46);
		lblNewLabel_1.setFont(new Font("Vladimir Script", Font.BOLD, 26));
		lblNewLabel_1.setForeground(Color.WHITE);
		frame.getContentPane().add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 15));
		passwordField.setEchoChar('●');
		passwordField.setBounds(365, 383, 320, 46);
		frame.getContentPane().add(passwordField);
		
		btnNewButton_2 = new JButton("LOGIN");
		btnNewButton_2.setBounds(555, 480, 130, 35);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					count++;
					if(count==3) System.exit(count);
					else authentification(passwordField.getText(),textField1.getText());
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "please enter a valid email and password");
				}
			}
		});
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 12));
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton = new JButton("S'INSCRIRE");
		btnNewButton.setBounds(365, 480, 120, 35);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.dispose();
					Subscribe sinscrire = new Subscribe();
					sinscrire.setVisible(true);
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "please enter a valid email");
				}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("ENSIAS LIBRARY");
		lblNewLabel_2.setFont(new Font("Vladimir Script", Font.BOLD, 55));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(118, 21, 586, 108);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
                lblNewLabel_3.setIcon(new ImageIcon("img/Ensiaslogo.png"));
		lblNewLabel_3.setBounds(10, 252, 250, 212);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(new ImageIcon("img/library.jpg"));
		lblNewLabel_4.setBounds(0, 0, 786, 700);
		frame.getContentPane().add(lblNewLabel_4);
	}
    }
