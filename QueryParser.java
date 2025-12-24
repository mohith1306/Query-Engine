public class QueryParser {
    public static class Query {
        public String type;
        public String key;
        public String value;
    }
    public static Query parse(String input) {

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty query");
        }

        String[] tokens = input.trim().split("\\s+");
        String command = tokens[0].toUpperCase();

        Query q = new Query();
        q.type = command;

        if (command.equals("PUT")) {

            if (tokens.length != 3) {
                throw new IllegalArgumentException("PUT requires exactly 2 arguments");
            }

            q.key = tokens[1];
            q.value = tokens[2];

        } 
        else if (command.equals("GET")) {

            if (tokens.length != 2) {
                throw new IllegalArgumentException("GET requires exactly 1 argument");
            }

            q.key = tokens[1];
            q.value = null;

        } 
        else if (command.equals("DELETE")) {

            if (tokens.length != 2) {
                throw new IllegalArgumentException("DELETE requires exactly 1 argument");
            }

            q.key = tokens[1];
            q.value = null;

        } 
        else {
            throw new IllegalArgumentException("Unknown command: " + command);
        }

        return q;
    }
}
