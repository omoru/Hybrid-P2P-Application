package msg_src;

public class MsgConfirmacionCerrarConexion extends Mensaje{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MsgConfirmacionCerrarConexion(String origen, String dest,String id_usuario) {
		super(origen, dest, "MENSAJE_CONFIRMACION_CERRAR_CONEXION");
		this.id_usuario=id_usuario;
	}


}
