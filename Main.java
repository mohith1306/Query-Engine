public class Main {
    public static void main(String[] args) throws Exception {
        StorageEngine storageEngine = new StorageEngine();
        QueryExecutor executor = new QueryExecutor(storageEngine);
        executor.execute(QueryParser.parse("PUT key1 value1"));
        executor.execute(QueryParser.parse("PUT key2 value2"));
        executor.execute(QueryParser.parse("PUT key3 value3"));
        executor.execute(QueryParser.parse("PUT key4 value4"));
        executor.execute(QueryParser.parse("PUT key5 value5"));

        executor.execute(QueryParser.parse("PUT key6 value6"));
        executor.execute(QueryParser.parse("PUT key7 value7"));

        executor.execute(QueryParser.parse("GET key1"));
        executor.execute(QueryParser.parse("GET key2"));

        executor.execute(QueryParser.parse("DELETE key1"));
        executor.execute(QueryParser.parse("DELETE key2"));

        executor.execute(QueryParser.parse("GET key1"));
        executor.execute(QueryParser.parse("GET key2"));
    }
}
