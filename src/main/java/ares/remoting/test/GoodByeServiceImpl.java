package ares.remoting.test;

public class GoodByeServiceImpl implements GoodByeService{
    @Override
    public String sayGoodBye(String somebody) {
        return "goodBye "+ somebody + "!";
    }
}
