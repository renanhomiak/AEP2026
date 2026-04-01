import java.time.LocalDateTime;

public class HistoricoStatus {
    private Status status;
    private String comentario;
    private String responsavel;
    private LocalDateTime data;

    public HistoricoStatus(Status status, String comentario, String responsavel) {
        this.status = status;
        this.comentario = comentario;
        this.responsavel = responsavel;
        this.data = LocalDateTime.now();
    }

    public String toString() {
        return data + " | " + status + " | " + comentario + " | " + responsavel;
    }
}