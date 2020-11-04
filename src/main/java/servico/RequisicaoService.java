package servico;
import java.util.List;

import modelo.Requisicao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.RequisicaoDAO;
import excecao.RequisicaoNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;


public class RequisicaoService {
	@Autowired
	private RequisicaoDAO requisicaoDAO;
	
	@Transactional
	public long inclui(Requisicao umaRequisicao) 
	{	return requisicaoDAO.inclui(umaRequisicao);
	}

	@Transactional
	public void altera(Requisicao umaRequisicao)
		throws RequisicaoNaoEncontradoException
	{	try
		{	requisicaoDAO.altera(umaRequisicao);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new RequisicaoNaoEncontradoException("Requisicao não encontrado");
		}
	}

	@Transactional
	public void exclui(Requisicao umaRequisicao) 
		throws RequisicaoNaoEncontradoException
	{	try
		{	
			requisicaoDAO.exclui(umaRequisicao.getNumero());
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new RequisicaoNaoEncontradoException("Requisicao não encontrado");
		}
	}

	public Requisicao recuperaUmaRequisicao(long numero) 
		throws RequisicaoNaoEncontradoException
	{	try
		{	return requisicaoDAO.recuperaUmaRequisicao(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new RequisicaoNaoEncontradoException("Requisicao não encontrado");
		}
	}

	public List<Requisicao> recuperaRequisicoes()
	{	return requisicaoDAO.recuperaRequisicoes();
	}

	public long recuperaQtdPeloNome(String nome) 
	{	
		return requisicaoDAO.recuperaQtdPeloNome(nome + "%");
	}
	
	public List<Requisicao> recuperaPeloNome(String nome, int deslocamento, int linhasPorPagina) 
	{	
		List<Requisicao> requisicoes = requisicaoDAO.recuperaPeloNome(nome + "%", deslocamento, linhasPorPagina);

		return requisicoes;
	}

}
