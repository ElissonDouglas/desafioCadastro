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

        String nomeDoArquivo = STR."./petsCadastrados/\{dataFormatada}-\{pet.getNome().toUpperCase()}\{pet.getSobrenome().toUpperCase()}.txt";


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
        } finally {
            System.out.println("======= Arquivo salvo com sucesso: " + nomeDoArquivo + "=======");
        }
    }


    // Converte dados dos arquivos .txt dos pets cadastrados para objetos Pet
    public static Pet converterArquivoParaPet(File file) {
        Pet pet = new Pet();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("1")) {
                    String[] nomeDividido = line.substring(3).trim().split(" ", 2);
                    pet.setNome(nomeDividido[0]);
                    pet.setSobrenome(nomeDividido.length > 1 ? nomeDividido[1] : "");
                } else if (line.startsWith("2")) {
                    pet.setTipo(entities.Tipo.valueOf(line.substring(3).trim().toUpperCase()));
                } else if (line.startsWith("3")) {
                    pet.setSexo(entities.Sexo.valueOf(line.substring(3).trim().toUpperCase()));
                } else if (line.startsWith("4")) {
                    pet.setEndereco(line.substring(3).trim());
                } else if (line.startsWith("5")) {
                    pet.setIdade(Float.parseFloat(line.substring(3).replace("anos", "").replace(",", ".").trim()));
                } else if (line.startsWith("6")) {
                    pet.setPeso(Float.parseFloat(line.substring(3).replace("kg", "").replace(",", ".").trim()));
                } else if (line.startsWith("7")) {
                    pet.setRaca(line.substring(3).trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + file.getName());
        }
        return pet;
    }
}
