package br.com.lopes.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.lopes.model.Produto;
import br.com.lopes.util.JpaUtils;

@ManagedBean
@ViewScoped
public class ProdutoController {
	private Produto produto;
	private List<Produto> produtos; // m�todo init serve s� para vermos em que
									// momento o bean � destru�do

	@PostConstruct
	public void init() {
		System.out.println("ProdutoController.init()");
		atribuirEstadoInicial();
	}

	/**
	 * * Deixa o bean em um estado inicial v�lido tanto para edi��o quanto para
	 * listagens
	 */
	private void atribuirEstadoInicial() {
		System.out.println("ProdutoController.atribuirEstadoInicial()"); // serve
																			// para
																			// deixar
																			// o
																			// bean
																			// em
																			// um
																			// estado
																			// onde
																			// pode
																			// acontecer
																			// uma
																			// nova
																			// edi��o
		produto = new Produto(); // limpa a listagem previamente carregada pois
									// ela n�o cont�m um elemento novo ou cont�m
									// um rec�m exclu�do
		produtos = null;
	}

	public void salvar() {
		System.out.println("ProdutoController.salvar()");
		JpaUtils.getEntityManager().getTransaction().begin();
		JpaUtils.getEntityManager().merge(produto);
		JpaUtils.getEntityManager().getTransaction().commit();
		atribuirEstadoInicial();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		System.out.println("ProdutoController.setProduto(): " + produto);
		this.produto = produto;
	}

	@SuppressWarnings("unchecked")
	public List<Produto> getProdutos() {
		if (produtos == null) {
			produtos = JpaUtils.getEntityManager()
					.createQuery("select p from Produto p").getResultList();
		}
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
