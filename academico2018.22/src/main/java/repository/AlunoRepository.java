/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.Aluno;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface AlunoRepository extends MongoRepository<Aluno, String>{
    public Aluno findByNome(String nome);
    public List <Aluno> findByNomeLikeIgnoreCase(String nome);
    
}
