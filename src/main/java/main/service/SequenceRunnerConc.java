package main.service;

import main.model.Sequence;
import main.util.OnSequenceUpdatedEventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SequenceRunnerConc extends SequenceRunner {

    private  List<Sequence> sequences = new ArrayList<>();
    Runnable runnable = () -> {
        Iterator<Sequence> it = sequences.iterator();
        while (it.hasNext()){
            Sequence sequence = it.next();
            if (sequence.isEnabled()) {
                sequence.iterateByAmount(1);
                if (sequence.hasCompleted()) {
                    it.remove();
                }
            }
        }
    };
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    synchronized void removeSequence(Sequence sequence) {
        this.sequences.remove(sequence);
    }

    @Override
    synchronized void addSequence(Sequence sequence) {
        this.sequences.add(sequence);
    }

    @Override
    synchronized void addSequences(List<Sequence> sequenceList) {
        this.sequences.addAll(sequenceList);
    }

    @Override
    synchronized void pauseSequence(Sequence sequence) {
        int index = this.sequences.indexOf(sequence);
        if (index > 0){
            this.sequences.get(index).setEnabled(Boolean.FALSE);
        }
    }

    @Override
    synchronized void resumeSequence(Sequence sequence) {
        int index = this.sequences.indexOf(sequence);
        if (index > 0){
            this.sequences.get(index).setEnabled(Boolean.TRUE);
        }
    }

    @Override
    void start() { executor.scheduleAtFixedRate(runnable, 0, this.getTickPeriod(), TimeUnit.SECONDS);
    }

    @Override
    void stop() {
        //TODO
    }
}
