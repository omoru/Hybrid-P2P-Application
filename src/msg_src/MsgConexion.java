package msg_src;


public class MsgConexion extends Mensaje {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MsgConexion(String ip_origen, String ip_dest,String id_usuario) {
		super(ip_origen,ip_dest, "MENSAJE_CONEXION");
		this.id_usuario= id_usuario;
	}
	
	


	
	
}
