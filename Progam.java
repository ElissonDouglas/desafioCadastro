import services.PetService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Progam {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (option != 6) {
            System.out.println("MENU");
            System.out.println("1 - Cadastrar novo pet");
            System.out.println("2 - Alterar dados do pet cadastrado");
            System.out.println("3 - Deletar um pet cadastrado");
            System.out.println("4 - Listar todos os pets cadastrados");
            System.out.println("5 - Lista pets por algum critério (idade, nome, raça)");
            System.out.println("6 - Sair");
            boolean entradaValida = false;

            do {
                try {
                    option = sc.nextInt();
                    sc.nextLine();
                    if (option <= 0 || option > 6) {
                        throw new IllegalArgumentException("Opçao inválida");
                    }
                    entradaValida = true;
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
                    PetService.iniciarCadastro();
                    break;
                case 4:
                    System.out.println(PetService.buscarTodosCadastros());
                    break;
                case 5:
                    PetService.buscarPet();
                    break;
            }
        }
        System.out.println("Progama encerrado.");
        sc.close();
    }


}
