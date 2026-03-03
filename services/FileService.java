package services;

import entities.Pet;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public static List<String> lerPerguntasFormulario() {
        List<String> perguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("formulario.txt"))) {
            String line = br.readLine();
            while (line != null) {
                perguntas.add(line);
                line = br.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("Erro: Falha ao ler o arquivo ");
        }
        return perguntas;
    }

    public static File[] lerCadastros() {
        File path = new File("./petsCadastrados/");
        return path.listFiles(File::isFile);
    }


    public static void salvarCadastro(Pet pet) throws IOException {
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String dataFormatada = dataHora.format(formatador);

        String nomeDoArquivo = "./petsCadastrados/"+ dataFormatada + "-"
                + pet.getNome().toUpperCase()
                + pet.getSobrenome().toUpperCase()
                + ".txt";


        try (BufferedWriter br = new BufferedWriter(new FileWriter(nomeDoArquivo, true))) {
            br.write("1 - " + pet.getNome() + " " + pet.getSobrenome() + "\n");
            br.write("2 - " + pet.getTipo() + "\n");
            br.write("3 - " + pet.getSexo() + "\n");
            br.write("4 - " + pet.getEndereco() + "\n");
            br.write("5 - " + String.format("%.2f", pet.getIdade()) + " anos" + "\n");
            br.write("6 - " + String.format("%.2f", pet.getPeso()) + "kg" + "\n");
            br.write("7 - " + pet.getRaca() + "\n");
        } catch (IOException e) {
            System.out.println("Falha ao salvar o arquivo");
        }
    }
}
