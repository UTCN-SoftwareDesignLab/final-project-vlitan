package main.service;

import main.model.Sequence;
import main.util.Notification;
import main.util.OnSequenceUpdatedEventListener;

public interface SequenceManagerService extends OnSequenceUpdatedEventListener {
    Notification<Boolean> start(Sequence sequence);
    Notification<Boolean> pause(Sequence sequence);
    Notification<Boolean> resume(Sequence sequence);
    void pause();
    void resume();
}
