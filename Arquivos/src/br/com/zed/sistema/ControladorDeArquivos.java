package br.com.zed.sistema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import br.com.zed.Arquivos.Arquivo;
import br.com.zed.Arquivos.ArquivoBat;

public class ControladorDeArquivos {
	private File controlador;
	private Arquivo arquivo;
	private Date data;

	public ControladorDeArquivos(Arquivo arquivo) {
		data = new GregorianCalendar().getTime();
		this.arquivo = arquivo;
		controlador = new File(this.arquivo.GetNomeDoArquivo());
	}

	public void CriarArquivo() {
		try {
			controlador.createNewFile();
		} catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
		}
	}

	public boolean ExisteArquivo(String nomeDoArquivo) {
		nomeDoArquivo += arquivo.GetTipoDoArquivo();
		File arquivo = new File(nomeDoArquivo);
		return arquivo.exists();
	}

	public void CopiarArquivo(String nomeDoArquivo, boolean sobreescrever) {
		nomeDoArquivo += arquivo.GetTipoDoArquivo();
		if (!ExisteArquivo() || sobreescrever) {
			try {
				try (Scanner leitor = new Scanner(new FileInputStream(arquivo.GetNomeDoArquivo()))) {
					try (PrintStream escritor = new PrintStream(nomeDoArquivo)) {
						while (leitor.hasNextLine()) {
							escritor.println(leitor.nextLine());
						}
					}
				}
				System.out.println("Arquivo copiado com sucesso!");
			} catch (IOException erro) {
				System.out.println("Erro: " + erro.getMessage());
			}
		} else
			System.out.println("Infelizmente já temos um arquivo com o mesmo nome!");
	}

	public void Renomear(String nomeDoArquivo) {
		arquivo.Renomear(nomeDoArquivo, arquivo.GetTipoDoArquivo());
		controlador.renameTo(new File(arquivo.GetNomeDoArquivo()));
	}

	public boolean ExisteArquivo() {
		return controlador.exists();
	}

	public void DeletarArquivo() {
		Path caminho = Paths.get(arquivo.GetNomeDoArquivo());
		try {
			Files.delete(caminho);
		} catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
		}
	}

	public int QuantasVezesDigiteiTalPalavra(String palavra) {
		int contador = 0;
		String[] palavras = arquivo.GetTexto().split(" ");
		for (int i = 0; i < palavras.length; i++) {
			if (palavras[i].contains(palavra)) {
				contador++;
			}
		}
		return contador;
	}

	public void ConverterTipos(TipoArquivo seuTipo, boolean escolhaPadrao) {
		String tipoAnteriorDoArquivo = arquivo.GetTipoDoArquivo();
		if (seuTipo.equals(TipoArquivo.txt)) {
			if (escolhaPadrao)
				arquivo.TrocarDeTipo(TipoArquivo.rtf);
			else
				arquivo.TrocarDeTipo(TipoArquivo.bat);
		} else if (seuTipo.equals(TipoArquivo.rtf)) {
			if (escolhaPadrao)
				arquivo.TrocarDeTipo(TipoArquivo.txt);
			else
				arquivo.TrocarDeTipo(TipoArquivo.bat);
		} else {
			if (escolhaPadrao)
				arquivo.TrocarDeTipo(TipoArquivo.txt);
			else
				arquivo.TrocarDeTipo(TipoArquivo.rtf);
		}
		Renomear(arquivo.GetNomeDoArquivo().replace(tipoAnteriorDoArquivo, ""));
	}

	public void ListarDiretorios() {
		File diretorio = new File(controlador.getAbsolutePath().replace(arquivo.GetNomeDoArquivo(), ""));
		if (diretorio.isDirectory()) {
			String[] arquivos = diretorio.list();
			for (String arquivo : arquivos) {
				System.out.println(arquivo);
			}
		} else
			System.out.println("Erro!");
	}
	public void Executar() {
		new ArquivoBat(arquivo.GetNomeDoArquivo().replace(arquivo.GetTipoDoArquivo(), "")).ExecutarArquivo();
	}
	public Arquivo GetArquivo() {
		return arquivo;
	}

	@Override
	public String toString() {
		controlador = new File(arquivo.GetNomeDoArquivo());
		StringBuilder texto = new StringBuilder(arquivo.toString());
		texto.append("\n");
		texto.append("Caminho do arquivo: " + controlador.getAbsolutePath());
		texto.append("\n");
		texto.append("Data: " + data);
		return texto.toString();
	}
}
