package main.service;

import main.model.Interval;
import main.repository.IntervalRepository;
import main.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IntervalServiceImpl implements IntervalService {
    @Autowired
    private IntervalRepository intervalRepository;

    @Override
    public Notification<Boolean> save(Interval interval) {
        return AbstractRepoAdapter.save(intervalRepository, interval);
    }

    @Override
    public Notification<Boolean> delete(Interval interval) {
        return AbstractRepoAdapter.delete(intervalRepository, interval);
    }

    @Override
    public Notification<Boolean> deleteById(Integer id) {
        return AbstractRepoAdapter.deleteById(intervalRepository, id);
    }

    @Override
    public Optional<Interval> findById(Integer id) {
        return AbstractRepoAdapter.findById(intervalRepository, id);
    }
}
