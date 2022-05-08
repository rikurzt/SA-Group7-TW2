package main;

public class CloudComputingController {
    static AI ai = new AI();
    //123
    static DataProcessing dataProcessing = new DataProcessing();
    static DatabaseManager databaseManager = new DatabaseManager();
    public static void main(String args[])  //static method
    {
        //objectArray = [command: "processData", RawData: {123,44,34,2}] or [Command: "retrieveResult", DateTime: "2022/03/28"]
        byte[] RawData = {123,44,34,2}; //objectArray.RawData
        String dateTime = "2022/03/28"; //objectArray.DateTime
        String command ="processData"; //objectArray.Command
        switch(command) {
            case "processData":
                dataProcessing(RawData);
                break;
            case "retrieveResult":
                RetrieveDataBaseResult(dateTime);
                break;
        }
    }
    public static void dataProcessing(byte[] Rawdata){
        byte[] result;
        boolean analyzingDone;
        dataProcessing.ReceiveRawData(Rawdata);
        result = dataProcessing.ReturnProcessedData();
        analyzingDone = ai.Analyzing(result);
        if(analyzingDone){
            sendNotification();
        }
    }
    public static void sendNotification(){
        //notification.push("今天有偵測出你有帕金森症的可能，趕緊開啟APP查看吧!")
    }
    public static String RetrieveDataBaseResult(String dateTime){
        return databaseManager.getData(dateTime); //getData("datetime") //用api傳回值給GUI
    }
}
