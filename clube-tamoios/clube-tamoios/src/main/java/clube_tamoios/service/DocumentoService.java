package clube_tamoios.service;

import clube_tamoios.entity.Documento;
import java.util.List;

public interface DocumentoService {

    Documento salvar(ArquivoUpload arquivo, Integer idPessoa, String tipo);

    Documento substituir(Integer id, ArquivoUpload novoArquivo);

    List<Documento> listarPorPessoa(Integer idPessoa);

    Documento buscarPorId(Integer id);

    byte[] obterDados(Integer id);

    void deletarPorId(Integer id);
}
