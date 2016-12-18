/*
 * Autor: Marcin Choromanski
 * 
 */
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class TicTacToe extends JFrame {
	JPanel p=new JPanel();
	static XOButton buttons[]=new XOButton[400];
	byte value=0;

	private static Connection con;
	
	
	public static void main(String args[]) throws UnknownHostException, IOException, NumberFormatException, JAXBException{
		
		new TicTacToe(Integer.parseInt(args[0]));
		
		
	}
	
	public TicTacToe(int port) throws UnknownHostException, IOException, JAXBException{
		super("TicTacToe");
		
		con = new Connection(port);
		
		
		setSize(600,600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setLayout(new GridLayout(20,20)); 
		for(int i=0;i<400;i++){
			buttons[i]=new XOButton(i);
			p.add(buttons[i]);
		}
		add(p);
		
		setVisible(true);
	}
	
	public static Connection get_con_var(){
		return con;
	}
	
	

	

	
		
	
}