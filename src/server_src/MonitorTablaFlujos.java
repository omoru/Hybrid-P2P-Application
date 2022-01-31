package server_src;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import users_src.FlujosUsuario;


public class MonitorTablaFlujos {
	
	private ArrayList<FlujosUsuario> tabla_flujos_usuarios = new ArrayList<FlujosUsuario>();//Contiene id, flujo de entrada y flujo de salida de los usuarios
	final Lock lockTablaFlujos = new ReentrantLock();//controla el acceso a la tabla_flujos_usuarios

	public MonitorTablaFlujos() {
		
	}
	
	 //añade los canales del usuario a la tabla de flujos
	 public void añadirFlujosUsuario(FlujosUsuario fu) {
		lockTablaFlujos.lock();
		tabla_flujos_usuarios.add(fu);
		lockTablaFlujos.unlock();
	}
	 
	 
	 //Devuelve la referencia al canal de salida del usuario con id_usuario
	 public ObjectOutputStream getOutputStreamOC(String id_usuario) {
		lockTablaFlujos.lock();
		ObjectOutputStream fout=null;
		boolean encontrado=false;
		for(int i=0; i < tabla_flujos_usuarios.size() && !encontrado;i++){
			if(id_usuario.equals(tabla_flujos_usuarios.get(i).getIdUsuario())) {
				encontrado=true;
				fout = tabla_flujos_usuarios.get(i).getFout();
			}
		}
		lockTablaFlujos.unlock();
		return fout;
	}
	
	
	
	//Devuelve la tabla de flujos de los usuarios
	public ArrayList<FlujosUsuario> getFlujosUsuarios(){
		lockTablaFlujos.lock();
		ArrayList<FlujosUsuario> flujos = new ArrayList<FlujosUsuario>();
		for(int i=0; i < this.tabla_flujos_usuarios.size();i++) {
			FlujosUsuario flujos_aux = new FlujosUsuario(tabla_flujos_usuarios.get(i).getIdUsuario(),
					tabla_flujos_usuarios.get(i).getFout(), tabla_flujos_usuarios.get(i).getFin());
			flujos.add(flujos_aux);
		}
		lockTablaFlujos.unlock();
		return flujos;
	}
	
	//Borra la informacion del usuario con id_usuario de la tabla de flujos
	 public void deleteFlujosUsuario(String id_usuario) {
		lockTablaFlujos.lock();
		for(int i=0; i < tabla_flujos_usuarios.size();i++){
			if(tabla_flujos_usuarios.get(i).getIdUsuario().equals(id_usuario)) {
				tabla_flujos_usuarios.remove(i);
				lockTablaFlujos.unlock();
				return;
			}
		}
		lockTablaFlujos.unlock();
	}
	
	
}
