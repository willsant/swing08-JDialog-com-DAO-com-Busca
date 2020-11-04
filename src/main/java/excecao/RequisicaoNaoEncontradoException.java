package excecao;

public class RequisicaoNaoEncontradoException extends Exception {
private final static long serialVersionUID = 1;
	
	private int codigo;
	
	public RequisicaoNaoEncontradoException(String msg)
	{	super(msg);
	}

	public RequisicaoNaoEncontradoException(int codigo, String msg)
	{	super(msg);
		this.codigo = codigo;
	}
	
	public int getCodigoDeErro()
	{	return codigo;
	}
}
