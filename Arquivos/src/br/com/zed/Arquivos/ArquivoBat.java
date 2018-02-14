package br.com.zed.Arquivos;

import java.io.File;
import java.io.IOException;

import br.com.zed.sistema.ArquivosExecutaveis;
import br.com.zed.sistema.TipoArquivo;

public class ArquivoBat extends Arquivo implements ArquivosExecutaveis{
	private File arquivo;
	public ArquivoBat(String nomeDoArquivo) {
		super(nomeDoArquivo, TipoArquivo.bat);
		arquivo = new File(GetNomeDoArquivo());
	}
	public void ExecutarArquivo() {
		try {
			Runtime.getRuntime().exec(arquivo.getAbsolutePath());
		} catch (IOException erro) {
			System.out.println("Erro: "+erro.getMessage());
		}
	}
}
