package main.util;

import java.util.List;
import java.util.stream.Collectors;

public class ResultFetchException extends RuntimeException {
    private final List<String> errors;

    public ResultFetchException(List<String> errors) {
        super("Failed to fetch the result as the operation encountered errors.");
        this.errors = errors;
    }

    @Override
    public String toString() {//TODO inspect this. streams dont seem to work in the spring, check again in summer
        return errors.stream().map(Object::toString).collect(Collectors.joining(",")) + super.toString();
    }
}