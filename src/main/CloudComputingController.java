package main;
import java.util.LinkedList;
import java.util.Queue;
public class CloudComputingController {
    static AI ai;
    //123
    static DataProcessing dataProcessing;
    static DatabaseManager databaseManager;
    public static void main(String args[])  //static method
    {
        ai = new AI();
        //123
        dataProcessing = new DataProcessing();
        databaseManager = new DatabaseManager();
    }

    //@app.route('/dataProcessing, method=['Post']')

    public void dataProcessing(byte[] RawData){
        Queue<byte[]> queue = new LinkedList<>();
        byte[] result;
        boolean analyzingDone = true;
        queue.add(RawData);
        while(!queue.isEmpty() && analyzingDone){
            analyzingDone = false;
            result = dataProcessing.ProcessData(queue.poll());
            analyzingDone = ai.Analyzing(result);
            sendNotification();
        }
    }
    public void sendNotification(){
        //notification.push("今天有偵測出你有帕金森症的可能，趕緊開啟APP查看吧!")
    }
    //@app.route('/RetrieveDataBaseResult, method=['GET']')
    public String RetrieveDataBaseResult(String dateTime){
        return databaseManager.getData(dateTime); //getData("datetime") //用api傳回值給GUI
    }
}
