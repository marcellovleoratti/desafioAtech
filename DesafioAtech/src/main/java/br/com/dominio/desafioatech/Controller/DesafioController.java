package br.com.dominio.desafioatech.Controller;

import br.com.dominio.desafioatech.Model.DesafioModel;
import br.com.dominio.desafioatech.Repository.DesafioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(path="/api")
public class DesafioController {

    @Autowired
    private DesafioRepository repository;

    public String nome="Marcello";
    public String email="luiz@gmail.com";




    public void salvarUsuario(String email, String nome){
       repository.save().filter(f->!(f.getNome().equals(nome) && f.getEmail().equals(email)));
    }


    public void cadastrarUsuario(@RequestBody DesafioModel model){
        repository.save(model);
    }

    @GetMapping(path="/listar/{id}")
    public List<DesafioModel> listar(@RequestBody DesafioModel modelIndex)  {
        return repository.findByUsuario();
    }

    @PutMapping(path= "/apagarUser/{id}")
    public ResponseEntity<DesafioModel> atualiza(@PathVariable(value = "id") String id, @Validated @RequestBody DesafioModel NovomodelIndex) throws ResourceNotFoundException {
        DesafioModel antigoModel = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item Não encontrado!! :: " + id));

        antigoModel.setNome(NovomodelIndex.getNome());
        antigoModel.setEmail(NovomodelIndex.getEmail());
        antigoModel.setSenha(NovomodelIndex.getSenha());
        antigoModel.setNomeUsuario((NovomodelIndex.getNomeUsuario()));
        antigoModel.setItemApagado("Item Apagado");

        return new ResponseEntity<DesafioModel>(repository.save(antigoModel), HttpStatus.OK);
    }

    @DeleteMapping(path="/apagar{id}")
    public Map<String, Boolean> Apagar(@PathVariable(value="id") String id)   throws ResourceNotFoundException{
        DesafioModel localId = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item Não encontrado!! :: " + id));
        repository.deleteById(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Apagado!!",Boolean.TRUE);
        return response;
    }
}

