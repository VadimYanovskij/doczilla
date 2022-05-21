public class Task1 {

    public static final String ROOT_FOLDER_PATH = "task1/Folders";
    public static final String RESULT_FILE_PATH = "task1/Result/result.txt";

    public static void main(String[] args) {
        FilesMerger merger = new FilesMerger(ROOT_FOLDER_PATH, RESULT_FILE_PATH);
        merger.doMerge();
    }
}
