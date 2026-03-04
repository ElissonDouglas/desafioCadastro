package services;

import entities.Pet;
import entities.Sexo;
import entities.Tipo;
import exceptions.InvalidAge;
import exceptions.InvalidWeight;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PetService {

    // FAZ A VERIFICAÇÃO SE O NOME DO PET SÓ CONTÉM LETRAS
    public static boolean validarNome(String nomeCompleto) {
        String regra = "^[A-Za-z]+( [A-Za-z]+)+$";

        if (!nomeCompleto.matches(regra) && !nomeCompleto.isEmpty()) {
            throw new IllegalArgumentException("Erro: O pet deve ter nome e sobrenome válidos, contendo APENAS letras de A-Z (sem acentos, números ou caracteres especiais)");
        }

        return true;
    }

    // INICIA O PROCESSO DE CADASTRO DE PET
    public void iniciarCadastro() {
        Scanner sc = new Scanner(System.in);
        FileService fileService = new FileService();
        Pet pet = new Pet();
        List<String> perguntas = fileService.lerPerguntasFormulario();

        // iniciar formulário
        for (String pergunta : perguntas) {
            int question = Integer.parseInt(String.valueOf(pergunta.charAt(0)));
            switch (question) {
                case 1:
                    boolean nomeValido = false;
                    while (!nomeValido) {
                        System.out.println(pergunta);
                        try {
                            String nomeCompleto = sc.nextLine();
                            if (validarNome(nomeCompleto)) {
                                String[] nomeDividido = nomeCompleto.split(" ", 2);
                                pet.setNome(nomeDividido[0]);
                                pet.setSobrenome(nomeDividido[1]);
                            }
                            nomeValido = true;
                        }
                        catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    continue;
                case 2:
                    boolean tipoValido = false;

                    while (!tipoValido) {
                        System.out.println(pergunta);
                        try{
                            int tipo = sc.nextInt();
                            sc.nextLine();
                            if (tipo == 1) {
                                pet.setTipo(Tipo.GATO);
                                tipoValido = true;
                            } else if (tipo == 2) {
                                pet.setTipo(Tipo.CACHORRO);
                                tipoValido = true;
                            } else {
                                throw new IllegalArgumentException("Erro: Valor inválido.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (InputMismatchException e) {
                            System.out.println("Erro");
                            sc.nextLine();
                        }
                    }
                    continue;
                case 3:
                    boolean sexoValido = false;

                    while (!sexoValido) {
                        System.out.println(pergunta);
                        try{
                            int tipo = sc.nextInt();
                            sc.nextLine();
                            if (tipo == 1) {
                                pet.setSexo(Sexo.MACHO);
                                sexoValido = true;
                            } else if (tipo == 2) {
                                pet.setSexo(Sexo.FEMEA);
                                sexoValido = true;
                            } else {
                                throw new IllegalArgumentException("Erro: Valor inválido.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (InputMismatchException e) {
                            System.out.println("Erro");
                            sc.nextLine();
                        }
                    }
                    continue;
                case 4:
                    System.out.println(pergunta);
                    System.out.print("Número da casa: ");
                    String numero = sc.nextLine();
                    System.out.print("Cidade: ");
                    String cidade = sc.nextLine();
                    System.out.print("Rua: ");
                    String rua = sc.nextLine();

                    pet.setEndereco(rua + ", " + cidade + ", " + numero);
                    continue;
                case 5:
                    boolean idadeValida = false;
                    while (!idadeValida) {
                        System.out.println(pergunta);
                        try{
                            float idade = sc.nextFloat();
                            sc.nextLine();
                            System.out.println("A idade está em: \n1 - anos\n2 - meses");
                            int anosOuMeses = sc.nextInt();
                            sc.nextLine();
                            if (anosOuMeses == 1) {
                                if (idade > 20) {
                                    throw new InvalidAge("Idade do pet não pode ser maior que 20 anos.");
                                }
                            } else if (anosOuMeses == 2){
                                idade /= 12;
                            } else {
                                System.out.println("Opção inválida");
                                continue;
                            }


                            pet.setIdade(idade);
                            idadeValida = true;
                        }
                        catch (InputMismatchException e){
                            System.out.println("Erro: Valor inválido");
                            sc.nextLine();
                        }
                        catch (InvalidAge e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                    continue;
                case 6:
                    boolean pesoValido = false;
                    while (!pesoValido) {
                        System.out.println(pergunta);
                        try{
                            float peso = sc.nextFloat();
                            sc.nextLine();
                            if (peso > 60 || peso < 0.5) {
                                throw new InvalidWeight("Peso não pode ser maior que 60kg ou menor que 0.5kg.");
                            }

                            pet.setPeso(peso);
                            pesoValido = true;
                        }
                        catch (InputMismatchException e){
                            System.out.println("Erro: Valor inválido");
                            sc.nextLine();
                        }
                        catch (InvalidWeight e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                    continue;
                case 7:
                    boolean racaValida = false;
                    while (!racaValida) {
                        try {
                            String regex = "^[\\d\\p{Punct}\\s]+$";
                            System.out.println(pergunta);
                            String raca = sc.nextLine();
                            if (!raca.matches(regex)) {
                                pet.setRaca(raca);
                                racaValida = true;
                            } else {
                                throw new IllegalArgumentException("Só é permitido letras.");
                            }

                        }
                        catch (IllegalArgumentException e) {
                            System.out.println("Erro:" + e.getMessage());
                        }
                    }
        }

        //Salva cadastro do pet
        try {
            fileService.salvarCadastro(pet);
            System.out.println("Pet cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
           }
       }
   }

    //BUSCAR PET POR CRITÉRIO
    public static ArrayList<File> buscarPet() {
        Scanner sc = new Scanner(System.in);
        ArrayList<File> arquivosResultados = new ArrayList<>();

        System.out.println("Qual o tipo de animal quer buscar?: \n1 - Gato\n2 - Cachorro");
        int num = sc.nextInt();
        sc.nextLine();
        Tipo tipoAnimal = Tipo.pegarPorNumero(num);

        String menuCriterios = "Escolha o parâmetro para realizar a busca: \n" +
                "1 - Nome ou sobrenome\n2 - Sexo\n3 - Idade\n" +
                "4 - Peso\n5 - Raça\n6 - Endereço";

        // PEGA O PRIMEIRO CRITÉRIO
        System.out.println("=== 1º CRITÉRIO ===");
        System.out.println(menuCriterios);
        int searchOption1 = sc.nextInt();
        sc.nextLine();

        // PERGUNTA SE VAI BUSCAR COM UM SEGUNDO CRITÉRIO
        int searchOption2 = 0; // 0 significa que não tem segundo critério
        System.out.println("Deseja combinar com um 2º critério de busca? (S/N)");
        String adicionarSegundo = sc.nextLine();

        if (adicionarSegundo.equalsIgnoreCase("S")) {
            System.out.println("=== 2º CRITÉRIO ===");
            System.out.println(menuCriterios);
            searchOption2 = sc.nextInt();
            sc.nextLine();
        }

        // PEGA A PALAVRA/TERMO PARA BUSCAR
        System.out.println("\n--- Valores da Busca ---");
        String termoBusca1 = pegarTermoDaBusca(sc, searchOption1);
        String termoBusca2 = "";

        // VERIFICA SE TEM SEGUNDO CRITÉRIO
        if (searchOption2 != 0) {
            termoBusca2 = pegarTermoDaBusca(sc, searchOption2);
        }

        // LÊ OS ARQUIVOS SALVOS
        File[] files = FileService.lerCadastros();
        if (files == null || files.length == 0) {
            System.out.println("Nenhum pet cadastrado no sistema.");
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int contadorResultados = 1;

        // A VERIFICAÇÃO DOS CRITÉRIOS COMEÇA
        for (File file : files) {
            Pet pet = FileService.converterArquivoParaPet(file);

            // Valida o tipo (Cachorro/Gato)
            boolean tipoBate = pet.getTipo().equals(tipoAnimal);

            // Valida o critério 1
            boolean criterio1Bate = validarCriterio(pet, searchOption1, termoBusca1);

            // Valida o critério 2 (Se não tiver critério 2, assume como verdadeiro para não atrapalhar)
            boolean criterio2Bate = true;
            if (searchOption2 != 0) {
                criterio2Bate = validarCriterio(pet, searchOption2, termoBusca2);
            }

            // Se passar em tudo adiciona na lista de resultados
            if (tipoBate && criterio1Bate && criterio2Bate) {
                sb.append(contadorResultados).append(". ")
                        .append(pet.getNome()).append(" ").append(pet.getSobrenome()).append(" - ")
                        .append(pet.getTipo()).append(" - ")
                        .append(pet.getSexo()).append(" - ")
                        .append(pet.getEndereco()).append(" - ")
                        .append(String.format("%.2f", pet.getIdade())).append(" anos - ")
                        .append(String.format("%.2f", pet.getPeso())).append("kg - ")
                        .append(pet.getRaca()).append("\n");
                arquivosResultados.add(file);
                contadorResultados++;
            }
        }

        // Imprime os resultados
        if (sb.length() > 0) {
            System.out.println("\n======= Resultados da Busca =======");
            System.out.println(sb);
        } else {
            System.out.println("\nNenhum pet encontrado com esses critérios.");
            buscarPet(); // Recomeça se não achou
        }
        return arquivosResultados;
    }

    // ALTERAR DADOS CADASTRADOS
    public static void alterarCadastro() {
        Scanner sc = new Scanner(System.in);

        ArrayList<File> arquivosResultados = buscarPet();
        boolean buscaValida = false;
        File arquivoEscolhido = null;

        while (!buscaValida) {
            try {
                System.out.println("Escolha uma das opções que deseja alterar.");
                int num = sc.nextInt();
                sc.nextLine();
                assert arquivosResultados != null;
                if (num <= arquivosResultados.size() && num > 0) {
                    arquivoEscolhido = arquivosResultados.get(num-1);
                    buscaValida = true;
                } else {
                    throw new IllegalArgumentException("opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: valor inválido.");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                sc.nextLine();
            }
        }

        Pet petParaAlterar = FileService.converterArquivoParaPet(arquivoEscolhido);
        List<String> perguntas = FileService.lerPerguntasFormulario();

        for (String pergunta : perguntas) {
            int numeroPergunta = Integer.parseInt(pergunta.substring(0, 1));

            switch (numeroPergunta) {
                case 1:
                    System.out.println(pergunta);
                    boolean nomeValido = false;
                    while (!nomeValido) {
                        String nomeCompleto = sc.nextLine();
                        try {
                            if (validarNome(nomeCompleto)) {
                                String[] nomeDividido = nomeCompleto.split(" ", 2);
                                petParaAlterar.setNome(nomeDividido[0]);
                                petParaAlterar.setSobrenome(nomeDividido.length > 1 ? nomeDividido[1] : "");
                                nomeValido = true;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 2: break;
                case 3: break;
                case 4:
                    System.out.println(pergunta);
                    petParaAlterar.setEndereco(sc.nextLine());
                    break;
                case 5:
                    boolean idadeValida = false;
                    while (!idadeValida) {
                        System.out.println(pergunta);
                        float idade = sc.nextFloat();
                        sc.nextLine();
                        System.out.println("A idade está em: \n1 - anos\n2 - meses");
                        int anosOuMeses = sc.nextInt();
                        sc.nextLine();
                        try {
                            if (anosOuMeses == 1) {
                                if (idade > 20) {
                                    throw new InvalidAge("Idade do pet não pode ser maior que 20 anos.");
                                } else {
                                    petParaAlterar.setIdade(idade);
                                    idadeValida = true;
                                }
                            } else if (anosOuMeses == 2){
                                idade = idade / 12;
                                petParaAlterar.setIdade(idade);
                                idadeValida = true;
                            } else {
                                System.out.println("Opção inválida");
                            }
                        } catch (InvalidAge e) {
                            System.out.println("Erro:" + e.getMessage());
                        }
                    }
                    break;
                case 6:
                    System.out.println(pergunta);
                    petParaAlterar.setPeso(sc.nextFloat());
                    sc.nextLine();
                    break;
                case 7:
                    System.out.println(pergunta);
                    petParaAlterar.setRaca(sc.nextLine());
                    break;
            }
        }

        if (arquivoEscolhido.delete()) {
            try {
                FileService.salvarCadastro(petParaAlterar);
            } catch (IOException e) {
                System.out.println("Erro:" + e.getMessage());
            }
        } else {
            System.out.println("Erro ao excluir o registro antigo");
        }
    }


    // busca todos os cadastros salvos na pasta petsCadastrados
    public static String buscarTodosCadastros() {
        File[] files = FileService.lerCadastros();

        if (files == null || files.length == 0) {
            return "Nenhum pet cadastrado no sistema.";
        }

        StringBuilder sb = new StringBuilder();
        int contadorResultados = 1;

        for (File file : files) {
            Pet pet = FileService.converterArquivoParaPet(file);

            sb.append(contadorResultados).append(" - ")
                    .append(pet.getNome()).append(" ").append(pet.getSobrenome()).append(" - ")
                    .append(pet.getTipo()).append(" - ")
                    .append(pet.getSexo()).append(" - ")
                    .append(pet.getEndereco()).append(" - ")
                    .append(String.format("%.1f", pet.getIdade())).append(" ano(s) - ")
                    .append(String.format("%.2f", pet.getPeso())).append("kg - ")
                    .append(pet.getRaca()).append("\n");

            contadorResultados++;
        }
        System.out.println("======= Resultados da pesquisa =======");
        return sb.toString();
    }


   // Deletar um arquivo da pasta petsCadastrados
    public void deletarCadastro() {
        Scanner sc = new Scanner(System.in);
        ArrayList<File> arquivosResultados = buscarPet();
        boolean buscaValida = false;
        File arquivoEscolhido = null;

        while (!buscaValida) {
            try {
                System.out.println("Escolha uma das opções que deseja deletar dos registros");
                int num = sc.nextInt();
                sc.nextLine();
                assert arquivosResultados != null;
                if (num <= arquivosResultados.size() && num > 0) {
                    arquivoEscolhido = arquivosResultados.get(num-1);
                    buscaValida = true;
                } else {
                    throw new IllegalArgumentException("opção inválida");
                }
            }  catch (InputMismatchException e) {
                System.out.println("Erro: valor inválido.");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                sc.nextLine();
             } // try
         } // while

        System.out.println("Você tem certeza que deseja deletar? (s/n)");
        String escolha = sc.nextLine().trim();

        if (escolha.equalsIgnoreCase("s")) {
            if (arquivoEscolhido.delete()) {
                System.out.println("Arquivo deletado com sucesso.");
            } else {
                System.out.println("Ocorreu um erro");
            }
        } else if (escolha.equalsIgnoreCase("n")) {
            System.out.println("Operação cancelada");
        }
}

    // PERGUNTA AO USUÁRIOS OS TERMOS PARA BUSCAR
    private static String pegarTermoDaBusca(Scanner sc, int opcao) {
        switch (opcao) {
            case 1: System.out.println("Digite o nome ou parte do nome:"); break;
            case 2: System.out.println("Digite o sexo (MACHO ou FEMEA):"); break;
            case 3: System.out.println("Digite a idade do pet:"); break;
            case 4: System.out.println("Digite o peso do pet:"); break;
            case 5: System.out.println("Digite a raça do pet:"); break;
            case 6: System.out.println("Digite o endereço ou parte do endereço do pet:"); break;
            default: return "";
        }
        return sc.nextLine().toLowerCase();
    }

    // CHECKA SE SE OS CRITÉRIOS BATEM COM ALGUM CADASTRO
    private static boolean validarCriterio(Pet pet, int opcao, String termoBusca) {
        if (opcao == 1) {
            return (pet.getNome() + " " + pet.getSobrenome()).toLowerCase().contains(termoBusca);
        } else if (opcao == 2) {
            return pet.getSexo().name().equalsIgnoreCase(termoBusca);
        } else if (opcao == 3) {
            return String.valueOf(pet.getIdade()).contains(termoBusca);
        } else if (opcao == 4) {
            return String.valueOf(pet.getPeso()).contains(termoBusca);
        } else if (opcao == 5) {
            return pet.getRaca().toLowerCase().contains(termoBusca);
        } else if (opcao == 6) {
            return pet.getEndereco().toLowerCase().contains(termoBusca);
        }
        return true;
    }
}
