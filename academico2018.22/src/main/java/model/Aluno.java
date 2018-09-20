/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static config.Config.df;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;


/**
 *
 * @author Muriel
 */
public class Aluno {
    @Id
    private String id;
    @Indexed(unique=true)
    private String ra;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private LocalDate dataCadastro;
    private List<Matricula> matriculas; 
    
    
    @DBRef
    private Cidade cidade;

    public Aluno() {
    }

    public Aluno(String ra, String nome, String email, LocalDate dataNascimento, List<Matricula> matriculas, Cidade cidade) {
        this.ra = ra;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.matriculas = matriculas;
        this.cidade = cidade;

    }
    
     public Aluno(String ra, String nome, String email, LocalDate dataNascimento, List<Matricula> matriculas) {
        this.ra = ra;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.matriculas = matriculas;
 
    }

    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
    
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public String getDataCadastroString() {
         if(dataCadastro != null)
        return dataCadastro.format(df);
        else return "";
    }

    public void setDataCadastro() {
        this.dataCadastro = LocalDate.now();;
    }

    
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public String getDataNascimentoString() {
        if(dataNascimento != null)
        return dataNascimento.format(df);
        else return "00/00/0000";
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    
    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    
    
    
    public int getQntdDisciplina(){
        if(matriculas != null)
           return matriculas.size();
        else return 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aluno other = (Aluno) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return  getNome();
    }
    
}
