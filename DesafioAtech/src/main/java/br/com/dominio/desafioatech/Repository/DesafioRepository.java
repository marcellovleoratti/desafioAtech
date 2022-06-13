package br.com.dominio.desafioatech.Repository;

import br.com.dominio.desafioatech.Model.DesafioModel;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@EnableScan
public interface DesafioRepository extends JpaRepository<DesafioModel,String> {

    @Override
    Optional<DesafioModel> findById(String s);

    @Query("Select c.nome, c.email, c.nomeUsuario from Usuario c")
    List<DesafioModel> findByUsuario();

    Stream<DesafioModel> save();
}
