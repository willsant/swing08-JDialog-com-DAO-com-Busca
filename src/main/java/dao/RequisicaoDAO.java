package dao;

import java.util.List;


import modelo.Requisicao;
import excecao.ObjetoNaoEncontradoException;

public interface RequisicaoDAO {
	public long inclui(Requisicao umaRequisicao); 

	public void altera(Requisicao umaRequisicao)
		throws ObjetoNaoEncontradoException; 
	
	public void exclui(long id) 
		throws ObjetoNaoEncontradoException; 
	
	public Requisicao recuperaUmaRequisicao(long numero) 
		throws ObjetoNaoEncontradoException; 

	public Requisicao recuperaUmaRequisicaoComLock(long numero) 
			throws ObjetoNaoEncontradoException; 
	
	public List<Requisicao> recuperaRequisicoes();
	
	long recuperaQtdPeloNome(String nome);
	
	List<Requisicao> recuperaPeloNome(String nome, 
         						   int deslocamento, 
            					   int linhasPorPagina);

}
