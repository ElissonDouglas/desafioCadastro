import entities.Pet;
import entities.Sexo;
import entities.Tipo;
import exceptions.InvalidAge;
import exceptions.InvalidWeight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
            // TODO: fazer a regra 10 do passo 3 do desafio
            case 1:
                Pet pet = new Pet();
                try (BufferedReader br = new BufferedReader(new FileReader("formulario.txt"))) {
                    String line = br.readLine();
                    while (line != null) {
                        //System.out.println(line);
                        int question = Integer.parseInt(String.valueOf(line.charAt(0)));
                        switch (question) {
                            case 1:
                                boolean nomeValido = false;
                                while (!nomeValido) {
                                    System.out.println(line);
                                    try {
                                        String nomeCompleto = sc.nextLine();
                                        if (cadastrarPet(nomeCompleto)) {
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
                                line = br.readLine();
                                continue;
                            case 2:
                                boolean tipoValido = false;

                                while (!tipoValido) {
                                    System.out.println(line);
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
                                line = br.readLine();
                                continue;
                            case 3:
                                boolean sexoValido = false;

                                while (!sexoValido) {
                                    System.out.println(line);
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
                                line = br.readLine();
                                continue;
                            case 4:
                                System.out.println(line);
                                System.out.print("Número da casa: ");
                                String numero = sc.nextLine();
                                System.out.print("Cidade: ");
                                String cidade = sc.nextLine();
                                System.out.print("Rua: ");
                                String rua = sc.nextLine();

                                pet.setEndereco(rua + ", " + cidade + ", " + numero);
                                line = br.readLine();
                                continue;
                            case 5:
                                boolean idadeValida = false;
                                while (!idadeValida) {
                                    System.out.println(line);
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
                                line = br.readLine();
                                continue;
                            case 6:
                                boolean pesoValido = false;
                                while (!pesoValido) {
                                    System.out.println(line);
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
                                line = br.readLine();
                                continue;
                            case 7:
                                boolean racaValida = false;
                                while (!racaValida) {
                                    try {
                                        String regex = "^[\\d\\p{Punct}\\s]+$";
                                        System.out.println(line);
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
                                line = null;
                                break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.println(pet);
                try {
                    pet.savePet();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        }
        sc.close();
    }

    public static boolean cadastrarPet(String nomeCompleto) {
        String regra = "^[A-Za-z]+( [A-Za-z]+)+$";

        if (!nomeCompleto.matches(regra) && !nomeCompleto.isEmpty()) {
            throw new IllegalArgumentException("Erro: O pet deve ter nome e sobrenome válidos, contendo APENAS letras de A-Z (sem acentos, números ou caracteres especiais)");
        }

        return true;
    }
}
