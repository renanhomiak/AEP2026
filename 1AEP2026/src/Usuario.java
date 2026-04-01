public class Usuario {
    private String nome;
    private boolean anonimo;

    public Usuario(String nome, boolean anonimo) {
        this.nome = anonimo ? "Anonimo" : nome;
        this.anonimo = anonimo;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAnonimo() {
        return anonimo;
    }
}