package msg_src;

public class MsgConfirmacionConexion extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MsgConfirmacionConexion(String origen, String dest) {
		super(origen, dest, "MENSAJE_CONFIRMACION_CONEXION");
	}


}
