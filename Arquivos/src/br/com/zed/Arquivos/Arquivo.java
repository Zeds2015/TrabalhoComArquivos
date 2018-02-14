package br.com.zed.Arquivos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import br.com.zed.sistema.TipoArquivo;

public abstract class Arquivo {
	private TipoArquivo tipoDoArquivo;
	private String nomeDoArquivo;
	private StringBuilder texto = new StringBuilder();

	public Arquivo(String nomeDoArquivo, TipoArquivo tipoDoArquivo) {
		this.tipoDoArquivo = tipoDoArquivo;
		this.nomeDoArquivo = nomeDoArquivo + this.tipoDoArquivo.GetTipoDoArquivo();
		LerDoArquivo();
	}

	public void EscreverNoArquivo(String palavra) {
		try (PrintStream escritor = new PrintStream(new FileOutputStream(nomeDoArquivo, true))) {
			escritor.println(palavra);
			texto.append(palavra);
			texto.append("\n");
			LerTexto();
		} catch (FileNotFoundException erro) {
			System.out.println("Erro: " + erro.getMessage());
		}
	}

	private void LerDoArquivo() {
		try {
			try (Scanner leitor = new Scanner(new FileInputStream(nomeDoArquivo))) {
				while (leitor.hasNextLine()) {
					texto.append(leitor.nextLine());
					texto.append("\n");
				}
			}
		} catch (IOException erro) {
			System.out.println("Arquivo criado com sucesso.");
		}
	}
	public void TrocarDeTipo(TipoArquivo tipo) {
		tipoDoArquivo = tipo;
	}
	public void Renomear(String nomeDoArquivo, String tipo) {
		this.nomeDoArquivo = nomeDoArquivo + tipo;
	}
	public void LerTexto() {
		System.out.println(texto);
	}

	public String GetNomeDoArquivo() {
		return nomeDoArquivo;
	}

	public String GetTexto() {
		return texto.toString();
	}

	public String GetTipoDoArquivo() {
		return tipoDoArquivo.GetTipoDoArquivo();
	}

	@Override
	public String toString() {
		return "Nome do arquivo: " + nomeDoArquivo + " Quantidade de caracteres: " + texto.length();
	}
}
