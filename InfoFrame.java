
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class InfoFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public Socket so;
	
	
	
	

	/*public void setCl(Client cli) {
		this.cl = cli;
	}*/

	public void setTable(String [] names){
		int i = 0;
		for (String string : names) {
			table.getModel().setValueAt(string, i, 0);
			i++;
		}
	}


	/**
	 * Launch the application.
	 */
	/**
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoFrame frame = new InfoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
**/
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public InfoFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 202);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 151, 141);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setFont(new Font("Tahoma", Font.BOLD, 11));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Idle Online Hosts"
			}
		));
		
		
		
		
		JButton btnShowOnline = new JButton("Show Online Idle Host");
		btnShowOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			//	System.out.println(cl.getIp()+"pass:"+cl.getPort());
				try {

					//Server server;

					Socket client = new Socket();
					client = so;

					//System.out.println(cl.getIp()+"pass:"+cl.getPort());
					
					DataInputStream dis = new DataInputStream(client.getInputStream());
					//DataInputStream is = new DataInputStream(so.getInputStream());

					 DataOutputStream dos = new DataOutputStream(client.getOutputStream());
					//DataOutputStream os = new DataOutputStream(so.getOutputStream());

					dos.writeBytes("LIST\n");

					String list = dis.readLine();
					System.out.println("table  "+list);
					String [] names = list.split(" ");
					setTable(names);
					
					((DefaultTableModel)table.getModel()).addRow(names);
					
					addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowIconified(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeiconified(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeactivated(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosing(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosed(WindowEvent arg0) {
							// TODO Auto-generated method stub
							/*try {
								dos.writeBytes("CLOSE\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
							
						}
						
						@Override
						public void windowActivated(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					

					

					
				} catch (Exception e) {

				}
				
			}
		});
		btnShowOnline.setBounds(196, 39, 190, 23);
		contentPane.add(btnShowOnline);
		
		JButton btnHostConnect = new JButton("Connect to this Host...");
		btnHostConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String user = table.getModel().getValueAt(table.getSelectedRow(),table.getSelectedColumn()).toString();
				System.out.println(user);
				
				try {

				//	Server server;

					Socket client = so;

					DataInputStream is = new DataInputStream(client
							.getInputStream());

					DataOutputStream os = new DataOutputStream(client
							.getOutputStream());

					os.writeBytes("CONNECT " + user + "\n");

					String list = is.readLine();
					//System.out.println("response of connect is " + list);
				
					try{
					ChatFrame chfFr = new ChatFrame(client);
					chfFr.setVisible(true);
					chfFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					chfFr.setTitle("connected to "+ list);
					
					
					
					
					}catch(Exception e){
						System.out.println("can't start chat frame" + e.getMessage());
					}
				//	System.out.println("after newing chat Frame");
					dispose();
			
					
					

				} catch (Exception e) {

				}


				
				
			}
		});
		btnHostConnect.setBounds(196, 73, 190, 23);
		contentPane.add(btnHostConnect);
		
		
	}
}
