import kmalfa.ReceiveMessageInterface;
import kmalfa.User;
import kmalfa.utils.PinCodeAnalyzer;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.Math.round;

public class RMITest {
    private static final String SERVER_ADDRESS = "13.81.213.191", REGISTRY_NAME = "bankServer";
    private static final int SERVER_PORT = 1099;
    private static ReceiveMessageInterface rmiServer;
    private static boolean isServerOK = true;
    private String us1 = "5198874239016512";
    private String us2 = "5821943277541234";
    private int us1oldPin = 1111;
    private int us1newPin = 1112;
    private int us2pin = 7643;

    @Test
    public void changePinTest() {
        connect();
        User user;
        try {
//            Assert.assertFalse(rmiServer.changePIN("fsdgshdf", 32525, 343, 34));
            int op = PinCodeAnalyzer.generateOp();
            int pin = PinCodeAnalyzer.generatePin(us1oldPin, op);
            String cardHolderName = rmiServer.verifyPIN(us1, pin, op);
            if (cardHolderName != null) {
                System.out.println("Test Pin change");
                user = new User(cardHolderName, us1, pin, op);
                int pinC = PinCodeAnalyzer.generatePin(us1newPin, user.getOp());
                Assert.assertTrue(rmiServer.changePIN(us1, pin, pinC, user.getOp()));
                Assert.assertTrue(rmiServer.changePIN(us1, pinC, pin, user.getOp()));
                Assert.assertFalse(rmiServer.changePIN(us1, pinC, pin, user.getOp()));
            }
//            Assert.assertTrue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void withdrawReplenishTest() {
        connect();
        User user;
        try {
//            Assert.assertFalse(rmiServer.changePIN("fsdgshdf", 32525, 343, 34));
            int op = PinCodeAnalyzer.generateOp();
            int pin = PinCodeAnalyzer.generatePin(Integer.parseInt("1111"), op);
            String cardHolderName = rmiServer.verifyPIN(us1, pin, op);
            if (cardHolderName != null) {
                System.out.println("Test withdrawal, replenishment and balance check");
                user = new User(cardHolderName, us1, pin, op);
                float bal = rmiServer.getBalance(us1, pin, op);
                Assert.assertTrue(rmiServer.replenishAccount(us1, pin, user.getOp(), 100));
                float bal1 = rmiServer.getBalance(us1, pin, op);
                Assert.assertEquals(100, round(bal1 - bal));
                Assert.assertTrue(rmiServer.withdrawMoney(us1, pin, user.getOp(), 100));
                float bal2 = rmiServer.getBalance(us1, pin, op);
                Assert.assertEquals(0, round(Math.abs(bal2 - bal)));
            }
//            Assert.assertTrue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferTest() {
        connect();
        User user;
        User user1;
        try {
//            Assert.assertFalse(rmiServer.changePIN("fsdgshdf", 32525, 343, 34));
            int op = PinCodeAnalyzer.generateOp();
            int pin = PinCodeAnalyzer.generatePin(us1oldPin, op);
            int op1 = PinCodeAnalyzer.generateOp();
            int pin1 = PinCodeAnalyzer.generatePin(us2pin, op1);
            String cardHolderName = rmiServer.verifyPIN(us1, pin, op);
            String cardHolderName1 = rmiServer.verifyPIN(us2, pin1, op1);
            if (cardHolderName != null && cardHolderName1 != null) {
                System.out.println("Test transfer");
                user = new User(cardHolderName, us1, pin, op);
                user1 = new User(cardHolderName1, us2, pin1, op1);
                float bal1BefTrans = rmiServer.getBalance(us1, pin, op);
                float bal2BefTrans = rmiServer.getBalance(us2, pin1, op1);
                Assert.assertTrue(rmiServer.transfer(us1, us2, pin, user.getOp(), 100));
                float bal1AfTrans = rmiServer.getBalance(us1, pin, op);
                float bal2AfTrans = rmiServer.getBalance(us2, pin1, op1);
                Assert.assertEquals(100, round(bal1BefTrans - bal1AfTrans));
                Assert.assertEquals(100, round(bal2AfTrans - bal2BefTrans));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void connect() {
        try {
            Registry registry = LocateRegistry.getRegistry(SERVER_ADDRESS, SERVER_PORT);
            rmiServer = (ReceiveMessageInterface) registry.lookup(REGISTRY_NAME);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
}
