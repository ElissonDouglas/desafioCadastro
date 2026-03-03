package services;

import entities.Pet;
import entities.Sexo;
import entities.Tipo;
import exceptions.InvalidAge;
import exceptions.InvalidWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

   public static void buscarPet() throws IOException {
        Scanner sc = new Scanner(System.in);
       System.out.println("Qual o tipo de animal quer buscar?: \n" +
               "1 - Gato\n" +
               "2- Cachorro");
       int num = sc.nextInt();
       sc.nextLine();

       Tipo tipoAnimal = Tipo.pegarPorNumero(num);

       System.out.println("Escolha o(s) paramêtros para realizar a busca: \n" +
               "1- Nome ou sobrenome\n" +
               "2 - Sexo\n" +
               "3 - Idade\n" +
               "4 - Peso\n" +
               "5- Raça\n" +
               "Endereço");
       int searchOption = sc.nextInt();
       sc.nextLine();

       switch (searchOption) {

           case 1:
               File[] files = FileService.lerCadastros();
               if (files == null || files.length == 0) {
                   System.out.println("Nenhum pet cadastrado no sistema.");
                   break;
               }

               StringBuilder sb = new StringBuilder();

               System.out.println("Digite o nome ou parte do nome do Pet:");
               String nomeBusca = sc.nextLine().toLowerCase();

               int contadorResultados = 1; // Para numerar os resultados (1., 2., 3...)

               for (File file : files) {
                   String nomePet = "";
                   String sexoPet = "";
                   String tipoPet = "";
                   String idadePet = "";
                   String pesoPet = "";
                   String racaPet = "";
                   String enderecoPet = "";


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

                       // 1. Verifica se o tipo de animal bate com o que o usuário escolheu no começo
                       boolean tipoBate = tipoPet.equalsIgnoreCase(tipoAnimal.name());

                       // 2. Verifica se o nome do pet contém o texto que o usuário digitou
                       boolean nomeBate = nomePet.toLowerCase().contains(nomeBusca);

                       if (tipoBate && nomeBate) {
                           // Se passou no filtro, monta a String
                           sb.append(contadorResultados).append(". ")
                                   .append(nomePet).append(" - ")
                                   .append(tipoPet).append(" - ")
                                   .append(sexoPet).append(" - ")
                                   .append(enderecoPet).append(" - ")
                                   .append(idadePet).append(" - ")
                                   .append(pesoPet).append(" - ")
                                   .append(racaPet).append("\n");

                           contadorResultados++;
                       }

                   } catch (IOException e) {
                       System.out.println("Erro ao ler o arquivo: " + file.getName());
                   }
               }


               if (sb.length() > 0) {
                   System.out.println("\nResultados da Busca:");
                   System.out.println(sb);
               } else {
                   System.out.println("\nNenhum pet encontrado com esses critérios.");
               }
               break;
       }
   }

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
}
