package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCargo")
    private Integer idCargo;

    @Column(name = "Nome", length = 45)
    private String nome;

    public Cargo() {}

    public Integer getIdCargo() { return idCargo; }
    public void setIdCargo(Integer idCargo) { this.idCargo = idCargo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // Mapeia o cargo real (Diretor/Secretário/Tesoureiro/Instrutor/Desbravador)
    // pras 3 categorias que o front usa pra agrupar as tabelas da tela de
    // Desbravadores.
    public static String categoriaDe(Cargo cargo) {
        String nomeCargo = cargo == null ? null : cargo.getNome();
        if (nomeCargo == null) return "aluno";
        return switch (nomeCargo) {
            case "Instrutor" -> "instrutor";
            case "Desbravador" -> "aluno";
            default -> "administrativo";
        };
    }
}
