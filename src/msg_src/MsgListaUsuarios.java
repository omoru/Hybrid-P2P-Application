package msg_src;

public class MsgListaUsuarios extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MsgListaUsuarios(String id_usuario,String origen, String dest) {
		super(origen, dest, "MENSAJE_LISTA_USARIOS");
		this.id_usuario=id_usuario;

	}


}
