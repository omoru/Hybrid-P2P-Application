package msg_src;

public class MsgCerrarConexion extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public MsgCerrarConexion(String ip_origen,String ip_dest,String id_usuario) {
		super(ip_origen, ip_dest,"MENSAJE_CERRAR_CONEXION");
		this.id_usuario=id_usuario;
	}
	

}
