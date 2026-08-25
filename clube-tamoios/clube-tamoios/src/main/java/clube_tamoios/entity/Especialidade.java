package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Especialidade")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspecialidade")
    private Integer idEspecialidade;

    @Column(length = 45)
    private String nome;

    // Categoria oficial do manual (Artes Manuais, Estudos da Natureza, ...). Fica
    // como texto porque o clube usa o rótulo completo com a sigla entre
    // parênteses, e não há tabela de referência para isso.
    @Column(length = 60)
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Caminho ou URL da insígnia. Nulo enquanto a imagem não for cadastrada — a
    // tabela da tela mostra um traço nesse caso.
    @Column(length = 500)
    private String imagem;

    public Especialidade() {}

    public Integer getIdEspecialidade() { return idEspecialidade; }
    public void setIdEspecialidade(Integer idEspecialidade) { this.idEspecialidade = idEspecialidade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}
