package view;

import static config.DAO.alunoRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.Cidade;
import model.Disciplina;
import model.Professor;
import model.Uf;
import static config.DAO.disciplinaRepository;
import static config.DAO.professorRepository;
import static config.DAO.cidadeRepository;
import static config.DAO.ufRepository;
import model.Aluno;

public class PrincipalController implements Initializable {
    

    
    Disciplina disciplina;
    Professor professor;
    Cidade cidade;
    Aluno aluno;
    Uf uf;
    List<Cidade> lstCit =   new ArrayList<Cidade>();
    List<Uf> lstUf  =   new ArrayList<Uf>();
    List<Disciplina> lstDisc    =   new ArrayList<Disciplina>();
    List<Professor> lstProf    =   new ArrayList<Professor>();
    List<Aluno> lstAlun    =   new ArrayList<Aluno>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        /**
//         * Salva os estados e suas Siglas
//         */
//        uf = new Uf("Paraná","PR");
//        ufRepository.save(uf);
//        uf = new Uf("Rio Grande de Sul","RS");
//        ufRepository.save(uf);
//        uf = new Uf("São Paulo","SP");
//        ufRepository.save(uf);
//        uf = new Uf("Rio de Janeiro","RJ");
//        ufRepository.save(uf);
//        uf = new Uf("Amazonas","AM");
//        ufRepository.save(uf);
//        uf = new Uf("Piauí","PI");
//        ufRepository.save(uf);
//        uf = new Uf("Maranhão","MA");
//        ufRepository.save(uf);
//        uf = new Uf("Tocantins","TO");
//        ufRepository.save(uf);
//        uf = new Uf("Rio Grande do Norte","RN");
//        ufRepository.save(uf);
//         /**
//         * Encontra as Siglas na tabela uf e cria um link com a cidade
//         */
//        uf =  ufRepository.findBySiglaLikeIgnoreCase("PR");
//        
//        cidade = new Cidade("Porto Amazonas",uf);
//        cidadeRepository.save(cidade);
//        
//        uf =  ufRepository.findBySiglaLikeIgnoreCase("RS");
//        cidade = new Cidade("Gramado",uf);
//        cidadeRepository.save(cidade);
//       
//        uf =  ufRepository.findBySiglaLikeIgnoreCase("SP");
//        cidade = new Cidade("São Paulo",uf);
//        cidadeRepository.save(cidade);
//        
//        uf =  ufRepository.findBySiglaLikeIgnoreCase("RJ");
//        cidade = new Cidade("Rio de Janeiro",uf);
//        cidadeRepository.save(cidade);
//            /**
//             * Cria professor com cidade
//             */
//            aluno =   new Aluno("Helen","antonio@email.com","123298131",cidadeRepository.findByNome("Pontta Grossa"));
//            alunoRepository.save(aluno);
//            aluno =   new Aluno("Jusino","jose@email.com","2131290331",cidadeRepository.findByNome("Pirai do Sul"));
//            alunoRepository.save(aluno);
//            aluno =   new Aluno("Bruna","bruna@email.com","90932131",cidadeRepository.findByNome("Castro"));
//            alunoRepository.save(aluno);
//            /**
//             * -------------------------
//             */
//            /**
//             * Cria disciplinas com professor
//             */
//             professor =  professorRepository.findByNome("Antonio");
//             disciplina =   new Disciplina(professor,"64565","Matemática",10,"Nenhuma");
//             disciplinaRepository.save(disciplina);
    }    
}
