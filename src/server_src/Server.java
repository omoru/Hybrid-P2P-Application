package server_src;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import users_src.FlujosUsuario;
import users_src.Usuario;


public class Server extends Thread {
	
	
	private int PUERTO;
	private String ip_server;
	private MonitorTablaFlujos monitor_tf;
	private MonitorTablaInfo monitor_ti;
	
	
	public Server(int puerto,String ip_server,MonitorTablaFlujos monitor_tf,MonitorTablaInfo monitor_ti) {
		this.PUERTO= puerto;
		this.monitor_tf = monitor_tf;
		this.monitor_ti = monitor_ti;
		this.ip_server = ip_server;
	}
	
	
	
	public void run(){
		ServerSocket s;
		try {
			s = new ServerSocket(PUERTO);
			while(true) {
				System.out.println("Esperando nuevas conexiones...");
				new OyenteClient(s.accept(),this).start(); // detenemos al servidor hasta que llega un cliente
				System.out.println("Se ha establecido una nueva conexión con el cliente");
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String getIpServer() {
		return this.ip_server;
	}
	public boolean userAlreadyExists(String id_user) {
		return monitor_ti.userAlreadyExists(id_user);
	}
	public void añadirUsuario(Usuario u) {
		monitor_ti.addUser(u);
	}
	
	
	public void añadirFlujosUsuario(FlujosUsuario fu) {
		monitor_tf.añadirFlujosUsuario(fu);		
	}
	
	
	
	public ArrayList<Usuario> getUsersInfo(){
		return monitor_ti.getUsersInfo();
	}
	
	public  boolean addFile(String filename,String ruta_filename,String id_usuario) {
		return monitor_ti.addFile(filename,ruta_filename,id_usuario);
	}
	
	
	
	public ArrayList<FlujosUsuario> getFlujosUsuarios(){
		return monitor_tf.getFlujosUsuarios();
	}
	
	
	public void deleteInfoUsuario(String id_usuario) {
		monitor_ti.deleteInfoUsuario(id_usuario);
	}
	
	
	public void deleteFlujosUsuario(String id_usuario) {
		monitor_tf.deleteFlujosUsuario(id_usuario);
	}
	
	public String getOwnerFile(String filename) {
		return monitor_ti.getOwnerFile(filename);
	}
	public ObjectOutputStream getOutputStreamOC(String id_usuario) {
		return monitor_tf.getOutputStreamOC(id_usuario);
	}



	


}
