package io.github.przbetkier.tuscan.domain.match;

public class MatchNotFoundException extends RuntimeException {
    MatchNotFoundException(String message) {
        super(message);
    }
}
