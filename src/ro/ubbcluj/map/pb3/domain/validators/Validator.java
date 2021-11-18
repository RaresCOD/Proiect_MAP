package ro.ubbcluj.map.pb3.domain.validators;

/**
 *
 * @param <T> entity
 */
public interface Validator<T> {
    /**
     *
     * @param entity entity
     * @throws ValidationException validation exception
     */
    void validate(T entity) throws ValidationException;
}