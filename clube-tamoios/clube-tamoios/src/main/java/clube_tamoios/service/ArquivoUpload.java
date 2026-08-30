package clube_tamoios.service;

public record ArquivoUpload(byte[] conteudo, String nomeOriginal, String mimeType) {

    public long tamanho() {
        return conteudo.length;
    }
}
