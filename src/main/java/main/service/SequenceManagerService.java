package main.service;

import main.model.Sequence;
import main.util.Notification;
import main.util.OnSequenceUpdatedEventListener;

import java.util.Optional;

public interface SequenceManagerService {
    Notification<Boolean> start(Sequence sequence);
    Notification<Boolean> pause(Sequence sequence);
    Notification<Boolean> resume(Sequence sequence);
    void pause();
    void resume();
    Optional<Sequence> getActiveSequenceByUserId(Integer id);
}
