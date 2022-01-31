package client_src;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import gui.ClientMainWindow;
import msg_src.Mensaje;
import msg_src.MsgAniadirArchivo;
import msg_src.MsgCerrarConexion;
import msg_src.MsgConexion;
import msg_src.MsgListaUsuarios;
import msg_src.MsgPedirFichero;

public class Client extends Thread{
		
	private int puerto;//puerto de conexion al server
	private String ip_client;//ip del cliente
	private String mode;//gui o batch
	private OyenteServer OS;
	private ObjectOutputStream f_out;
	private String ip_host;//ip server
	private String id_usuario;
	private Semaphore waitConected = new Semaphore(0);

	
	public Client(int puerto, String ip_client, String ip_host,String runMode) {
		this.puerto = puerto;
		this.ip_client = ip_client;
		this.ip_host= ip_host;
		this.mode= runMode;
		
	}
	

	public Semaphore getSemaphore() {
		return this.waitConected;
	}
	
	public String getMode() {
		return this.mode;
	}
	
	public String getIP_HOST() {
		return this.ip_host;
	}
	public String getIP() {
		return ip_client;
	}
	
	
	
	public String get_idUsuario() {
		return this.id_usuario;
	}
	
	public void setIdUsuario(String id_user) {
		this.id_usuario= id_user;
	}
	
	
	 public void sendMensaje(Mensaje m) {
		try {
			f_out.writeObject(m);
		} catch (IOException e) {
			System.out.println("No se pudo enviar el mensaje");
			e.printStackTrace();
		}
	}
	 
		public void addObserver(OSobserver o) {
			this.OS.addObserver(o);
			
		}
		
	
	
	public void run() {
			if(mode.equalsIgnoreCase("GUI")) {
				runGUI();	
			}
			else if(mode.equalsIgnoreCase("BATCH")) 
				runBatch();	
	}
	
	
	private void runGUI() {
		try {			
			String name = JOptionPane.showInputDialog("Introduzca su nombre porfavor");
			while(name.length()==0) {
				name = JOptionPane.showInputDialog("El nombre no puede estar vacio");
			}
			this.id_usuario = name;
			
			Socket socket = new Socket(ip_client,puerto);
			this.f_out= new ObjectOutputStream(socket.getOutputStream());
			this.OS =new OyenteServer(socket,this);
			Client cliente = this;
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					new ClientMainWindow(cliente);						
				}	
				
			});
		
			OS.start();
			Mensaje m = new MsgConexion(ip_client,ip_host,id_usuario);
			f_out.writeObject(m);	
		}catch(Exception e) {
			System.out.println("Mal funcionamiento en Cliente");
			e.printStackTrace();
			return;
		
		}	
	}

	
	private void runBatch() {
		try {
			Socket socket = new Socket(ip_client,puerto);
			this.f_out= new ObjectOutputStream(socket.getOutputStream());
				
			//El usuario nos proporciona el nombre 
			Scanner sc = new Scanner(System.in);
			System.out.println("Introduzca su nombre porfavor ");
			this.id_usuario = sc.nextLine();
		
			new OyenteServer(socket,this).start();
			Mensaje m = new MsgConexion(ip_client,ip_host,id_usuario);
			f_out.writeObject(m);	
			/////////////////////////////
			waitConected.acquire();// el proceso cliente se detiene hasta que le despierta el oyente cliente al recibir confirmación conexión
			////////////////////////////
			System.out.println("Bienvenido, "+this.id_usuario);
			
			int option;
			do{
				displayMenu();
				option = sc.nextInt();
				//Envia mensaje a server
				Mensaje msg = procesaOpcion(option,id_usuario,ip_client,ip_host);
				if(msg!=null)
					f_out.writeObject(msg);
			}while(option!=0);
			sc.close();
		
		}catch(Exception e) {
			System.out.println("Mal funcionamiento en Cliente");
			e.printStackTrace();
			return;
		
		}	
		
	}
	
	
	
	
	
	private Mensaje procesaOpcion(int op,String nombre,String ip_client,String ip_host) {
		Mensaje m = null;
		try {
			
			switch(op) {			
				case 1://enviar MENSAJE_LISTA_USARIOS
					m = new MsgListaUsuarios(nombre,ip_client,ip_host);
					break;
				case 2://enviar MENSAJE_PEDIR_FICHERO
					System.out.println("Introduce nombre fichero:");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String filename = br.readLine();
					m = new MsgPedirFichero(nombre,ip_client, ip_host, filename);			
					break;
				case 3:
					System.out.println("Introduce nombre archivo a añadir:");
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String filename1,ruta_filename;
					filename1 = br1.readLine();
					System.out.println("Introduce la ruta del archivo:");
					ruta_filename= br1.readLine();
					File file= new File(ruta_filename);
					if(file.exists())
						m= new MsgAniadirArchivo(ip_client, ip_host, filename1,ruta_filename,id_usuario);
					else System.out.println("El archivo no existe");
					break;
				case 0://enviar MENSAJE_CERRAR_CONEXION
					m = new MsgCerrarConexion(ip_client,ip_host,nombre);
					break;
				default: System.out.println("Opcion no reconocida");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();			
		}
		
		return m;	
	}
	

	
	
	private void displayMenu() {
		System.out.println("Menú:");
		System.out.println("==========");
		System.out.println("1 -consultar lista usuarios");
		System.out.println("2 -pedir fichero");
		System.out.println("3-añadir fichero");
		System.out.println("0 -salir");
		System.out.println("Introduzca el numero de opcion: ");		
	}
	

	
	public void changeUserName() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Introduzca otro id, el que ha elegido esta repetido: ");
		String nombre = br.readLine();
		this.id_usuario = nombre;
		
	}
	
	
	
	
	

	
	

}
