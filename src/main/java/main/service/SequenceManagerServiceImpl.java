package main.service;

import main.model.Interval;
import main.model.Sequence;
import main.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SequenceManagerServiceImpl implements SequenceManagerService, Observer {
    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private IntervalService intervalService;

    //TODO maybe autowire this, after it works ok.
    private SequenceRunner sequenceRunner = new SequenceRunnerConc();

    public SequenceManagerServiceImpl(){
        sequenceRunner.start();
    }

    @Override
    public Notification<Boolean> start(Sequence sequence) {
        if (sequenceRunner.getSequences().contains(sequence)){
            sequenceRunner.getSequences().remove(sequence);
        }
        sequence.refresh();
        sequence.setEnabled(true);
        sequence.addObserver(this);
        sequenceRunner.addSequence(sequence);
        sequenceService.save(sequence);
        return new Notification<>(Boolean.TRUE);
    }

    @Override
    public Notification<Boolean> pause(Sequence sequence) {
        sequence.setEnabled(false);
        sequenceService.save(sequence);
        sequenceRunner.pauseSequence(sequence);
        return new Notification<>(Boolean.TRUE);
    }

    @Override
    public Notification<Boolean> resume(Sequence sequence) {
        sequence.setEnabled(true);
        sequenceService.save(sequence);
        sequenceRunner.resumeSequence(sequence);
        return new Notification<>(Boolean.TRUE);
    }


    @Override
    public void pause() {
        //pause(sequenceService.getActiveSequenceByUserId())
    }

    @Override
    public void resume() {

    }

    @Override
    public Optional<Sequence> getActiveSequenceByUserId(Integer id) {
        List<Sequence> sequences = sequenceRunner.getSequences();
        sequences = sequences.stream().filter(s -> s.getUser().getId().equals(id)).collect(Collectors.toList());
        if (sequences.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(sequences.get(0));
        }
    }


    public void onSequenceUpdated(Sequence sequence) {
        sequenceService.save(sequence);
        Interval interval = sequence.getIntervals().get(sequence.getCurrentIntervalIndex());
        System.out.println("[SequenceManager] sequence " + sequence.getName() + " at interval " + interval.getName());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof Sequence){
            onSequenceUpdated((Sequence)observable);
        }
    }
}
