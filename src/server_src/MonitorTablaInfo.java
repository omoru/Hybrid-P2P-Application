package server_src;


import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import users_src.File;
import users_src.Usuario;


public class MonitorTablaInfo {
	
	private ArrayList<Usuario> tabla_informacion_usuarios= new ArrayList<Usuario>();;//Contiene id, ip y ficheros de los usuarios
	final Lock lockTablaInfo = new ReentrantLock();//controla el acceso a la tabla información de usuarios

	public MonitorTablaInfo() {
		
	}
	//Devuelve true si el id_usuario yá está en server
		public boolean userAlreadyExists(String id_usuario) {
			lockTablaInfo.lock();
			for(int i=0; i < tabla_informacion_usuarios.size();i++){
				if(tabla_informacion_usuarios.get(i).getIdUsuario().equals(id_usuario)) {
					lockTablaInfo.unlock();
					return true;
				}
			}
			lockTablaInfo.unlock();
			return false;
		}
		
		//Devuelve el id del usuario que tiene el archivo indicado por parámetros
		 public String getOwnerFile(String filename) {
			lockTablaInfo.lock();
			String id_owner=null;
			boolean encontrado= false;
			for(int i=0; i < tabla_informacion_usuarios.size() && !encontrado;i++){
				for(File f:tabla_informacion_usuarios.get(i).getFiles()) {
					if(f.getFilename().equals(filename)) {
						id_owner= tabla_informacion_usuarios.get(i).getIdUsuario();
						encontrado=true;
						break;					
					}
				}
			}
			lockTablaInfo.unlock();
			return id_owner;
		}
		
		
		 	
		 //Añade al usuario a la base de datos
		 public void addUser(Usuario u) {
			lockTablaInfo.lock();
			tabla_informacion_usuarios.add(u);
			lockTablaInfo.unlock();
		}
		
		 //Añade el archivo a la lista  del usuario en la base de datos.
		 //Devuelve true si lo consigue y false en otro caso
		 public boolean addFile(String filename,String ruta_filename,String id_usuario) {
			 lockTablaInfo.lock();
			 for(int i=0; i < tabla_informacion_usuarios.size();i++){
					if(tabla_informacion_usuarios.get(i).getIdUsuario().equals(id_usuario)) {
						this.tabla_informacion_usuarios.get(i).addFile(new String(filename), new String(ruta_filename));
						lockTablaInfo.unlock();
						return true;
					}
				}
			 lockTablaInfo.unlock();
			 return false;
		 }
		 
		 //Devuelve la tabla de informacion de los usuarios con sus ficheros
		 public ArrayList<Usuario> getUsersInfo(){
			 lockTablaInfo.lock();
			 ArrayList<Usuario> table_info = new ArrayList<Usuario>();
		        for(Usuario user : tabla_informacion_usuarios) {
		        	table_info.add(new Usuario(user));
		        }
		     lockTablaInfo.unlock();
		     return table_info;
		}
		
		 //Borra la informacion del usuario con id_usuario de la tabla de informacion
		 public void deleteInfoUsuario(String id_usuario) {
			lockTablaInfo.lock();
			for(int i=0; i < tabla_informacion_usuarios.size();i++){
				if(tabla_informacion_usuarios.get(i).getIdUsuario().equals(id_usuario)) {
					tabla_informacion_usuarios.remove(i);
					lockTablaInfo.unlock();
					return;
				}
			}
			lockTablaInfo.unlock();
		}
		

}
