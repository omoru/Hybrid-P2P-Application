package launcher;

import server_src.MonitorTablaFlujos;
import server_src.MonitorTablaInfo;
import server_src.Server;

public class MainServer {

	
	public static void main(String[] args) {
	
			if(args.length!=2) {
				System.out.println("Arguments: ip_server server_port");
				System.out.println("Example: 192.169.0.21 555");
				return;
			}
			String ip_server =args[0];
			int PUERTO = Integer.parseInt(args[1]);
			MonitorTablaFlujos monitor_tf = new MonitorTablaFlujos();
			MonitorTablaInfo monitor_ti = new MonitorTablaInfo();
			//Iniciamos el server
			Server server = new Server(PUERTO,ip_server,monitor_tf,monitor_ti);
			server.start();
			try {
				server.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
