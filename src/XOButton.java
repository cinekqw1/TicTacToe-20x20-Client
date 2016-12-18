import javax.swing.JButton;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.Color;
import java.awt.event.ActionEvent;


public class XOButton extends JButton implements ActionListener, ReceiveMessage {
	
	public static boolean IamFirst;
	static int[][] tab = new int[20][20];
	
	byte value=0;
	int prev=0;
	int id;
	
	
	
	
	
	
	public XOButton(){
		
	}
	
	public XOButton(int id){
		this.id=id;
		this.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		/*
		 * Pierwszy gracz ma 1!
		 */
		if(IamFirst){
			value++;
			value%=2;
			switch(value){
				case 0:
				try {
					TicTacToe.get_con_var().send(String.format("%1$03d", id) +"0"); //format do id=001;
				} catch (IOException e1) {
					e1.printStackTrace();
				}break;
				
				case 1:
				try {
					TicTacToe.get_con_var().send(String.format("%1$03d", id)+"1");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}break;
				
				
				
			}
		}else{
			
			/*
			 * Drugi gracz ma 2!
			 */
			
			value++;
			value%=2;
			switch(value){
				case 0:
				try {
					TicTacToe.get_con_var().send(String.format("%1$03d", id) +"0"); 
				} catch (IOException e1) {
					e1.printStackTrace();
				}break;
				
				
				case 1:
				try {
					TicTacToe.get_con_var().send(String.format("%1$03d", id)+"2");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
					
					break;
				
			}
			
			
		}
	}

	@Override
	public void callBackFromServer(String c) {
	
		if(Integer.parseInt(c)==9999) IamFirst=true;
		
		int first = Integer.parseInt(c)/10;
		int second = Integer.parseInt(c)%10;
		//System.out.println("Button id: "+first+" "+"value: "+second);
		
		int y = first/20;
		int x =first-(y*20);
		System.out.println("x: "+x+" "+"y: "+y);
		if(IamFirst){
			
			
			if(second==0){
				tab[x][y] = 0;
				TicTacToe.buttons[first].setBackground(null);
			} 
			if(second==1){
				tab[x][y] =1;
				TicTacToe.buttons[first].setBackground(Color.GREEN);
			} 	
			if(second==2){
				tab[x][y]=2;
				TicTacToe.buttons[first].setBackground(Color.GRAY);
			} 	
			
			if(Integer.parseInt(c)!=9999){
				if(check(x,y,1)==1){
					System.out.println("Wygrales");
					reset();
				}
				if(check(x,y,2)==1){
					System.out.println("Przegrales");
					reset();
				}
				
			}
			
			
			
			
			
		}else{
			
			if(second==0){
				tab[x][y] = 0;
				TicTacToe.buttons[first].setBackground(null);
			} 
			if(second==1){
				tab[x][y] =1;
				TicTacToe.buttons[first].setBackground(Color.GRAY);
			} 	
			if(second==2){
				tab[x][y]=2;
				TicTacToe.buttons[first].setBackground(Color.GREEN);
			} 	
			
			if(check(x,y,2)==1){
				System.out.println("Wygrales");
				reset();
			}
			if(check(x,y,1)==1){
				System.out.println("Przegrales");
				reset();
			}
			
		
		}
		
		
	}
	
	
	private int check(int x,int y, int v) {
		
		int size =0;
		int size_pion = 0;
		int skos_1 =0;
		int skos_2 =0;
		
		if(tab[x][y]==v){
			size++;
			size_pion++;
			skos_1++;
			skos_2++;
			/*w prawo*/
			int temp = x+1;
			
			while(temp<20){
				if(tab[temp][y]==v){
					size++;
					temp++;
				}else break;
				
			}
			/*w lewo*/
			temp = x-1;
			
			while(temp>-1){
				if(tab[temp][y]==v){
					size++;
					temp--;
				}else break;
				
			}
			/*w gore*/
			temp = y-1;
			
			while(temp>-1){
				if(tab[x][temp]==v){
					size_pion++;
					temp--;
				}else break;
				
			}
			/*w dol*/
			temp = y+1;
			
			while(temp<20){
				if(tab[x][temp]==v){
					size_pion++;
					temp++;
				}else break;
				
			}
			
			int temp_x = x+1;
			int temp_y = y+1;
			while(temp_x<20&temp_y<20){
				if(tab[temp_x][temp_y]==v){
					skos_1++;
					temp_x++;
					temp_y++;
				}else break;
				
			}
			 temp_x = x-1;
			 temp_y = y-1;
			while(temp_x>-1&temp_y>-1){
				if(tab[temp_x][temp_y]==v){
					skos_1++;
					temp_x--;
					temp_y--;
				}else break;
				
			}
			
			 temp_x = x+1;
			 temp_y = y-1;
			while(temp_x<20&temp_y>-1){
				if(tab[temp_x][temp_y]==v){
					skos_2++;
					temp_x++;
					temp_y--;
				}else break;
				
			}
			
			 temp_x = x-1;
			 temp_y = y+1;
			while(temp_x>-1&temp_y<20){
				if(tab[temp_x][temp_y]==v){
					skos_2++;
					temp_x--;
					temp_y++;
				}else break;
				
			}
			
		}
		
		if(size>4){
			
			return 1;
		
		}
		if(size_pion>4){
			
			return 1;
			
		}
		if(skos_1>4){
			
			return 1;
		
		}
		if(skos_2>4){
			
			return 1;
		
		}
		
		return 0;
		
		
		
		
		
	}
	private void reset() {
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++) tab[i][j]=0;
		
		for(int z=0;z<400;z++){
			TicTacToe.buttons[z].setBackground(null);
			TicTacToe.buttons[z].value=0;
			
		}
			

		
	}
	
	
	

	
	
	
}