package entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pet {
    private String nome;
    private String sobrenome;
    private Tipo tipo;
    private Sexo sexo;
    private String endereco;
    private float idade;
    private float peso;
    private String raca;

    public Pet(String nome, String sobrenome, Tipo tipo, Sexo sexo, String endereco, float idade, float peso, String raca) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipo = tipo;
        this.sexo = sexo;
        this.endereco = endereco;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }
    public Pet() {
    }

    @Override
    public String toString() {
        return "Pet{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", tipo=" + tipo +
                ", sexo=" + sexo +
                ", endereco='" + endereco + '\'' +
                ", idade=" + idade +
                ", peso=" + peso +
                ", raca='" + raca + '\'' +
                '}';
    }

    // getters setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public float getIdade() {
        return idade;
    }

    public void setIdade(float idade) {
        this.idade = idade;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void savePet() throws IOException {
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String dataFormatada = dataHora.format(formatador);

        String nomeDoArquivo = "./"+ dataFormatada + "-"
                + this.nome.toUpperCase()
                + this.sobrenome.toUpperCase()
                + ".txt";


        try (BufferedWriter br = new BufferedWriter(new FileWriter(nomeDoArquivo, true))) {
            br.write("1 - " + this.nome + " " + this.sobrenome + "\n");
            br.write("2 - " + this.tipo + "\n");
            br.write("3 - " + this.sexo + "\n");
            br.write("4 - " + this.endereco + "\n");
            br.write("5 - " + this.idade + " anos" + "\n");
            br.write("6 - " + this.peso + "kg" + "\n");
            br.write("7 - " + this.raca + "\n");
        } catch (IOException e) {
            System.out.println("Falha ao salvar o arquivo");
        }
    }
}
