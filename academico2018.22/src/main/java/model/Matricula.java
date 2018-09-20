/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author bueno
 */
@Document
public class Matricula {
    @DBRef
    private Disciplina disciplina;
    private int nota1Sem = 0;
    private int nota2Sem = 0;
    private int notaEx = 0;
    private int faltas;
    
    public Matricula (){
        
    }

    public Matricula(Disciplina disciplina, int nota1Sem, int nota2Sem, int notaEx, int faltas) {
        this.disciplina = disciplina;
        this.nota1Sem = nota1Sem;
        this.nota2Sem = nota2Sem;
        this.notaEx = notaEx;
        this.faltas = faltas;
    }
    

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public int getNota1Sem() {
        return nota1Sem;
    }

    public void setNota1Sem(int nota1Sem) {
        if (nota1Sem < 0) {
            this.nota1Sem = 0;
        } else if (nota1Sem > 100) {
            this.nota1Sem = 100;
        } else {
            this.nota1Sem = nota1Sem;
        }
    }

    public int getNota2Sem() {
        return nota2Sem;
    }

    public void setNota2Sem(int nota2Sem) {
        if (nota2Sem < 0) {
            this.nota2Sem = 0;
        } else if (nota2Sem > 100) {
            this.nota2Sem = 100;
        } else {
            this.nota2Sem = nota2Sem;
        }
    }

    public int getNotaEx() {
        return notaEx;
    }

    public void setNotaEx(int notaEx) {
        if (notaEx < 0) {
            this.notaEx = 0;
        } else if (notaEx > 100) {
            this.notaEx = 100;
        } else {
            this.notaEx = notaEx;
        }
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        if(faltas > disciplina.getAulas())
        this.faltas = disciplina.getAulas();
        else 
            this.faltas = faltas;
    }
    
    public Double getpercFrequencia(){
        return 100-(faltas/disciplina.getAulas()*100.0);
    }

    public Double getMedia(){
        Double media;
        media = (nota1Sem + nota2Sem) / 2.0;

        if (media >= 70.0) {
            return media;
        } else {
            media = (nota1Sem + nota2Sem + notaEx) / 3.0;
        }
        return media;
    }
    
    public String getStatus(){
        if(getpercFrequencia() < 75)
        return "REPROVAD FALTAS";
        else if (getMedia() >= 70)
            return "APROVADO";
        else if(getMedia() >= 50)
            return "APROVADO";
        else 
            return "REPROVADO";
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.disciplina);
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
        final Matricula other = (Matricula) obj;
        if (!Objects.equals(this.disciplina, other.disciplina)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
