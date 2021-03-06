package entities;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String nomeCategoria;
    private int ativo;
    private String loginPrestador;

    public Produto() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public String getLoginPrestador() {
        return loginPrestador;
    }

    public void setLoginPrestador(String loginPrestador) {
        this.loginPrestador = loginPrestador;
    }
}
