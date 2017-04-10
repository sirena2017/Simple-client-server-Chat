import static java.lang.System.out;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server {
	Vector<String> users = new Vector<String>();
	Vector<HandleClients> clients = new Vector<HandleClients>();

	public void process() {
		ServerSocket server;
		try {
			server = new ServerSocket(9999);
			out.println("Server Started...");
			while (true) {
				Socket client = server.accept();
				System.out.println("client port is "+ client.getInetAddress());
				HandleClients c = new HandleClients(client);
				//clients.add(c);
			}// end of while
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("process function 1 " + e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("process function 2 " + e.getMessage());
		}

	}// end of process

	public static void main(String[] args) {
		new Server().process();
	} // end of main

	public void boradcast(String otheruser, String message) {
		// send message to all connected users
		System.out.println("Entered broadcast");
		for (HandleClients c : clients){
//			System.out.println("finding client" + otheruser);
			if (c.getUserName().equals(otheruser)){
				System.out.println("found..." + otheruser);
				try {
					//c.sendMessage(otheruser, message);
					c.os.writeBytes("said" + ":" + message);
					System.out.println();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("broadcast function " + e.getMessage());
				}
			}
		}
	}

	class HandleClients extends Thread {
		String name = "";
		String other = "";
		DataInputStream is;
		DataOutputStream os;
		


		public HandleClients(Socket s) throws Exception {
			// TODO Auto-generated constructor stub
			is = new DataInputStream(s.getInputStream());
			os = new DataOutputStream(s.getOutputStream());

			String line = is.readLine();
			String[] command = line.split(" ");
			boolean stop = false;
			
			while (stop != true) {
				if (valid(command[1], command[2]) == true) {
					
					users.addElement(command[1]);
					clients.add(this);
					this.name = command[1];
					
					stop = true;
					
					String list = users.firstElement();
					
					for (int i = 1; i < users.size(); i++) {
						list = list + " " + users.elementAt(i);
					}
					
					os.writeBytes(list + "\n");

					start();
					
				}// end of if
				else {
					JOptionPane.showMessageDialog(null,
							"Wrong username or password", "Dialog",
							JOptionPane.INFORMATION_MESSAGE);
					command[1] = JOptionPane.showInputDialog(null,
							"Enter your username :", "Username",
							JOptionPane.PLAIN_MESSAGE);
					command[2] = JOptionPane.showInputDialog(null,
							"Enter your password :", "Username",
							JOptionPane.PLAIN_MESSAGE);
					
				}
				
			}//end of while

		}// end of constructor

		public boolean valid(String user, String pass) {

			if (user.equals("Zahra") && pass.equals("Zahra")) {

				return true;
			}
			if (user.equals("massy") && pass.equals("massy")) {

				return true;
			}
			if (user.equals("nahid") && pass.equals("nahid")) {

				return true;
			}

			return false;
		}// end of valid

		
		public String getUserName() {
			return name;
		}
		

		public String getOther() {
			return other;
		}
		

		public void sendMessage(String uname, String msg) throws IOException {

			System.out.println(" before send message");
			//os.writeBytes(uname + ":" + msg);
			System.out.println(" after send message");
			for (HandleClients c : clients)
				if (c.getUserName().equals(uname))
					try {
						c.os.writeBytes("said" + ":" + msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						System.out.println("broadcast function " + e.getMessage());
					}
		}

		public void run() {
			String line = "";
			String list = "";
			try {

				boolean stop = false;
				while (stop == false) {
				//	System.out.println("wait for reading");
					line = is.readLine();
					//System.out.println("read line is " + line);
					
					list = users.firstElement();
					
					if (line.contains("LIST")) {
					//	System.out.println("LIST started");
						
						for (int i = 1; i < users.size(); i++) {
							list = list + " " + users.elementAt(i);
						}
						os.writeBytes(list + "\n");
						line = "";
					}
					
					if (line.contains("CONNECT")) {
						//System.out.println("connect started");
						String[] com = line.split(" ");
						other = com[1];
						//System.out.println("connected to " + other);
						os.writeBytes(other + "\n");
						line = "";
						stop = true;
					}
				}

				//System.out.println("exit from first while");
				
				while (true) {
					System.out.println("chat started");
					System.out.println("wait to read");
					line = is.readLine();
					System.out.println("read " + line);
					if (line.equals("end")) {
						clients.remove(this);
						users.remove(name);
						break;
					}
					line = line +"\n";
					boradcast(other, line); // method of outer class - send
											// messages to other
				} // end of while
			} // try
			catch (Exception ex) {
				System.out.println("run function " + ex.getMessage());
			}
		}// end of run

	}// end of inner class

}