package net.ersiner.jacksonetty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Operation {
    private final char operation;
    private final float operand1;
    private final float operand2;

    @JsonCreator
    Operation(@JsonProperty("opr") char operation,
              @JsonProperty("opd1") float operand1,
              @JsonProperty("opd2") float operand2) {
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @JsonProperty("opr")
    public char getOperation() {
        return operation;
    }

    @JsonProperty("opd1")
    public float getOperand1() {
        return operand1;
    }

    @JsonProperty("opd2")
    public float getOperand2() {
        return operand2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Operation operation1 = (Operation) o;
        return Objects.equals(operation, operation1.operation) &&
               Objects.equals(operand1, operation1.operand1) &&
               Objects.equals(operand2, operation1.operand2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, operand1, operand2);
    }
}
