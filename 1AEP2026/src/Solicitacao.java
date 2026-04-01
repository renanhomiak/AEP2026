import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Solicitacao {
    private static int contador = 1;

    private int id;
    private Categoria categoria;
    private String descricao;
    private String localizacao;
    private int prioridade;
    private Status status;
    private Usuario usuario;
    private List<HistoricoStatus> historico;
    private LocalDateTime dataCriacao;
    private int prazoHoras; // SLA

    public Solicitacao(Categoria categoria, String descricao, String localizacao, int prioridade, Usuario usuario) {

        if (descricao == null || descricao.length() < 5) {
            throw new IllegalArgumentException(" *** Descrição inválida!");
        }

        this.id = contador++;
        this.categoria = categoria;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.prioridade = prioridade;
        this.usuario = usuario;
        this.status = Status.ABERTO;
        this.historico = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();

        definirSLA();

        historico.add(new HistoricoStatus(status, "Solicitação criada", usuario.getNome()));
    }

    //define o prazo de horas dependendo do nivel de prioridade, quanto mais alto menor o prazo.
    private void definirSLA() {
        switch (prioridade) {
            case 1: prazoHoras = 72; break;
            case 2: prazoHoras = 48; break;
            case 3: prazoHoras = 24; break;
            case 4: prazoHoras = 12; break;
            case 5: prazoHoras = 6; break;
            default: prazoHoras = 48;
        }
    }

    public int getId() { return id; }
    public int getPrioridade() { return prioridade; }
    public String getLocalizacao() { return localizacao; }
    public Categoria getCategoria() { return categoria; }

    public void atualizarStatus(Status novoStatus, String comentario, String responsavel) {

        if (comentario == null || comentario.isEmpty()) {
            throw new IllegalArgumentException(" *** Comentário obrigatório!");
        }

        this.status = novoStatus;
        historico.add(new HistoricoStatus(novoStatus, comentario, responsavel));
    }

    //imprimir resumo da solicitacao
    public void exibirResumo() {
        System.out.println("ID: " + id + " | Categoria: " + categoria + " | Bairro: " + localizacao + " | Prioridade: " + prioridade + " | Status: " + status);
    }

    //detalhes que mostram ao buscar por id/protocolo
    public void exibirDetalhes() {
        exibirResumo();
        System.out.println("Descrição: " + descricao);
        System.out.println("Prazo (horas): " + prazoHoras);

        System.out.println("Histórico:");
        for (HistoricoStatus h : historico) {
            System.out.println(h);
        }
    }
}