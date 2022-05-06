package main;

public class CloudComputingController {
    AI ai = new AI();
    DataProcessing dataProcessing = new DataProcessing();
    DatabaseManager databaseManager = new DatabaseManager();
    public void dataProcessing(byte[] Rawdata){
        byte[] result;
        dataProcessing.ReceiveRawData(Rawdata);
        result = dataProcessing.ReturnProcessedData();
        ai.Analyzing(result);
    }
    public void sendNotification(){
        //notification.push("今天有偵測出你有帕金森症的可能，趕緊開啟APP查看吧!")
    }
    public String RetrieveDataBaseResult(String dateTime){
        return databaseManager.getData(dateTime); //getData("datetime")
    }
}
