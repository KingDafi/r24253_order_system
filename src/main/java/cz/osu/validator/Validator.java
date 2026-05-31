package cz.osu.validator;

public interface Validator<T> {
    void validate(T value) throws Exception;
}
