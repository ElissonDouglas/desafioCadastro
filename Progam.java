import entities.Pet;
import entities.Sexo;
import entities.Tipo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Progam {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("MENU");
        System.out.println("1 - Cadastrar novo pet");
        System.out.println("2 - Alterar dados do pet cadastrado");
        System.out.println("3 - Deletar um pet cadastrado");
        System.out.println("4 - Listar todos os pets cadastrados");
        System.out.println("5 - Lista pets por algum critério (idade, nome, raça)");
        System.out.println("6 - Sair");
        boolean entradaValida = false;
        int option = 0;

        do {
            try {
                option = sc.nextInt();
                sc.nextLine();
                if (option <= 0 || option > 6) {
                    throw new IllegalArgumentException("Opçao inválida");
                }
                entradaValida = true;
                System.out.println("opcao valida");
            } catch (InputMismatchException e) {
                System.out.println("Error: caractere não aceito.");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!entradaValida);

        switch (option) {
            case 1:
                Pet pet = new Pet();
                try (BufferedReader br = new BufferedReader(new FileReader("formulario.txt"))) {
                    String line = br.readLine();
                    while (line != null) {
                        System.out.println(line);
                        int question = Integer.parseInt(String.valueOf(line.charAt(0)));
                        switch (question) {
                            case 1:
                                boolean nomeValido = false;
                                while (!nomeValido) {
                                    System.out.print("Nome completo do pet: ");
                                    try {
                                        String nomeCompleto = sc.nextLine();
                                        cadastrarPet(nomeCompleto);
                                        nomeValido = true;
                                    }
                                    catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                line = br.readLine();
                                continue;
                            case 2:
                                boolean opcaoValida = false;

                                while (!opcaoValida) {
                                    System.out.println("Qual o tipo do pet? (1 - Gato || 2 - Cachorro");
                                    try{
                                        int tipo = sc.nextInt();
                                        sc.nextLine();
                                        if (tipo == 1) {
                                            pet.setTipo(Tipo.GATO);
                                            opcaoValida = true;
                                        } else if (tipo == 2) {
                                            pet.setTipo(Tipo.CACHORRO);
                                            opcaoValida = true;
                                        } else {
                                            throw new IllegalArgumentException("Erro: Valor inválido.");
                                        }
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    } catch (InputMismatchException e) {
                                        System.out.println("Erro");
                                    }
                                }
                                line = br.readLine();
                                continue;
                            case 3:
                                String sexo = sc.nextLine();
                                sc.nextLine();
                                if (sexo == "macho") {
                                    pet.setSexo(Sexo.MACHO);
                                } else if (sexo == "fêmea") {
                                    pet.setSexo(Sexo.FEMEA);
                                }
                                line = br.readLine();
                                continue;
                            case 4:
                                System.out.print("Número da casa: ");
                                String numero = sc.nextLine();
                                System.out.print("Cidade: ");
                                String cidade = sc.nextLine();
                                System.out.print("Rua: ");
                                String rua = sc.nextLine();

                                pet.setEndereco(rua + ", " + cidade + ", " + cidade);
                                line = br.readLine();
                                continue;
                            case 5:
                                int idade = sc.nextInt();
                                pet.setIdade(idade);
                                sc.nextLine();
                                line = br.readLine();
                                continue;
                            case 6:
                                double peso = sc.nextDouble();
                                pet.setPeso(peso);
                                line = br.readLine();
                                continue;
                            case 7:
                                String raca = sc.nextLine();
                                pet.setRaca(raca);
                                break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

                System.out.println(pet);
        }


        sc.close();
    }

    public static void cadastrarPet(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O nome não pode estar vazio");
        }

        String regra = "^[A-Za-z]+( [A-Za-z]+)+$";

        if (!nomeCompleto.matches(regra)) {
            throw new IllegalArgumentException("Erro: O pet deve ter nome e sobrenome válidos, contendo APENAS letras de A-Z (sem acentos, números ou caracteres especiais)");
        }

        System.out.println("Pet cadastrado com sucesso!");
    }
}
