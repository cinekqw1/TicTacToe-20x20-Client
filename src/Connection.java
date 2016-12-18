 import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.ws.handler.MessageContext;

import org.omg.CORBA.portable.Delegate;

public class Connection {

	public static Socket sock;
	private static ReceiveMessage message;
	static int length_of_string = 0;
	
	
	public Connection(int port) throws UnknownHostException, IOException{
		message = new XOButton();
		sock = new Socket("localhost", port);
		t.start();
		
	}
	
	public void send( String data) throws IOException{
		OutputStream os = sock.getOutputStream();
		os.write(data.getBytes());
	}
	
	public  void close() throws IOException{
		sock.close();
	}

	
	static Thread t = new Thread(new Runnable(){
		public void run(){
			byte[] bRes = new byte[100];
			InputStream is;
			int l;
			 try {
				 String s = "";
				 is = sock.getInputStream();
				 StringBuilder sb = new StringBuilder(s);
				 while(true){
					 l = is.read(bRes);
					 sb.append(new String(bRes,0,l));
					 length_of_string++;
					 
					 if(length_of_string==4){
						 message.callBackFromServer(sb.toString());
						 length_of_string=0;
						 sb.deleteCharAt(0);
						 sb.deleteCharAt(0);
						 sb.deleteCharAt(0);
						 sb.deleteCharAt(0);
						 
					 }
						 
						 
					 
					 
				 }
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		}
			 

	});
}
