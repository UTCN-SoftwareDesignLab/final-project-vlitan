package main.service;

import main.model.Sequence;
import main.repository.SequenceRepository;
import main.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class SequenceServiceImpl implements SequenceService {
    @Autowired
    private SequenceRepository sequenceRepository;

    @Override
    public Notification<Boolean> save(Sequence sequence) {
        return AbstractRepoAdapter.save(sequenceRepository, sequence);
    }

    @Override
    public Notification<Boolean> delete(Sequence sequence) {
        return AbstractRepoAdapter.delete(sequenceRepository, sequence);
    }

    @Override
    public Notification<Boolean> deleteById(Integer id) {
        return AbstractRepoAdapter.deleteById(sequenceRepository, id);
    }

    @Override
    public Optional<Sequence> findById(Integer id) {
        return AbstractRepoAdapter.findById(sequenceRepository, id);
    }
}
