package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.professorRepository;
import static config.DAO.cidadeRepository;
import static config.DAO.ufRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import model.Cidade;
import model.Uf;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class CRUDCidadeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private CRUDProfessorController controllerPai;
    private CRUDAlunoController controllerPaiAluno;
    public char acao;
    public Cidade cidade; 
    
    @FXML
    private TextField txtFldNome;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button btnConfirma;

    @FXML
    private ComboBox cmbUf;
    
    @FXML
    private MaterialDesignIconView btnIncluir;
    
    @FXML
    private MaterialDesignIconView btnExcluir;

       
    
    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
    }

    @FXML
    private void btnConfirmaClick() {
        
        controllerPai.cidade.setNome(txtFldNome.getText());
        controllerPai.cidade.setUf((Uf) cmbUf.getSelectionModel().getSelectedItem());
        try {
            if (cidadeRepository.countByNomeAndUf(controllerPai.cidade.getNome(), controllerPai.cidade.getUf()) == 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Cadastro de Cidade");
                alert.setContentText("Cidade já Cadastrada");
                alert.showAndWait();
            }else{
            switch (controllerPai.acao) {
                case INCLUIR:
                    cidadeRepository.insert(controllerPai.cidade);
                    break;
                case ALTERAR:
                    cidadeRepository.save(controllerPai.cidade);
                    break;
            }
            controllerPai.cmbCidade.setItems(
                    FXCollections.observableList(cidadeRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            anchorPane.getScene().getWindow().hide();
         }
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Cidade");
            
                alert.setContentText(e.getMessage());
            
            alert.showAndWait();
                }
        
    }
    @FXML
    private void btnAlunoConfirmaClick() {
        controllerPaiAluno.cidade.setNome(txtFldNome.getText());
        controllerPaiAluno.cidade.setUf((Uf) cmbUf.getSelectionModel().getSelectedItem());
        try {
            switch (controllerPaiAluno.acao) {
                case INCLUIR:
                    cidadeRepository.insert(controllerPaiAluno.cidade);
                    break;
                case ALTERAR:
                    cidadeRepository.save(controllerPaiAluno.cidade);
                    break;
            }
            controllerPaiAluno.cmbCidade.setItems(
                    FXCollections.observableList(cidadeRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            anchorPane.getScene().getWindow().hide();
//
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Cidade");
            
                alert.setContentText(e.getMessage());
            
            alert.showAndWait();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnConfirma.disableProperty().bind((txtFldNome.textProperty().isEmpty()).or(cmbUf.selectionModelProperty().isNull()));

    }

    public void setCadastroController(CRUDProfessorController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldNome.setText(controllerPai.cidade.getNome());
        cmbUf.setItems(FXCollections.observableList(ufRepository.findAll()));

        if (controllerPai.acao != INCLUIR) {
            cmbUf.getSelectionModel().select(controllerPai.cidade.getUf());
        }
        
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);


    }
    public void setCadastroController(CRUDAlunoController controllerPai) {
        this.controllerPaiAluno = controllerPai;
        txtFldNome.setText(controllerPai.cidade.getNome());
        cmbUf.setItems(FXCollections.observableList(ufRepository.findAll()));

        if (controllerPai.acao != INCLUIR) {
            cmbUf.getSelectionModel().select(controllerPai.cidade.getUf());
        }
        
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);


    }
    
    

}
