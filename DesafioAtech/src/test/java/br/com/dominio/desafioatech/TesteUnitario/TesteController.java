package br.com.dominio.desafioatech.TesteUnitario;

import br.com.dominio.desafioatech.Model.DesafioModel;
import br.com.dominio.desafioatech.Repository.DesafioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TesteController {

    @Autowired
    private DesafioRepository repository;

    DesafioModel md = new DesafioModel("Luiz","luiz@gmail.com","123456","Apagado","Luiz");

    @Test
    public void salvarUsuario(){
        repository.save().filter(f->!(f.getNome().equals(md.getNome()) && f.getEmail().equals(md.getEmail())));
    }

    @Test
    public void salvar(DesafioModel desafioModel){
        repository.save(desafioModel);
    }

    @Test
    public List<DesafioModel> listar()  {
        return repository.findByUsuario();
    }

    @Test
    public ResponseEntity<DesafioModel> atualiza() throws ResourceNotFoundException {
        String id= "5";
        DesafioModel antigoModel = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item Não encontrado!! :: " + id));

        antigoModel.setNome(md.getNome());
        antigoModel.setEmail(md.getEmail());
        antigoModel.setSenha(md.getSenha());
        antigoModel.setNomeUsuario((md.getNomeUsuario()));
        antigoModel.setItemApagado("Item Apagado");

        return new ResponseEntity<DesafioModel>(repository.save(antigoModel), HttpStatus.OK);
    }

    @Test
    public Map<String, Boolean> Apagar()   throws ResourceNotFoundException{
        String id = "6";
        DesafioModel localId = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item Não encontrado!! :: " + id));
        repository.deleteById(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Apagado!!",Boolean.TRUE);
        return response;
    }
}
