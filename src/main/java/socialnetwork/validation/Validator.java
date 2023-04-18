package socialnetwork.validation;

import socialnetwork.validation.exceptions.ValidationException;

public interface Validator<Entity> {
    void validate(Entity entity) throws ValidationException;
}
