package br.com.zed.sistema;

public enum TipoArquivo {
	txt(1), rtf(2), bat(3);
	private String tipoDoArquivo;

	private TipoArquivo(int numero) {
		switch (numero) {
		case 1:
			tipoDoArquivo = ".txt";
			break;
		case 2:
			tipoDoArquivo = ".rtf";
			break;
		case 3:
			tipoDoArquivo = ".bat";
			break;
		}
	}

	public String GetTipoDoArquivo() {
		return tipoDoArquivo;
	}
}
