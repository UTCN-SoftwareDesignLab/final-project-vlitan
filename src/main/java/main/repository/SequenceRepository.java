package main.repository;

import main.model.Sequence;
import org.hibernate.boot.model.source.spi.JpaCallbackSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Integer> {
    List<Sequence> findAllByUserEmail(String email);
}
