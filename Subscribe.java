package cedocpackage;

    import java.awt.BorderLayout;
    import java.awt.EventQueue;

    import javax.swing.JFrame;
    import javax.swing.JPanel;
    import javax.swing.border.EmptyBorder;
    import javax.swing.JLabel;
    import javax.swing.JOptionPane;
    import javax.swing.JTextField;
    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import java.awt.Font;
    import java.awt.Image;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.Statement;
    import java.awt.Color;
    import javax.swing.JRadioButton;
    import javax.swing.JMenuBar;
    import javax.swing.JMenu;
    import javax.swing.JPopupMenu;
    import java.awt.Component;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
    import javax.swing.JMenuItem;
    import javax.swing.JList;
    import javax.swing.JScrollPane;


    public class Subscribe extends JFrame {

	private JPanel contentPane;
	private JTextField PASSWORD;
	private JTextField EMAIL;
	private JTextField CNE_TEXT;
	//private JRadioButton[] rdbtnNewRadioButton;
	private JTextField DATE_NAISSANCE_TEXT;
	private JTextField NOM_txt;
	private JTextField GSM_TEXT;
	private JTextField GROUPE_TEXT;
	private JLabel  lblNewLabel_2,lblNewLabel_3,date_label;
	private JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Subscribe frame = new Subscribe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Connection connection=sqlConnection.dbconnection();
	private JTextField txtChoisissezUnNiveau;
	private JComboBox comboBox;
	private JTextField Prenom_txt;
	private JLabel lblNewLabel_4;
	
	public void subscription(String email,String password1,String cin,String datenaissance,int gsm,String nom,String prenom) {
		
		try {
			String sql= "INSERT INTO info_etudiant(EMAIL,PASSWORD,CIN,datenaissance,gsm,nom,prenom) VALUES(?,?,?,?,?,?,?) ";
			PreparedStatement st=connection.prepareStatement(sql);
			st.setString(1,email);
			st.setString(2,password1);
			st.setString(3,cin);
                        st.setString(4,datenaissance);
                        st.setInt(5,gsm);
                        st.setString(6,nom);
                        st.setString(7,prenom);
                        st.executeUpdate();
			st.close();
		}catch(Exception e1){
			System.out.println(e1);
		}
		
	}
	public int setID() {
		try {
		int count=0;
		String statement= "select MAX(id) from info_etudiant";
		PreparedStatement ste=connection.prepareStatement(statement);
		ResultSet rst=ste.executeQuery();
		while(rst.next()) {	
			count=rst.getInt(1);
			return count;
		}
		return count;
		}catch(Exception e1) {
			e1.printStackTrace();
			return 0;
		}
	}
	public Subscribe() {
                setIconImage(new ImageIcon("img/graduated.png").getImage());
                setTitle("CeDoc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
                contentPane.setBackground(new Color(113,159,228));
		/*String[] level = {"1A","2A","3A","Master","Doctorant"};
		
		comboBox = new JComboBox(level);
		comboBox.setBounds(523, 443, 253, 50);
		contentPane.add(comboBox);*/
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		//Image img = new ImageIcon(this.getClass().getResource("img/Ensiaslogo.png")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon("img/Ensiaslogo.png"));
		lblNewLabel_1.setBounds(10, 11, 250, 212);
		contentPane.add(lblNewLabel_1);
		
		PASSWORD = new JTextField();
		PASSWORD.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 16));
		PASSWORD.setBounds(523, 234, 253, 50);
		contentPane.add(PASSWORD);
		PASSWORD.setColumns(10);
		
		EMAIL = new JTextField();
		EMAIL.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 17));
		EMAIL.setBounds(110, 234, 216, 50);
		contentPane.add(EMAIL);
		EMAIL.setColumns(10);
		
		CNE_TEXT = new JTextField();
		CNE_TEXT.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 16));
		CNE_TEXT.setBounds(110, 295, 216, 50);
		contentPane.add(CNE_TEXT);
		CNE_TEXT.setColumns(10);
		
		DATE_NAISSANCE_TEXT = new JTextField();
		DATE_NAISSANCE_TEXT.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 16));
		DATE_NAISSANCE_TEXT.setBounds(523, 295, 253, 50);
		contentPane.add(DATE_NAISSANCE_TEXT);
		DATE_NAISSANCE_TEXT.setColumns(10);
		
		GSM_TEXT = new JTextField();
		GSM_TEXT.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 16));
		GSM_TEXT.setBounds(110, 360, 216, 50);
		contentPane.add(GSM_TEXT);
		GSM_TEXT.setColumns(10);
		
		/*GROUPE_TEXT = new JTextField();
		GROUPE_TEXT.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
		GROUPE_TEXT.setBounds(110, 427, 205, 48);
		contentPane.add(GROUPE_TEXT);
		GROUPE_TEXT.setColumns(10);*/
		
		JButton CONITNUE = new JButton("CONTINUER");
		CONITNUE.setFont(new Font("Arial", Font.BOLD, 12));
		CONITNUE.setBounds(554, 446, 193, 50);
		CONITNUE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					subscription(EMAIL.getText(),PASSWORD.getText(),CNE_TEXT.getText(),DATE_NAISSANCE_TEXT.getText(),Integer.parseInt(GSM_TEXT.getText()),NOM_txt.getText(),Prenom_txt.getText());
					setVisible(false);
					Acceuil home = new Acceuil(setID());
					home.setVisible(true);
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "please enter a valid email");
				}
			}
		});
		contentPane.add(CONITNUE);
                
                JButton retour = new JButton("Retourner");
		retour.setFont(new Font("Arial", Font.BOLD, 12));
		retour.setBounds(554, 502, 193, 50);
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setVisible(false);
					Login login = new Login();
					login.frame.setVisible(true);
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "please enter a valid email");
				}
			}
		});
		contentPane.add(retour);
		
		JLabel email = new JLabel("EMAIL :");
		email.setForeground(Color.WHITE);
		email.setFont(new Font("Arial", Font.BOLD, 19));
		email.setBounds(10, 234, 90, 50);
		contentPane.add(email);
		
		JLabel Password = new JLabel("Password :");
		Password.setFont(new Font("Arial", Font.BOLD, 19));
		Password.setForeground(Color.WHITE);
		Password.setBounds(387, 232, 119, 50);
		contentPane.add(Password);
		
		JLabel CNE = new JLabel("  CNE:");
		CNE.setForeground(Color.WHITE);
		CNE.setFont(new Font("Arial", Font.BOLD, 19));
		CNE.setBounds(0, 294, 103, 48);
		contentPane.add(CNE);
		
		JLabel GSM = new JLabel("   GSM :");
		GSM.setForeground(Color.WHITE);
		GSM.setFont(new Font("Arial", Font.BOLD, 19));
		GSM.setBounds(0, 359, 112, 48);
		contentPane.add(GSM);
		
		/*JLabel GROUPE = new JLabel("  GROUPE :");
		GROUPE.setForeground(Color.WHITE);
		GROUPE.setFont(new Font("Arial", Font.BOLD, 19));
		GROUPE.setBounds(0, 420, 125, 59);
		contentPane.add(GROUPE);*/
		
		JLabel date_naissance = new JLabel("DATE NAISSANCE:");
		date_naissance.setForeground(Color.WHITE);
		date_naissance.setFont(new Font("Arial", Font.BOLD, 19));
		date_naissance.setBounds(336, 295, 193, 50);
		contentPane.add(date_naissance);
		
		/*JLabel ANNEE = new JLabel(" ANNEE :");
		ANNEE.setFont(new Font("Arial", Font.BOLD, 19));
		ANNEE.setForeground(Color.WHITE);
		ANNEE.setBounds(438, 443, 83, 50);
		contentPane.add(ANNEE);*/
		
		
                date_label = new JLabel("Date sous forme: YYYY-MM-DD");
                date_label.setForeground(Color.WHITE);
                date_label.setFont(new Font("Arial", Font.BOLD, 15));
                date_label.setBounds(529,355,250,20);
                contentPane.add(date_label);
                
		NOM_txt = new JTextField();
		NOM_txt.setBounds(523, 381, 253, 51);
		contentPane.add(NOM_txt);
		NOM_txt.setColumns(10);
		
		lblNewLabel_3 = new JLabel("NOM:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_3.setBounds(441, 375, 65, 66);
		contentPane.add(lblNewLabel_3);
		
		
		Prenom_txt = new JTextField();
		Prenom_txt.setBounds(110, 435, 216, 48);
		contentPane.add(Prenom_txt);
		Prenom_txt.setColumns(10);
		
		lblNewLabel_4 = new JLabel("Prenom :");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(10, 440, 90, 59);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_2 = new JLabel("SUBSCRIBE");
		lblNewLabel_2.setFont(new Font("Vladimir Script", Font.BOLD, 73));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(270, 26, 481, 163);
		contentPane.add(lblNewLabel_2);
		
		/*txtChoisissezUnNiveau = new JTextField();
		txtChoisissezUnNiveau.setFont(new Font("Arial", Font.BOLD, 16));
		txtChoisissezUnNiveau.setText("       Choisissez un niveau");
		txtChoisissezUnNiveau.setBounds(523, 446, 253, 47);
		contentPane.add(txtChoisissezUnNiveau);
		txtChoisissezUnNiveau.setColumns(10);*/
		
		JLabel lblNewLabel = new JLabel("");
		//Image image = new ImageIcon(this.getClass().getResource("/library.jpg")).getImage();
		//lblNewLabel.setIcon(new ImageIcon("img/library.jpg"));
		lblNewLabel.setBounds(0, 0, 786, 700);
		contentPane.add(lblNewLabel);
		
		
		
	
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
    }
