package de.cofinpro.demo.pact.spaceships.helper;

import java.util.Objects;

public class PactState {

    private String state;

    public PactState() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PactState pactState = (PactState) o;
        return Objects.equals(state, pactState.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    @Override
    public String toString() {
        return "PactState{" +
                "state='" + state + '\'' +
                '}';
    }
}
