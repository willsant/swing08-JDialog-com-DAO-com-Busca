package visao;

import javax.swing.JDialog;
import javax.swing.JFrame;

import modelo.Cliente;

public abstract class Dialog extends JDialog {

	public Dialog() {
		super();
	}
	public Dialog(JFrame frame) {
		super(frame);
	}
	
	private static final long serialVersionUID = 1L;

	public abstract void designaClienteAFrame(Cliente umCliente);
	public abstract void editavel();
	public abstract void novo();
}
