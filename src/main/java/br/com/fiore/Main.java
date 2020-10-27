package br.com.fiore;

import java.sql.SQLException;
import java.util.Scanner;

import br.com.fiore.dao.ProdutoDao;
import br.com.fiore.model.Produto;

public class Main {

	private static int choiceMenu = 0;
	private static int choiceMenuUpdate = 0;
	
	public static void main(String[] args) {
		menu();
		System.out.println("fim...");
	}

	private static void menu() {
		
		while (choiceMenu != 6) {

			choiceMenu = whichMenu();
			
			switch (choiceMenu) {
			case 1:
				try {
					ProdutoDao dao = new ProdutoDao();
					Produto produto = new Produto();
					dao.save(newProduto(produto));

					System.out.println("Produto salvo com sucesso!");
				} catch (Exception e) {

					System.out.println(e.getMessage());
				}
				break;

			case 2:
				try {
					ProdutoDao dao = new ProdutoDao();
					for (Produto produto : dao.findAll()) {
						showProduto(produto);
					}
				} catch (Exception e) {

					System.out.println(e.getMessage());
				}
				break;
			case 3:
				showprodutoById();
				
				break;
				
			case 4:
				try {
					ProdutoDao dao = new ProdutoDao();
					Produto produto = new Produto();
					
					dao.update(updateProduto(produto, dao));

				} catch (Exception e) {

					System.out.println(e.getMessage());
				}
				break;
				
			case 5:
				delete();

				break;
				
			case 6:
				System.out.println("Sessão finalizada...");
				break;
				
			default:
				System.out.println("valor digitado inválido");
				break;
			}
		
		}
	}
	
	@SuppressWarnings("resource")
	private static int whichMenu() {
		Scanner in = new Scanner(System.in);
		//
		System.out.println("========  MENU PRINCIPAL =======");
		System.out.println("1) CADASTRAR PRODUTO");
		System.out.println("2) MOSTRAR TODOS OS PRODUTOS");
		System.out.println("3) MOSTRAR PRODUTO PELO CODIGO");
		System.out.println("4) ALTERAR PRODUTO");
		System.out.println("5) EXCLUIR PRODUTO");
		System.out.println("6) FINALIZAR SISTEMA");
		//
		System.out.print("ESCOLHA A OPÇÃO DESEJADA: ");
		if(!in.hasNextInt()) {
			System.out.println("Opçao inválida Tente novamente");
			choiceMenu = 0;
		}else {
			choiceMenu = in.nextInt();
	
		}

		return choiceMenu;
	}

	@SuppressWarnings("resource")
	private static void delete() {
		Scanner in = new Scanner(System.in);
		
		try {
			ProdutoDao dao = new ProdutoDao();
			
			in = new Scanner(System.in);
			Integer codigo = null;

			System.out.print("Digite o codigo do Produto => ");
			if (!in.hasNextInt()) {
				
				System.out.println("Codigo inválido");
				delete();
			} else {
				codigo = in.nextInt();
			}
			
			try {
				if(dao.findById(codigo) != null) {
					dao.delete(codigo);
				}else {
					
					System.out.println("O produto que você procurou não existe");
				}
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
		
	}

	private static void showProduto(Produto produto) {

		System.out.println("-----PRODUTO " + produto.getCodigo() + "-----");
		System.out.println("codigo => " + produto.getCodigo());
		System.out.println("produto => " + produto.getProduto());
		System.out.println("preço => " + produto.getPreco());
		System.out.println("quantidade => " + produto.getQuantidade());
		System.out.println("subtotal => " + produto.getSubTotal());
		System.out.println("--------------------");
	}

	@SuppressWarnings("resource")
	private static void showprodutoById() {

		try {
			ProdutoDao dao = new ProdutoDao();
			
			Scanner in = new Scanner(System.in);
			Integer codigo = null;

			System.out.print("Digite o codigo do Produto => ");
			if (!in.hasNextInt()) {
				System.out.println("Codigo inválido");
				showprodutoById();
			} else {
				codigo = in.nextInt();
			}

			try {
				
				if(dao.findById(codigo) != null) {
					showProduto(dao.findById(codigo));
				}else {
					System.out.println("O produto que você procurou não existe");
				}
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@SuppressWarnings("resource")
	private static Produto newProduto(Produto produto) {
		Scanner in = new Scanner(System.in);

		System.out.println("------- Cadastrar um novo Produto --------");
		//
		System.out.print("Digite o nome do produto: ");
		produto.setProduto(in.nextLine());
		//
		System.out.print("Digite o preço: ");
		if (in.hasNextDouble()) {
			produto.setPreco(in.nextDouble());
		} else {
			System.out.println("Erro: você digitou um preço não válido");
			newProduto(produto);
		}
		//
		System.out.print("Digite a quantidade: ");
		if (!in.hasNextInt()) {
			System.out.println("Erro: você digitou uma quantidade não válida");
			newProduto(produto);
		} else {
			produto.setQuantidade(in.nextInt());
		}
		//
		produto.calcularSubTotal();

		return produto;

	}
	
	
	@SuppressWarnings("resource")
	private static Produto updateProduto(Produto produto, ProdutoDao dao) {
		Scanner in = new Scanner(System.in);
		
		int code = 0;
		
		System.out.println("------- Alterar um novo Produto --------");
		//
		System.out.print("Digite o codigo do produto que deseja alterar =>");
		
		if (!in.hasNextInt()) {
			System.out.println("Codigo inválido");
		} else {
			code = in.nextInt();
		}
	
		try {
			
			if(dao.findById(code) == null) {
				System.out.println("O produto que você procurou não existe");
				updateProduto(produto, dao);
				
			}else {
				
				produto = dao.findById(code);
				
				choiceMenuUpdate = 0;
				
				while (choiceMenuUpdate != 4) {
					
					produto = getValueUpdate(produto);
				}
				
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return produto;
	}
	
	
	@SuppressWarnings("resource")
	private static Produto getValueUpdate(Produto produto) {
		choiceMenuUpdate = menuUpdate(produto);
		Scanner in = new Scanner(System.in);
		switch (choiceMenuUpdate) {
			case 1:
				System.out.println("Nome do produto atual => " + produto.getProduto());
				System.out.print("Digite o novo nome do produto: ");
				produto.setProduto(in.nextLine());
				break;
				
			case 2:
				System.out.println("Preço atual => " + produto.getPreco());
				System.out.print("Digite o novo preço: ");
				
				if (in.hasNextDouble()) {
					produto.setPreco(in.nextDouble());
				} else {
					System.out.println("Erro: você digitou um preço não válido");
					getValueUpdate(produto);
				}
				break;
				
			case 3:
				
				System.out.println("Quantidade atual => " + produto.getQuantidade());
				System.out.print("Digite a nova quantidade: ");
				if (!in.hasNextInt()) {
					System.out.println("Erro: você digitou uma quantidade não válida");
					getValueUpdate(produto);
				} else {
					produto.setQuantidade(in.nextInt());
				}
				break;
				
			case 4:
				System.out.println("Alteração concluida...");
				break;

			default:
				System.out.println("valor digitado inválido");
				break;
			}
		
		produto.calcularSubTotal();
			
		return produto;
	}
	

	@SuppressWarnings("resource")
	private static int menuUpdate(Produto produto) {
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("====== Alterar Produto: " + produto.getProduto() + " ======");
		System.out.println("1) ALTERAR NOME DO PRODUTO");
		System.out.println("2) ALTERAR PREÇO");
		System.out.println("3) ALTERAR QUANTIDADE");
		System.out.println("4) FINALIZAR ALTERAÇÃO");
		
		System.out.print("ESCOLHA A OPÇÃO DESEJADA: ");
		if(!in.hasNextInt()) {
			System.out.println("Opçao inválida Tente novamente");
			
		}else {
			choiceMenuUpdate = in.nextInt();
	
		}
		
		return choiceMenuUpdate;
	}

}
