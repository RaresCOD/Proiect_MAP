package ro.ubbcluj.map.pb3.domain.validators;

import ro.ubbcluj.map.pb3.Message.Message;

public class MessageValidator implements Validator<Message>{
    @Override
    public void validate(Message entity) throws ValidationException {
        if(entity.getMsg() == "") throw  new ValidationException("Mesajul nu poate fi gol");
    }
}
