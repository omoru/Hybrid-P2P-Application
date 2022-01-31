package msg_src;


public class MsgErrorConexion extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MsgErrorConexion(String ip_origen,String ip_destino) {
		super(ip_origen,ip_destino,"MENSAJE_ERROR_CONEXION");		
	}
	


}
