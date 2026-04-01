import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServicoSolicitacoes servico = new ServicoSolicitacoes();

        //menu inicial do sistema
        while (true) {
            System.out.println("\n - Sistema ObservaAção -");
            System.out.println(" - AEP 2026 | ESOFT5S-B | UNICESUMAR MARINGÁ, PR");
            System.out.println(" - Danilo Antonio Alves Rosa, Kauan Marcolino Garcia, Renan Homiak Guimarães\n");
            System.out.println("Bem Vindo, Escolha uma opção: ");
            System.out.println("  1 - Painel do Cidadão");
            System.out.println("  2 - Painel do Gestor");
            System.out.println("  0 - Sair\n");

            int op = sc.nextInt();
            sc.nextLine();

            if (op == 1) painelCidadao(sc, servico);
            else if (op == 2) painelGestor(sc, servico);
            else if (op == 0) break;
        }

        }

    //painel do usuario/cidadao
    public static void painelCidadao(Scanner sc, ServicoSolicitacoes servico) {

        System.out.println("\n--- PAINEL DO CIDADÃO ---");
        System.out.println("1 - Criar solicitação");
        System.out.println("2 - Consultar por protocolo");

        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {

            //anonimo ou nao
            System.out.print("Deseja ser anônimo? (s/n): ");
            boolean anonimo = sc.nextLine().equalsIgnoreCase("s");

            String nome = anonimo ? "Anônimo" : "";
            if (!anonimo) {
                System.out.print("Nome: ");
                nome = sc.nextLine();
            }

            Usuario u = new Usuario(nome, anonimo);

            //categorias
            System.out.println("Categoria:");
            System.out.println("1-ILUMINAÇÂO 2-INFRAESTRUTURA 3-LIMPEZA 4-SAÚDE 5-SEGURANÇA");
            int c = sc.nextInt();
            sc.nextLine();

            Categoria cat = Categoria.values()[c - 1];

            System.out.print("Descrição: ");
            String desc = sc.nextLine();

            System.out.print("Bairro: ");
            String bairro = sc.nextLine();

            int prio;

            while (true) {
                System.out.print("Prioridade (1-5): ");

                if (sc.hasNextInt()) {
                    prio = sc.nextInt();

                    if (prio >= 1 && prio <= 5) {
                        break; // válido, sai do loop
                    } else {
                        System.out.println("Erro: a prioridade deve ser entre 1 e 5.");
                    }

                } else {
                    System.out.println("Erro: digite um número válido.");
                    sc.next(); // limpa entrada inválida
                }
            }

            try {
                Solicitacao s = new Solicitacao(cat, desc, bairro, prio, u);
                servico.criarSolicitacao(s);

                System.out.println("Protocolo gerado: " + s.getId());
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        else if (op == 2) {
            System.out.print("Digite o protocolo: ");
            int id = sc.nextInt();

            Solicitacao s = servico.buscarPorId(id);

            if (s != null) s.exibirDetalhes();
            else System.out.println("Não encontrado");
        }
    }

    //painel do gestor/administrador
    public static void painelGestor(Scanner sc, ServicoSolicitacoes servico) {

        System.out.println("\n--- PAINEL DO GESTOR ---");
        System.out.println("1 - Listar todas as solicitações");
        System.out.println("2 - Buscar por protocolo");
        System.out.println("3 - Atualizar status");

        int op = sc.nextInt();
        sc.nextLine();

        //listar todas
        if (op == 1) {
            servico.listarSolicitacoes();
        }

        //buscar por id
        else if (op == 2) {
            System.out.print("Digite o ID/protocolo: ");
            int id = sc.nextInt();

            Solicitacao s = servico.buscarPorId(id);

            if (s != null) {
                s.exibirDetalhes();
            } else {
                System.out.println("Solicitação não encontrada.");
            }
        }

        //atualizar status
        else if (op == 3) {

            System.out.print("ID da solicitação: ");
            int id = sc.nextInt();
            sc.nextLine();

            Solicitacao s = servico.buscarPorId(id);

            if (s != null) {

                System.out.println("Novo status:");
                System.out.println("1- TRIAGEM");
                System.out.println("2- EM_EXECUCAO");
                System.out.println("3- RESOLVIDO");
                System.out.println("4- ENCERRADO");

                int st = sc.nextInt();
                sc.nextLine();

                Status novo = Status.TRIAGEM;

                switch (st) {
                    case 1: novo = Status.TRIAGEM; break;
                    case 2: novo = Status.EM_EXECUCAO; break;
                    case 3: novo = Status.RESOLVIDO; break;
                    case 4: novo = Status.ENCERRADO; break;
                    default:
                        System.out.println("Status inválido!");
                        return;
                }

                String comentario;
                while (true) {
                    System.out.print("Comentário (mínimo 10 caracteres): ");
                    comentario = sc.nextLine();
                    if (comentario != null && comentario.trim().length() >= 10) {
                        break; // válido
                    } else {
                        System.out.println("Erro: o comentário deve ter pelo menos 10 caracteres.");
                    }
                } try {
                    s.atualizarStatus(novo, comentario, "Gestor");
                    System.out.println("Status atualizado com sucesso!");
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else {
                System.out.println("Solicitação não encontrada.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

}