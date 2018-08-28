/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.alunoRepository;
import static config.DAO.cidadeRepository;
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
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author bueno
 */
public class CRUDAlunoController implements Initializable {

    private AlunoController controllerPai;


    @FXML
    private TextField txtFldCpf;

    @FXML
    private TextField txtFldNome;
    
    @FXML
    private TextField txtFldEmail;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button btnConfirma;

    @FXML
    private ComboBox cmbCidade;
    
    
     @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblViewAluno.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.aluno.setCpf(txtFldCpf.getText());
        controllerPai.aluno.setNome(txtFldNome.getText());
        controllerPai.aluno.setEmail(txtFldEmail.getText());
        controllerPai.aluno.setCidade((Cidade) cmbCidade.getSelectionModel().getSelectedItem());
        try {
            switch (controllerPai.acao) {
                case INCLUIR:
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
            alert.setHeaderText("Cpf de Disciplina");
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
        
        btnConfirma.disableProperty().bind(txtFldCpf.textProperty().isEmpty().
                or(txtFldNome.textProperty().isEmpty()).or(txtFldEmail.textProperty().isEmpty()) );
        
    }
        
        public void setCadastroController(AlunoController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldCpf.setText(controllerPai.aluno.getCpf());
        txtFldNome.setText(controllerPai.aluno.getNome());
        txtFldEmail.setText(controllerPai.aluno.getEmail());

        cmbCidade.setItems(FXCollections.observableList(
                cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));

        if (controllerPai.acao != INCLUIR) {
            cmbCidade.getSelectionModel().select(controllerPai.aluno.getCidade());
        }

        txtFldCpf.setDisable(controllerPai.acao == EXCLUIR);
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldEmail.setDisable(controllerPai.acao == EXCLUIR);

    }
        
        
    
}
