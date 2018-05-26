package main.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import main.model.Sequence;
import main.util.Notification;

import java.util.List;
import java.util.Optional;

public interface SequenceService {
    Notification<Boolean> save(Sequence sequence);
    Notification<Boolean> delete(Sequence sequence);
    Notification<Boolean> deleteById(Integer id);
    Optional<Sequence> findById(Integer id);
    List<Sequence> findByUserEmail(String email);
}
