
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JPasswordField;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtfuser;
	private JTextField txtfsport;
	private JTextField txtfsip;
	private JPasswordField txtfpass;
	public Socket so;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 223);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIpServer = new JLabel("Server IP");
		lblIpServer.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblIpServer.setBounds(38, 29, 107, 14);
		contentPane.add(lblIpServer);

		JLabel lblNewLabel = new JLabel("Server Port");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(38, 54, 107, 14);
		contentPane.add(lblNewLabel);

		JLabel lblClientUsername = new JLabel("Client Username");
		lblClientUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClientUsername.setBounds(38, 79, 107, 14);
		contentPane.add(lblClientUsername);

		JLabel lblClientPassword = new JLabel("Client Password");
		lblClientPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClientPassword.setBounds(38, 104, 107, 14);
		contentPane.add(lblClientPassword);

		txtfuser = new JTextField();
		txtfuser.setBounds(148, 77, 128, 20);
		contentPane.add(txtfuser);
		txtfuser.setColumns(10);

		txtfsport = new JTextField();
		txtfsport.setBounds(148, 52, 128, 20);
		contentPane.add(txtfsport);
		txtfsport.setColumns(10);

		txtfsip = new JTextField();
		txtfsip.setBounds(148, 27, 128, 20);
		contentPane.add(txtfsip);
		txtfsip.setColumns(10);

		txtfpass = new JPasswordField();
		txtfpass.setBounds(148, 102, 128, 20);
		contentPane.add(txtfpass);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
				
				
				
				
				
				
				//Client cl = new Client();
				
					
					
					String sip = txtfsip.getText();
					String sport = txtfsport.getText();
					String username = txtfuser.getText();
					String password = txtfpass.getText();
	
				
				
						try {


							Socket client = new Socket(txtfsip.getText(),
									Integer.parseInt(sport));
							so = client;

							DataInputStream is = new DataInputStream(client
									.getInputStream());

							DataOutputStream os = new DataOutputStream(client
									.getOutputStream());

							os.writeBytes("LOGIN " + txtfuser.getText() + " "
									+ txtfpass.getText() + "\n");

							String list = is.readLine();

							String [] names = list.split(" ");
							
							InfoFrame infFr = new InfoFrame();
							
							infFr.setTable(names);
							infFr.setVisible(true);
							infFr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							infFr.so = client;
							dispose();

						} catch (Exception e) {

						}

					
			}
		});
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConnect.setBounds(104, 147, 89, 23);
		contentPane.add(btnConnect);

	}
}
