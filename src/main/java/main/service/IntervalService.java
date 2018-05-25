package main.service;

import main.model.Interval;
import main.util.Notification;

import java.util.Optional;

public interface IntervalService {
    Notification<Boolean> save(Interval interval);
    Notification<Boolean> delete(Interval interval);
    Notification<Boolean> deleteById(Integer id);
    Optional<Interval> findById(Integer id);
}
