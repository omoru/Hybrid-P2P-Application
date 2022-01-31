package msg_src;

import java.util.ArrayList;

import users_src.Usuario;

public class MsgConfirmListaUsuarios extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Usuario> info_usuarios;
	
	public MsgConfirmListaUsuarios(String origen, String dest,ArrayList<Usuario> info_usuarios) {
		super(origen, dest, "MENSAJE_CONFIRMACION_LISTA_USARIOS");
		this.info_usuarios=info_usuarios;
	}
	public ArrayList<Usuario> getInfo_usuarios() {
		return this.info_usuarios;
	}

}
