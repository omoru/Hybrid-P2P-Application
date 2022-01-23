package client_src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import msg_src.Mensaje;
import msg_src.MsgConexion;
import msg_src.MsgConfirmListaUsuarios;
import msg_src.MsgConfirmacionCerrarConexion;
import msg_src.MsgEmitirFichero;
import msg_src.MsgErrorConexion;
import msg_src.MsgPreparadoCS;
import msg_src.MsgPreparadoSC;
import users_src.File;
import users_src.Usuario;

public class OyenteServer extends Thread{
	
	private Socket socket;
	private ObjectInputStream f_in; // flujo entrada a cliente
	private Client client;
	private ArrayList<OSobserver> observers;
	
	
	public OyenteServer(Socket s,Client client) {
		try {
			this.client=client;
			this.socket = s;
			this.f_in = new ObjectInputStream(socket.getInputStream());
			this.observers= new ArrayList<OSobserver>();
		} catch (IOException e) {
			System.out.println("Problema en la construccion de un objeto OyenteServer");
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
		if(client.getMode().equalsIgnoreCase("GUI")) {
			runGUI();
		}
		else runBatch();
		
	}
	
	private void runBatch() {
		while(true) {
			Mensaje m;		
				try {
					 m = (Mensaje)f_in.readObject();
					switch(m.getMensaje()) {
						case "MENSAJE_CONFIRMACION_CONEXION":{
							System.out.println("Conexion realizada con server");
							client.getSemaphore().release();
							break;
						}
						case "MENSAJE_ERROR_CONEXION":{
							reintentarConexion((MsgErrorConexion) m);
							break;
						}
						case "MENSAJE_CONFIRMACION_LISTA_USARIOS":{
							System.out.println("Se ha recibido la información de los usuarios");
							ArrayList<Usuario> tabla = ((MsgConfirmListaUsuarios) m).getInfo_usuarios();
							printInfoUsuarios(tabla);
							break;
						}
						case "MENSAJE_CONFIRMACION_AÑADIR_ARCHIVO":{
							System.out.println("Archivo añadido correctamente");
							break;
						}
						case "MENSAJE_EMITIR_FICHERO":{
							enviarArchivo((MsgEmitirFichero) m);
							break;
						}
						case "MENSAJE_PREPARADO_SERVIDORCLIENTE":{
							
							recibirArchivo((MsgPreparadoSC) m);
							break;
						}
						case "MENSAJE_CONFIRMACION_CERRAR_CONEXION":{
							System.out.println("Adios "+ ((MsgConfirmacionCerrarConexion) m).getIdUsuario());
							socket.close();
							f_in.close();
							return;
							
						}
						default:
	                        System.out.println("Mensaje no identificado");break;
					}
				}
				catch (Exception e) {
					System.out.println("Se ha cortado la conexion del server, cerrando OyenteServidor");
					try {
						socket.close();
						f_in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
					return;
				}
				
		
		}
		
	}
	
	private void runGUI() {
		while(true) {
			Mensaje m;		
				try {
					 m = (Mensaje)f_in.readObject();
					switch(m.getMensaje()) {
						case "MENSAJE_CONFIRMACION_CONEXION":{
							System.out.println("Conexion realizada con server");
							for(int i=0; i <observers.size();i++)observers.get(i).onClientConnected(this.client.get_idUsuario());
							break;
						}
						case "MENSAJE_ERROR_CONEXION":{
							for(int i=0; i <observers.size();i++)observers.get(i).onChangeUsername((MsgErrorConexion) m);
							break;
						}
						case "MENSAJE_CONFIRMACION_LISTA_USARIOS":{
							for(int i=0; i <observers.size();i++)observers.get(i).onListaUsuariosRecibida(((MsgConfirmListaUsuarios) m).getInfo_usuarios());
							break;
						}
						case "MENSAJE_CONFIRMACION_AÑADIR_ARCHIVO":{
							for(int i=0; i <observers.size();i++)observers.get(i).onFileAdded();
							break;
						}
						case "MENSAJE_EMITIR_FICHERO":{
							enviarArchivo((MsgEmitirFichero) m);
							break;
						}
						case "MENSAJE_PREPARADO_SERVIDORCLIENTE":{
							recibirArchivo((MsgPreparadoSC) m);
							break;
						}
						case "MENSAJE_CONFIRMACION_CERRAR_CONEXION":{
							System.out.println("Adios "+ ((MsgConfirmacionCerrarConexion) m).getIdUsuario());
							socket.close();
							f_in.close();
							return;
							
						}
						default:
	                        System.out.println("Mensaje no identificado");break;
					}
				}
				catch (Exception e) {
					System.out.println("Se ha cortado la conexion del server, cerrando OyenteServidor");
					try {
						socket.close();
						f_in.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
					return;
				}
				
		
		}
		
	}
	
	private void reintentarConexion(MsgErrorConexion msg) throws IOException {
		client.changeUserName();
		client.sendMensaje(new MsgConexion(client.getIP(),msg.getIPOrigen(), client.get_idUsuario()));
	}
	
	private void enviarArchivo(MsgEmitirFichero msg) throws IOException {
		ServerSocket sk= new ServerSocket(0); // Si metemos 0 como puerto, el socket se encarga de buscar un puerto abierto en el que establece su escucha
		sk.setReuseAddress(true);
		Mensaje mm = new MsgPreparadoCS(msg.getIdUsuario(),msg.getIPOrigen(),client.getIP(),sk.getLocalPort(),msg.getFilename());
		client.sendMensaje(mm);
		new Emisor(sk,msg.getRutaFilename()).start();//peerEmisor
	}
	
	private void recibirArchivo(MsgPreparadoSC msg) {
		String ip_dest= msg.getMyIP();
		int  puerto_dest=msg.getPuertoPropio();
		new Receptor(this.observers,ip_dest,puerto_dest,this.client.getMode(),msg.getFilename()).start();//peerReceptor
	}
	
	
	private void printInfoUsuarios(ArrayList<Usuario> usuarios) {
		System.out.println("\nTABLA INFORMACION USUARIOS");
		for(Usuario u: usuarios) {
			System.out.println("ID: " + u.getIdUsuario());
			System.out.println("Ficheros:");
			for(File file: u.getFiles()) {
				System.out.println(file.getFilename());
			}
			
		}
		
	}
	public void addObserver(OSobserver o) {
		this.observers.add(o);
	}


}
