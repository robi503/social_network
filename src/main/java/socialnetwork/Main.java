package socialnetwork;

import socialnetwork.UI.Interface;
import socialnetwork.validation.FriendValidator;
import socialnetwork.validation.UserValidator;


public class Main {
    public static void main(String[] args) {
        UserValidator userValidator = new UserValidator();
        FriendValidator friendValidator = new FriendValidator();
        Interface anInterface= new Interface(friendValidator,userValidator);
        anInterface.run();
    }
}