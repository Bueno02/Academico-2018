package repository;

import java.util.List;
import model.Cidade;

import model.Professor;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 *
 * @author bueno
 */
public interface ProfessorRepository extends MongoRepository<Professor, String>{
    public Professor findByNome(String nome);
    public List <Professor> findByNomeLikeIgnoreCase(String nome);
    public List <Professor> findByNomeLikeIgnoreCaseOrEmailLikeIgnoreCase(String nome,String email);
}
