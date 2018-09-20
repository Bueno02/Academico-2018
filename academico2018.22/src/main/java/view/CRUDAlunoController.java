/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import config.DAO;
import static config.DAO.alunoRepository;
import static config.DAO.cidadeRepository;
import static config.DAO.disciplinaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Cidade;
import model.Disciplina;
import model.Matricula;
import org.controlsfx.control.ListSelectionView;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author bueno
 */
public class CRUDAlunoController implements Initializable {

    private AlunoController controllerPai;
    public Cidade cidade;
    public char acao;


    @FXML
    private TextField txtFldRa;

    @FXML
    private TextField txtFldNome;
    
    @FXML
    private TextField txtFldEmail;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button btnConfirma;

    @FXML
    public ComboBox cmbCidade;
    
    @FXML
    public DatePicker dtPckNascimento;
    
    
    @FXML
    private MaterialDesignIconView btnIncluir;
    
    @FXML
    private MaterialDesignIconView btnAlterar;
    
    @FXML
    private ListSelectionView <Disciplina> lstSelDisciplinas;
    
    
    @FXML
    private void acIncluir(){
        acao = INCLUIR;
        cidade = new Cidade();

        showCRUD();
        
    }
    
    @FXML
    private void acAlterar(){
        acao = ALTERAR;
        cidade = (Cidade) cmbCidade.getSelectionModel().getSelectedItem();

        showCRUD();
        
    }
    
        private void showCRUD() {
        String cena = "/fxml/CRUDCidadeAluno.fxml";
        XPopOver popOver = null;

        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Cidade", btnIncluir);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Cidade", btnAlterar);
                break;
        }
        CRUDCidadeController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }
    
    
     @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblViewAluno.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.aluno.setRa(txtFldRa.getText());
        controllerPai.aluno.setNome(txtFldNome.getText());
        controllerPai.aluno.setEmail(txtFldEmail.getText());
        controllerPai.aluno.setCidade((Cidade) cmbCidade.getSelectionModel().getSelectedItem());
        controllerPai.aluno.setDataNascimento(dtPckNascimento.getValue());
        List<Matricula> tmpMatriculas = new ArrayList<>();
        
        if(controllerPai.aluno.getMatriculas() != null){
        for (Matricula ant : controllerPai.aluno.getMatriculas()){
            if(lstSelDisciplinas.getTargetItems().contains(ant.getDisciplina())){
                tmpMatriculas.add(ant);
                lstSelDisciplinas.getTargetItems().remove(ant.getDisciplina());
            }
        }
    }
        for(Disciplina nv : lstSelDisciplinas.getTargetItems()){
            Matricula mat = new Matricula(nv, 0,0,0,0);
            tmpMatriculas.add(mat);
        };
        controllerPai.aluno.setMatriculas(tmpMatriculas);
        
        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    controllerPai.aluno.setDataCadastro();
                    alunoRepository.insert(controllerPai.aluno);
                    break;
                case ALTERAR:
                    alunoRepository.save(controllerPai.aluno);
                    break;
                case EXCLUIR:
                    alunoRepository.delete(controllerPai.aluno);
                    break;
            }
            controllerPai.tblViewAluno.setItems(
                    FXCollections.observableList(alunoRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            controllerPai.tblViewAluno.refresh();
            controllerPai.tblViewAluno.getSelectionModel().clearSelection();
            controllerPai.tblViewAluno.getSelectionModel().select(controllerPai.aluno);
            anchorPane.getScene().getWindow().hide();
//
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Ra de Aluno");
            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Cpf já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();

        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnConfirma.disableProperty().bind(txtFldRa.textProperty().isEmpty().
                or(txtFldNome.textProperty().isEmpty())
                .or(txtFldEmail.textProperty().isEmpty()).or(dtPckNascimento.valueProperty().isNull()) );
         btnAlterar.visibleProperty().bind(cmbCidade.getSelectionModel().selectedItemProperty().isNotNull());
        
    }
        
        public void setCadastroController(AlunoController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldRa.setText(controllerPai.aluno.getRa());
        txtFldNome.setText(controllerPai.aluno.getNome());
        txtFldEmail.setText(controllerPai.aluno.getEmail());
        dtPckNascimento.setValue(controllerPai.aluno.getDataNascimento());
        cmbCidade.setItems(FXCollections.observableList(
        cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
        
        List<Disciplina> todasDisciplinas = disciplinaRepository.findAll(new Sort(new Sort.Order("nome")));
  
   
        if (controllerPai.acao != INCLUIR) {
            cmbCidade.getSelectionModel().select(controllerPai.aluno.getCidade());
        }

        if (controllerPai.aluno.getMatriculas() != null){
            List<Disciplina> lstAtual = new ArrayList<>();
            lstSelDisciplinas.getTargetItems().addAll(lstAtual);
            
            for(Matricula nv : controllerPai.aluno.getMatriculas()){
              lstAtual.add(nv.getDisciplina());  
            }
            
            todasDisciplinas.removeAll(lstAtual);
           
            lstSelDisciplinas.getTargetItems().addAll(lstAtual);
            lstSelDisciplinas.getSourceItems().addAll(todasDisciplinas);
        }else{
            lstSelDisciplinas.getSourceItems().addAll(todasDisciplinas);
        }
        
        txtFldRa.setDisable(controllerPai.acao == EXCLUIR);
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldEmail.setDisable(controllerPai.acao == EXCLUIR);
        dtPckNascimento.setDisable(controllerPai.acao == EXCLUIR);

    }
        
        
    
}
