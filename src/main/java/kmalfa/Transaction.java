package kmalfa;//package kmalfa;
//
//import java.rmi.RemoteException;
//import java.util.Date;
//import java.util.Map;
///**
// * This abstract class is implemented by the various transaction carried out during the customer.
// *
// * @author ARCHIT
// *
// */
//public abstract class Transaction {
//    private static int transid = 1;    // Maintaining the transaction id for each request for future purpose
//
//    protected Transaction() {
//        transid++;
//    }
//
//    public Map<String,String> performTransaction(User user, ReceiveMessageInterface rmiServer, String out, float amount, int type, Date date) throws RemoteException {
//        Map<String, String> message = null;
//        int val  = getCustomerRequest();
//        if(val != -1) {
//            message = rmiServer.messageToPerform(user.getCard(),  out, amount, type, date );
//        }
//        return message;
//    }
//    protected abstract int getCustomerRequest();
//};
