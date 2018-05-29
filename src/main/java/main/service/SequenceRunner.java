package main.service;

import main.model.*;

import java.util.List;

public abstract class SequenceRunner {
    public static final int SEQUENCE_DEFAULT_PERIOD = 1;//in seconds
    private int tickPeriod = SEQUENCE_DEFAULT_PERIOD;

    abstract void removeSequence(Sequence sequence);
    abstract void addSequence(Sequence sequence);
    abstract void addSequences(List<Sequence> sequenceList);
    abstract void pauseSequence(Sequence sequence);
    abstract void resumeSequence(Sequence sequence);
    abstract void start();
    abstract void stop();
    abstract List<Sequence> getSequences();

    public void setTickPeriod(int period){
        this.tickPeriod = period;
    }

    public int getTickPeriod(){
        return this.tickPeriod;
    }
}
