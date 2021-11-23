package ro.ubbcluj.map.pb3.domain.validators;

import ro.ubbcluj.map.pb3.domain.Prietenie;

/**
 * FriendshipValidator
 */
public class FriendshipValidator implements Validator<Prietenie>{

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        if(entity.getId().getLeft() < 0 || entity.getId().getRight() < 0) throw new ValidationException("Id-urile user-ilor nu pot fi negative");
    }
}
