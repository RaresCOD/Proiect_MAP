package ro.ubbcluj.map.pb3.repository.file;

import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.Validator;


import java.util.List;

/**
 * user file class
 */
public class UtilizatorFile extends AbstractFileRepository<Long, Utilizator> {

    /**
     *
     * @param fileName fn
     * @param validator val
     */
    public UtilizatorFile(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> attributes) {
        Utilizator ut = new Utilizator(attributes.get(1), attributes.get(2));
        ut.setId(Long.parseLong(attributes.get(0)));
        return ut;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId().toString() + ";" + entity.getLastName() + ";" + entity.getFirstName();
    }
}
