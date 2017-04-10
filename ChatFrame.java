import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;



import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtf;
	private JEditorPane txtme;
	private JEditorPane txthe;
	public Socket so;
	DataInputStream is;
	DataOutputStream os;
	
	public static boolean f = false;


	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame frame = new ChatFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("newing chat Frame" + e.getMessage());
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public ChatFrame(Socket so) {
		this.so = so;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 417, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtme = new JEditorPane();
		txtme.setBounds(10, 25, 184, 301);
		txtme.setEditable(false);
		contentPane.add(txtme);

		txthe = new JEditorPane();
		txthe.setBounds(204, 25, 187, 301);
		contentPane.add(txthe);
		txthe.setEditable(false);

		JButton btnSend = new JButton("Send");

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				f = true;
				
			
				
				/**
				
				String msg = txtf.getText();
				txtme.setText(txtme.getText() + "\n" + msg);
				txtf.setText("");
				try {
					os.writeBytes(msg+"\n");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("send button " + e.getMessage());
				}//catch
				**/
			}
		});
		
		btnSend.setBounds(302, 338, 89, 41);
		contentPane.add(btnSend);

		txtf = new JTextField();
		txtf.setBounds(10, 338, 282, 40);
		contentPane.add(txtf);
		txtf.setColumns(10);

		JLabel lblMe = new JLabel("Me");
		lblMe.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMe.setBounds(88, 11, 46, 14);
		contentPane.add(lblMe);

		JLabel lblHeshe = new JLabel("He/She");
		lblHeshe.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHeshe.setBounds(283, 11, 46, 14);
		contentPane.add(lblHeshe);
		
		
		try {
		//	so = new Socket("localhost",9999);
			is = new DataInputStream(so.getInputStream());
			os = new DataOutputStream(so.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("newing is & os " + e.getMessage());
		}
		
		
		new MessagesThread().start();
		new MessagesWrite().start();

	}

	
	// inner class
	class MessagesThread extends Thread {
		public void run() {
			String line = "";
			try {
				while (true) {
					System.out.println("wait reading client");
					line = is.readLine();
					System.out.println("client recieved " + line);
					txthe.setText(txthe.getText() + line + "\n");
				} // end of while
			} catch (Exception ex) {
				System.out.println("Message Thread " + ex.getMessage());
			}
		}
	}//end of inner class
	
	class MessagesWrite extends Thread {
		public void run() {
			try {
				while (true) {
					Thread.sleep(1000);
					if(f== true){
						System.out.println("write " + txtf.getText());
						os.writeBytes(txtf.getText() + "\n");
						f=false;
						String msg = txtf.getText();
						txtf.setText("");
						txtme.setText(txtme.getText() + msg + "\n");
					}
					
				} // end of while
			} catch (Exception ex) {
				System.out.println("Message Thread Write " + ex.getMessage());
			}
		}
	}//end of inner class
	
	
}
