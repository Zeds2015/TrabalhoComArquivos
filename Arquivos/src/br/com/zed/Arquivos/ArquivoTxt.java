package br.com.zed.Arquivos;

import br.com.zed.sistema.TipoArquivo;

public class ArquivoTxt extends Arquivo{

	public ArquivoTxt(String nomeDoArquivo) {
		super(nomeDoArquivo, TipoArquivo.txt);
	}
}
