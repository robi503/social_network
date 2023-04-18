package socialnetwork.validation;

import socialnetwork.domain.Friend;
import socialnetwork.validation.exceptions.ValidationException;

public class FriendValidator implements Validator<Friend>{

    @Override
    public void validate(Friend friend) throws ValidationException {
        if(friend.getId1() == friend.getId2())
        {
            throw new ValidationException("Id1 trebuie sa fie diferit de id2.");
        }
    }
}
