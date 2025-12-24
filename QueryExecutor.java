public class QueryExecutor {
    private final StorageEngine storageEngine;
    public QueryExecutor(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }
    public void execute(QueryParser.Query query) throws Exception {
        if (query == null) {
            throw new IllegalArgumentException("Query is null");
        }
        String type = query.type;
        if (type.equals("PUT")) {
            storageEngine.set(query.key, query.value);
            System.out.println("OK");
        } 
        else if (type.equals("GET")) {
            String result = storageEngine.get(query.key);
            if (result == null) {
                System.out.println("NOT FOUND");
            } else {
                System.out.println(result);
            }
        } 
        else if (type.equals("DELETE")) {
            storageEngine.delete(query.key);
            System.out.println("OK");
        } 
        else {
            throw new IllegalArgumentException("Unsupported query type: " + type);
        }
    }
}
