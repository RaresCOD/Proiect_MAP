package ro.ubbcluj.map.pb3.repository.file;

import ro.ubbcluj.map.pb3.domain.Prietenie;
import ro.ubbcluj.map.pb3.domain.Tuple;
import ro.ubbcluj.map.pb3.domain.validators.Validator;

import java.util.List;

/**
 * friendship class
 */
public class FriendshipFile extends AbstractFileRepository<Tuple<Long, Long>, Prietenie>{

    /**
     *
     * @param fileName - filename
     * @param validator - firend validator
     */
    public FriendshipFile(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);
    }

    @Override
    protected Prietenie extractEntity(List<String> attributes) {
        Prietenie prietenie = new Prietenie();
        Tuple<Long, Long> tuple = new Tuple(Long.valueOf(attributes.get(0)), Long.valueOf(attributes.get(1)));
        prietenie.setId(tuple);
        return prietenie;
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft() + ";" + entity.getId().getRight();
    }
}
