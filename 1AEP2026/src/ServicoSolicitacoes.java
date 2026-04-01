import java.util.ArrayList;
import java.util.List;

public class ServicoSolicitacoes {

    private List<Solicitacao> lista = new ArrayList<>();

    public void criarSolicitacao(Solicitacao s) {
        lista.add(s);
    }

    public void listarSolicitacoes() {
        for (Solicitacao s : lista) {
            s.exibirResumo();
        }
    }

    public Solicitacao buscarPorId(int id) {
        for (Solicitacao s : lista) {
            if (s.getId() == id) return s;
        }
        return null;
    }
}