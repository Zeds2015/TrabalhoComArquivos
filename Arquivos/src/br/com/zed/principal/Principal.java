package br.com.zed.principal;

import java.util.Scanner;

import br.com.zed.Arquivos.ArquivoBat;
import br.com.zed.Arquivos.ArquivoRtf;
import br.com.zed.Arquivos.ArquivoTxt;
import br.com.zed.sistema.ControladorDeArquivos;
import br.com.zed.sistema.TipoArquivo;

public class Principal {
	private static ControladorDeArquivos controlador;
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		Programa();
	}

	private static void PularLinha() {
		System.out.println();
		System.out.println();
	}

	private static void TiposArquivos() {
		System.out.println("(1) TXT - Bloco de notas");
		System.out.println("(2) RTF - Formato Word");
		System.out.println("(3) BAT - Executavel Windows");
	}

	private static void MostrarOpcoes() {
		System.out.println("(0) Listar arquivos no diretório");
		System.out.println("(1) Escrever no arquivo");
		System.out.println("(2) Ler do arquivo");
		System.out.println("(3) Copiar arquivo");
		System.out.println("(4) Renomear Arquivo");
		System.out.println("(5) Deletar Arquivo");
		System.out.println("(6) Ver quantas vezes uma palavra foi digitada");
		System.out.println("(7) Converter Arquivo");
		System.out.println("(8) Mexer em outro programa");
		System.out.println("(9) Executar Bat");
		System.out.println("(10) Sair");
	}

	private static void ConverterDePara() {
		boolean tipoTexto = controlador.GetArquivo().GetTipoDoArquivo().equals(TipoArquivo.txt.GetTipoDoArquivo());
		boolean tipoWord = controlador.GetArquivo().GetTipoDoArquivo().equals(TipoArquivo.rtf.GetTipoDoArquivo());

		if (tipoTexto) {
			System.out.println("(1) De TXT para RTF");
			System.out.println("(2) De TXT para BAT");
		} else if (tipoWord) {
			System.out.println("(3) De RTF para TXT");
			System.out.println("(4) De RTF para BAT");
		} else {
			System.out.println("(5) De BAT para TXT");
			System.out.println("(6) De BAT para RTF");
		}
	}

	private static void Programa() {
		System.out.println("Deseja criar um arquivo? Apenas digite sim caso queira");
		String escolha = teclado.nextLine();
		if (escolha.toLowerCase().contains("sim") || escolha.toLowerCase().contains("yes")) {
			EscolherTipoDeArquivo(false);
			CriarArquivo();
		} else {
			System.out.println("Deseja modificar um arquivo? Apenas digite sim caso queira");
			escolha = teclado.nextLine();
			if (escolha.toLowerCase().contains("sim") || escolha.toLowerCase().contains("yes")) {
				EscolherTipoDeArquivo(false);
				ModificarArquivo();
			} else {
				System.out.println("Até um outro dia!");
			}
		}
	}

	private static void EscolherTipoDeArquivo(boolean reset) {
		teclado = new Scanner(System.in);
		System.out.print("Digite o nome do arquivo: ");
		String nomeDoArquivo = teclado.nextLine();
		PularLinha();
		TiposArquivos();
		System.out.print("Escolha o tipo do arquivo: ");
		int tipoDoArquivo = teclado.nextInt();

		switch (tipoDoArquivo) {
		case 1:
			controlador = new ControladorDeArquivos(new ArquivoTxt(nomeDoArquivo));
			break;
		case 2:
			controlador = new ControladorDeArquivos(new ArquivoRtf(nomeDoArquivo));
			break;
		default:
			controlador = new ControladorDeArquivos(new ArquivoBat(nomeDoArquivo));
			break;
		}
		if (reset)
			ModificarArquivo();
	}

	private static void ModificarArquivo() {
		if (!controlador.ExisteArquivo()) {
			System.out.println("Arquivo não existe, foi criado um!");
			CriarArquivo();
		}
		OpcoesArquivo();
	}

	private static void CriarArquivo() {
		if (controlador.ExisteArquivo()) {
			System.out.println("Arquivo já existe, abrimos ele.");
			OpcoesArquivo();
		}
		controlador.CriarArquivo();
		OpcoesArquivo();
	}

	private static void OpcoesArquivo() {
		int opcao;
		do {
			PularLinha();
			System.out.println(controlador.toString());
			MostrarOpcoes();
			System.out.print("Escolha uma opção: ");
			opcao = teclado.nextInt();
			switch (opcao) {
			case 0:
				ListarArquivos();
				OpcoesArquivo();
				break;
			case 1:
				EscreverNoArquivo();
				OpcoesArquivo();
				break;

			case 2:
				PularLinha();
				System.out.println("Texto Contido no arquivo...");
				controlador.GetArquivo().LerTexto();
				OpcoesArquivo();
				break;
			case 3:
				CopiarArquivo();
				OpcoesArquivo();
				break;

			case 4:
				RenomearArquivo();
				OpcoesArquivo();
				break;
			case 5:
				teclado = new Scanner(System.in);
				PularLinha();
				System.out.println("Tem certeza que deseja deletar? Apenas digite sim caso queira");
				String decisao = teclado.nextLine();
				if (decisao.toLowerCase().contains("sim") || decisao.toLowerCase().contains("yes")) {
					System.out.println("Deletando arquivo...");
					controlador.DeletarArquivo();
					if (!controlador.ExisteArquivo()) {
						System.out.println("Arquivo deletado com sucesso!");
						Programa();
					} else
						System.out.println("Infelizmente o arquivo não foi deletado!");
				} else
					System.out.println("Operação cancelada!");
				OpcoesArquivo();
				break;

			case 6:
				QuantasVezesDigiteiAPalavra();
				OpcoesArquivo();
				break;
			case 7:
				Conversor();
				OpcoesArquivo();
				break;
			case 8:
				EscolherTipoDeArquivo(true);
				OpcoesArquivo();
				break;
			case 9:
				ExecutarArquivo();
				OpcoesArquivo();
				break;
			case 10:
				System.exit(0);
				break;
			}
		} while (opcao > 10 || opcao <= 0);
	}

	private static void ExecutarArquivo() {
		PularLinha();
		boolean ehArquivoBat = controlador.GetArquivo().GetTipoDoArquivo().equals(TipoArquivo.bat.GetTipoDoArquivo());
		if (ehArquivoBat) {
			controlador.Executar();
		} else
			System.out.println("Infelizmente não podemos executar este tipo de arquivo");
	}

	private static void Conversor() {
		PularLinha();
		teclado = new Scanner(System.in);
		System.out.println("Deseja fazer uma cópia antes? (Apenas digite sim caso queira) O arquivo original será perdido no processo");
		String copiar = teclado.nextLine();
		if(copiar.toLowerCase().contains("sim") || copiar.toLowerCase().contains("yes")) {
			CopiarArquivo();
		}
		ConverterDePara();
		System.out.print("Selecione o tipo de conversão: ");
		int escolha = teclado.nextInt();
		switch (escolha) {
		case 1:
			controlador.ConverterTipos(TipoArquivo.txt, true);
			break;
		case 2:
			controlador.ConverterTipos(TipoArquivo.txt, false);
			break;
		case 3:
			controlador.ConverterTipos(TipoArquivo.rtf, true);
			break;
		case 4:
			controlador.ConverterTipos(TipoArquivo.rtf, false);
			break;
		case 5:
			controlador.ConverterTipos(TipoArquivo.bat, true);
			break;
		default:
			controlador.ConverterTipos(TipoArquivo.bat, false);
			break;
		}
	}

	private static void RenomearArquivo() {
		teclado = new Scanner(System.in);
		PularLinha();
		System.out.print("Digite o novo nome do arquivo: ");
		String nomeNovo = teclado.nextLine();
		if (!controlador.ExisteArquivo(nomeNovo)) {
			controlador.Renomear(nomeNovo);
			System.out.println("Renomeado com sucesso!");
		} else {
			System.out.println("Sistema Operacional não deixa, ter dois arquivos com mesmo nome.");
		}
	}

	private static void CopiarArquivo() {
		teclado = new Scanner(System.in);
		PularLinha();
		System.out.print("Digite o nome do arquivo a receber a cópia: ");
		String nomeDaCopia = teclado.nextLine();
		if (controlador.ExisteArquivo(nomeDaCopia)) {
			System.out.println("Deseja sobreescrever?");
			String opcao = teclado.nextLine();
			if (opcao.toLowerCase().contains("sim") || opcao.toLowerCase().contains("sim"))
				controlador.CopiarArquivo(nomeDaCopia, true);
			else
				controlador.CopiarArquivo(nomeDaCopia, false);
		} else
			controlador.CopiarArquivo(nomeDaCopia, true);
	}

	private static void EscreverNoArquivo() {
		PularLinha();
		String palavra;
		System.out.println("Digite o seu texto aqui, Digite (quit) para sair...");
		do {
			palavra = teclado.nextLine();
			if (!palavra.toLowerCase().matches("quit"))
				controlador.GetArquivo().EscreverNoArquivo(palavra);
		} while (!palavra.toLowerCase().matches("quit"));
	}

	private static void QuantasVezesDigiteiAPalavra() {
		teclado = new Scanner(System.in);
		PularLinha();
		System.out.print("Digite a palavra que deseja saber sobre: ");
		String palavra = teclado.nextLine();
		int quantidade = controlador.QuantasVezesDigiteiTalPalavra(palavra);
		System.out.printf("A palavra %s foi digitada %d vezes.", palavra, quantidade);
	}

	private static void ListarArquivos() {
		controlador.ListarDiretorios();
	}
}