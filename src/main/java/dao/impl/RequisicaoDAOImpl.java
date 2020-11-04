package dao.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import modelo.Requisicao;

import org.springframework.stereotype.Repository;

import dao.RequisicaoDAO;
import excecao.ObjetoNaoEncontradoException;

@Repository
public class RequisicaoDAOImpl implements RequisicaoDAO {
	
	@PersistenceContext
	private EntityManager em;

    public long inclui(Requisicao umaRequisicao) 
	{	
    	em.persist(umaRequisicao);
		return umaRequisicao.getNumero();
	}

	public void altera(Requisicao umaRequisicao) 
		throws ObjetoNaoEncontradoException 
	{	
		Requisicao requisicao = em.find(Requisicao.class, umaRequisicao.getNumero(), LockModeType.PESSIMISTIC_WRITE);
		
		if(requisicao == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		em.merge(umaRequisicao);
	}

    public void exclui(long id) 
		throws ObjetoNaoEncontradoException 
	{	
		Requisicao requisicao = em.find(Requisicao.class, id, LockModeType.PESSIMISTIC_WRITE);
		
		if(requisicao == null)
		{	throw new ObjetoNaoEncontradoException();
		}
		
		em.remove(requisicao);
	}

    public Requisicao recuperaUmaRequisicao(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Requisicao umaRequisicao = (Requisicao)em
			.find(Requisicao.class, new Long(numero));
			
		if (umaRequisicao == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umaRequisicao;
	}

	public Requisicao recuperaUmaRequisicaoComLock(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Requisicao umaRequisicao = (Requisicao)em
			.find(Requisicao.class, new Long(numero), LockModeType.PESSIMISTIC_WRITE);

		if (umaRequisicao == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umaRequisicao;
	}

	@SuppressWarnings("unchecked")
	public List<Requisicao> recuperaRequisicoes()
	{	
		List<Requisicao> requisicoes = em
			.createQuery("select c from Requisicao c " +
					     "order by c.id asc")
			.getResultList();

		return requisicoes;
	}

	public long recuperaQtdPeloNome(String nome) 
	{	
		long qtd = (Long) em.createQuery("select count(c) from Cliente c where c.nome like :nome")
						    .setParameter("nome", nome.toUpperCase())
							.getSingleResult();
		return qtd;
	}
	
	@SuppressWarnings("unchecked")
	public List<Requisicao> recuperaPeloNome(String nome, 
            							  int deslocamento, 
            							  int linhasPorPagina)
	{	
		List<Requisicao> requisicoes = em
			.createQuery("select c from Requisicao c "
					   + "where c.nome like :nome order by c.nome asc")
			.setParameter("nome", nome.toUpperCase())
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();

		return requisicoes;
	}
	


}
