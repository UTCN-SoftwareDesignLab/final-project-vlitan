package main.service;

import main.model.Interval;
import main.model.Sequence;
import main.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

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
        sequence.setEnabled(true);
        sequence.addObserver(this);
        sequenceService.save(sequence);
        sequenceRunner.addSequence(sequence);
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
    public void onSequenceUpdated(Sequence sequence) {
        sequenceService.save(sequence);
        System.out.println("[SequenceManager] sequence " + sequence.getName() + " ticked");
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
