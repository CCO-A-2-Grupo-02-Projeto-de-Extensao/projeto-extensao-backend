package clube_tamoios.service;

import clube_tamoios.entity.Documento;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentoService {

    Documento salvar(MultipartFile arquivo, Integer idPessoa);

    List<Documento> listarPorPessoa(Integer idPessoa);

    Documento buscarPorId(Integer id);

    byte[] obterDados(Integer id);

    void deletarPorId(Integer id);
}
