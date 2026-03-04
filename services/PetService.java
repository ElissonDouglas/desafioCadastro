package services;

import entities.Pet;
import entities.Sexo;
import entities.Tipo;
import exceptions.InvalidAge;
import exceptions.InvalidWeight;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PetService {

    public static boolean validarNome(String nomeCompleto) {
        String regra = "^[A-Za-z]+( [A-Za-z]+)+$";

        if (!nomeCompleto.matches(regra) && !nomeCompleto.isEmpty()) {
            throw new IllegalArgumentException("Erro: O pet deve ter nome e sobrenome válidos, contendo APENAS letras de A-Z (sem acentos, números ou caracteres especiais)");
        }

        return true;
    }

    public static void iniciarCadastro() {
        Scanner sc = new Scanner(System.in);
        Pet pet = new Pet();
        List<String> perguntas = FileService.lerPerguntasFormulario();

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
                            if (PetService.validarNome(nomeCompleto)) {
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

        //Salvar cadastro do pet
        try {
            FileService.salvarCadastro(pet);
            System.out.println("Pet cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
           }
       }
   }

    public static ArrayList<File> buscarPet() {
        Scanner sc = new Scanner(System.in);
        ArrayList<File> arquivosResultados = new ArrayList<>();

        // 1. PEGANDO O TIPO DE ANIMAL (Filtro obrigatório)
        System.out.println("Qual o tipo de animal quer buscar?: \n1 - Gato\n2 - Cachorro");
        int num = sc.nextInt();
        sc.nextLine();
        Tipo tipoAnimal = Tipo.pegarPorNumero(num);

        // 2. PEGANDO O CRITÉRIO DE BUSCA
        System.out.println("Escolha o parâmetro para realizar a busca: \n" +
                "1 - Nome ou sobrenome\n" +
                "2 - Sexo\n" +
                "3 - Idade\n" +
                "4 - Peso\n" +
                "5 - Raça\n" +
                "6 - Endereço");
        int searchOption = sc.nextInt();
        sc.nextLine();

        // 3. DESCOBRINDO O TERMO DA BUSCA ANTES DE ABRIR OS ARQUIVOS
        String termoBusca = "";
        switch (searchOption) {
            case 1:
                System.out.println("Digite o nome ou parte do nome:");
                termoBusca = sc.nextLine().toLowerCase();
                break;
            case 2:
                System.out.println("Digite o sexo (MACHO ou FEMEA):");
                termoBusca = sc.nextLine().toLowerCase();
                break;
            case 3:
                System.out.println("Digite a idade do pet");
                termoBusca = sc.nextLine();
                break;
            case 4:
                System.out.println("Digite o peso do pet");
                termoBusca = sc.nextLine();
                break;
            case 5:
                System.out.println("Digite a raça do pet");
                termoBusca = sc.nextLine();
                break;
            case 6:
                System.out.println("Digite o endereço ou parte do endereço do pet");
                termoBusca = sc.nextLine();
                break;
            default:
                System.out.println("Opção inválida.");
                return null;
        }

        // 4. PREPARANDO A BUSCA
        File[] files = FileService.lerCadastros();
        if (files == null || files.length == 0) {
            System.out.println("Nenhum pet cadastrado no sistema.");
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int contadorResultados = 1;

        // 5. O LAÇO ÚNICO (Lê os arquivos apenas uma vez, independente do critério)
        for (File file : files) {
            String nomePet = "", sexoPet = "", tipoPet = "", idadePet = "", pesoPet = "", racaPet = "", enderecoPet = "";

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("1")) nomePet = line.substring(3).trim();
                    else if (line.startsWith("2")) tipoPet = line.substring(3).trim();
                    else if (line.startsWith("3")) sexoPet = line.substring(3).trim();
                    else if (line.startsWith("4")) enderecoPet = line.substring(3).trim();
                    else if (line.startsWith("5")) idadePet = line.substring(3).trim();
                    else if (line.startsWith("6")) pesoPet = line.substring(3).trim();
                    else if (line.startsWith("7")) racaPet = line.substring(3).trim();
                }

                // ==========================================================
                // A MÁGICA ACONTECE AQUI: AVALIAÇÃO DINÂMICA
                // ==========================================================
                boolean tipoBate = tipoPet.equalsIgnoreCase(tipoAnimal.name());
                boolean criterioBate = false;

                // Verificamos apenas o critério que o usuário escolheu lá em cima
                if (searchOption == 1) {
                    criterioBate = nomePet.toLowerCase().contains(termoBusca);
                } else if (searchOption == 2) {
                    criterioBate = sexoPet.toLowerCase().equalsIgnoreCase(termoBusca);
                } else if (searchOption == 3) {
                    criterioBate = idadePet.contains(termoBusca);
                } else if (searchOption == 4) {
                    criterioBate = pesoPet.contains(termoBusca);
                } else if (searchOption == 5) {
                    criterioBate = racaPet.contains(termoBusca);
                } else if (searchOption == 6) {
                    criterioBate = enderecoPet.contains(termoBusca);
                }


                // se for o correto entao é adicionado nos resultados

                if (tipoBate && criterioBate) {
                    sb.append(contadorResultados).append(". ")
                            .append(nomePet).append(" - ")
                            .append(tipoPet).append(" - ")
                            .append(sexoPet).append(" - ")
                            .append(enderecoPet).append(" - ")
                            .append(idadePet).append(" - ")
                            .append(pesoPet).append(" - ")
                            .append(racaPet).append("\n");
                    arquivosResultados.add(file);
                    contadorResultados++;
                }

            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + file.getName());
            }
        }

        // 6. IMPRIME O RESULTADO FINAL
        if (sb.length() > 0) {
            System.out.println("\nResultados da Busca:");
            System.out.println(sb);
        } else {
            System.out.println("\nNenhum pet encontrado com esses critérios.");
            buscarPet();
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
            } // try
        } // while

        Pet petParaAlterar = new Pet();

        // Aqui vai ler o arquivoEscolhido e preencher o objeto petParaAlterar
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoEscolhido))){
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("1")) { // nome e sobrenome
                    String[] nomeDividido = line.substring(3).split(" ", 2);
                    petParaAlterar.setNome(nomeDividido[0]);
                    petParaAlterar.setSobrenome(nomeDividido[1]);
                } else if (line.startsWith("2")) { // tipo
                    petParaAlterar.setTipo(Tipo.valueOf(line.substring(3).trim().toUpperCase()));
                } else if (line.startsWith("3")) { // sexo
                    petParaAlterar.setSexo(Sexo.valueOf(line.substring(3).trim().toUpperCase()));
                } else if (line.startsWith("4")) { // endereço
                    petParaAlterar.setEndereco(line.substring(3));
                }
                else if (line.startsWith("5")) { // idade
                    petParaAlterar.setIdade(Float.parseFloat(line.substring(3).replace("anos", "").replace(",", ".").trim()));
                } else if (line.startsWith("6")) { // peso
                    petParaAlterar.setPeso(Float.parseFloat(line.substring(3).replace("kg", "").replace(",", ".").trim()));
                } else if (line.startsWith("7")) { // raça
                    petParaAlterar.setRaca(line.substring(3));
                }
            } // while
        } catch (IOException e) {
            System.out.println("Erro: Falha ao ler o arquivo.");
        }


        // Aqui começa a ler as perguntas do formulário
        List<String> perguntas = FileService.lerPerguntasFormulario();

        for (String pergunta : perguntas) {
            int numeroPergunta = Integer.parseInt(pergunta.substring(0, 1));

            switch (numeroPergunta) {
                case 1:
                    System.out.println(pergunta);
                    boolean nomeValido = false;
                    while (!nomeValido) {
                        String nomeCompleto = sc.nextLine();
                        if (validarNome(nomeCompleto)) {
                            petParaAlterar.setNome(nomeCompleto.split(" ", 2)[0]);
                            petParaAlterar.setSobrenome(nomeCompleto.split(" ", 2)[1]);
                            nomeValido = true;
                        } else {
                            System.out.println("nome inválido");
                        }
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
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
            } // switch
        } // for loop
        System.out.println(petParaAlterar);

        // Deletar o arquivo antigo e salvar o arquivo novo
        if (arquivoEscolhido.delete()) {
            try {
                FileService.salvarCadastro(petParaAlterar);
            }
            catch (IOException e) {
                System.out.println("Erro:" + e.getMessage());;
            }
        } else {
            System.out.println("Erro ao excluir o registro antigo");
        }

    }


    // busca todos os cadastros salvos na pasta petsCadastrados
   public static String buscarTodosCadastros() {
       File[] files = FileService.lerCadastros();
       StringBuilder sb = new StringBuilder();
       int contadorResultados = 0;

       for (File file : files) {
           try (BufferedReader br = new BufferedReader(new FileReader(file))) {
               String line = br.readLine();
               sb.append((contadorResultados+1) + " - ");
               while ((line != null)) {
                   sb.append(line.substring(3) + " - ");
                   line = br.readLine();
               }
           } catch (IOException e) {
               System.out.println("Erro ao ler o arquivo: " + file.getName());
           }
           finally {
               sb.append("\n");
               contadorResultados++;
           }
       }
        return sb.toString();
   }


   // Deletar um arquivo
    public static void deletarCadastro() {
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
}
