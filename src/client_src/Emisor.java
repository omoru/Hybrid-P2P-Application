package client_src;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread{
	
	private Socket socket;
	private String filename;
	private ServerSocket sk;
	private DataOutputStream dos;
	private FileInputStream fis;
	
	
	public Emisor(ServerSocket sk,String filename) {
			this.sk=sk;
			this.filename= filename;
	}
	
	public void run() {
		
		try {			
			this.socket=sk.accept();
			//canales para comunicarse con los clientes
			dos = new DataOutputStream(socket.getOutputStream());
	        fis = new FileInputStream(filename);
			int count;
			byte[] bytes = new byte[8 * 1024];
			while((count = fis.read(bytes)) > 0) {
				dos.write(bytes, 0, count);
			}
				
			//cerramos canales y la conexion
			fis.close();
			dos.close();
			socket.close();
			
		}
		catch (Exception e) {
			System.out.println("Algo ha fallado en la conexión");
			e.printStackTrace();
			return;
		}
				
		
		
	}


}
