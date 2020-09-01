package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import helper.Utils;
import model.Produto;

public class Mercado {
	
	private static Scanner teclado = new Scanner(System.in);
	private static ArrayList<Produto> produtos;
	private static Map<Produto, Integer> carrinho;

	public static void main(String[] args) {
		
		produtos = new ArrayList<Produto>();
		carrinho = new HashMap<Produto, Integer>();
		Mercado.menu();

	}
	
	private static void menu() {
		System.out.println("=================================");
		System.out.println("========== Bem - Vindo ==========");
		System.out.println("========== Super Marky ==========");
		System.out.println("=================================");
		
		System.out.println("Selecione a opção desejada:");
		System.out.println("1 - Cadastrar produto");
		System.out.println("2 - Listar Produtos");
		System.out.println("3 - Comprar produto");
		System.out.println("4 - Visualizar carrinho");
		System.out.println("5 - Sair do sistema");
		System.out.println("OPÇÃO DESEJADA:");
		
		int opcao = 0;
		
		try {
			opcao = Integer.parseInt(Mercado.teclado.nextLine());
		}catch(InputMismatchException e) {
			Mercado.menu();
		}catch(NumberFormatException f) {
			Mercado.menu();
		}
		
		switch(opcao) {
		case 1: 
			Mercado.cadastrarProduto();
			break;
		case 2:
			Mercado.ListarProdutos();
			break;
		case 3:
			Mercado.comprarProduto();
			break;
		case 4: 
			Mercado.visualizarCarrinho();
			break;
		case 5:
			System.out.println("Volte sempre!");
			Utils.pausar(2);
			System.exit(0);
		default:
			System.out.println("Opção inválida");
			Utils.pausar(2);
			Mercado.menu();
			break;
		}
		
	}
	
	private static void cadastrarProduto() {
		System.out.println("=================================");
		System.out.println("=======Cadastro de Produto=======");
		System.out.println("=================================");
		
		try {
			System.out.println("Informe o nome do produto: ");
			String nome = Mercado.teclado.nextLine();
		
			System.out.println("Informe o preço do produto: ");
			Double preco = Double.parseDouble(Mercado.teclado.nextLine());
		

			Produto produto = new Produto(nome, preco);
			Mercado.produtos.add(produto);
			System.out.println("O produto " + produto.getNome() + " foi cadastrado com sucesso");
		
		}catch(NumberFormatException f){
			System.out.println("Dados inválidos, não foi possível cadastrar o produto");
		}finally {
			Utils.pausar(2);
			Mercado.menu();
		}	
		
	}
	
	
	private static void ListarProdutos() {
		
		if(Mercado.produtos.size() > 0) {
			System.out.println("=================================");
			System.out.println("======Listagem de Produtos=======");
			System.out.println("=================================");
			
			Mercado.listagemProdutos();
			
		}else {
			System.out.println("Ainda não possuem produtos cadstrados");
		}
		
		Utils.pausar(2);
		Mercado.menu();
		
	}
	
	private static void comprarProduto() {
		
		if(Mercado.produtos.size() > 0) {
			System.out.println("=================================");
			System.out.println("=======Compra de Produtos========");
			System.out.println("=================================");
			System.out.println("Lista de Produtos: ");
			
			Mercado.listagemProdutos();
			try {
				System.out.println("Digite o código do produto que deseja comprar:");
				int codigo = Integer.parseInt(Mercado.teclado.nextLine());
			
				Mercado.colocaNoCarrinho(codigo);
			
				Mercado.menuCarrinho();
			
			}catch(NumberFormatException e) {
					System.out.println("Dado inválido, retornando ao menu principal");
					Utils.pausar(2);
					Mercado.menu();
				}
				
			}else {
				System.out.println("Ainda não existem produtos cadastrados no mercado");
				Utils.pausar(2);
				Mercado.menu();
		}
	}
	
	private static void visualizarCarrinho() {
		
		if(Mercado.carrinho.size() > 0) {
			System.out.println("=================================");
			System.out.println("======Carrinho de Compras========");
			System.out.println("=================================");
			
			Mercado.listagemCarrinho();
			
		}else {
			System.out.println("Ainda não possuem produtos no seu carrinho");
		}
		
		Utils.pausar(2);
		Mercado.menu();
		
	}

	private static void fecharPedido() {
		Double total = 0.0;
		System.out.println("Carrinho final:");
		Mercado.listagemCarrinho();
		for (Produto p : Mercado.carrinho.keySet()) {
			int quant = Mercado.carrinho.get(p);
			total += p.getPreco() * quant;
		}
		System.out.println("Total do Carrinho = " + Utils.doubleParaString(total));
		Mercado.carrinho.clear();
		System.out.println("Obrigado pela preferência");
		Utils.pausar(5);
		Mercado.menu();
	}
	
	private static void listagemProdutos() {
		for (Produto p : Mercado.produtos) {
			System.out.println(p);
			System.out.println("=================================");
		}
	}
	
	private static void listagemCarrinho() {
		for (Produto p : Mercado.carrinho.keySet()) {
			System.out.println("Produto: " + p + "\nQuantidade: " + Mercado.carrinho.get(p));
			System.out.println("=================================");
		}
	}

	private static void colocaNoCarrinho(int codigo) {
		boolean contem = false;
		
		for(Produto p: Mercado.produtos) {
			if(p.getCodigo() == codigo) {
								contem = true;
								int quant = 0;
								try {
									quant = Mercado.carrinho.get(p);
									quant ++;
									Mercado.carrinho.put(p, quant);
								}catch(NullPointerException e) {
									Mercado.carrinho.put(p, 1);
								}
				System.out.println("O produto " + p.getNome() + " foi adicionado ao carrinho");
			}
		}
		
		if(!contem) {
				System.out.println("Não foi encontrado o produto com o código informado");
				Utils.pausar(2);
				Mercado.menu();
		}
	}
	
	private static void menuCarrinho(){

			System.out.println("Deseja adicionar outros produtos ao carrinho?");
			System.out.println("1 - Adicionar mais produtos");
			System.out.println("2 - Finalizar Compra");
			System.out.println("3 - Voltar ao menu Principal");
			int opcao = 0;
			
			try {
				opcao = Integer.parseInt(Mercado.teclado.nextLine());
			}catch(InputMismatchException e) {
				Mercado.menu();
			}catch(NumberFormatException f) {
				Mercado.menu();
			}
			
			switch(opcao) {
			case 1: 
				Mercado.comprarProduto();
				break;
			case 2:
				Mercado.fecharPedido();
				break;
			case 3:
				Mercado.menu();
				break;
			default:
				System.out.println("Opção inválida");
				Utils.pausar(2);
				Mercado.menuCarrinho();
				break;
			}
			
			
		
	}
}
