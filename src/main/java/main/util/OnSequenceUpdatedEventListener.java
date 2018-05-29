package main.util;

import main.service.SequenceService;
import main.model.*;

public interface OnSequenceUpdatedEventListener {
    void onSequenceUpdated(Sequence sequence);
}
